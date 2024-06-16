package io.github.iozhik

import cats.implicits._
import io.github.iozhik.Generator.Model._
import io.github.iozhik.GeneratorUtils._
import io.github.iozhik.scalagen.CirceUtils._
import io.github.iozhik.scalagen.GeneratorConfig
import io.github.iozhik.scalagen.GeneratorMeta
import io.github.iozhik.scalagen.PredefGenerator._
import io.github.iozhik.scalagen.ScalaApiGeneratorUtils._
import io.github.iozhik.scalagen.UpickleUtils._

import java.io.File

class ScalaApiGeneratorV1 {

  def delimiter = "\n"
  def pathDelim: String = File.separator
  def DomPostfix = "Req" // function domain postfix
  def CodPostfix = "Res" // function codomain postfix

  def camel2snake(name: String): String = name
    .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
    .replaceAll("([a-z\\d])([A-Z])", "$1_$2")
    .toLowerCase
  def snake2camel(name: String): String = {
    def loop(x : List[Char]): List[Char] = (x: @unchecked) match {
      case '_' :: '_' :: rest => loop('_' :: rest)
      case '_' :: c :: rest => Character.toUpperCase(c) :: loop(rest)
      case '_' :: Nil => Nil
      case c :: rest => c :: loop(rest)
      case Nil => Nil
    }
    if (name == null) "" else loop('_' :: name.toList).mkString
  }

  private val predefined = Set(
    "Boolean", "Int", "Long", "Double", "Float",
    "Vector", "List", "Option", "String", "Either", "Map"
  )

  def sanitize(name: String): String = GeneratorUtils.sanitize(name, keywords)

  def genField(x: Field, withDoc: Boolean = true, isDef: Boolean = false, wrapEnumType: String => String = wrapWithOpenEnum)(
    implicit meta: GeneratorMeta
  ): Either[String, String] = {
    for {
      kind <- genFieldType(x.kind, wrapEnumType)
    } yield {
      val d = delimiter
      val docsBody = if (withDoc) x.doc.split("\n").toList.map(_.trim).intercalate("\n* ") else ""
      val keyword = if (isDef) "def " else ""
      val docs = if (docsBody.nonEmpty) { s"$d/** $docsBody */$d" } else { "" }
      defaults.find(x => kind.startsWith(x._1)).fold(
        docs + keyword + sanitize(x.name) + ": " + kind
      ){ case (_, default) => docs + keyword + sanitize(x.name) + ": " + kind + " = " + default }
    }
  }

  private def createStrucImports(kinds: List[Kind])(implicit meta: GeneratorMeta) =
    kinds
      .flatMap(_.unresolvedIn(meta.space.symbol).flatMap(_.unresolvedIn(meta.space.symbol)))
      .filter(y => !predefined.contains(y.name))
      .flatMap(meta.symt.resolve)
      .collect {
        case y: Struc if y.kind.nonEmpty => (y.path.reverse.filter(_.nonEmpty).intercalate("."), y.kind.get.name)
      }

  private def genStrucImportsWithCodecs(kinds: List[Kind])(implicit meta: GeneratorMeta) =
    createStrucImports(kinds).flatMap {
      case (path, _) => List(s"import $path._", s"import $path.CirceImplicits._")
    }

  def genImpor(x: Impor)(implicit meta: GeneratorMeta): Either[String, List[Code]] = {
    for {
      name <- genKind(x.name)
    } yield {
      List(Code(s"import $name"))
    }
  }

  def genExter(x: Exter): Either[String, List[Code]] = {
    Right(List(Code(s"// External type: ${x.name}")))
  }

  def genAlias(x: Alias)(implicit meta: GeneratorMeta): Either[String, List[Code]] = {
    for {
      alias <- genKind(x.alias)
      name <- genKind(x.name)
    } yield {
      List(Code(s"type $alias = $name"))
    }
  }

  def genJsonCodecs(x: Wrapp)(implicit meta: GeneratorMeta): Either[String, List[Code]] = {
    for {
      name <- genKind(x.name)
      target <- genKind(x.target)
    } yield {
      val codecName = x.name.name.toLowerCase
      val encoderBody = if (target == "java.io.File") {
          s"(x: $name) => x.${x.fname}.getName.asJson"
        } else {
          s"(x: $name) => x.${x.fname}.asJson"
        }
      val decoderBody = if (target == "java.io.File") {
          s"Decoder[String].map(s => $name(new java.io.File(s)))"
        } else {
          s"Decoder[$target].map($name.apply)"
        }
      val decoder = s"implicit lazy val ${codecName}Decoder: Decoder[$name] = $decoderBody"
      val encoder = s"implicit lazy val ${codecName}Encoder: Encoder[$name] = $encoderBody"
      val body = s"$encoder$delimiter$decoder"
      List(Code(body = body, packageObject = "CirceImplicits"))
    }
  }

  def genJsonCodecs(x: EnumStr)(implicit meta: GeneratorMeta): Either[String, List[Code]] = {
    for {
      name <- genKind(x.name)
    } yield {
      val codecName = x.name.name.toLowerCase
      val encoderBody = s"""(_: $name.type) => "${x.target}".asJson"""
      val decoderBody = s"""Decoder[String].emap(s => Either.cond(s == "${x.target}", $name, s"Expected ${x.target}"))"""
      val decoder = s"implicit lazy val ${codecName}Decoder: Decoder[$name.type] = $decoderBody"
      val encoder = s"implicit lazy val ${codecName}Encoder: Encoder[$name.type] = $encoderBody"
      val body = s"$encoder$delimiter$decoder"
      List(Code(body = body, packageObject = "CirceImplicits"))
    }
  }

