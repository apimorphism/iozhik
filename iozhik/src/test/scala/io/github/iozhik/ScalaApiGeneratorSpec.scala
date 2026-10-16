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

  "IFile-like wrap targeting telegramium.bots.NamedInputStream" must "encode via the stream's filename and refuse to decode" in {
    val bodies = generate(
      """
        |namespace telegramium {
        |  namespace bots(snake circe) {
        |    enum IFile {
        |      wrap InputStreamFile (file) = telegramium.bots.NamedInputStream
        |    }
        |  }
        |}
      """.stripMargin
    )
    bodies.exists(
      _.contains("inputstreamfileEncoder: Encoder[InputStreamFile] = (x: InputStreamFile) => x.file.filename.asJson")
    ) shouldBe true
    bodies.exists(
      _.contains("""inputstreamfileDecoder: Decoder[InputStreamFile] = Decoder.failedWithMessage[InputStreamFile](s"InputStreamFile cannot be decoded")""")
    ) shouldBe true
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
    body should include("val (sticker1, stickerFiles) = MethodReq.attachFile(name, v.sticker)")
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
      """val (mediaAttached, mediaFilesParts) = media.zipWithIndex.map { case (v, idx) => attachFiles_InputMedia(s"media$idx", v) }.unzip"""
    )
    body should include("""val mediaFiles = mediaFilesParts.foldLeft(Map.empty[String, IFile])(_ ++ _)""")
    body should include("""val req = SendMediaGroupReq(mediaAttached)""")
    body should include("""MethodReq[Boolean]("sendMediaGroup", req.asJson, mediaFiles)""")
    body should include("val (media1, mediaFiles) = MethodReq.attachFile(name, v.media)")
    body should include("(v.copy(media = media1), mediaFiles)")
    body should include("""val (media1, mediaFiles) = MethodReq.attachFile((name + "_media"), v.media)""")
    body should include("""val (thumbnail1, thumbnailFiles) = MethodReq.attachFileOpt((name + "_thumbnail"), v.thumbnail)""")
    body should include("(v.copy(media = media1, thumbnail = thumbnail1), mediaFiles ++ thumbnailFiles)")
  }
}
