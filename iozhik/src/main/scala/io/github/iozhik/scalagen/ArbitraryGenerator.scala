package io.github.iozhik.scalagen

import cats.implicits._
import io.github.iozhik.Generator.Model._
import io.github.iozhik.scalagen.ScalaApiGeneratorUtils._

/** Generates test-scope ScalaCheck generators for every model type, plus the round-trip list: the types that have both
  * an encoder and a decoder.
  *
  * The output goes into a test source tree of its own that holds nothing else, so a regen can wipe it like the main
  * tree. Nothing is published.
  */
object ArbitraryGenerator {

  // The shared primitives, shrinkers and RoundTripCase: a Scala file in the resources, so it gets highlighting
  private lazy val prims: String =
    scala.util.Using.resource(scala.io.Source.fromResource("scalagen/GenPrims.scala", getClass.getClassLoader))(_.mkString)

  private val primitives = Map(
    "Int" -> "genInt",
    "Long" -> "genLong",
    "Boolean" -> "genBoolean",
    "Float" -> "genFloat",
    "Double" -> "genDouble",
    "String" -> "genString",
    "java.io.File" -> "genJavaFile",
  )

  private def objName(typeName: String): String = "Gen" + typeName.capitalize

  /** The generator and shrinker expressions for a field type, and whether the type is free of composites. */
  private final case class FieldGen(gen: String, shrink: String, primitive: Boolean)

  /** One generated type: its object, and the pieces the aggregating object and the round-trip list need. */
  private final case class TypeGen(
    typeName: String,
    obj: String,
    forwarder: String,
    roundTrip: Option[String],
    shrinks: Boolean,
  )

  // The same field-type decisions as genFieldType: which enums are wrapped in OpenEnum depends on the enclosing struct
  // and the codomain set, so this goes through useOpenEnum rather than working it out again.
  private def genFor(k: Kind, enclosing: Option[Kind])(implicit meta: GeneratorMeta): Either[String, FieldGen] =
    k.name match {
      case "List" | "Option" =>
        k.params.headOption.toRight(s"${k.name} without a type parameter").flatMap(genFor(_, enclosing)).map { inner =>
          val (fn, shrinkFn) = if (k.name == "List") ("list", "shrinkList") else ("opt", "shrinkOpt")
          val suffix = if (inner.primitive) "Prim" else ""
          FieldGen(s"$fn$suffix(${inner.gen})", s"$shrinkFn(${inner.shrink})", inner.primitive)
        }
      case "Either" =>
        for {
          a <- genFor(k.params(0), enclosing)
          b <- genFor(k.params(1), enclosing)
        } yield FieldGen(s"either(${a.gen}, ${b.gen})", s"shrinkEither(${a.shrink}, ${b.shrink})", a.primitive && b.primitive)
      case n if primitives.contains(n) => Right(FieldGen(primitives(n), NoShrink, primitive = true))
      case n =>
        meta.symt.resolve(k) match {
          case Some(s: Struc) =>
            val obj = objName(n)
            if (s.isEnum && useOpenEnum(k, enclosing))
              Right(FieldGen(s"openEnum($obj.gen)", s"shrinkOpenEnum($obj.shrink)", primitive = false))
            else Right(FieldGen(s"$obj.gen", s"$obj.shrink", primitive = false))
          case other => Left(s"no generator for kind $n (resolved: $other)")
        }
    }

  private val NoShrink = "noShrink"

  // Constructor-argument order, as in genStruc.
  private def orderedFields(x: Struc)(implicit meta: GeneratorMeta): List[Field] = {
    val usingf = x.usings
      .flatMap(y => meta.symt.find(y.kind, x.kind))
      .collect { case s: Struc => s }
      .flatMap(_.fields)
    usingf ++ requiredFieldsFirst(x.fields)
  }

  // How deep the shallowest value of each type is. At size 0 a sum type picks only its shallowest variants, so every
  // required field there is shallower than its holder and required recursive fields (RichTextBold.text) end. Shortest
  // paths over required fields: a struct is one deeper than its deepest required field, a sum type than its
  // shallowest variant.
  private def minDepths(root: Space)(implicit meta: GeneratorMeta): Map[String, Int] = {
    def withLeaves(s: Struc): List[Struc] = s :: s.leaves.flatMap(withLeaves)
    def strucs(x: Space): List[Struc] = x.strucs.flatMap(withLeaves) ++ x.spaces.flatMap(strucs)
    def depth(k: Kind, depths: Map[String, Int]): Option[Int] = k.name match {
      case "List" | "Option" => Some(0)
      case "Either" => k.params.traverse(depth(_, depths)).map(_.max)
      case n if primitives.contains(n) => Some(0)
      case n => depths.get(n)
    }
    val rules: List[(String, Map[String, Int] => Option[Int])] = strucs(root).flatMap { s =>
      s.kind.map(_.name).toList.flatMap { name =>
        if (s.isEnum)
          (name -> ((d: Map[String, Int]) => variants(s).flatMap(d.get).minOption.map(_ + 1))) ::
            s.wrapps.map(w => w.name.name -> ((d: Map[String, Int]) => depth(w.target, d).map(_ + 1))) ++
            s.enumstrs.map(e => e.name.name -> ((_: Map[String, Int]) => Some(0)))
        else List(name -> ((d: Map[String, Int]) => orderedFields(s).traverse(f => depth(f.kind, d)).map(_.maxOption.getOrElse(0) + 1)))
      }
    }
    def relax(depths: Map[String, Int]): Map[String, Int] = {
      val next = rules.flatMap { case (name, rule) => rule(depths).map(name -> _) }.toMap
      if (next == depths) depths else relax(next)
    }
    relax(Map.empty)
  }