  def genJsonCodecs(struc: Struc)(implicit meta: GeneratorMeta): Either[String, List[Code]] = {
    val d = delimiter
    val useSnake = meta.space.opts.contains("snake")
    val useOpenEnums = struc.kind.exists(useOpenEnum)
    if (struc.isEnum) {
      if (struc.typets.nonEmpty) {
        for {
          kind <- struc.kind.map(_.name).toRight("No kind name")
          typet <- struc.typet().toRight(s"No type tag found for $kind")
          items <- struc.leaves.traverse(genJsonCodecs).map(_.flatten)
          cases <- struc.leaves.groupBy { leaf =>
            val kn = leaf.kind.map(_.name).getOrElse("ANONYMOUS_STRUC")
            leaf.typet().map(_.tag).getOrElse(s"Type tag not found for: $kn")
          }
            .toList
            .traverse { case (typeTag, leaves) =>
              if (leaves.size == 1)
                genCirceCodecCases(struc, typeTag, typet, leaves.head)
              else
                genMergedCirceCodecs(struc, kind, typeTag, typet, leaves)
            }
        } yield {
          val tag = "\"" + typet.name + "\""
          val name = kind + versionPostfix(struc.minVersion, struc.maxVersion)
          val wrappedName = if (useOpenEnums) wrapWithOpenEnum(name) else name
          val unknownCase = if (useOpenEnums) s"Decoder.const(iozhik.OpenEnum.Unknown[$name](unknown))" 
            else s"""throw iozhik.DecodingError(s"Unknown type for $name: $$unknown")"""
          val body = s"""
                        | implicit lazy val ${name.toLowerCase}Encoder: Encoder[$name] = {
                        |   ${cases.map(_._1).intercalate(d)}
                        | }
                        | implicit lazy val ${name.toLowerCase}Decoder: Decoder[$wrappedName] = for {
                        |   fType <- Decoder[String].prepare(_.downField($tag))
                        |   value <- fType match {
                        |     ${cases.map(_._2).intercalate(d)}
                        |     case unknown => $unknownCase
                        |   }
                        | } yield value
        """.stripMargin
          List(Code(body = body, packageObject = "CirceImplicits")) ++ items
        }
      } else {
        for {
          kind <- struc.kind.map(_.name).toRight("No kind name")
          wrapps <- struc.wrapps.flatTraverse(genJsonCodecs)
          enumstrs <- struc.enumstrs.flatTraverse(genJsonCodecs)
          leaves <- struc.leaves.flatTraverse(genJsonCodecs)
          cases <- (
              struc.wrapps.map(_.name -> false) ++
                struc.enumstrs.map(_.name -> true) ++
                struc.leaves.flatMap(_.kind).map(_ -> false)
            ).traverse{ case (k, isSingle) =>
              for {
                name <- genKind(k)
                codecName = k.name.toLowerCase
              } yield {
                (
                  if (isSingle) s"case $name => $name.asJson" else s"case x: $name => x.asJson",
                  s"${codecName}Decoder.widen",
                )
              }
            }
        } yield {
          val name = kind + versionPostfix(struc.minVersion, struc.maxVersion)
          val wrappedName = if (useOpenEnums) wrapWithOpenEnum(name) else name
          val wrapEnumType = if (useOpenEnums) ".map(iozhik.OpenEnum.Known(_)).or(Decoder[String].map(iozhik.OpenEnum.Unknown(_)))" else ""
          val body =
            s"""
              | implicit lazy val ${name.toLowerCase}Encoder: Encoder[$name] = {
              |   ${cases.map(_._1).intercalate(d)}
              | }
              | implicit lazy val ${name.toLowerCase}Decoder: Decoder[$wrappedName] = {
              |   List[Decoder[$name]](
              |     ${cases.map(_._2).intercalate(",")}
              |   ).reduceLeft(_ or _)$wrapEnumType
              | }
            """.stripMargin
          List(Code(
            body = body,
            packageObject = "CirceImplicits",
            imports = List("import cats.syntax.functor._")
          )) ++ wrapps ++ enumstrs ++ leaves
        }
      }
    } else {
      val usingf = struc.usings
        .flatMap{ y => meta.symt.find(y.kind, struc.kind) }
        .collect{ case s: Struc => s}
        .flatMap(_.fields)
      val impPacks = (usingf ++ struc.fields)
        .flatMap(_.kind.unresolvedIn(meta.space.symbol))
        .filter(y => !predefined.contains(y.name))
        .flatMap(meta.symt.resolve)
        .collect{
          case y: Struc if y.kind.nonEmpty => (y.path.reverse.filter(_.nonEmpty).intercalate("."), y.kind.get.name)
        }
      val imps = impPacks
        .map{ case (path, name) => s"import $path.$name" } ++ impPacks
          .map(_._1)
          .distinct
          .map(path => s"import $path.CirceImplicits._")
      for {
        kind <- struc.kind.map(_.name).toRight("No kind name")
        fieldKinds <- (usingf ++ struc.fields).traverse(y => genFieldType(y.kind))
        fields = (usingf ++ struc.fields).map(y => y.name).zip(fieldKinds)
      } yield {
        val postfix = if (struc.fields.nonEmpty || struc.usings.nonEmpty) "" else ".type"
        val name = kind + versionPostfix(struc.minVersion, struc.maxVersion)
        val decoderForBody = fields
          .map{ case (fname, k) =>
            val n = if (useSnake) { camel2snake(fname) } else { fname }
            if (k.startsWith("List")) {
              s"""_$fname <- h.getOrElse[$k]("$n")(List.empty)"""
            } else {
              s"""_$fname <- h.get[$k]("$n")"""
            }
          }
          .intercalate(d)
        val decoderFields = fields
          .map{ case (fname, _) =>
            s"${sanitize(fname)} = _$fname"
          }
          .intercalate(",")
        val encoderFields = (fields
          .map{ case (fname, _) =>
            val n = if (useSnake) { camel2snake(fname) } else { fname }
            val fn = sanitize(fname)
            s""""$n" -> x.$fn.asJson"""
          } ++ struc.typets.find(_.name == "method").map(m => s""""method" -> "${m.tag}".asJson"""))
          .intercalate(",")
        val decoderBody = if (struc.fields.nonEmpty || struc.usings.nonEmpty) {
          s"""
             |Decoder.instance { h =>
             |  for {
             |    $decoderForBody
             |  } yield {
             |    $name($decoderFields)
             |  }
             |}
           """.stripMargin
        } else {
          s"(_: HCursor) => Right($name)"
        }
        val encoderBody = if (struc.fields.nonEmpty || struc.usings.nonEmpty) {
          s"""
             | (x: $name) => {
             |   Json.fromFields(
             |     List(
             |       $encoderFields
             |     ).filter(!_._2.isNull)
             |   )
             | }
           """.stripMargin
        } else {
          s"(_: $name.type) => ().asJson"
        }
        val decoder = if (kind.endsWith(DomPostfix)) "" else s"implicit lazy val ${name.toLowerCase}Decoder: Decoder[$name$postfix] = $decoderBody"
        val encoder = s"implicit lazy val ${name.toLowerCase}Encoder: Encoder[$name$postfix] = $encoderBody"
        val body = s"$encoder$delimiter$decoder"
        List(Code(
          body = body,
          packageObject = "CirceImplicits",
          imports = imps ++ (if (struc.fields.isEmpty && struc.usings.isEmpty) {
            List("import io.circe.HCursor")
          } else { List.empty[String] })
        ))
      }
    }
  }

