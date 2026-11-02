package io.github.iozhik

import io.github.iozhik.scalagen.{GeneratorConfig, OpenEnumConfig}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

// A struct's shape (record or union) and its used structs' fields are resolved once, in the model, and every
// generator reads them from there.
class StrucShapeSpec extends AnyFlatSpec with Matchers {

  private val cfg = GeneratorConfig(
    apiPath = "",
    fmtConfigPath = "",
    openEnum = OpenEnumConfig(enabled = false, excludeTypes = List.empty)
  )

  private def translate(dsl: String): Either[String, Generator.Model.Space] =
    Parser.Grammar.parse(dsl).flatMap(Generator.Model.translate)

  private def generate(dsl: String): String =
    translate(dsl)
      .flatMap(new ScalaApiGeneratorV1().gen(_, cfg))
      .fold(err => fail(s"Generation failed: $err"), _.map(_.body).mkString("\n"))

  private def api(items: String): String =
    s"""
       |namespace telegramium {
       |  namespace bots(snake circe) {
       |$items
       |  }
       |}
     """.stripMargin

  "A variant whose fields all come from `use`" must "be coded as a case class, not a case object" in {
    val body = generate(api(
      """
        |    Props {
        |      area: Double
        |    }
        |    enum Figure {
        |      Square {
        |        type: "square"
        |        use Props
        |      }
        |    }
        |    Methods (methodsFactory) {
        |      def draw {
        |        id: Int
        |      } => Method[Figure]
        |    }
      """.stripMargin
    ))
    body should include("final case class Square(area: Double)")
    body should include("""case "square" => Decoder[Square]""")
    body should not include "Square.type"
  }

  "A type reached only through a `use`d field" must "get a decoder" in {
    val body = generate(api(
      """
        |    Inner {
        |      x: Int
        |    }
        |    WithInner {
        |      inner: Inner
        |    }
        |    Holder {
        |      use WithInner
        |    }
        |    Methods (methodsFactory) {
        |      def get {
        |        id: Int
        |      } => Method[Holder]
        |    }
      """.stripMargin
    ))
    body should include("implicit lazy val innerDecoder: Decoder[Inner]")
  }

  "A method whose params `use` a struct" must "take the used fields as params and pass them to its request" in {
    val body = generate(api(
      """
        |    Common {
        |      chatId: Long
        |    }
        |    Methods (methodsFactory) {
        |      def send {
        |        use Common
        |        text: String
        |      } => Method[Boolean]
        |    }
      """.stripMargin
    ))
    body should include("final case class SendReq(chatId: Long, text: String)")
    body should include("def send(chatId: Long, text: String): Method[Boolean]")
    body should include("val req = SendReq(chatId,text)")
  }

  "A `use` of a struct that doesn't exist" must "fail the translation" in {
    translate(api(
      """
        |    Holder {
        |      use Missing
        |    }
      """.stripMargin
    )).left.toOption.getOrElse(fail("translated")) should include("can't resolve `use Missing` in Holder")
  }

  "A `use` inside a method's params that doesn't exist" must "fail the translation, naming the method" in {
    translate(api(
      """
        |    Methods (methodsFactory) {
        |      def send {
        |        use Missing
        |        text: String
        |      } => Method[Boolean]
        |    }
      """.stripMargin
    )).left.toOption.getOrElse(fail("translated")) should include("can't resolve `use Missing` in the params of `send`")
  }

  "A `use` of a struct that `use`s another" must "take the fields of both" in {
    val body = generate(api(
      """
        |    Inner {
        |      x: Int
        |    }
        |    Middle {
        |      use Inner
        |      y: Int
        |    }
        |    Outer {
        |      use Middle
        |    }
      """.stripMargin
    ))
    body should include("final case class Middle(x: Int, y: Int)")
    body should include("final case class Outer(x: Int, y: Int)")
  }

