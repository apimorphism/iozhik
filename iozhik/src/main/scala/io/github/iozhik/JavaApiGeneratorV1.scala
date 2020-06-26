package io.github.iozhik

import io.github.iozhik.Generator.Model._
import cats.implicits._
import com.google.googlejavaformat.java.Formatter

class JavaApiGeneratorV1 extends Generator {

  private val javaFormatter = new Formatter()

  def delimiter: String = "\n"

  private val keywords: Set[String] = Set(
    "interface", "class", "object", "void"
  )

  override def gen(x: Generator.Model.Space): Either[String, List[Code]] = {
    val strucs = getAllStrucs(x)
    val services = getAllServices(x)

    implicit val symtable: Symtable = x.symbol
    implicit val space: Space = x

    for {
      strucsSrcs        <- strucs.flatTraverse(s => if (s.isEnum) enumToCode(s) else strucToCode(s))
      servicesSrcs      <- services.flatTraverse(s => serviceToCode(s))
      servicesImplSrcs  <- services.flatTraverse(s => genServiceUnirestImpl(s))
      serviceEntitySrcs <- services.flatTraverse(genServiceEntities)
    } yield {
      strucsSrcs ++ servicesSrcs ++ serviceEntitySrcs ++ servicesImplSrcs
    }
  }

  private def serviceToCode(service: Servc)(implicit symt: Symtable): Either[String, List[Code]] = {
    val serviceName = service.kind.name
    val packageCode = service.path.reverse.tail.mkString(".")

    val defs = service.items.collect{ case a: Defun => a }

    val body =
      javaFormatter.formatSource(
        s"""
           | package $packageCode;
           |
           | public interface $serviceName {
           |   ${defs.map(genDefun).mkString("\n")}
           | }
      """.stripMargin)

    val res = List(Code(
      name = s"$serviceName.java",
      path = packageCode.replace('.', '/'),
      body = body
    ))

    Right(res)
  }

  private def genServiceUnirestImpl(service: Servc)(implicit symt: Symtable): Either[String, List[Code]] = {
    val serviceName = service.kind.name
    val packageCode = service.path.reverse.tail.mkString(".")

    val defs = service.items.collect{ case a: Defun => a }
    val implName = "ApiUnirestImpl"

    val body =
      javaFormatter.formatSource(
        s"""
           | package $packageCode;
           |
           | import com.mashape.unirest.http.HttpResponse;
           | import com.mashape.unirest.http.Unirest;
           | import com.mashape.unirest.http.exceptions.UnirestException;
           |
           | public class $implName implements $serviceName {
           |
           |   String baseUrl;
           |
           |   public $implName(String baseUrl) {
           |     this.baseUrl = baseUrl;
           |   }
           |
           |   ${defs.map(genDefunUnirestImpl).mkString("\n")}
           |
           |   // TODO: implement Either, wrap result to it, eliminate exception throwing
           |   private <S, Q> S post(Q x, String method, Class<? extends S> resClass) throws UnirestException {
           |       HttpResponse<S> update = Unirest.post(baseUrl + "/" + method)
           |               .header("Content-Type", "application/json")
           |               .body(x)
           |               .asObject(resClass);
           |
           |       return update.getBody();
           |   }
           |
           |   private <S> S post(String method, Class<? extends S> resClass) throws UnirestException {
           |     HttpResponse<S> update =
           |         Unirest.post(baseUrl + "/" + method)
           |             .header("Content-Type", "application/json")
           |             .asObject(resClass);
           |
           |     return update.getBody();
           |   }
           | }
      """.stripMargin)

    val res = List(Code(
      name = s"$implName.java",
      path = packageCode.replace('.', '/'),
      body = body
    ))

    Right(res)
  }

  private def genServiceEntities(service: Servc)(implicit symt: Symtable, space: Space): Either[String, List[Code]] = {
    service.items.collect{ case a: Defun => a }.flatTraverse { x =>
      for {
        rawDom <- x.dom.fold(
          y => symt.find(y, Option(x.kind)),
          y => Option(y: Sym)
        )
            .collect { case s: Struc => s }
            .toRight(s"Can't find ${x.kind}")
        rawCod <- x.cod.fold(
          y => symt.find(y, Option(x.kind)),
          y => Option(y: Sym)
        )
            .collect { case s: Struc => s }
            .toRight(s"Can't find ${x.kind}")
        dom <- strucToCode(rawDom.copy(kind = Option(Kind(x.kind.name.capitalize + "Req"))))
        cod <- strucToCode(rawCod.copy(kind = Option(Kind(x.kind.name.capitalize + "Res"))))
      } yield {
        dom ++ cod
      }
    }
  }

