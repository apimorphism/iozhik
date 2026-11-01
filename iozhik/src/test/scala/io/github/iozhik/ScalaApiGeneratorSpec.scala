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

  // https://github.com/apimorphism/telegramium/issues/666
  "A methodsFactory method taking a plain compound type with an embedded file field" must "attach the embedded file and rewrite it to an attach:// reference" in {
    val bodies = generate(
      """
        |namespace telegramium {
        |  namespace bots(snake circe) {
        |    enum IFile {
        |      wrap InputPartFile (file) = java.io.File
        |      wrap InputLinkFile (file) = String
        |    }
        |    InputSticker {
        |      sticker: (file) IFile
        |      format: String
        |    }
        |    Methods (methodsFactory) {
        |      def addStickerToSet {
        |        userId: Long
        |        sticker: InputSticker
        |      } => Method[Boolean]
        |    }
        |  }
        |}
      """.stripMargin
    )
    val body = bodies.mkString("\n")
    body should include("""val (stickerAttached, stickerFiles) = attachFiles_InputSticker("sticker", sticker)""")
    body should include("""val req = AddStickerToSetReq(userId,stickerAttached)""")
    body should include("""MethodReq[Boolean]("addStickerToSet", req.asJson, stickerFiles)""")
    body should include("""val (sticker1, stickerFiles) = MethodReq.attachFile(name + "_sticker", v.sticker)""")
    body should include("(v.copy(sticker = sticker1), stickerFiles)")
  }

  "A methodsFactory method taking a List of a union type whose variants embed file fields" must "attach each element's file(s) under an indexed name and rewrite them to attach:// references" in {
    val bodies = generate(
      """
        |namespace telegramium {
        |  namespace bots(snake circe) {
        |    enum IFile {
        |      wrap InputPartFile (file) = java.io.File
        |      wrap InputLinkFile (file) = String
        |    }
        |    abstract InputMedia {
        |      InputMediaPhoto {
        |        media: (file) IFile
        |      }
        |      InputMediaVideo {
        |        media: (file) IFile
        |        thumbnail: (file) Option[IFile]
        |      }
        |    }
        |    Methods (methodsFactory) {
        |      def sendMediaGroup {
        |        media: List[InputMedia]
        |      } => Method[Boolean]
        |    }
        |  }
        |}
      """.stripMargin
    )
    val body = bodies.mkString("\n")
    body should include(
      """val (mediaAttached, mediaFiles) = media.zipWithIndex.map { case (v, idx) => attachFiles_InputMedia(s"media$idx", v) }.unzip"""
    )
    body should include("""val req = SendMediaGroupReq(mediaAttached)""")
    body should include("""MethodReq[Boolean]("sendMediaGroup", req.asJson, mediaFiles.flatten.toMap)""")
    body should include("""val (media1, mediaFiles) = MethodReq.attachFile(name + "_media", v.media)""")
    body should include("(v.copy(media = media1), mediaFiles)")
    body should include("""val (thumbnail1, thumbnailFiles) = MethodReq.attachFile(name + "_thumbnail", v.thumbnail)""")
    body should include("(v.copy(media = media1, thumbnail = thumbnail1), mediaFiles ++ thumbnailFiles)")
  }

  // https://github.com/apimorphism/telegramium/issues/686
  "An (omitempty) List field" must "be left out of the encoded JSON when empty, unlike a required List or an Option[List]" in {
    val bodies = generate(
      """
        |namespace telegramium {
        |  namespace bots(snake circe) {
        |    InputRichMessage {
        |      blocks: (omitempty) List[String]
        |      rows: List[String]
        |      allowedUpdates: Option[List[String]]
        |    }
        |  }
        |}
      """.stripMargin
    )
    val body = bodies.mkString("\n")
    body should include(""""blocks" -> (if (x.blocks.isEmpty) Json.Null else x.blocks.asJson)""")
    body should include(""""rows" -> x.rows.asJson""")
    body should include(""""allowed_updates" -> x.allowedUpdates.asJson""")
  }

  "An (omitempty) field that is not a List" must "fail the generation" in {
    val result = for {
      space <- Parser.Grammar.parse(
        """
          |namespace telegramium {
          |  namespace bots(snake circe) {
          |    Foo {
          |      name: (omitempty) String
          |    }
          |  }
          |}
        """.stripMargin
      )
      model <- Generator.Model.translate(space)
      codes <- new ScalaApiGeneratorV1().gen(model, cfg)
    } yield codes
    result.left.toOption.getOrElse(fail("Generation succeeded")) should include("Foo.name")
  }

  private val gensDsl =
    """
      |namespace telegramium {
      |  namespace bots(snake circe) {
      |    Dated {
      |      date: Long
      |    }
      |    Reply {
      |      dated: Dated
      |    }
      |    SendOnly {
      |      text: String
      |    }
      |    Methods (methodsFactory) {
      |      def getReply {
      |      } => Method[Reply]
      |      def send {
      |        sendOnly: SendOnly
      |      } => Method[Boolean]
      |    }
      |  }
      |}
    """.stripMargin

  private def generateGens(dsl: String, gensCfg: GeneratorConfig = cfg.copy(generateGens = true)): Map[String, String] = {
    val result = for {
      space <- Parser.Grammar.parse(dsl)
      model <- Generator.Model.translate(space)
      codes <- new ScalaApiGeneratorV1().gen(model, gensCfg)
    } yield codes.filter(_.path.contains("test/scala")).map(c => c.name -> c.body).toMap
    result.fold(err => fail(s"Generation failed: $err"), identity)
  }

  "Generators" must "be left out unless the config asks for them" in {
    generateGens(gensDsl, cfg) shouldBe empty
  }

  "Generators" must "go into the test sources, one small object per type, with the aggregating object and the round-trip list" in {
    val files = generateGens(gensDsl)
    files.keySet shouldBe Set("GenPrims.scala", "GenTypes.scala", "Gens.scala", "RoundTrips.scala")
    files("GenTypes.scala") should include("object GenDated")
    files("GenTypes.scala") should include("object GenReply")
    files("Gens.scala") should include("def arbDated: Arbitrary[Dated] = GenDated.arb")
  }

  "The round-trip list" must "hold the types with both an encoder and a decoder, and leave out encode-only types" in {
    val list = generateGens(gensDsl)("RoundTrips.scala")
    list should include("""RoundTripCase[Reply]("Reply"""")
    list should include("""RoundTripCase[Dated]("Dated"""")
    list should not include "SendOnly"
  }

  "A custom-gens entry" must "post-process the generated generator of its type, and leave other types alone" in {
    val files = generateGens(
      gensDsl,
      cfg.copy(generateGens = true, customGens = Map("Dated" -> "generated.map(d => d.copy(date = 1L))"))
    )
    files("GenTypes.scala") should include("lazy val gen: Gen[Dated] = generated.map(d => d.copy(date = 1L))")
    files("GenTypes.scala") should include("lazy val gen: Gen[Reply] = generated")
  }
}