  def genSCodecs(x: Struc)(implicit meta: GeneratorMeta): Either[String, List[Code]] = {
    val d = delimiter
    if (x.isEnum) {
      if (x.typets.isEmpty) {
        Right(List.empty[Code])
      } else {
        for {
          kind  <- x.kind.map(_.name).toRight("No kind name")
          _     <- x.typet().toRight(s"No type tag found for $kind")
          items <- x.leaves.traverse(genSCodecs).map(_.flatten)
          cases <- x.leaves.traverse{ y =>
            for {
              nn <- y.kind.map(_.name).toRight("No kind name")
              tt <- y.typet().flatMap(_.id).toRight(s"No type id found for $nn")
            } yield {
              if (y.fields.nonEmpty) {
                s".typecase($tt, ${nn.toLowerCase}Codec)"
              } else {
                s".typecase($tt, provide($nn))"
              }
            }
          }
        } yield {
          val name = kind + versionPostfix(x.minVersion, x.maxVersion)
          val body = s"""
                        | implicit lazy val ${name.toLowerCase}Codec: Codec[$name] = discriminated[$name]
                        |   .by(uint32)
                        |   ${cases.intercalate(d)}
        """.stripMargin
          List(Code(
            body = body,
            packageObject = "ScodecImplicits",
          )) ++ items
        }
      }
    } else {
      val usingf = x.usings
        .flatMap{ y => meta.symt.find(y.kind, x.kind) }
        .collect{ case s: Struc => s}
        .flatMap(_.fields)
      for {
        kind <- x.kind.map(_.name).toRight("No kind name")
        fieldKinds <- (usingf ++ x.fields).traverse(y => genKind(y.kind))
        fields = (usingf ++ x.fields).map(y => y.name).zip(fieldKinds)
      } yield {
        val name = kind + versionPostfix(x.minVersion, x.maxVersion)
        val codecBody = fields.map{ case (n, k) => s"""("$n" | Codec[$k])""" }.intercalate(s" ::$d")
        val codec = if (x.fields.nonEmpty) {
          s"""
             | implicit lazy val ${name.toLowerCase}Codec = {
             |   $codecBody
             | }.as[$name]
           """.stripMargin
        } else {
          s"implicit lazy val ${name.toLowerCase}Codec = provide($name)"
        }
        val body = s"$codec"
        val imps = if (x.fields.nonEmpty) {
          List("import codecs.implicits._ ")
        } else {
          List.empty[String]
        }
        List(Code(
          body = body,
          packageObject = "ScodecImplicits",
          imports = imps
        ))
      }
    }
  }

  def genuPickle(x: Wrapp)(implicit meta: GeneratorMeta): Either[String, List[Code]] = {
    for {
      name <- genKind(x.name)
      target <- genKind(x.target)
    } yield {
      val codecName = x.name.name.toLowerCase
      val encoderBody = if (target == "java.io.File") {
        s"(x: $name) => x.${x.fname}.getName.asJson"
      } else {
        s"(x: $name) => x.${x.fname}.asJson"
      }
      val decoderBody = if (target == "java.io.File") {
        s"Decoder[String].map(s => $name(new java.io.File(s)))"
      } else {
        s"Decoder[$target].map($name)"
      }
      val decoder = s"implicit lazy val ${codecName}Decoder: Decoder[$name] = $decoderBody"
      val encoder = s"implicit lazy val ${codecName}Encoder: Encoder[$name] = $encoderBody"
      val body = s"$encoder$delimiter$decoder"
      List(Code(body = body, packageObject = "CirceImplicits"))
    }
  }