  private def genDefun(defun: Defun)(implicit symt: Symtable): String = {
    val name = defun.kind.name

    val rawDom = defun.dom.fold(
        y => symt.find(y, Option(defun.kind)),
        y => Option(y : Sym)
      )
      .collect { case s: Struc => s }

    rawDom.map {
      dom =>
        val domain = if (dom.fields.isEmpty && dom.usings.isEmpty) "" else s"${name.capitalize}Req x"
        s"${name.capitalize}Res ${defun.kind.name}($domain) throws Exception;"
    }.getOrElse("")
  }

  private def genDefunUnirestImpl(defun: Defun)(implicit symt: Symtable): String = {
    val name = defun.kind.name

    val rawDom = defun.dom.fold(
      y => symt.find(y, Option(defun.kind)),
      y => Option(y : Sym)
    )
        .collect { case s: Struc => s }

    rawDom.map {
      dom =>
        if (dom.fields.isEmpty && dom.usings.isEmpty)
          s"""
             | public ${name.capitalize}Res ${defun.kind.name}() throws UnirestException {
             |   return post("${defun.kind.name}", ${name.capitalize}Res.class);
             | }
             | """.stripMargin
        else {
          s"""
             | public ${name.capitalize}Res ${defun.kind.name}(${name.capitalize}Req x) throws UnirestException {
             |   return post(x, "${defun.kind.name}", ${name.capitalize}Res.class);
             | }
             | """.stripMargin
        }
    }.getOrElse("")
  }

  private def strucToCode(struc: Struc, interface: Option[Struc] = None)(implicit symt: Symtable, space: Space): Either[String, List[Code]] = {
    val packageCode = struc.path.reverse.tail.mkString(".")
    val strucName = struc.kind.map(_.name).get

    val interfaceName = interface.flatMap(_.kind).map(_.name)
    val implementCode =
      if (interfaceName.isDefined)
        "implements " + interfaceName.get
      else
        ""

    val imports =
      (getUsingFields(struc) ++ struc.fields).flatMap { f =>
        val strucsToImport =
            (f.kind.params :+ f.kind).flatMap { kp =>
              symt.find(kp).collect{ case s: Struc => s}
            }

        strucsToImport.map { s =>
          s"import ${s.path.reverse.tail.mkString(".") + ".*;"}"
        }.distinct
      }.distinct.mkString("\n")

    val body =
      javaFormatter.formatSource(
      s"""
        | package $packageCode;
        |
        | import java.util.*;
        | import com.fasterxml.jackson.databind.PropertyNamingStrategy;
        | import com.fasterxml.jackson.databind.annotation.JsonNaming;
        | $imports
        |
        | ${if (true) "@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)" else ""}
        | public class $strucName $implementCode {
        |   ${genFields(struc).mkString("\n")}
        |   ${genWhatImpl(interfaceName, strucName)}
        |   ${struc.typet().map(tt => s"""public String getDiscriminator() { return "${tt.tag}"; }""").getOrElse("")}
        |   ${genNoArgConstructor(strucName)}
        |   ${genAllArgConstructor(struc)}
        |   ${genGSetter(struc).mkString("\n")}
        |   ${genIsAsMethodsImpl(interface.map(_.leaves).getOrElse(List.empty).map(s => s -> (s == struc)))}
        |   ${genEqualsMethod(struc)}
        |   ${genToStringMethod(struc)}
        | }
      """.stripMargin)

    Right(List(Code(
      name = s"$strucName.java",
      path = packageCode.replace('.', '/'),
      body = body
    )))
  }

  // considered that enum doesn't have inner enums
  private def enumToCode(struc: Struc)(implicit symt: Symtable, space: Space): Either[String, List[Code]] =
    if (!struc.isEnum) {
      Left("Struc is not enum")
    } else {
      val packageCode = struc.path.reverse.tail.mkString(".")
      val enumName = struc.kind.map(_.name).get
      val subStrucNames = getSubStrucs(struc).flatMap(_.kind).map(_.name)
      val body =
      if (subStrucNames.isEmpty) {
        javaFormatter.formatSource(
          s"""
             | package $packageCode;
             |
             | public interface $enumName {
             | }
            """.stripMargin)
      } else {
        javaFormatter.formatSource(
          s"""
             | package $packageCode;
             |
             | import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
             | import $packageCode.deserializators.${enumName}Deserializer;
             |
             | import java.util.*;
             |
             | @JsonDeserialize(using = ${enumName}Deserializer.class)
             | public interface $enumName {
             |   ${genEnum(enumName, subStrucNames)}
             |   ${enumName}s what();
             |   ${genGSetterProto(struc).mkString("\n")}
             |   ${genIsAsMethods(subStrucNames)}
             | }
          """.stripMargin)
      }

      for {
        codes <- getSubStrucs(struc).flatTraverse(x => strucToCode(x, Some(struc)))
        wrappCodes <- struc.wrapps.flatTraverse(x => wrappToCode(struc.kind.getOrElse(Kind("$$")), x))
      } yield {
        List(Code(body, s"$enumName.java", packageCode.replace('.', '/'))) ++
            (if (subStrucNames.isEmpty) List.empty[Code] else List(genCustomDeserializer(struc))) ++
            codes ++ wrappCodes
      }
    }