  "A struct reaching the same struct along two paths of `use`s" must "get its fields once" in {
    val body = generate(api(
      """
        |    Base {
        |      id: Int
        |    }
        |    Left {
        |      use Base
        |      l: Int
        |    }
        |    Right {
        |      use Base
        |      r: Int
        |    }
        |    Both {
        |      use Left
        |      use Right
        |    }
        |    Twice {
        |      use Base
        |      use Base
        |    }
      """.stripMargin
    ))
    body should include("final case class Both(id: Int, l: Int, r: Int)")
    body should include("final case class Twice(id: Int)")
  }

  "Two used fields with one name and different types" must "fail the translation" in {
    translate(api(
      """
        |    Left {
        |      id: Int
        |    }
        |    Right {
        |      id: String
        |    }
        |    Both {
        |      use Left
        |      use Right
        |    }
      """.stripMargin
    )).left.toOption.getOrElse(fail("translated")) should include("Both has two fields named `id` that differ in type or options")
  }

  "Two used fields of one type written in different namespaces" must "be one field" in {
    val body = generate(
      """
        |namespace telegramium(snake) {
        |  Outer {
        |    id: Int
        |  }
        |  namespace bots(snake) {
        |    Inner {
        |      id: Int
        |    }
        |    Both {
        |      use Outer
        |      use Inner
        |    }
        |  }
        |}
      """.stripMargin
    )
    body should include("final case class Both(id: Int)")
  }

  "Two used fields with one name and different options" must "fail the translation" in {
    translate(api(
      """
        |    Left {
        |      ids: (omitempty) List[Int]
        |    }
        |    Right {
        |      ids: List[Int]
        |    }
        |    Both {
        |      use Left
        |      use Right
        |    }
      """.stripMargin
    )).left.toOption.getOrElse(fail("translated")) should include("Both has two fields named `ids` that differ in type or options")
  }

  "A field declared on a struct and also used with another type" must "fail the translation" in {
    translate(api(
      """
        |    Used {
        |      id: String
        |    }
        |    Holder {
        |      use Used
        |      id: Int
        |    }
      """.stripMargin
    )).left.toOption.getOrElse(fail("translated")) should include("Holder has two fields named `id` that differ in type or options")
  }

  "A field declared on a struct and also used with the same type" must "be one field" in {
    val body = generate(api(
      """
        |    Used {
        |      id: Int
        |      name: String
        |    }
        |    Holder {
        |      use Used
        |      id: Int
        |    }
      """.stripMargin
    ))
    body should include("final case class Holder(name: String, id: Int)")
  }

  "A cycle of `use`s" must "fail the translation" in {
    translate(api(
      """
        |    First {
        |      use Second
        |    }
        |    Second {
        |      use First
        |    }
      """.stripMargin
    )).left.toOption.getOrElse(fail("translated")) should include("`use` is circular: First → Second → First")
  }

  "A `use` of a variant" must "take the variant's fields, the ones it inherits from its union too" in {
    val body = generate(api(
      """
        |    enum Figure {
        |      color: String
        |      Square {
        |        type: "square"
        |        side: Int
        |      }
        |    }
        |    Box {
        |      use Square
        |    }
      """.stripMargin
    ))
    body should include("final case class Box(color: String, side: Int)")
  }

  "A `use` on an enum" must "give every variant the used fields the trait declares" in {
    val body = generate(api(
      """
        |    Props {
        |      area: Double
        |    }
        |    enum Figure {
        |      use Props
        |      Square {
        |        type: "square"
        |      }
        |      Circle {
        |        type: "circle"
        |        use Props
        |        radius: Int
        |      }
        |    }
      """.stripMargin
    ))
    body should include("def area: Double")
    body should include("final case class Square(area: Double)")
    body should include("final case class Circle(area: Double, radius: Int)")
  }

  "A flatten embed on an enum" must "fail the translation" in {
    translate(api(
      """
        |    enum Figure {
        |      extra: (flatten) {
        |        color: String
        |      }
        |      Square {
        |        type: "square"
        |        side: Int
        |      }
        |    }
      """.stripMargin
    )).left.toOption.getOrElse(fail("translated")) should include("Figure can't have a flatten embed (`extra`): it's an enum")
  }

