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

  "A `use` of a struct that doesn't exist" must "be dropped" in {
    val body = generate(api(
      """
        |    Holder {
        |      use Missing
        |      x: Int
        |    }
      """.stripMargin
    ))
    body should include("final case class Holder(x: Int)")
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

  "A used field's doc" must "be written once, inline, and not again as an @param of the case class" in {
    val body = generate(api(
      """
        |    Common {
        |      /* the chat */
        |      chatId: Long
        |    }
        |    Holder {
        |      use Common
        |      /* the text */
        |      text: String
        |    }
      """.stripMargin
    ))
    body.replaceAll("\\s+", " ") should include(
      "/** @param text the text */ final case class Holder( /** the chat */ chatId: Long, text: String)"
    )
  }

  "A Java method whose params are an enum without fields" must "take no request" in {
    val body = translate(
      """
        |namespace iozhik {
        |  namespace geometry {
        |    enum Figure {
        |      Square {
        |        type: "square:1"
        |      }
        |    }
        |    Methods {
        |      def draw Figure => {
        |        ok: Boolean
        |      }
        |    }
        |  }
        |}
      """.stripMargin
    ).flatMap(new JavaApiGeneratorV1().gen(_)).fold(err => fail(s"Generation failed: $err"), _.map(_.body).mkString("\n"))
    body should include("draw() throws")
    body should not include "DrawReq x"
  }

  "An abstract enum" must "be generated as an enum" in {
    val body = generate(api(
      """
        |    abstract enum Figure {
        |      Square {
        |        type: "square"
        |        side: Int
        |      }
        |    }
      """.stripMargin
    ))
    body should include("sealed trait Figure")
    body should include("final case class Square(side: Int) extends Figure")
  }
}