  def genuPickle(struc: Struc)(implicit meta: GeneratorMeta): Either[String, List[Code]] = {
    val d = delimiter
    if (struc.isEnum) {
      if (struc.typetForBins.isEmpty) {
        Right(List.empty[Code])
      } else {
        for {
          kind  <- struc.kind.map(_.name).toRight("No kind name")
          _     <- struc.typetForBins.toRight(s"No type tag found for $kind")
          items <- struc.leavesForBins.traverse(genuPickle).map(_.flatten)
          hasDuplicateLeavesTypets = struc.leavesForBins.groupBy { leaf =>
            val kn = leaf.kind.map(_.name).toRight("No kind name")
            leaf.typet().map(_.tag).getOrElse(s"Type tag not found for: $kn")
          }
            .exists {
              case (_, leaves) => leaves.size >= 2
            }
          name = kind + versionPostfix(struc.minVersion, struc.maxVersion)
          body <- if (hasDuplicateLeavesTypets) genMergedUpickleCodecs(struc, name) else genUpickleCodecCases(struc, name)
        } yield {
          List(Code(
            body = body,
            packageObject = "uPickleImplicits",
          )) ++ items
        }
      }
    } else {
      val usingf = struc.usings
        .flatMap{ y => meta.symt.find(y.kind, struc.kind) }
        .collect{ case s: Struc => s}
        .flatMap(_.fields)
      val impPacks = (usingf ++ struc.fields)
        .flatMap(_.kind.unresolvedIn(meta.space.symbol))
        .filter(y => !predefined.contains(y.name))
        .flatMap(meta.symt.resolve)
        .collect{
          case y: Struc if y.kind.nonEmpty => (y.path.reverse.filter(_.nonEmpty).intercalate("."), y.kind.get.name)
        }
      val imps = impPacks
        .map{ case (path, name) => s"import $path.$name" } ++ impPacks
        .map(_._1)
        .distinct
        .map(path => s"import $path.uPickleImplicits._")
      for {
        kind <- struc.kind.map(_.name).toRight("No kind name")
        fieldKinds <- (usingf ++ struc.fields).traverse(y => genFieldType(y.kind))
        fields = (usingf ++ struc.fields).map(y => y.name).zip(fieldKinds)
      } yield {
        val name = kind + versionPostfix(struc.minVersion, struc.maxVersion)
        val keys = fields.map{ case (n, _) => s"""val ${n}Key = upack.Str("$n")""" }.intercalate(d)
        val wf = fields.map{ case (n, _) => s"""${n}Key -> writeMsg(x.${sanitize(n)})""" }.intercalate(",")
        val rf = fields.map{ case (n, k) =>
          s"""${sanitize(n)} <- m.get(${n}Key).map(x => readBinary[$k](x))"""
        }.intercalate(d)
        val assign = fields.map{ case (n, _) => s"${sanitize(n)} = ${sanitize(n)}" }.intercalate(",")
        val codec = if (struc.fields.nonEmpty || struc.usings.nonEmpty) {
          s"""
             | implicit lazy val ${name.toLowerCase}Codec: ReadWriter[$kind] = {
             |   $keys
             |   readwriter[upack.Msg].bimap(
             |     x => {
             |       upack.Obj(
             |         $wf
             |       )
             |     },
             |     msg => {
             |       val m = msg.obj
             |       val result = for {
             |         $rf
             |       } yield {
             |         $name(
             |           $assign
             |         )
             |       }
             |       result.get
             |     }
             |   )
             | }
           """.stripMargin
        } else {
          s"""implicit lazy val ${name.toLowerCase}Codec: ReadWriter[$name.type] = macroRW"""
        }
        val body = s"$codec"
        List(Code(
          body = body,
          packageObject = "uPickleImplicits",
          imports = imps,
        ))
      }
    }
  }

  def genWrapp(parentKind: Kind, x: Wrapp)
              (implicit meta: GeneratorMeta): Either[String, List[Code]] = {
    for {
      parent <- genKind(parentKind)
      name <- genKind(x.name)
      target <- genKind(x.target)
    } yield {
      List(Code(
        name = parentKind.name,
        body = s"final case class $name(${x.fname}: $target) extends $parent",
      ))
    }
  }

  def genEnumStr(parentKind: Kind, x: EnumStr)
              (implicit meta: GeneratorMeta): Either[String, List[Code]] = {
    for {
      parent <- genKind(parentKind)
      name <- genKind(x.name)
    } yield {
      List(Code(
        name = parentKind.name,
        body = s"""
           case object $name extends $parent {
             override def toString = "${x.target}"
           }
         """
      ))
    }
  }

  private def genStrucDocs(struc: Struc) = {
    val docsBody = (if (struc.doc.nonEmpty) struc.doc.split("\n").toList.map(_.trim) else List.empty)
      .intercalate(s"$delimiter* ")
    val params = requiredFieldsFirst(struc.fields).flatMap { field =>
      if (field.doc.nonEmpty) Some((s"@param ${field.name}" + field.doc).split(delimiter).toList.map(_.trim))
      else None
    }
      .flatten
      .intercalate(s"$delimiter* ")
    val docsText = (docsBody, params) match {
      case ("", "") => ""
      case (_, "") => docsBody
      case ("", _) => params
      case _ => s"$docsBody$delimiter*$delimiter* $params"
    }
    if (docsText.nonEmpty) s"$delimiter/** $docsText */$delimiter" else ""
  }