  def wrappToCode(parentKind: Kind, x: Wrapp)
              (implicit symt: Symtable, space: Space): Either[String, List[Code]] = {
    val packageCode = parentKind.path.reverse.tail.mkString(".")
    for {
      parent <- genKind(parentKind)
      name <- genKind(x.name)
    } yield {
      val ttype = scalaToJavaType(x.target);
      List(Code(
        name = name + ".java",
        body =
            s"""
               | package $packageCode;
               |
               | import com.fasterxml.jackson.annotation.JsonValue;
               |
               | public class $name implements $parent {
               |   $ttype ${x.fname};
               |
               |   public $name() {}
               |
               |   public $name($ttype v) {
               |     this.${x.fname} = v;
               |   }
               |
               |   public $name set${x.fname.capitalize}($ttype v) {
               |     this.${x.fname} = v;
               |     return this;
               |   }
               |
               |   @JsonValue
               |   public $ttype get${x.fname.capitalize}() {
               |     return this.${x.fname};
               |   }
               | }
               | """.stripMargin,
        path = parentKind.path.reverse.tail.mkString("/")
      ))
    }
  }

  def genKind(x: Kind)(implicit symt: Symtable, space: Space): Either[String, String] = {
    for {
      items <- x.params.map(genKind).sequence
      body = "[" + items.mkString(", ") + "]"
    } yield {
      sanitize(x.name) + (if (x.params.nonEmpty) body else "")
    }
  }

  def sanitize(name: String): String = {
    if (keywords.contains(name)) {
      s"`$name`"
    } else {
      name
    }
  }

  private def strucToJsonParseExpr(strucs: List[Struc])(implicit symt: Symtable): String = {
    def params(struc: Struc): String = (for {
          field <- struc.fields ++ getUsingFields(struc)
        } yield {
         val fieldType = scalaToJavaType(field.kind)
          if (javaPrimitiveTypes.contains(fieldType)) {
            s"""
               | node.get("${field.name}").as${fieldType.capitalize}()
            """.stripMargin
          } else {
            val typeReferenceStr =
              if (field.kind.params.nonEmpty)
                s"new TypeReference<$fieldType>(){}"
              else
                s"$fieldType.class"

            s"""
               | objectMapper.readValue(node.get("${field.name}").traverse(), $typeReferenceStr)
            """.stripMargin
          }
        }).mkString(",")

    val strucsGrouped = strucs.groupBy(_.typet().map(_.tag).getOrElse("none"))

    strucsGrouped.map { case (discriminator, strucss) =>
      val discrs = List.fill(strucss.length)(discriminator)
        .zip("" +: (1 until strucss.length).map(_.toString))
        .map(x => x._1 + x._2)

      strucss.zip(discrs).map { case (struc, discr) =>
        val strucName = struc.kind.map(_.name).get
        s"""
           | case "$discr":
           |   return new $strucName(${params(struc)});
        """.stripMargin
      }.mkString("\n")
    }.mkString("\n")
  }