  "A flatten embed on an abstract struct" must "fail the translation" in {
    translate(api(
      """
        |    abstract Figure {
        |      extra: (flatten) {
        |        color: String
        |      }
        |    }
      """.stripMargin
    )).left.toOption.getOrElse(fail("translated")) should include("Figure can't have a flatten embed (`extra`): it's abstract")
  }

  "An abstract variant of an enum" must "fail the translation" in {
    translate(api(
      """
        |    enum Text {
        |      TextPlain {
        |        type : "plain:0"
        |        text : String
        |      }
        |      abstract TextStyled {
        |        text : String
        |      }
        |    }
      """.stripMargin
    )).left.toOption.getOrElse(fail("translated")) should include("TextStyled can't be abstract: it's a variant of the enum Text")
  }

  "A multipart upload of a file union whose first wrap isn't a file" must "match on the java.io.File wrap" in {
    val body = generate(
      """
        |namespace telegramium {
        |  namespace bots(snake circe http4s) {
        |    enum IFile {
        |      wrap InputLinkFile (file) = String
        |      wrap InputPartFile (file) = java.io.File
        |    }
        |    Methods (client) {
        |      def sendPhoto {
        |        photo: (file) IFile
        |      } => Boolean
        |    }
        |  }
        |}
      """.stripMargin
    )
    body should include("case InputPartFile(f) => makePart(\"photo\", f)")
    body should not include "case InputLinkFile(f) => makePart"
  }

  "A struct with variants that is neither abstract nor an enum" must "fail the translation" in {
    translate(api(
      """
        |    Holder {
        |      Inner {
        |        x: Int
        |      }
        |    }
      """.stripMargin
    )).left.toOption.getOrElse(fail("translated")) should include("Holder has variants but is neither abstract nor an enum")
  }

  "A variant under each of two versions of one enum" must "take the fields of its own version" in {
    val body = generate(api(
      """
        |    PropsA {
        |      a: Int
        |    }
        |    PropsB {
        |      b: Int
        |    }
        |    enum Figure <5.0 {
        |      use PropsA
        |      Square {
        |        type: "square"
        |      }
        |    }
        |    enum Figure {
        |      use PropsB
        |      Square {
        |        type: "square"
        |      }
        |    }
      """.stripMargin
    ))
    body should include("final case class Square(a: Int)")
    body should include("final case class Square(b: Int)")
  }

  "Two used fields whose types are different aliases of one name" must "fail the translation" in {
    translate(
      """
        |namespace telegramium(snake) {
        |  type Id = Long
        |  Outer {
        |    id: Id
        |  }
        |  namespace bots(snake) {
        |    type Id = String
        |    Inner {
        |      id: Id
        |    }
        |    Both {
        |      use Outer
        |      use Inner
        |    }
        |  }
        |}
      """.stripMargin
    ).left.toOption.getOrElse(fail("translated")) should include("Both has two fields named `id` that differ in type or options")
  }

  "Two used fields whose types are aliases of one type" must "be one field" in {
    val space = translate(
      """
        |namespace telegramium(snake) {
        |  type Id = Long
        |  Outer {
        |    id: Id
        |  }
        |  namespace bots(snake) {
        |    type Id = Long
        |    Inner {
        |      id: Id
        |    }
        |    Both {
        |      use Outer
        |      use Inner
        |    }
        |  }
        |}
      """.stripMargin
    ).fold(err => fail(s"Translation failed: $err"), identity)
    def strucs(x: Generator.Model.Space): List[Generator.Model.Struc] = x.strucs ++ x.spaces.flatMap(strucs)
    strucs(space).find(_.kind.exists(_.name == "Both")).map(_.allFields.map(_.name)) shouldBe Some(List("id"))
  }
}
