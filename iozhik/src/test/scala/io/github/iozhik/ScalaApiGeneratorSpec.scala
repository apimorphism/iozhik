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
    body should include("""val (sticker1, stickerFiles) = MethodReq.attachFile(name + ".sticker", v.sticker)""")
    body should include("val files = stickerFiles")
    body should include("if (files.isEmpty) (v, files) else (v.copy(sticker = sticker1), files)")
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
      """val (a0, f0) = media.zipWithIndex.map { case (x0, i0) => attachFiles_InputMedia("media." + i0, x0) }.unzip"""
    )
    body should include("val files0 = f0.flatten.toMap")
    body should include("(if (files0.isEmpty) media else a0, files0)")
    body should include("""val req = SendMediaGroupReq(mediaAttached)""")
    body should include("""MethodReq[Boolean]("sendMediaGroup", req.asJson, mediaFiles)""")
    body should include("""val (media1, mediaFiles) = MethodReq.attachFile(name + ".media", v.media)""")
    body should include("if (files.isEmpty) (v, files) else (v.copy(media = media1), files)")
    body should include("""val (thumbnail1, thumbnailFiles) = MethodReq.attachFile(name + ".thumbnail", v.thumbnail)""")
    body should include("val files = mediaFiles ++ thumbnailFiles")
    body should include("if (files.isEmpty) (v, files) else (v.copy(media = media1, thumbnail = thumbnail1), files)")
  }

  // https://github.com/apimorphism/iozhik/issues/21
  "A methodsFactory method whose params hold files deeper than one level, through Option, List and a recursive union" must "attach every file under a name built from its path" in {
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
        |    }
        |    InputPollOption {
        |      text: String
        |      media: Option[InputMedia]
        |    }
        |    enum InputRichBlock {
        |      InputRichBlockParagraph {
        |        type : "paragraph:0"
        |        text : String
        |      }
        |      InputRichBlockPhoto {
        |        type : "photo:1"
        |        photo : InputMediaPhoto
        |      }
        |      InputRichBlockCollage {
        |        type : "collage:2"
        |        blocks : List[InputRichBlock]
        |      }
        |    }
        |    InputRichMessage {
        |      blocks: (omitempty) List[InputRichBlock]
        |    }
        |    Methods (methodsFactory) {
        |      def sendPoll {
        |        options: List[InputPollOption]
        |        media: Option[InputMedia]
        |      } => Method[Boolean]
        |      def sendRichMessage {
        |        richMessage: InputRichMessage
        |      } => Method[Boolean]
        |    }
        |  }
        |}
      """.stripMargin
    )
    val body = bodies.mkString("\n")
    // sendPoll.options[i].media: a List of a struct holding an Option of a union
    body should include("""val (a0, f0) = options.zipWithIndex.map { case (x0, i0) => attachFiles_InputPollOption("options." + i0, x0) }.unzip""")
    body should include("case v: InputPollOption =>")
    body should include("val (a0, f0) = attachFiles_InputMedia(name + \".media\", x0)")
    // sendPoll.media: an Option param
    body should include("""val (mediaAttached, mediaFiles) = media match {""")
    body should include("""val (a0, f0) = attachFiles_InputMedia("media", x0)""")
    body should include("""MethodReq[Boolean]("sendPoll", req.asJson, optionsFiles ++ mediaFiles)""")
    // sendRichMessage.rich_message.blocks[i].photo.media, and blocks nested in a collage
    body should include("""val (richMessageAttached, richMessageFiles) = attachFiles_InputRichMessage("rich_message", richMessage)""")
    body should include("""v.blocks.zipWithIndex.map { case (x0, i0) => attachFiles_InputRichBlock(name + ".blocks." + i0, x0) }.unzip""")
    body should include("case v @ (_: InputRichBlockParagraph) => (v, Map.empty[String, IFile])")
    body should not include "case v => (v, Map.empty[String, IFile])"
    body should include("""val (photo1, photoFiles) = attachFiles_InputMediaPhoto(name + ".photo", v.photo)""")
    body should include("case v: InputRichBlockCollage =>")
    body should include("""private def attachFiles_InputMediaPhoto(name: String, value: InputMediaPhoto)""")
  }

  "A file-holding field named after a Scala keyword, next to variants without files" must "generate valid local names and match those variants in one case" in {
    val bodies = generate(
      """
        |namespace telegramium {
        |  namespace bots(snake circe) {
        |    enum IFile {
        |      wrap InputPartFile (file) = java.io.File
        |      wrap InputLinkFile (file) = String
        |    }
        |    abstract InputThing {
        |      InputThingFile {
        |        type: (file) IFile
        |      }
        |      InputThingNested {
        |        object: InputThing
        |      }
        |      InputThingText {
        |        text: String
        |      }
        |      InputThingNone {}
        |    }
        |    Methods (methodsFactory) {
        |      def sendThing {
        |        type: InputThing
        |      } => Method[Boolean]
        |    }
        |  }
        |}
      """.stripMargin
    )
    val body = bodies.mkString("\n")
    body should include("""val (type1, typeFiles) = MethodReq.attachFile(name + ".type", v.`type`)""")
    body should include("if (files.isEmpty) (v, files) else (v.copy(`type` = type1), files)")
    body should include("""val (object1, objectFiles) = attachFiles_InputThing(name + ".object", v.`object`)""")
    body should include("""val (typeAttached, typeFiles) = attachFiles_InputThing("type", `type`)""")
    body should include("case v @ (_: InputThingText | InputThingNone) => (v, Map.empty[String, IFile])")
  }

  // https://github.com/apimorphism/iozhik/issues/21
  "Every file reachable from a methodsFactory method's params in the real Bot API" must "be attached by the generated Methods code" in {
    import Generator.Model.{Defun, Kind, Space, Struc}

    val apiFile = List("iozhtest/src/main/resources/tgbot.api", "../iozhtest/src/main/resources/tgbot.api")
      .map(new java.io.File(_))
      .find(_.exists)
      .getOrElse(fail("tgbot.api not found"))
    val source = scala.io.Source.fromFile(apiFile, "UTF-8")
    val dsl    = try source.mkString finally source.close()
    val (space, code) = (for {
      parsed <- Parser.Grammar.parse(dsl)
      model  <- Generator.Model.translate(parsed)
      codes  <- new ScalaApiGeneratorV1().gen(model, cfg)
    } yield model -> codes.map(_.body).mkString("\n")).fold(err => fail(s"Generation failed: $err"), identity)

    def methods(s: Space): List[Defun] =
      s.servcs.filter(_.opts.contains("methodsFactory")).flatMap(_.items.collect { case d: Defun => d }) ++
        s.spaces.flatMap(methods)

    // A step on the way to a file: the type as a field names it, the variant holding the field, and the field
    final case class Step(owner: String, variant: String, field: String)

    // Every path from a value of `kind` to a (file) field, through struct fields, union leaves and any type params
    def paths(kind: Kind, seen: Set[String]): List[List[Step]] =
      if (kind.params.nonEmpty) kind.params.flatMap(paths(_, seen))
      else if (seen(kind.name)) Nil
      else
        space.symbol.resolve(kind).toList.collect { case s: Struc => s }.flatMap { s =>
          (if (s.leaves.nonEmpty) s.leaves else List(s)).flatMap { v =>
            val variant = v.kind.map(_.name).getOrElse(kind.name)
            v.fields.flatMap { f =>
              val step = Step(kind.name, variant, f.name)
              if (f.opts.contains("file")) List(List(step))
              else paths(f.kind, seen + kind.name).map(step :: _)
            }
          }
        }

    // The generated code of a def, up to the next one
    def defBody(signature: String): Option[String] = {
      val start = code.indexOf(signature)
      Option.when(start >= 0) {
        val end = code.indexOf("def ", start + signature.length)
        code.substring(start, if (end < 0) code.length else end)
      }
    }

    def covered(step: Step): Boolean =
      defBody(s"def attachFiles_${step.owner}(").exists { helper =>
        helper.split("case v: ").exists(c => c.startsWith(s"${step.variant} =>") && c.contains(s"v.${step.field}"))
      }

    // Each link that isn't attached, with the shortest path through it as an example
    val uncovered = (for {
      method <- methods(space)
      param  <- method.dom.toOption.toList.flatMap(_.fields).filterNot(_.opts.contains("file"))
      path   <- paths(param.kind, Set.empty)
      paramLink = s"${method.kind.name}.${param.name}"
      missing <- (if (defBody(s"def ${method.kind.name}(").exists(_.contains(s"${param.name}Attached"))) None
                  else Some(paramLink)).orElse(path.find(!covered(_)).map(s => s"${s.variant}.${s.field} in attachFiles_${s.owner}"))
    } yield missing -> (paramLink + path.map(s => s".${s.variant}.${s.field}").mkString))
      .groupBy(_._1)
      .map { case (missing, examples) => s"$missing, e.g. ${examples.map(_._2).minBy(_.length)}" }
      .toList
      .sorted

    withClue(s"${uncovered.size} links to a file aren't attached:\n${uncovered.mkString("\n")}\n") {
      uncovered shouldBe empty
    }
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
      |      note: Option[String]
      |    }
      |    Reply {
      |      dated: Dated
      |    }
      |    enum Text {
      |      TextPlain {
      |        type : "plain:0"
      |        text : String
      |      }
      |      TextBold {
      |        type : "bold:1"
      |        text : Text
      |      }
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
    } yield codes.filter(_.test).map(c => c.name -> c.body).toMap
    result.fold(err => fail(s"Generation failed: $err"), identity)
  }

  "Generators" must "be left out unless the config asks for them" in {
    generateGens(gensDsl, cfg) shouldBe empty
  }

  "Generators" must "go into a test source tree of their own, one small object per type, with the round-trip list" in {
    val files = generateGens(gensDsl)
    files.keySet shouldBe Set("GenPrims.scala", "GenTypes.scala", "RoundTrips.scala")
    files("GenTypes.scala") should include("object GenDated")
    files("GenTypes.scala") should include("object GenReply")
  }

  "A generator" must "zip its fields and pass them by name, in groups of up to 9" in {
    val types = generateGens(gensDsl)("GenTypes.scala")
    types should include("Gen.zip(genLong, optPrim(genString)).map { case (a0, a1) => Dated(date = a0, note = a1) }")
    types should include("GenDated.gen.map { a0 => Reply(dated = a0) }")
  }

  "A shrinker" must "have entries for the optional and composite fields only" in {
    val types = generateGens(gensDsl)("GenTypes.scala")
    types should include("field(x.note)(shrinkOpt(noShrink))(v => x.copy(note = v))")
    types should not include "x.date)"
    types should include("field(x.dated)(GenDated.shrink)(v => x.copy(dated = v))")
  }

  "A shrinker" must "leave out composite fields whose type has nothing to shrink, recursive ones too" in {
    val types = generateGens(gensDsl)("GenTypes.scala")
    // TextBold.text only leads back to Text, and TextPlain holds a String
    types should include("lazy val shrink: Shrink[TextBold] = noShrink")
    types should include("lazy val shrink: Shrink[Text] = noShrink")
    types should not include "GenText.shrink"
  }

  "A sum type" must "pick only the variants without a required recursive field at size 0" in {
    val types = generateGens(gensDsl)("GenTypes.scala")
    types should include("sum[Text](List(GenTextPlain.gen, GenTextBold.gen), List(GenTextPlain.gen))")
  }

  "A required Either with a recursive side" must "pick only the other side at size 0" in {
    val types = generateGens(
      """
        |namespace telegramium {
        |  namespace bots(snake circe) {
        |    Leaf {
        |      date: Long
        |    }
        |    Chain {
        |      next: Either[Leaf, Chain]
        |    }
        |  }
        |}
      """.stripMargin
    )("GenTypes.scala")
    types should include("eitherLeftAtZero(GenLeaf.gen, GenChain.gen)")
  }

  "Generators for types in more than one package" must "fail the generation" in {
    gensError(
      """
        |namespace telegramium {
        |  namespace bots(snake circe) {
        |    Dated {
        |      date: Long
        |    }
        |  }
        |  namespace other(snake circe) {
        |    Undated {
        |      note: String
        |    }
        |  }
        |}
      """.stripMargin
    ) should include("generators for more than one package aren't supported: telegramium.bots, telegramium.other")
  }

  "The round-trip list" must "hold the types with both an encoder and a decoder, and leave out encode-only types" in {
    val list = generateGens(gensDsl)("RoundTrips.scala")
    list should include("""RoundTripCase[Reply]("Reply"""")
    list should include("""RoundTripCase[Dated]("Dated"""")
    list should not include "SendOnly"
  }

  "A custom-gens entry" must "apply to the generated and the shrunk values of its type, and leave other types alone" in {
    val types = generateGens(
      gensDsl,
      cfg.copy(generateGens = true, customGens = Map("Dated" -> "d => d.copy(date = 1L)"))
    )("GenTypes.scala")
    types should include("private val fix: Dated => Dated = d => d.copy(date = 1L)")
    types should include(".map(fix))")
    types should include("lazy val shrink: Shrink[Dated] = fixed(shrinkFields[Dated]")
    types.split("object GenReply").last should not include "fix"
  }

  private def gensError(dsl: String, gensCfg: GeneratorConfig = cfg.copy(generateGens = true)): String = {
    val result = for {
      space <- Parser.Grammar.parse(dsl)
      model <- Generator.Model.translate(space)
      codes <- new ScalaApiGeneratorV1().gen(model, gensCfg)
    } yield codes
    result.left.toOption.getOrElse(fail("Generation succeeded"))
  }

  "A custom-gens entry for a type without a generator" must "fail the generation" in {
    gensError(gensDsl, cfg.copy(generateGens = true, customGens = Map("Dtaed" -> "identity"))) should include("custom-gens for types without a generator: Dtaed")
  }

  "An enum with an abstract variant" must "fail the generation" in {
    gensError(
      """
        |namespace telegramium {
        |  namespace bots(snake circe) {
        |    enum Text {
        |      TextPlain {
        |        type : "plain:0"
        |        text : String
        |      }
        |      abstract TextStyled {
        |        text : String
        |      }
        |    }
        |  }
        |}
      """.stripMargin
    ) should include("abstract variant TextStyled of Text")
  }

  "A field of a type without a generator" must "fail the generation" in {
    gensError(
      """
        |namespace telegramium {
        |  namespace bots(snake circe) {
        |    abstract Styled {
        |      text : String
        |    }
        |    Holder {
        |      styled : Styled
        |    }
        |  }
        |}
      """.stripMargin
    ) should include("no generator for Styled, which Holder refers to")
  }

  "A type without a finite value" must "fail the generation" in {
    gensError(
      """
        |namespace telegramium {
        |  namespace bots(snake circe) {
        |    Loop {
        |      next : Loop
        |    }
        |  }
        |}
      """.stripMargin
    ) should include("no finite value to generate for: Loop")
  }
}