  private def genCustomDeserializer(struc: Struc)(implicit symt: Symtable): Code = {
    val enumName = struc.kind.map(_.name).get
    val packageCode = struc.path.reverse.tail.mkString(".")

    val body = javaFormatter.formatSource(
      s"""
       | package $packageCode.deserializators;
       |
       | import java.util.*;
       |
       | import com.fasterxml.jackson.core.JsonParser;
       | import com.fasterxml.jackson.core.JsonProcessingException;
       | import com.fasterxml.jackson.core.type.TypeReference;
       | import com.fasterxml.jackson.databind.DeserializationContext;
       | import com.fasterxml.jackson.databind.JsonNode;
       | import com.fasterxml.jackson.databind.ObjectMapper;
       |
       | import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
       |
       | import $packageCode.*;
       |
       | import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
       |
       | import java.io.IOException;
       |
       | public class ${enumName}Deserializer extends StdDeserializer<$enumName> {
       |
       |   ObjectMapper objectMapper = new ObjectMapper();
       |
       |   public ${enumName}Deserializer() {
       |       this(null);
       |       objectMapper.registerModule(new Jdk8Module());
       |   }
       |
       |   public ${enumName}Deserializer(Class<$enumName> t) {
       |       super(t);
       |       objectMapper.registerModule(new Jdk8Module());
       |   }
       |
       |   @Override
       |   public $enumName deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
       |     JsonNode node = jp.getCodec().readTree(jp);
       |
       |     String type = node.get("type").asText();
       |
       |     switch (type) {
       |       ${strucToJsonParseExpr(getSubStrucs(struc))}
       |       default: throw ctxt.weirdStringException("type", $enumName.class, "Unknown discriminator");
       |     }
       |   }
       | }
      """.stripMargin)

    Code(body, s"${enumName}Deserializer.java", s"$packageCode.deserializators".replace('.', '/'))
  }

  private def genFields(struc: Struc)(implicit symt: Symtable): List[String] = {
    for {
      field <- struc.fields ++ getUsingFields(struc)
    } yield {
      s"""
         | private ${scalaToJavaType(field.kind)} ${field.name};
       """.stripMargin
    }
  }

  private def genGSetter(struc: Struc)(implicit symt: Symtable): List[String] = {
    val strucName = struc.kind.map(_.name).get
    for {
      field <- struc.fields ++ getUsingFields(struc)
    } yield {
      val fieldType = scalaToJavaType(field.kind)
      if (field.kind.name == "Option") {
        s"""
           | public Optional<$fieldType> get${field.name.capitalize}() { return Optional.ofNullable(${field.name}); }
           | public $strucName set${field.name.capitalize}($fieldType v) { this.${field.name} = v; return this; }
        """.stripMargin
      } else {
        s"""
           | public $fieldType get${field.name.capitalize}() { return this.${field.name}; }
           | public $strucName set${field.name.capitalize}($fieldType v) { this.${field.name} = v; return this; }
        """.stripMargin
      }
    }
  }

  private def genGSetterProto(struc: Struc)(implicit symt: Symtable): List[String] = {
    val strucName = struc.kind.map(_.name).get
    for {
      field <- struc.fields ++ getUsingFields(struc)
    } yield {
      val fieldType = scalaToJavaType(field.kind)
      s"""
         | ${struc.typet().map(_ => "String getDiscriminator();").getOrElse("")}
         | $fieldType get${field.name.capitalize}();
         | $strucName set${field.name.capitalize}($fieldType v);
       """.stripMargin
    }
  }

  private def getAllServices(space: Generator.Model.Space): List[Servc] = {
    space.servcs ++ space.spaces.flatMap(getAllServices)
  }

  private def getAllStrucs(space: Generator.Model.Space): List[Struc] = {
    space.strucs ++ space.spaces.flatMap(getAllStrucs)
  }

  private def getSubStrucs(struc: Struc): List[Struc] = {
    struc.leaves ++ struc.leaves.flatMap(getSubStrucs)
  }

  private def camel2snake(name: String): String = name
      .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
      .replaceAll("([a-z\\d])([A-Z])", "$1_$2")
      .toLowerCase

  private def genEnum(name: String, vals : List[String]): String = {
    s"""
       | enum ${name}s {
       |     ${vals.map(camel2snake(_).toUpperCase).mkString(",\n")}
       | }
     """.stripMargin
  }

  private def genIsAsMethods(names: List[String]): String = {
    names.map{ name =>
      s"""
         | boolean is$name();
         | $name as$name();
       """.stripMargin
    }.mkString("\n")
  }

  private def genIsAsMethodsImpl(strucs: List[(Struc, Boolean)]): String = {
    strucs.map { case (st, isCurrent) =>
      val stName = st.kind.map(_.name).get
      s"""
         | public boolean is$stName() { return $isCurrent; }
         | public $stName as$stName() {
         |   ${if (isCurrent) "return this;" else "throw new IllegalStateException(\"Not a $stName: \" + this);" }
         | }
       """.stripMargin
    }.mkString("\n")
  }

  private def genWhatImpl(interfaceName: Option[String], strucName: String): String = {
    if (interfaceName.isDefined)
      s"""public ${interfaceName.get}s what() { return ${interfaceName.get}s.${camel2snake(strucName).toUpperCase}; }"""
    else
      ""
  }