  def genStruc(x: Struc, wrapEnumType: String => String)(implicit meta: GeneratorMeta): Either[String, List[Code]] = {
    val kindOrDefault = x.kind.getOrElse(Kind("$$"))
    val abstractOrEnum = x.isAbstract || x.isEnum
    val usingf = x.usings
      .flatMap{ y => meta.symt.find(y.kind, x.kind) }
      .collect{ case s: Struc => s}
      .flatMap(_.fields)
    for {
      kind <- genKind(kindOrDefault)
      fields <- requiredFieldsFirst(x.fields).traverse(genField(_, withDoc = abstractOrEnum, isDef = abstractOrEnum, wrapEnumType = wrapEnumType))
      mixins <- x.mixins.traverse(m => genKind(m))
      leaves <- x.leaves.traverse(genStruc(_, wrapEnumType))
      wrapps <- x.wrapps.traverse(genWrapp(kindOrDefault, _))
      enumstrs <- x.enumstrs.traverse(genEnumStr(kindOrDefault, _))
      usings <- usingf.traverse(genField(_, isDef = abstractOrEnum, wrapEnumType = wrapEnumType))
      upickles <- if (meta.space.opts.contains("upack")) { genuPickle(x) } else { Right(List.empty[Code]) }
      jsonCodecs <- if (meta.space.opts.contains("circe")) { genJsonCodecs(x) } else { Right(List.empty[Code]) }
      scodecs <- if (meta.space.opts.contains("scodec")) { genSCodecs(x) } else { Right(List.empty[Code]) }
      ms = mixins.toNel.map(_.intercalate(" with ")).fold("")(" extends " + _)
    } yield {
      val imps = ((usingf ++ x.fields).map(_.kind) ++ x.mixins)
        .flatMap(_.unresolvedIn(meta.space.symbol).flatMap(_.unresolvedIn(meta.space.symbol)))
        .filter(y => !predefined.contains(y.name))
        .flatMap(meta.symt.resolve)
        .collect{
          case y: Struc if y.kind.nonEmpty => (y.path.reverse.filter(_.nonEmpty).intercalate("."), y.kind.get.name)
        }
        .map{ case (path, name) => s"import $path.$name" }
      val name = kind + versionPostfix(x.minVersion, x.maxVersion)
      val filename = sanitize(kindOrDefault.name) + versionPostfix(x.minVersion, x.maxVersion)
      val fs = if (x.isAbstract || x.isEnum) {
        (usings ++ fields).toNel.map(_.intercalate(delimiter)).getOrElse("")
      } else {
        (usings ++ fields).toNel.map(_.intercalate(", ")).getOrElse("")
      }
      val children = leaves.flatten ++ wrapps.flatten ++ enumstrs.flatten
      if (x.isEnum) {
        val body = s"sealed trait $name$ms {$delimiter$fs$delimiter}"
        List(
          Code(
            body = (body :: children.filter(_.name.nonEmpty).map(_.body)).intercalate(delimiter + delimiter),
            name = filename,
            imports = imps,
          ),
        ) ++ jsonCodecs ++ scodecs ++ upickles
      } else if (x.isAbstract) {
        val body = s"trait $name$ms { $fs }"
        Code(body = body, name = filename, imports = imps) :: children
      } else {
        val docs = genStrucDocs(x)
        val body = if (fs.nonEmpty) {
          s"${docs}final case class $name($fs)$ms"
        } else {
          s"${docs}case object $name$ms"
        }
        Code(body = body, name = name, imports = imps) :: (children ++ jsonCodecs ++ scodecs ++ upickles)
      }
    }
  }

  private def genDefunCod(x: Defun)(implicit meta: GeneratorMeta) =
    x.cod.fold(
      kind => Right(List(Code(
        imports = importKind(kind),
        packageObject = "__this_trait__",
      ))),
      struc => genStruc(struc.copy(kind = Option(Kind(x.kind.name.capitalize + CodPostfix))), wrapEnumType = wrapWithOpenEnum)
    )

  private def genDefunCodName(x: Defun)(implicit meta: GeneratorMeta) =
    x.cod.fold(
      kind => genKind(kind, wrapWithOpenEnum),
      struc => struc.kind.map(genKind(_)).getOrElse(Right(sanitize(s"${x.kind.name.capitalize}$CodPostfix")))
    )

  private def genDefunCodomain(x: Defun, codName: String) =
    x.cod.map(y => if (y.fields.isEmpty && y.usings.isEmpty) s"$codName.type" else codName)
    .fold(_ => codName, identity)

  private def genDefunDocs(x: Defun) = {
    val d = delimiter
    val params = x.dom.fold(
      _ => "",
      struc => requiredFieldsFirst(struc.fields).flatMap { field =>
        (s"@param ${field.name}" + field.doc).split(d).toList.map(_.trim)
      }
        .intercalate(s"$d* ")
    )
    val paramDocs = if (params.isEmpty) params else s"$d*$d* $params"
    val docsBody = x.doc.split(d).toList.map(_.trim).intercalate(s"$d* ")
    if (docsBody.nonEmpty) s"$d/** $docsBody$paramDocs */$d" else ""
  }

  def genDefun(x: Defun)(implicit meta: GeneratorMeta): Either[String, List[Code]] = {
    for {
      kind <- genKind(x.kind)
      dom <- x.dom.fold(
        y => Right(List(Code(
          imports = importKind(y),
          packageObject = "__this_trait__",
        ))),
        y => genStruc(y.copy(kind = Option(Kind(x.kind.name.capitalize + DomPostfix))), wrapEnumType = identity)
      )
      cod <- genDefunCod(x)
      domName <- x.dom.fold(
        y => genKind(y),
        y => y.kind.map(genKind(_)).getOrElse(Right(sanitize(s"${x.kind.name.capitalize}$DomPostfix")))
      )
      codName <- genDefunCodName(x)
    } yield {
      val domain = x.dom
        .map(y => if (y.fields.isEmpty && y.usings.isEmpty) "" else s"x: $domName")
        .fold(_ => domName, identity)
      val codomain = genDefunCodomain(x, codName)
      val docs = genDefunDocs(x)
      val http4sClientDefun = if (meta.space.opts.contains("http4s")) {
        genHttp4sClientDefun(x, kind, domain, codomain)
      } else {
        List.empty[Code]
      }
      val http4sServerDefun = if (meta.space.opts.contains("http4s")) {
        genHttp4sServerDefun(x, kind, domName, domain, codomain)
      } else {
        List.empty[Code]
      }
      dom ++ cod ++ List(Code(
        body = s"${docs}def $kind($domain): F[$codomain]",
        name = x.kind.name,
        packageObject = "__this_trait__",
      )) ++ http4sClientDefun ++ http4sServerDefun
    }
  }