  private def variants(x: Struc): List[String] =
    x.leaves.flatMap(_.kind).map(_.name) ++ x.wrapps.map(_.name.name) ++ x.enumstrs.map(_.name.name)

  // Each type gets its own small object: one object with everything fails to compile on Scala 3 ("Class too large").
  // A custom-gens entry is a function applied to every generated and shrunk value.
  private def emit(
    typeName: String,
    scalaType: String,
    genBody: String,
    shrinkBody: String,
    roundTrip: Option[Boolean],
    meta: GeneratorMeta,
  ): TypeGen = {
    val obj = objName(typeName)
    val custom = meta.cfg.customGens.get(typeName)
    val fix = custom.fold("")(f => s"\n  // custom-gens: see the generator config for the reason\n  private val fix: $scalaType => $scalaType = $f")
    val gen = if (custom.isEmpty) genBody else s"$genBody.map(fix)"
    val shrink = if (custom.isEmpty || shrinkBody == NoShrink) shrinkBody else s"fixed($shrinkBody, fix)"
    val body =
      s"""object $obj {
         |  import GenPrims._$fix
         |  lazy val gen: Gen[$scalaType] = Gen.lzy($gen)
         |  lazy val shrink: Shrink[$scalaType] = $shrink
         |}""".stripMargin
    TypeGen(
      typeName = typeName,
      obj = body,
      forwarder = s"implicit def arb${typeName.capitalize}: Arbitrary[$scalaType] = Arbitrary($obj.gen)",
      roundTrip = roundTrip.map { isOpen =>
        val ctor = if (isOpen) "RoundTripCase.open" else "RoundTripCase"
        s"""$ctor[$scalaType]("$typeName", $obj.gen, $obj.shrink)"""
      },
      shrinks = shrinkBody != NoShrink,
    )
  }

  private def genCaseClass(
    typeName: String,
    fields: List[Field],
    enclosing: Option[Kind],
    roundTrip: Option[Boolean],
  )(implicit meta: GeneratorMeta): Either[String, TypeGen] =
    if (fields.isEmpty)
      Right(emit(typeName, s"$typeName.type", s"Gen.const($typeName)", NoShrink, roundTrip, meta))
    else
      fields.traverse(f => genFor(f.kind, enclosing).map(f -> _)).map { gens =>
        // A flat zip of zips: Gen.zip takes up to 9. Not a for comprehension: a closure per field overflows the Scala 2
        // compiler's stack on the hundred-field types. Fields are passed by name, so the compiler checks the order.
        def group(nodes: List[(String, String)]): List[(String, String)] =
          nodes.grouped(9).toList.map {
            case List(one) => one
            case g => (s"Gen.zip(${g.map(_._1).mkString(", ")})", g.map(_._2).mkString("(", ", ", ")"))
          }
        def top(nodes: List[(String, String)]): (String, String) =
          if (nodes.size <= 9) group(nodes).head else top(group(nodes))
        val (src, pat) = top(gens.zipWithIndex.map { case ((_, g), i) => (g.gen, s"a$i") })
        val args = gens.zipWithIndex.map { case ((f, _), i) => s"${sanitize(f.name)} = a$i" }.mkString(", ")
        val lambda = if (gens.size == 1) pat else s"case $pat"
        val body = s"$src.map { $lambda => $typeName($args) }"
        // primitive fields don't shrink, so they get no entry
        val shrinkers = gens.collect { case (f, g) if g.shrink != NoShrink =>
          val name = sanitize(f.name)
          s"field(x.$name)(${g.shrink})(v => x.copy($name = v))"
        }
        val shrink =
          if (shrinkers.isEmpty) NoShrink
          else s"shrinkFields[$typeName](x => List(${shrinkers.mkString(", ")}))"
        emit(typeName, typeName, s"deeper($body)", shrink, roundTrip, meta)
      }

  private def sanitize(name: String): String = io.github.iozhik.GeneratorUtils.sanitize(name, keywords)

