package io.github.iozhik.scalagen

import cats.implicits._
import io.github.iozhik.Generator.Model._
import io.github.iozhik.scalagen.ScalaApiGeneratorUtils._

/** Generates test-scope ScalaCheck generators for every model type, plus the round-trip list: the types that have both
  * an encoder and a decoder.
  *
  * The output goes next to the generated sources, in the sibling `test/scala` tree. Nothing is published.
  */
object ArbitraryGenerator {

  private val TestSourcesPath = "../../test/scala"

  // Recursion is bounded by a size budget: every composite generator spends one unit, and optional or list fields of
  // composite type collapse to None/Nil at zero.
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
      |  def sequenceAll(gs: List[Gen[Any]]): Gen[Vector[Any]] =
      |    gs.foldLeft(Gen.const(Vector.empty[Any]))((acc, g) => acc.flatMap(v => g.map(v :+ _)))
      |
      |  def either[A, B](a: => Gen[A], b: => Gen[B]): Gen[Either[A, B]] =
      |    Gen.oneOf(Gen.lzy(a).map(Left(_)), Gen.lzy(b).map(Right(_)))
      |
      |  // Only Known values: Unknown values are receive-only
      |  def openEnum[A](g: => Gen[A]): Gen[iozhik.OpenEnum[A]] = Gen.lzy(g).map(iozhik.OpenEnum.Known(_))
      |
      |  def shrinkTo[A](candidates: A => List[A]): Shrink[A] = Shrink(a => candidates(a).toStream)
      |  def noShrink[A]: Shrink[A] = Shrink.shrinkAny
      |  def shrinkOpt[A](s: => Shrink[A]): Shrink[Option[A]] =
      |    shrinkTo[Option[A]] {
      |      case Some(a) => None :: s.shrink(a).toList.map(Some(_))
      |      case None => Nil
      |    }
      |  def shrinkList[A](s: => Shrink[A]): Shrink[List[A]] =
      |    shrinkTo[List[A]] { l =>
      |      if (l.isEmpty) Nil
      |      else Nil :: l.indices.toList.flatMap(i => s.shrink(l(i)).toList.map(l.updated(i, _)))
      |    }
      |  def shrinkEither[A, B](sa: => Shrink[A], sb: => Shrink[B]): Shrink[Either[A, B]] =
      |    shrinkTo[Either[A, B]] {
      |      case Left(a) => sa.shrink(a).toList.map(Left(_))
      |      case Right(b) => sb.shrink(b).toList.map(Right(_))
      |    }
      |  def shrinkOpenEnum[A](s: => Shrink[A]): Shrink[iozhik.OpenEnum[A]] =
      |    shrinkTo[iozhik.OpenEnum[A]] {
      |      case iozhik.OpenEnum.Known(a) => s.shrink(a).toList.map(iozhik.OpenEnum.Known(_))
      |      case _ => Nil
      |    }
      |  // the shrunk variants of one field, each rebuilt into the whole value
      |  def shrinkField[A, F](field: F)(s: => Shrink[F])(copy: F => A): List[A] =
      |    s.shrink(field).toList.map(copy)
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
    scalaType: String,
    obj: String,
    forwarder: String,
    roundTrip: Option[String],
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
      case n if primitives.contains(n) => Right(FieldGen(primitives(n), "noShrink", primitive = true))
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

  // Constructor-argument order, as in genStruc.
  private def orderedFields(x: Struc)(implicit meta: GeneratorMeta): List[Field] = {
    val usingf = x.usings
      .flatMap(y => meta.symt.find(y.kind, x.kind))
      .collect { case s: Struc => s }
      .flatMap(_.fields)
    usingf ++ requiredFieldsFirst(x.fields)
  }

  // Each type gets its own small object: one object with everything fails to compile on Scala 3 ("Class too large").
  // `generated` is what iozhik derives; `gen` is that or the custom-gens override of it.
  private def emit(
    typeName: String,
    scalaType: String,
    genBody: String,
    shrinkBody: String,
    roundTrip: Option[Boolean],
    meta: GeneratorMeta,
  ): TypeGen = {
    val obj = objName(typeName)
    val genExpr = meta.cfg.customGens.getOrElse(typeName, "generated")
    val body =
      s"""object $obj {
         |  import GenPrims._
         |  lazy val generated: Gen[$scalaType] = Gen.lzy($genBody)
         |  lazy val gen: Gen[$scalaType] = $genExpr
         |  lazy val shrink: Shrink[$scalaType] = $shrinkBody
         |  implicit lazy val arb: Arbitrary[$scalaType] = Arbitrary(gen)
         |}""".stripMargin
    TypeGen(
      typeName = typeName,
      scalaType = scalaType,
      obj = body,
      forwarder = s"implicit def arb${typeName.capitalize}: Arbitrary[$scalaType] = $obj.arb",
      roundTrip = roundTrip.map { isOpen =>
        val ctor = if (isOpen) "RoundTripCase.open" else "RoundTripCase"
        s"""$ctor[$scalaType]("$typeName", $obj.gen, $obj.shrink)"""
      },
    )
  }