  private def genMethodsFactoryDefun(x: Defun)(implicit meta: GeneratorMeta) =
    for {
      kind <- genKind(x.kind)
      params <- x.dom.fold(
        _ => Right(List.empty),
        struc => requiredFieldsFirst(struc.fields).traverse(genField(_, withDoc = false, wrapEnumType = identity))
      )
      reqName = x.kind.name.capitalize + DomPostfix
      dom <- x.dom.fold(
        kind => Right(List(Code(
          imports = importKind(kind),
          packageObject = "__this_trait__",
        ))),
        struc => genStruc(struc.copy(
          kind = Option(Kind(reqName)),
          typets = Typet("", "method", List.empty, x.kind.name, None) :: struc.typets
        ), wrapEnumType = identity)
      )
      cod <- genDefunCod(x)
      codName <- genDefunCodName(x)
    } yield {
      val reqFields = x.dom.fold(_ => List.empty, struc => requiredFieldsFirst(struc.fields))
      val domImports = x.dom.fold(_ => List.empty[String], struc => genStrucImportsWithCodecs(requiredFieldsFirst(struc.fields).map(_.kind)))
      val codImports = x.cod.fold(
        kind => genStrucImportsWithCodecs(kind.params),
        struc => genStrucImportsWithCodecs(requiredFieldsFirst(struc.fields).map(_.kind))
      )
      val docs = genDefunDocs(x)
      val reqParams = params.intercalate(", ")
      val fieldNames = reqFields.map(f => sanitize(f.name))
      val reqArgs = if (fieldNames.isEmpty) "" else fieldNames.mkString("(", ",", ")")
      val returnTypeParams = x.cod.fold(
        kind => kind.params.map(p => genKind(p, wrapWithOpenEnum)).sequence.map(_.mkString("[", ", ", "]")),
        _ => Right("")
      ).merge
      val fileFields = x.dom.fold(
        _ => List.empty,
        struc => struc.fields.filter(_.opts.contains("file"))
      )
        .map { f =>
          val fieldName = if (meta.space.opts.contains("snake")) camel2snake(f.name) else f.name
          val domFieldName = if (f.kind.name == "Option") f.name else s"Option(${f.name})"
          s""""$fieldName" -> $domFieldName"""
        }
      val attachments = if (fileFields.isEmpty) "" else fileFields.mkString(", Map(", ", ", ").collect { case (k, Some(v)) => k -> v }")
      val codomain = genDefunCodomain(x, codName)
      val defunBody =
        s"""val req = $reqName$reqArgs
           |MethodReq$returnTypeParams("${x.kind.name}", req.asJson$attachments)""".stripMargin
      val code =
        s"""${docs}def $kind($reqParams): $codomain = {
           |  $defunBody
           |}
       """.stripMargin
      dom ++ cod :+ Code(
        body = code,
        name = x.kind.name,
        packageObject = "__this_trait__",
        imports = codImports ++ domImports
      )
    }

  def genHttp4sClientDefun(x: Defun, name: String, dom: String, rawCod: String)
                    (implicit meta: GeneratorMeta): List[Code] = {
    // TODO: we need generic mechanism for this (resolving name conflicts with library entities)
    val cod = rawCod.replace("Message", "telegramium.bots.Message")
    val withEntity = if (dom.isEmpty) { "" } else { ".withEntity(x.asJson)" }
    val defaultBody = {
      s"""  for {
         |    uri <- F.fromEither[Uri](Uri.fromString(s"$$baseUrl/$name"))
         |    req = Request[F]()
         |      .withMethod(GET)
         |      .withUri(uri)
         |      $withEntity
         |    res <- decodeResponse[$cod](req)
         |  } yield {
         |    res
         |  }
       """.stripMargin
    }
    val defaultFun = {
      s"""
         |/**${x.doc}*/
         |def $name($dom): F[$cod] = {
         |  $defaultBody
         |}
       """.stripMargin
    }
    val body = x.dom.fold(
      _ => (defaultFun, List.empty[String]),
      dd => {
        val fileFields = dd.fields.filter(_.opts.contains("file"))
        if (fileFields.isEmpty) {
          (defaultFun, List.empty[String])
        } else {
          val otherFields = (dd.fields
            .filter(!_.opts.contains("file"))
            .map{ f =>
              val fname = if (meta.space.opts.contains("snake")) { camel2snake(f.name) } else { f.name }
              s"""("$fname", x.${sanitize(f.name)}.asJson)"""
            } ++ dd.fields.filter(_.opts.contains("file")).map { f =>
              s"""("${f.name}", if (${f.name}Part.isEmpty) { x.${f.name}.asJson } else { Json.Null } )"""
            }).intercalate(",")
          val ff = fileFields.map{ y =>
              if (y.kind.name == "Option") {
                (y.name, meta.symt.find(y.kind.params.head), true)
              } else {
                (y.name, meta.symt.find(y.kind), false)
              }
            }
            .collect {
              case (fname, Some(s: Struc), isOpt) if s.wrapps.exists(_.target.name == "java.io.File") && s.kind.nonEmpty =>
                val imps = s.kind.toList
                  .flatMap(_.unresolvedIn(meta.space.symbol))
                  .filter(y => !predefined.contains(y.name))
                  .flatMap(meta.symt.resolve)
                  .collect{
                    case y: Struc if y.kind.nonEmpty => (y.path.reverse.filter(_.nonEmpty).intercalate("."), y.kind.get.name)
                  }
                  .flatMap{ case (path, _) =>
                    List(
                      s"import $path._",
                      s"import $path.CirceImplicits._",
                    )
                  }
                val fileC = s.wrapps.head.name.name
                val fn = if (meta.space.opts.contains("snake")) { camel2snake(fname) } else { fname }
                (
                  s"""${fname}Part""",
                  s"""${fname}Part <- ${fname}PartF""",
                  if (isOpt) {
                    s"""
                       | val ${fname}PartF = x.${sanitize(fname)} match {
                       |   case Some($fileC(f)) => makePart("$fn", f)
                       |   case _ => F.pure(List.empty[Part[F]])
                       | }
                    """.stripMargin
                  } else {
                    s"""
                       | val ${fname}PartF = x.${sanitize(fname)} match {
                       |   case $fileC(f) => makePart("$fn", f)
                       |   case _ => F.pure(List.empty[Part[F]])
                       | }
                    """.stripMargin
                  },
                  imps
                )
            }
          (s"""
             |/**${x.doc}*/
             |def $name($dom): F[$cod] = {
             |  ${ff.map(_._3).intercalate(delimiter)}
             |  List(${ff.map(_._1 + "F").intercalate(",")}).sequence.map(_.flatten).flatMap{ l =>
             |    if (l.nonEmpty) {
             |      for {
             |        uri <- F.fromEither[Uri](Uri.fromString(s"$$baseUrl/$name"))
             |        ${ff.map(_._2).intercalate(delimiter)}
             |        body = Multipart[F](
             |          Vector($otherFields).filter(!_._2.isNull).map{ case (n, v) => Part.formData[F](n, v.noSpaces) } ++
             |          ${ff.map(_._1).intercalate(" ++ ")}
             |        )
             |        req = Request[F]()
             |          .withMethod(POST)
             |          .withUri(uri)
             |          .withEntity(body)
             |          .withHeaders(body.headers)
             |        res <- decodeResponse[$cod](req)
             |      } yield {
             |        res
             |      }
             |    } else {
             |      $defaultBody
             |    }
             |  }
             |}
          """.stripMargin,
            List(
              "import org.http4s.multipart.Multipart",
              "import cats.syntax.traverse._",
              "import cats.instances.list._",
              "import io.circe.Json",
            ) ++  ff.flatMap(_._4)
          )
        }
      }
    )
    List(Code(
      body = body._1,
      name = x.kind.name,
      packageObject = "__this_impl__",
      imports = body._2
    ))
  }