  private def genStrucs(x: Struc, depths: Map[String, Int])(implicit meta: GeneratorMeta): Either[String, List[TypeGen]] = {
    val kind = x.kind.map(_.name).toRight("No kind name")
    // mirrors where genJsonCodecs emits a decoder
    val decodable = isCodKind(x)
    if (x.isAbstract) Right(Nil)
    else if (x.isEnum) {
      val isOpen = x.kind.exists(k => useOpenEnum(k, struc = None))
      for {
        name <- kind
        leaves <- x.leaves.flatTraverse(genStrucs(_, depths))
        // wrapps and enum strings always get both codecs
        wrapps <- x.wrapps.traverse { w =>
          genFor(w.target, x.kind).map { g =>
            val n = w.name.name
            val shrink =
              if (g.shrink == NoShrink) NoShrink
              else s"Shrink[$n](x => ${g.shrink}.shrink(x.${sanitize(w.fname)}).map($n(_)))"
            emit(n, n, s"deeper(${g.gen}.map($n(_)))", shrink, Some(false), meta)
          }
        }
        enumStrs = x.enumstrs.map { e =>
          emit(e.name.name, s"${e.name.name}.type", s"Gen.const(${e.name.name})", NoShrink, Some(false), meta)
        }
        all = variants(x)
        gens = (vs: List[String]) => vs.map(objName(_) + ".gen").mkString(", ")
        shallowest = all.flatMap(depths.get).minOption
        atZero = all.filter(v => depths.get(v) == shallowest)
        parentBody =
          if (all.size == 1) s"${objName(all.head)}.gen"
          else if (atZero.isEmpty || atZero == all) s"Gen.oneOf[$name](${gens(all)})"
          else s"sum[$name](List(${gens(all)}), List(${gens(atZero)}))"
        shrinkable = (leaves ++ wrapps).filter(t => t.shrinks && all.contains(t.typeName)).map(_.typeName)
        parentShrink =
          if (shrinkable.isEmpty) NoShrink
          else
            s"shrinkSum[$name](${shrinkable.map(v => s"variant(${objName(v)}.shrink)").mkString(", ")})"
      } yield leaves ++ wrapps ++ enumStrs :+ emit(name, name, parentBody, parentShrink, Option.when(decodable)(isOpen), meta)
    } else
      kind.flatMap(genCaseClass(_, orderedFields(x), x.kind, Option.when(decodable)(false)))
        .map(List(_))
  }

  private def genSpace(x: Space, cfg: GeneratorConfig, symt: Symtable, pkg: List[String], depths: Map[String, Int]): Either[String, List[(List[String], List[TypeGen])]] = {
    implicit val meta: GeneratorMeta = GeneratorMeta(x, symt, cfg, collectCodKinds(x)(x.symbol))
    val here = pkg ++ Option(x.name).filter(_.nonEmpty)
    for {
      strucs <- if (x.opts.contains("circe")) x.strucs.flatTraverse(genStrucs(_, depths)) else Right(Nil)
      spaces <- x.spaces.flatTraverse(genSpace(_, cfg, symt, here, depths))
    } yield (if (strucs.isEmpty) Nil else List(here -> strucs)) ++ spaces
  }

  // On top of the main config: scalafmt otherwise puts every argument on a line of its own, and the wide types have a
  // hundred fields. One comment: a second one replaces the first.
  private val fmtOverrides =
    "// scalafmt: { binPack.callSite = always, binPack.defnSite = always, danglingParentheses.callSite = false, " +
      "newlines.configStyle.callSite.prefer = false, newlines.topLevelStatementBlankLines = [], align.preset = some }"

  private def file(pkg: String, name: String, imports: String, body: String): Code =
    Code(
      body = s"""package $pkg
                |
                |$imports
                |
                |// GENERATED BY iozhik - DO NOT EDIT
                |$fmtOverrides
                |$body
                |""".stripMargin,
      name = name,
      path = pkg.replace(".", "/"),
      test = true,
    )

  def gen(x: Space, cfg: GeneratorConfig): Either[String, List[Code]] = {
    val depths = minDepths(x)(GeneratorMeta(x, x.symbol, cfg, collectCodKinds(x)(x.symbol)))
    genSpace(x, cfg, x.symbol, Nil, depths).map(_.flatMap { case (pkgPath, types) =>
      val pkg = pkgPath.mkString(".")
      List(
        file(pkg, "GenPrims.scala", "import org.scalacheck.{Gen, Shrink}\nimport io.circe.{Decoder, Encoder}\nimport scala.reflect.ClassTag", prims),
        file(pkg, "GenTypes.scala", "import org.scalacheck.{Gen, Shrink}", types.map(_.obj).mkString("\n\n")),
        file(
          pkg,
          "Gens.scala",
          "import org.scalacheck.Arbitrary",
          s"object Gens {\n${types.map(_.forwarder).mkString("\n")}\n}"
        ),
        file(
          pkg,
          "RoundTrips.scala",
          s"import $pkg.CirceImplicits._\nimport $pkg.GenPrims.RoundTripCase",
          s"object RoundTrips {\n  val all: List[RoundTripCase[_]] = List(\n${types.flatMap(_.roundTrip).map("    " + _).mkString(",\n")}\n  )\n}"
        ),
      )
    })
  }
}