  private def genEqualsMethod(struc: Struc)(implicit symt: Symtable): String = {
    val allFields = struc.fields ++ getUsingFields(struc)
    val strucName = struc.kind.map(_.name).get

    if (allFields.isEmpty) {
      s"""
         |  @Override
         |  public boolean equals(Object thatObj) {
         |    if (this == thatObj) return true;
         |
         |    return (thatObj instanceof $strucName);
         |  }
      """.stripMargin
    } else {
      val fieldsEqualExpr = (
          for {
            field <- struc.fields ++ getUsingFields(struc)
          } yield {
            val fieldType = scalaToJavaType(field.kind)
            if (javaPrimitiveTypes.contains(fieldType))
              s"this.${field.name} == that.${field.name}"
            else
              s"this.${field.name}.equals(that.${field.name})"
          }
          ).mkString(" && ")

      s"""
         |  @Override
         |  public boolean equals(Object thatObj) {
         |    if (this == thatObj) return true;
         |
         |    if (!(thatObj instanceof $strucName)) return false;
         |
         |    $strucName that = ($strucName) thatObj;
         |
         |    return $fieldsEqualExpr;
         |  }
      """.stripMargin
    }
  }

  private def genToStringMethod(struc: Struc)(implicit symt: Symtable): String = {
    val fieldsToStringExpr = (
      for {
        field <- struc.fields ++ getUsingFields(struc)
      } yield {
        val fieldType = scalaToJavaType(field.kind)
        if (fieldType == "String")
          s"""
             | "${field.name}=" + '\\'' + this.${field.name} + '\\''
             """.stripMargin
        else
          s"""
             | "${field.name}=" + this.${field.name}
             """.stripMargin
      }
    ).mkString("+ ',' +")

    val strucName = struc.kind.map(_.name).get
    s"""
       | @Override
       | public String toString() {
       |   return "${strucName}{" +
       |     ${fieldsToStringExpr} +
       |   '}';
       | }
     """.stripMargin
  }

  private def genAllArgConstructor(struc: Struc)(implicit symt: Symtable): String = {
    val allFields = struc.fields ++ getUsingFields(struc)
    if (allFields.isEmpty) {
      ""
    } else {
      val (constructorParams, assigmentExprs) = (
          for {
            field <- allFields
          } yield {
            val fieldType = scalaToJavaType(field.kind)
            val fieldName = field.name

            s"$fieldType $fieldName" -> s"this.$fieldName = $fieldName"
          }
          ).unzip

      val strucName = struc.kind.map(_.name).get

      s"""
         | public $strucName(${constructorParams.mkString(",")}) {
         |   ${assigmentExprs.mkString(";")};
         | }
     """.stripMargin
    }
  }

  private def genNoArgConstructor(strucName: String)(implicit symt: Symtable): String = {
    s"""
       | public $strucName() {}
     """.stripMargin
  }

  private def getUsingFields(struc: Struc)(implicit symt: Symtable): List[Field] = {
    struc.usings
      .flatMap { y => symt.find(y.kind, struc.kind) }
      .collect { case s: Struc => s }
      .flatMap(_.fields)
  }

  private def scalaToJavaType(kind: Kind, inGeneric: Boolean = false): String = {
    Seq(
      {
        val tt = scalaToJavaTypes.getOrElse(kind.name, kind.name)
        if (inGeneric)
          primitiveToBoxedTypes.getOrElse(tt, tt)
        else
          tt
      },
      if (kind.params.nonEmpty)
          if (kind.name == "Option")
            s"${kind.params.map(scalaToJavaType(_, true)).mkString(", ")}"
          else
            s"<${kind.params.map(scalaToJavaType(_, true)).mkString(", ")}>"
      else
        ""
    ).mkString
  }

  private val scalaToJavaTypes = Map(
    "Int" -> "int",
    "String" -> "String",
    "Option" -> "",
    "Long" -> "long",
    "Vector" -> "List",
    "Boolean" -> "boolean",
    "Double" -> "double",
  )

  private val primitiveToBoxedTypes = Map(
    "int" -> "Integer",
    "float" -> "Float",
    "double" -> "Double",
    "boolean" -> "Boolean",
    "long" -> "Long"
  )

  private val javaPrimitiveTypes = Set(
    "int", "byte", "short", "long", "char", "float", "double", "boolean"
  )

  private val javaBoxedTypes = Set(
    "Integer", "Float", "Double", "Boolean", "Long", "String"
  )

}
