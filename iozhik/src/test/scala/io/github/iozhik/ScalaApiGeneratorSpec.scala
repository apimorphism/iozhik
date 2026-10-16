package io.github.iozhik

import io.github.iozhik.scalagen.{GeneratorConfig, OpenEnumConfig}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ScalaApiGeneratorSpec extends AnyFlatSpec with Matchers {

  private val cfg = GeneratorConfig(
    apiPath = "",
    fmtConfigPath = "",
    openEnum = OpenEnumConfig(enabled = false, excludeTypes = List.empty)
  )

  private def generate(dsl: String): List[String] = {
    val result = for {
      space <- Parser.Grammar.parse(dsl)
      model <- Generator.Model.translate(space)
      codes <- new ScalaApiGeneratorV1().gen(model, cfg)
    } yield codes.map(_.body)
    result.fold(err => fail(s"Generation failed: $err"), identity)
  }

  "Generated code" must "include the NamedInputStream predef type that InputStreamFile wraps" in {
    val bodies = generate(
      """
        |namespace telegramium {
        |  namespace bots(snake circe) {
        |    enum IFile {
        |      wrap InputStreamFile (file) = iozhik.NamedInputStream
        |    }
        |  }
        |}
      """.stripMargin
    )
    bodies.exists(_.contains("final case class NamedInputStream(filename: String, openStream: () => java.io.InputStream)")) shouldBe true
  }

  "IFile-like wrap with a custom target" must "fall back to the generic encode/decode-as-wrapped-value codecs" in {
    val bodies = generate(
      """
        |namespace telegramium {
        |  namespace bots(snake circe) {
        |    enum IFile {
        |      wrap InputLinkFile (file) = String
        |    }
        |  }
        |}
      """.stripMargin
    )
    bodies.exists(_.contains("inputlinkfileEncoder: Encoder[InputLinkFile] = (x: InputLinkFile) => x.file.asJson")) shouldBe true
    bodies.exists(_.contains("inputlinkfileDecoder: Decoder[InputLinkFile] = Decoder[String].map(InputLinkFile.apply)")) shouldBe true
  }

  "IFile-like wrap targeting java.io.File" must "encode/decode via the file's name, not as a JSON object" in {
    val bodies = generate(
      """
        |namespace telegramium {
        |  namespace bots(snake circe) {
        |    enum IFile {
        |      wrap InputPartFile (file) = java.io.File
        |    }
        |  }
        |}
      """.stripMargin
    )
    bodies.exists(_.contains("inputpartfileEncoder: Encoder[InputPartFile] = (x: InputPartFile) => x.file.getName.asJson")) shouldBe true
    bodies.exists(_.contains("inputpartfileDecoder: Decoder[InputPartFile] = Decoder[String].map(s => InputPartFile(new java.io.File(s)))")) shouldBe true
  }

  "IFile-like wrap targeting iozhik.NamedInputStream" must "fall back to the same generic wrapped-value codecs as any other predef type" in {
    val bodies = generate(
      """
        |namespace telegramium {
        |  namespace bots(snake circe) {
        |    enum IFile {
        |      wrap InputStreamFile (file) = iozhik.NamedInputStream
        |    }
        |  }
        |}
      """.stripMargin
    )
    bodies.exists(
      _.contains("inputstreamfileEncoder: Encoder[InputStreamFile] = (x: InputStreamFile) => x.file.asJson")
    ) shouldBe true
    bodies.exists(
      _.contains("inputstreamfileDecoder: Decoder[InputStreamFile] = Decoder[iozhik.NamedInputStream].map(InputStreamFile.apply)")
    ) shouldBe true
  }
}
