package io.github.iozhik.scalagen

// PROTOTYPE (branch prototype/arbitrary-gens): throwaway spike that emits ScalaCheck Gen/Arbitrary instances
// for every struct, enum variant and method request into a single test-scope file. Not for merging.

import cats.implicits._
import io.github.iozhik.Generator.Model._
import io.github.iozhik.GeneratorUtils.sanitize
import io.github.iozhik.scalagen.ScalaApiGeneratorUtils._

object ArbitraryGenerator {

  // Hand-written primitives the generated gens are built from. `Budget` is the recursion depth: every
  // composite gen spends one unit of `size`, and optional/list fields of composite type collapse to
  // None/Nil once it hits zero.
  val prelude: String =
    """
      |  val Budget = 3
      |
      |  def deeper[A](g: Gen[A]): Gen[A] = Gen.sized(n => Gen.resize(math.max(n - 1, 0), g))
      |
      |  private val edgeInts = Seq(0, 1, -1, 255, 256, 65535, Int.MaxValue, Int.MinValue)
      |  val genInt: Gen[Int] = Gen.frequency(2 -> Gen.oneOf(edgeInts), 3 -> Gen.choose(-100000, 100000))
      |
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
      |  private val nonAscii = Seq("Привет", "日本語のテキスト", "😀🎲", "naïve café", "line1\nline2", "quote\"and\\slash", "")
      |  val genString: Gen[String] = Gen.frequency(
      |    1 -> Gen.oneOf(nonAscii),
      |    4 -> Gen.listOfN(8, Gen.alphaNumChar).map(_.mkString)
      |  )
      |
      |  val genJavaFile: Gen[java.io.File] = Gen.alphaNumStr.map(s => new java.io.File(s + ".bin"))
      |
      |  def opt[A](g: => Gen[A]): Gen[Option[A]] =
      |    Gen.sized(n => if (n <= 0) Gen.const(None) else Gen.frequency(1 -> Gen.const(None), 1 -> g.map(Some(_))))
      |  def optP[A](g: => Gen[A]): Gen[Option[A]] = Gen.frequency(1 -> Gen.const(None), 1 -> g.map(Some(_)))
      |
      |  def list[A](g: => Gen[A]): Gen[List[A]] =
      |    Gen.sized { n =>
      |      if (n <= 0) Gen.const(Nil)
      |      else Gen.frequency(1 -> Gen.const(Nil), 3 -> Gen.choose(1, 3).flatMap(Gen.listOfN(_, g)))
      |    }
      |  def listP[A](g: => Gen[A]): Gen[List[A]] =
      |    Gen.frequency(1 -> Gen.const(Nil), 3 -> Gen.choose(1, 3).flatMap(Gen.listOfN(_, g)))
      |
      |  def either[A, B](a: => Gen[A], b: => Gen[B]): Gen[Either[A, B]] =
      |    Gen.oneOf(Gen.lzy(a).map(Left(_)), Gen.lzy(b).map(Right(_)))
      |
      |  // PROTOTYPE: Unknown encodes as a bare string, so it cannot round-trip through object-shaped enums
      |  @volatile var allowUnknown: Boolean = true
      |  def openEnum[A](g: => Gen[A]): Gen[iozhik.OpenEnum[A]] =
      |    Gen.frequency(
      |      9 -> Gen.lzy(g).map(iozhik.OpenEnum.Known(_)),
      |      (if (allowUnknown) 1 else 0) -> Gen.alphaNumStr.map(s => iozhik.OpenEnum.Unknown[A]("unknown_" + s))
      |    )
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

  private def objName(kindName: String): String = "Gen" + kindName.capitalize
  private def genName(kindName: String): String = objName(kindName) + ".gen"

  // Mirrors genFieldType: which kinds get wrapped in OpenEnum depends on the enclosing struct and the codomain set.
  private def genFor(k: Kind, enclosing: Option[Kind], wrapOpen: Boolean)(implicit meta: GeneratorMeta): Either[String, (String, Boolean)] =
    // (expr, isPrimitive) - primitives skip the depth cutoff in opt/list
    k.name match {
      case "List" | "Option" =>
        genFor(k.params.head, enclosing, wrapOpen).map { case (inner, prim) =>
          val fn = (if (k.name == "List") "list" else "opt") + (if (prim) "P" else "")
          (s"$fn($inner)", prim)
        }
      case "Either" =>
        for {
          a <- genFor(k.params(0), enclosing, wrapOpen)
          b <- genFor(k.params(1), enclosing, wrapOpen)
        } yield (s"either(${a._1}, ${b._1})", a._2 && b._2)
      case n if primitives.contains(n) => Right((primitives(n), true))
      case n =>
        meta.symt.resolve(k) match {
          case Some(s: Struc) =>
            val base = genName(n)
            if (s.isEnum && wrapOpen && useOpenEnum(k, enclosing)) Right((s"openEnum($base)", false))
            else Right((base, false))
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

  private def genCaseClass(name: String, fields: List[Field], enclosing: Option[Kind], wrapOpen: Boolean, isObject: Boolean)(
    implicit meta: GeneratorMeta
  ): Either[String, (String, String)] =
    if (fields.isEmpty && isObject) Right((s"$name.type", "Gen.const(" + name + ")"))
    else
      fields.traverse(f => genFor(f.kind, enclosing, wrapOpen).map(f -> _._1)).map { gens =>
        val enumerators = gens.zipWithIndex.map { case ((_, g), i) => s"f$i <- $g" }.mkString("\n")
        val args = gens.indices.map(i => s"f$i").mkString(", ")
        (name, s"deeper(for {\n$enumerators\n} yield $name($args))")
      }

  // Each type gets its own small object (one big object overflows the JVM class-size limit); `arb` instances are
  // exposed through the aggregating `Gens` object so they are not ambiguous when several objects are imported.
  private def emit(typeName: String, scalaType: String, body: String, meta: GeneratorMeta): List[String] = {
    val obj = objName(typeName)
    val genDef = meta.cfg.customGens.get(typeName) match {
      case Some(expr) =>
        s"""  private lazy val generated: Gen[$scalaType] = Gen.lzy($body)
           |  lazy val gen: Gen[$scalaType] = $expr""".stripMargin
      case None => s"  lazy val gen: Gen[$scalaType] = $body"
    }
    List(
      s"""object $obj {
         |  import GenPrims._
         |$genDef
         |  lazy val arb: Arbitrary[$scalaType] = Arbitrary(Gen.resize(Budget, gen))
         |}""".stripMargin,
      s"AGG implicit def arb${typeName.capitalize}: Arbitrary[$scalaType] = $obj.arb",
      s"ALL (\"$typeName\", $obj.gen)",
    )
  }

  private def genStrucs(x: Struc)(implicit meta: GeneratorMeta): Either[String, List[String]] = {
    val kindName = x.kind.map(_.name).getOrElse("$$")
    val wrapOpen = true
    if (x.isAbstract) Right(Nil)
    else if (x.isEnum) {
      val leafGens = x.leaves.flatTraverse(genStrucs)
      val wrappGens = x.wrapps.traverse { w =>
        genFor(w.target, x.kind, wrapOpen).map { case (g, _) =>
          emit(w.name.name, w.name.name, s"deeper($g.map(${w.name.name}(_)))", meta)
        }
      }.map(_.flatten)
      val enumStrGens = x.enumstrs.map { e =>
        emit(e.name.name, s"${e.name.name}.type", s"Gen.const(${e.name.name})", meta)
      }.flatten
      val variants =
        x.leaves.flatMap(_.kind).map(_.name) ++ x.wrapps.map(_.name.name) ++ x.enumstrs.map(_.name.name)
      val parentBody =
        if (variants.size == 1) genName(variants.head)
        else s"Gen.oneOf[$kindName](${variants.map(genName).mkString(", ")})"
      for {
        l <- leafGens
        w <- wrappGens
      } yield l ++ w ++ enumStrGens ++ emit(kindName, kindName, parentBody, meta)
    } else {
      genCaseClass(kindName, orderedFields(x), x.kind, wrapOpen, isObject = true).map { case (tpe, body) =>
        emit(kindName, tpe, body, meta)
      }
    }
  }

  private def genDefuns(x: Space)(implicit meta: GeneratorMeta): Either[String, List[String]] =
    x.servcs.flatMap(_.items).collect { case d: Defun => d }.flatTraverse { d =>
      d.dom match {
        case Right(struc) if struc.fields.nonEmpty || struc.usings.nonEmpty =>
          val name = d.kind.name.capitalize + "Req"
          // method parameters are never OpenEnum-wrapped (genStruc is called with wrapEnumType = identity)
          genCaseClass(name, orderedFields(struc), Some(Kind(name)), wrapOpen = false, isObject = false).map { case (_, body) =>
            emit(name, name, body, meta)
          }
        case _ => Right(Nil)
      }
    }

  private def genSpace(x: Space, cfg: GeneratorConfig, symt: Symtable): Either[String, List[String]] = {
    implicit val meta: GeneratorMeta = GeneratorMeta(x, symt, cfg, collectCodKinds(x)(x.symbol))
    for {
      strucs <- x.strucs.flatTraverse(genStrucs)
      defuns <- genDefuns(x)
      spaces <- x.spaces.flatTraverse(genSpace(_, cfg, symt))
    } yield strucs ++ defuns ++ spaces
  }

  def gen(x: Space, cfg: GeneratorConfig): Either[String, Code] =
    genSpace(x, cfg, x.symbol).map { items =>
      val aggs = items.filter(_.startsWith("AGG "))
      val alls = items.filter(_.startsWith("ALL "))
      val objs = items.filterNot(i => i.startsWith("AGG ") || i.startsWith("ALL "))
      val body =
        s"""package telegramium.bots
           |
           |import org.scalacheck.{Arbitrary, Gen}
           |import telegramium.bots.client._
           |
           |// GENERATED BY iozhik PROTOTYPE (prototype/arbitrary-gens) - DO NOT EDIT
           |object GenPrims {
           |$prelude
           |}
           |
           |${objs.mkString("\n\n")}
           |
           |object Gens {
           |${aggs.map(_.stripPrefix("AGG ")).mkString("\n")}
           |
           |  // PROTOTYPE: registry used by the measuring spec
           |  def all: List[(String, Gen[Any])] = List(
           |${alls.map(_.stripPrefix("ALL ")).mkString(",\n")}
           |  )
           |}
           |""".stripMargin
      Code(body = body, name = "Gens.scala", path = "../../test/scala/telegramium/bots")
    }
}