  private def genCaseClass(
    typeName: String,
    fields: List[Field],
    enclosing: Option[Kind],
    roundTrip: Option[Boolean],
  )(implicit meta: GeneratorMeta): Either[String, TypeGen] =
    if (fields.isEmpty)
      Right(emit(typeName, s"$typeName.type", s"Gen.const($typeName)", "noShrink", roundTrip, meta))
    else
      fields.traverse { f =>
        for {
          g <- genFor(f.kind, enclosing)
          tpe <- genFieldType(f.kind, enclosing)
        } yield (f, g, tpe)
      }.map { gens =>
        // Not a for comprehension: a hundred nested closures overflow the Scala 2 compiler's stack
        val args = gens.zipWithIndex.map { case ((_, _, tpe), i) => s"v($i).asInstanceOf[$tpe]" }.mkString(", ")
        val shrinkers = gens.map { case (f, g, _) =>
          val name = sanitize(f.name)
          s"shrinkField(x.$name)(${g.shrink})(v => x.copy($name = v))"
        }
        emit(
          typeName,
          typeName,
          s"deeper(sequenceAll(List[Gen[Any]](\n${gens.map(_._2.gen).mkString(",\n")}\n)).map(v => $typeName($args)))",
          s"shrinkTo[$typeName](x => List(\n${shrinkers.mkString(",\n")}\n).flatten)",
          roundTrip,
          meta,
        )
      }

  private def sanitize(name: String): String = io.github.iozhik.GeneratorUtils.sanitize(name, keywords)

  private def genStrucs(x: Struc)(implicit meta: GeneratorMeta): Either[String, List[TypeGen]] = {
    val kind = x.kind.map(_.name).toRight("No kind name")
    // mirrors where genJsonCodecs emits a decoder
    val decodable = isCodKind(x)
    if (x.isAbstract) Right(Nil)
    else if (x.isEnum) {
      val isOpen = x.kind.exists(k => useOpenEnum(k, struc = None))
      for {
        name <- kind
        leaves <- x.leaves.flatTraverse(genStrucs)
        // wrapps and enum strings always get both codecs
        wrapps <- x.wrapps.traverse { w =>
          genFor(w.target, x.kind).map { g =>
            val n = w.name.name
            emit(n, n, s"deeper(${g.gen}.map($n(_)))", s"shrinkTo[$n](x => ${g.shrink}.shrink(x.${sanitize(w.fname)}).toList.map($n(_)))", Some(false), meta)
          }
        }
        enumStrs = x.enumstrs.map { e =>
          emit(e.name.name, s"${e.name.name}.type", s"Gen.const(${e.name.name})", "noShrink", Some(false), meta)
        }
        variants = x.leaves.flatMap(_.kind).map(_.name) ++ x.wrapps.map(_.name.name) ++ x.enumstrs.map(_.name.name)
        parentBody =
          if (variants.size == 1) s"${objName(variants.head)}.gen"
          else s"Gen.oneOf[$name](${variants.map(v => objName(v) + ".gen").mkString(", ")})"
        // case objects have nothing to shrink, and can't be matched by type
        shrinkable = x.leaves.filter(l => l.fields.nonEmpty || l.usings.nonEmpty).flatMap(_.kind).map(_.name) ++
          x.wrapps.map(_.name.name)
        parentShrink =
          s"shrinkTo[$name] {\n" + shrinkable
            .map(v => s"case v: $v => ${objName(v)}.shrink.shrink(v).toList").mkString("\n") + "\ncase _ => Nil\n}"
      } yield leaves ++ wrapps ++ enumStrs :+ emit(name, name, parentBody, parentShrink, Option.when(decodable)(isOpen), meta)
    } else
      kind.flatMap(genCaseClass(_, orderedFields(x), x.kind, Option.when(decodable)(false)))
        .map(List(_))
  }

  private def genSpace(x: Space, cfg: GeneratorConfig, symt: Symtable, pkg: List[String]): Either[String, List[(List[String], List[TypeGen])]] = {
    implicit val meta: GeneratorMeta = GeneratorMeta(x, symt, cfg, collectCodKinds(x)(x.symbol))
    val here = pkg ++ Option(x.name).filter(_.nonEmpty)
    for {
      strucs <- if (x.opts.contains("circe")) x.strucs.flatTraverse(genStrucs) else Right(Nil)
      spaces <- x.spaces.flatTraverse(genSpace(_, cfg, symt, here))
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

  def gen(x: Space, cfg: GeneratorConfig): Either[String, List[Code]] =
    genSpace(x, cfg, x.symbol, Nil).map(_.flatMap { case (pkgPath, types) =>
      val pkg = pkgPath.mkString(".")
      val scalacheck = "import org.scalacheck.{Arbitrary, Gen, Shrink}"
      List(
        file(pkg, "GenPrims.scala", s"$scalacheck\nimport io.circe.{Decoder, Encoder}", s"object GenPrims {\n$prelude\n}"),
        file(pkg, "GenTypes.scala", s"$scalacheck", types.map(_.obj).mkString("\n\n")),
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