  def genHttp4sServerDefun(x: Defun, name: String, domT: String, dom: String, cod: String): List[Code] = {
    val body = {
      val input = if (dom.isEmpty) {
        ("", "", "", "")
      } else {
        (
          s"implicit val ${domT}EntityDecoder: EntityDecoder[F, $domT] = jsonOf[F, $domT]",
          s"rawReq: org.http4s.Request[F]",
          s"req <- rawReq.as[$domT]",
          s"req"
        )
      }
      val content = s"""
         |/**${x.doc}*/
         |def $name($dom): F[$cod] = ???
         |
         |${input._1}
         |
         |def ${name}Req(${input._2}): F[org.http4s.Response[F]] = {
         |  for {
         |    ${input._3}
         |    res <- $name(${input._4})
         |  } yield {
         |    org.http4s.Response().withEntity(res.asJson)
         |  }
         |}
       """.stripMargin
      (content, List.empty[String])
    }
    List(Code(
      body = body._1,
      name = x.kind.name,
      packageObject = "__this_server_defs__",
      imports = body._2
    ))
  }

  def genServc(x: Servc)(implicit meta: GeneratorMeta): Either[String, List[Code]] = {
    for {
      kind <- genKind(x.kind)
      defs = x.items.collect{ case a: Defun => a }
      itemsRaw <- defs.traverse(d => if (x.opts.contains("methodsFactory")) genMethodsFactoryDefun(d) else genDefun(d))
      items = itemsRaw.flatten
      d = delimiter
    } yield {
      val strucs = items.filter{ y =>
        y.packageObject != "__this_trait__" &&
        y.packageObject != "__this_impl__" &&
        y.packageObject != "__this_server_defs__"
      }
      val defuns = items.filter(_.packageObject == "__this_trait__").map(_.body).intercalate(d)
      val code = if (x.opts.contains("methodsFactory")) genMethodsFactory(x, defuns, kind, items)
      else genServcDefault(x, defuns, kind, items)
      strucs ++ code
    }
  }

  private def genServcDefault(x: Servc, defuns: String, kind: String, items: List[Code])(
    implicit meta: GeneratorMeta
  ): List[Code] = {
    val d = delimiter
    List(Code(
      body = s"trait $kind[F[_]]$d{$d$defuns$d}",
      name = x.kind.name,
      imports = items.filter(_.packageObject == "__this_trait__").flatMap(_.imports)
    )) ++ (if (meta.space.opts.contains("http4s") && x.opts.contains("client")) {
      genHttp4sClient(kind, items.filter(_.packageObject == "__this_impl__"))
    } else { List.empty[Code] }) ++ (if (meta.space.opts.contains("http4s") && x.opts.contains("server")) {
      genHttp4sServer(kind, items.filter(_.packageObject == "__this_server_defs__"))
    } else { List.empty[Code] })
  }

