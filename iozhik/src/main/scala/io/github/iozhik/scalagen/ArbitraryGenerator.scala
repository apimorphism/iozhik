package io.github.iozhik.scalagen

import cats.implicits._
import io.github.iozhik.Generator.Model._
import io.github.iozhik.scalagen.ScalaApiGeneratorUtils._

/** Generates test-scope ScalaCheck generators for every model type, plus the round-trip list: the types that have both
  * an encoder and a decoder.
  *
  * The output goes next to the generated sources, into a sibling `test/scala-generated` tree that holds nothing else, so
  * a regen can wipe it like the main tree. Nothing is published.
  */
object ArbitraryGenerator {

  val TestSourcesPath = "../../test/scala-generated"

  // Recursion is bounded by a size budget: every composite generator spends one unit, optional or list fields of
  // composite type collapse to None/Nil at zero, and sum types pick their shallowest variants.
  private val prelude: String =
    """
      |  val Budget = 3
      |
      |  // capped at Budget, so that the generators are bounded whatever size they are run at
      |  def deeper[A](g: Gen[A]): Gen[A] = Gen.sized(n => Gen.resize(math.max(math.min(n, Budget) - 1, 0), g))
      |
      |  private val edgeInts = Seq(0, 1, -1, 255, 256, 65535, Int.MaxValue, Int.MinValue)
      |  val genInt: Gen[Int] = Gen.frequency(2 -> Gen.oneOf(edgeInts), 3 -> Gen.choose(-100000, 100000))
      |
      |  // IDs above 2^31 are what broke us before
      |  private val edgeLongs =
      |    Seq(0L, 1L, -1L, Int.MaxValue.toLong, Int.MaxValue.toLong + 1, Int.MinValue.toLong - 1, 4294967296L,
      |      -1001234567890L, 7000000000000L, Long.MaxValue, Long.MinValue)
      |  val genLong: Gen[Long] = Gen.frequency(3 -> Gen.oneOf(edgeLongs), 2 -> Gen.choose(Long.MinValue, Long.MaxValue))
      |
      |  val genBoolean: Gen[Boolean] = Gen.oneOf(true, false)
      |
      |  val genFloat: Gen[Float] = Gen.frequency(1 -> Gen.const(0.0f), 3 -> Gen.choose(-1000.0f, 1000.0f))
      |  val genDouble: Gen[Double] = Gen.frequency(1 -> Gen.const(0.0), 3 -> Gen.choose(-1000.0, 1000.0))
      |
      |  private val edgeStrings =
      |    Seq("Привет", "日本語のテキスト", "😀🎲", "naïve café", "line1\nline2", "quote\"and\\slash", "")
      |  val genString: Gen[String] = Gen.frequency(
      |    1 -> Gen.oneOf(edgeStrings),
      |    4 -> Gen.listOfN(8, Gen.alphaNumChar).map(_.mkString)
      |  )
      |
      |  // a plain file name; checks that build wire requests create the matching temporary files themselves
      |  val genJavaFile: Gen[java.io.File] = Gen.alphaNumStr.map(s => new java.io.File(s + ".bin"))
      |
      |  def opt[A](g: => Gen[A]): Gen[Option[A]] =
      |    Gen.sized(n => if (n <= 0) Gen.const(None) else Gen.frequency(1 -> Gen.const(None), 1 -> g.map(Some(_))))
      |  def optPrim[A](g: => Gen[A]): Gen[Option[A]] = Gen.frequency(1 -> Gen.const(None), 1 -> g.map(Some(_)))
      |
      |  def list[A](g: => Gen[A]): Gen[List[A]] =
      |    Gen.sized { n =>
      |      if (n <= 0) Gen.const(Nil)
      |      else Gen.frequency(1 -> Gen.const(Nil), 3 -> Gen.choose(1, 3).flatMap(Gen.listOfN(_, g)))
      |    }
      |  def listPrim[A](g: => Gen[A]): Gen[List[A]] =
      |    Gen.frequency(1 -> Gen.const(Nil), 3 -> Gen.choose(1, 3).flatMap(Gen.listOfN(_, g)))
      |
      |  // a sum type whose variants aren't all equally shallow: at size 0 it picks only the shallowest ones, so required
      |  // recursive fields end there
      |  def sum[A](all: Seq[Gen[A]], atZero: Seq[Gen[A]]): Gen[A] = {
      |    def pick(gs: Seq[Gen[A]]): Gen[A] = if (gs.size == 1) gs.head else Gen.choose(0, gs.size - 1).flatMap(gs(_))
      |    Gen.sized(n => if (n <= 0) pick(atZero) else pick(all))
      |  }
      |
      |  def either[A, B](a: => Gen[A], b: => Gen[B]): Gen[Either[A, B]] =
      |    Gen.oneOf(Gen.lzy(a).map(Left(_)), Gen.lzy(b).map(Right(_)))
      |
      |  // Only Known values: Unknown values are receive-only
      |  def openEnum[A](g: => Gen[A]): Gen[iozhik.OpenEnum[A]] = Gen.lzy(g).map(iozhik.OpenEnum.Known(_))
      |
      |  // Shrinking only drops optional and list values, and shrinks the composite ones: primitives stay as generated, so a
      |  // shrunk value can't break a restriction on them. Everything is lazy, as ScalaCheck stops at the first failing one.
      |  def noShrink[A]: Shrink[A] = Shrink.shrinkAny
      |  def shrinkOpt[A](s: => Shrink[A]): Shrink[Option[A]] =
      |    Shrink {
      |      case Some(a) => None #:: s.shrink(a).map(Some(_))
      |      case None => Stream.empty
      |    }
      |  def shrinkList[A](s: => Shrink[A]): Shrink[List[A]] =
      |    Shrink { l =>
      |      if (l.isEmpty) Stream.empty
      |      else Nil #:: l.indices.toStream.flatMap(i => s.shrink(l(i)).map(l.updated(i, _)))
      |    }
      |  def shrinkEither[A, B](sa: => Shrink[A], sb: => Shrink[B]): Shrink[Either[A, B]] =
      |    Shrink {
      |      case Left(a) => sa.shrink(a).map(Left(_))
      |      case Right(b) => sb.shrink(b).map(Right(_))
      |    }
      |  def shrinkOpenEnum[A](s: => Shrink[A]): Shrink[iozhik.OpenEnum[A]] =
      |    Shrink {
      |      case iozhik.OpenEnum.Known(a) => s.shrink(a).map(iozhik.OpenEnum.Known(_))
      |      case _ => Stream.empty
      |    }
      |  // a sum type shrinks as the variant it is; case objects have nothing to shrink, and can't be matched by type
      |  def shrinkSum[A](variants: PartialFunction[A, Stream[A]]*): Shrink[A] =
      |    Shrink(a => variants.find(_.isDefinedAt(a)).fold(Stream.empty[A])(_(a)))
      |  def variant[A, B <: A: ClassTag](s: Shrink[B]): PartialFunction[A, Stream[A]] = { case b: B => s.shrink(b) }
      |  // the shrunk variants of each field, each rebuilt into the whole value
      |  def shrinkFields[A](fields: A => List[Stream[A]]): Shrink[A] = Shrink(a => fields(a).toStream.flatten)
      |  def field[A, F](value: F)(s: => Shrink[F])(copy: F => A): Stream[A] = s.shrink(value).map(copy)
      |  // a custom-gens fix applies to the shrunk values too, so shrinking can't produce what the generator never would
      |  def fixed[A](s: Shrink[A], fix: A => A): Shrink[A] = Shrink(a => s.shrink(a).map(fix).filter(_ != a))
      |
      |  /** A type with both an encoder and a decoder, with the generator to feed it. */
      |  final class RoundTripCase[A](
      |    val name: String,
      |    val gen: Gen[A],
      |    val shrink: Shrink[A],
      |    val encoder: Encoder[A],
      |    val decoder: Decoder[A]
      |  )
      |
      |  object RoundTripCase {
      |    def apply[A](name: String, gen: Gen[A], shrink: Shrink[A])(implicit e: Encoder[A], d: Decoder[A]): RoundTripCase[A] =
      |      new RoundTripCase(name, gen, shrink, e, d)
      |
      |    // decoded as an OpenEnum; generators only produce the Known values
      |    def open[A](name: String, gen: Gen[A], shrink: Shrink[A])(implicit e: Encoder[A], d: Decoder[iozhik.OpenEnum[A]]): RoundTripCase[A] =
      |      new RoundTripCase(name, gen, shrink, e, d.emap {
      |        case iozhik.OpenEnum.Known(a) => Right(a)
      |        case iozhik.OpenEnum.Unknown(v) => Left("unknown value: " + v)
      |      })
      |  }
      |""".stripMargin

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
        // Gen.zip groups of up to 9 fields: a for comprehension with a closure per field overflows the Scala 2
        // compiler's stack on the hundred-field types
        val groups = gens.map(_._2.gen).zipWithIndex.grouped(9).toList
        val steps = groups.zipWithIndex.map { case (group, gi) =>
          val method = if (gi == groups.size - 1) "map" else "flatMap"
          group match {
            case List((g, i)) => s"$g.$method { a$i =>"
            case _ =>
              s"Gen.zip(${group.map(_._1).mkString(", ")}).$method { ${group.map("a" + _._2).mkString("case (", ", ", ")")} =>"
          }
        }
        val args = groups.map(_.map("a" + _._2).mkString(", "))
        val ctor = if (args.size == 1) s"$typeName(${args.head})" else s"$typeName(${args.map("\n        " + _).mkString(",")}\n      )"
        // laid out like a for comprehension: one line per group, closed together at the end
        val body = steps.map("\n    " + _).mkString + s"\n      $ctor\n    " + "}" * steps.size
        // primitive fields don't shrink, so they get no entry
        val shrinkers = gens.collect { case (f, g) if g.shrink != NoShrink =>
          val name = sanitize(f.name)
          s"field(x.$name)(${g.shrink})(v => x.copy($name = v))"
        }
        val shrink =
          if (shrinkers.isEmpty) NoShrink
          else s"shrinkFields[$typeName](x => List(${shrinkers.map("\n    " + _).mkString(",")}\n  ))"
        emit(typeName, typeName, s"deeper($body\n  )", shrink, roundTrip, meta)
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
            s"shrinkSum[$name](" + shrinkable.map(v => s"\n    variant(${objName(v)}.shrink)").mkString(",") + "\n  )"
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

  private def file(pkg: String, name: String, imports: String, body: String): Code =
    Code(
      body = s"""package $pkg
                |
                |$imports
                |
                |// GENERATED BY iozhik - DO NOT EDIT
                |$body
                |""".stripMargin,
      name = name,
      path = TestSourcesPath + "/" + pkg.replace(".", "/"),
    )

  def gen(x: Space, cfg: GeneratorConfig): Either[String, List[Code]] = {
    val depths = minDepths(x)(GeneratorMeta(x, x.symbol, cfg, collectCodKinds(x)(x.symbol)))
    genSpace(x, cfg, x.symbol, Nil, depths).map(_.flatMap { case (pkgPath, types) =>
      val pkg = pkgPath.mkString(".")
      List(
        file(pkg, "GenPrims.scala", "import org.scalacheck.{Gen, Shrink}\nimport io.circe.{Decoder, Encoder}\nimport scala.reflect.ClassTag", s"// ScalaCheck's Shrink is built on Stream, deprecated since Scala 2.13\n@scala.annotation.nowarn(\"cat=deprecation\")\nobject GenPrims {\n$prelude\n}"),
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