  def genHttp4sClient(name: String, defuns: List[Code]): List[Code] = {
    val defs = defuns.map(_.body).intercalate(delimiter)
    List(Code(
      name = s"${name}Http4sImp",
      body =
        s"""
          |class ${name}Http4sImp[F[_]: ConcurrentEffect: ContextShift](http: Client[F], baseUrl: String, blocker: Blocker)
          |                          (implicit F: MonadError[F, Throwable]) extends $name[F] {
          |  import io.circe.Decoder
          |
          |  implicit def decodeEither[A, B](implicit
          |                                  decoderA: Decoder[A],
          |                                  decoderB: Decoder[B]): Decoder[Either[A, B]] = decoderA.either(decoderB)
          |
          |  case class Response[A: Decoder](
          |    ok: Boolean,
          |    result: Option[A],
          |    description: Option[String]
          |  )
          |
          |  private implicit def responseDecoder[A: Decoder]: Decoder[Response[A]] =
          |    Decoder.instance { h =>
          |      for {
          |        _ok          <- h.get[Boolean]("ok")
          |        _result      <- h.get[Option[A]]("result")
          |        _description <- h.get[Option[String]]("description")
          |      } yield {
          |        Response[A](ok = _ok, result = _result, description = _description)
          |      }
          |    }
          |
          |  private def decodeResponse[A: io.circe.Decoder](req: Request[F]): F[A] = {
          |    for {
          |      response <- http.expect(req)(jsonOf[F, Response[A]])
          |      result <- F.fromOption[A](
          |        response.result,
          |        new RuntimeException(response.description.getOrElse("Unknown error occurred"))
          |      )
          |    } yield {
          |      result
          |    }
          |  }
          |
          |  def makePart(field: String, file: java.io.File): F[List[Part[F]]] = {
          |    import org.http4s.headers._
          |    val ext = "\\\\.[A-Za-z0-9]+$$".r.findFirstIn(file.getName).getOrElse("").drop(1)
          |    val extOpt = MediaType.forExtension(ext).map(x => `Content-Type`(x))
          |    val error = new IllegalArgumentException("Can't determine Content-Type for ext: " + ext)
          |    for {
          |      mediaType <- OptionT(F.pure(extOpt)).getOrElseF(F.raiseError(error))
          |    } yield {
          |      List(Part.fileData(field, file, blocker, mediaType))
          |    }
          |  }
          |
          |  $defs
          |}
        """.stripMargin,
      imports = List(
        "import io.circe.syntax._",
        "import org.http4s._",
        "import org.http4s.dsl.io._",
        "import org.http4s.client._",
        "import org.http4s.circe._",
        "import org.http4s.multipart.Part",
        "import cats.effect._",
        "import cats.MonadError",
        "import cats.data.OptionT",
        "import cats.syntax.functor._",
        "import cats.syntax.flatMap._",
        "import CirceImplicits._",
      ) ++ defuns.flatMap(_.imports)
    ))
  }

  def genHttp4sServer(name: String, defuns: List[Code]): List[Code] = {
    val defs = defuns.map(_.body).intercalate(delimiter)
    List(Code(
      name = s"${name}Http4sBackend",
      body =
        s"""
           |trait ${name}Http4sBackend[F[_]] {
           |
           |  import cats.syntax.functor._
           |  import cats.syntax.flatMap._
           |
           |  implicit def syncF: Sync[F]
           |
           |  $defs
           |}
        """.stripMargin,
      imports = List(
        "import io.circe.syntax._",
        "import org.http4s._",
        "import org.http4s.circe._",
        "import cats.effect._",
        "import CirceImplicits._",
      ) ++ defuns.flatMap(_.imports)
    ))
  }

  def genSpace(x: Space, cfg: GeneratorConfig, symt: Symtable): Either[String, List[Code]] = {
    val meta = GeneratorMeta(x, symt, cfg, collectCodKinds(x)(x.symbol))
    for {
      impors <- x.impors.traverse(genImpor(_)(meta))
      exters <- x.exters.traverse(genExter)
      aliass <- x.aliass.traverse(genAlias(_)(meta))
      strucs <- x.strucs.traverse(genStruc(_, wrapEnumType = wrapWithOpenEnum)(meta))
      servcs <- x.servcs.traverse(genServc(_)(meta))
      spaces <- x.spaces.traverse(s => genSpace(s, cfg, symt))
    } yield {
      List(impors, exters, aliass, strucs, servcs)
        .flatten
        .flatten
        .map(_.copy(path = x.name)) ++
        spaces.flatten.map {
          case Code(body, name, path, packageObject, imports) =>
            if (x.name.isEmpty) {
              Code(body, name, path, packageObject, imports)
            } else {
              Code(body, name, s"${x.name}.$path", packageObject, imports)
            }
        }
    }
  }

  def gen(x: Generator.Model.Space, cfg: GeneratorConfig): Either[String, List[Code]] = {
    val d = delimiter
    genSpace(x, cfg, x.symbol).map{ list =>
      val commons = list.filter(_.name.isEmpty)
        .groupBy(_.path)
        .filter(_._2.nonEmpty)
        .flatMap {
          case (path, pathCodes) =>
            pathCodes.groupBy(_.packageObject)
              .map {
                case (packageObject, codes) => {
                  val imp = impMap(packageObject) ++ codes.flatMap(_.imports).distinct.intercalate(d)
                  Code(
                    body = s"object $packageObject {$d$imp$d$d${codes.map(_.body).intercalate(d)}$d}",
                    name = "package.scala",
                    path = path,
                  )
                }
              }
        }
        .toList
        .groupBy(c => c.path -> c.name)
        .map {
          case ((path, name), codes) =>
            val packageDef = s"package $path"
            val imports = codes.flatMap(_.imports).distinct.intercalate(d)
            Code(
              body = s"$packageDef$d$d$imports$d$d" ++ codes.map(_.body).intercalate(d),
              name = name,
              path = path.replace(".", pathDelim)
            )
        }
        .toList
      val content = list.filter(_.name.nonEmpty).map{ code =>
        val packageDef = s"package ${code.path}"
        val imports = code.imports.distinct.intercalate(d)
        code.copy(
          body = s"$packageDef$d$d$imports$d$d${code.body}",
          name = code.name + ".scala",
          path = code.path.replace(".", pathDelim),
        )
      }

      predef ++ commons ++ content
    }
  }

  val impMap = Map(
    "CirceImplicits" -> """
        | import io.circe.syntax._
        | import io.circe.{Encoder, Decoder, Json}
        | import iozhik._
      """.stripMargin,
    "ScodecImplicits" ->
      """
        | import scodec._
        | import codecs._
      """.stripMargin,
    "uPickleImplicits" ->
      """
        | import upickle.default._
        |
        | implicit lazy val javaiofileCodec: ReadWriter[java.io.File] = {
        |   readwriter[upack.Msg].bimap(
        |     x => upack.Str(x.getName),
        |     msg => new java.io.File(readBinary[String](msg))
        |   )
        | }
      """.stripMargin,
  )

}
