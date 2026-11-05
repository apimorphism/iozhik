import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import TgBotApiScrapper._

class TgBotApiScrapperSpec extends AnyFlatSpec with Matchers {

  private def parse(content: String): List[ApiItem] =
    TgBotApiScrapper.parse(JsoupBrowser.typed().parseString(s"""<div id="dev_page_content">$content</div>"""))

  private def method(content: String): Method = parse(content).collect { case m: Method => m } match {
    case m :: Nil => m
    case other    => fail(s"Expected exactly one method, got $other")
  }

  private def entity(content: String): Entity = parse(content).collect { case e: Entity => e } match {
    case e :: Nil => e
    case other    => fail(s"Expected exactly one entity, got $other")
  }

  private val methodTable =
    """<table class="table">
      |<thead><tr><th>Parameter</th><th>Type</th><th>Required</th><th>Description</th></tr></thead>
      |<tbody><tr><td>limit</td><td>Integer</td><td>Optional</td><td>Limits the number of updates</td></tr></tbody>
      |</table>""".stripMargin

  private val entityTable =
    """<table class="table">
      |<thead><tr><th>Field</th><th>Type</th><th>Description</th></tr></thead>
      |<tbody><tr><td>file_id</td><td>String</td><td>Identifier for this file</td></tr></tbody>
      |</table>""".stripMargin

  "A note between the description and the table" must "be kept as its own paragraph" in {
    val file = entity(
      s"""<h4>File</h4>
         |<p>This object represents a file ready to be downloaded.</p>
         |<blockquote>
         |<p>The maximum file size to download is 20 MB</p>
         |</blockquote>
         |$entityTable""".stripMargin
    )
    file.desc shouldBe "This object represents a file ready to be downloaded.\n\nThe maximum file size to download is 20 MB"
    file.table.map(_.name) shouldBe List("file_id")
  }

  "A note after the table" must "be kept, with its <br>-separated items" in {
    val getUpdates = method(
      s"""<h4>getUpdates</h4>
         |<p>Use this method to receive incoming updates. Returns an Array of <a href="#update">Update</a> objects.</p>
         |$methodTable
         |<blockquote>
         |<p><strong>Notes</strong><br><strong>1.</strong> This method will not work if an outgoing webhook is set up.<br><strong>2.</strong> In order to avoid getting duplicate updates, recalculate <em>offset</em> after each server response.</p>
         |</blockquote>
         |<h4>setWebhook</h4>""".stripMargin
    )
    getUpdates.desc shouldBe
      """Use this method to receive incoming updates. Returns an Array of <a href="https://core.telegram.org/bots/api#update">Update</a> objects.
        |
        |<strong>Notes</strong><br>
        |
        |<strong>1.</strong> This method will not work if an outgoing webhook is set up.<br>
        |
        |<strong>2.</strong> In order to avoid getting duplicate updates, recalculate <em>offset</em> after each server response.""".stripMargin
    getUpdates.returns shouldBe "List[Update]"
    getUpdates.table.map(_.name) shouldBe List("limit")
  }

  "A note with several paragraphs" must "keep each of them" in {
    val setWebhook = method(
      s"""<h4>setWebhook</h4>
         |<p>Use this method to specify a URL. Returns <em>True</em> on success.</p>
         |<p>If you'd like to make sure that the webhook was set by you, you can specify secret data.</p>
         |$methodTable
         |<blockquote>
         |<p><strong>3.</strong> Ports currently supported <em>for webhooks</em>: <strong>443, 80, 88, 8443</strong>.</p>
         |<p>If you're having any trouble setting up webhooks, please check out this guide.</p>
         |</blockquote>""".stripMargin
    )
    setWebhook.desc shouldBe
      """Use this method to specify a URL. Returns <em>True</em> on success.
        |If you'd like to make sure that the webhook was set by you, you can specify secret data.
        |
        |<strong>3.</strong> Ports currently supported <em>for webhooks</em>: <strong>443, 80, 88, 8443</strong>.
        |
        |If you're having any trouble setting up webhooks, please check out this guide.""".stripMargin
    setWebhook.returns shouldBe "Boolean"
  }

  "A note between two paragraphs" must "stay in place" in {
    val sendChatAction = method(
      s"""<h4>sendChatAction</h4>
         |<p>Use this method when you need to tell the user that something is happening. Returns <em>True</em> on success.</p>
         |<blockquote>
         |<p>Example: The <a href="https://t.me/imagebot">ImageBot</a> needs some time to process a request.</p>
         |</blockquote>
         |<p>We only recommend using this method when a response will take a <strong>noticeable</strong> amount of time.</p>
         |$methodTable""".stripMargin
    )
    sendChatAction.desc shouldBe
      """Use this method when you need to tell the user that something is happening. Returns <em>True</em> on success.
        |
        |Example: The <a href="https://t.me/imagebot">ImageBot</a> needs some time to process a request.
        |
        |We only recommend using this method when a response will take a <strong>noticeable</strong> amount of time.""".stripMargin
    sendChatAction.returns shouldBe "Boolean"
  }

  "A note after an image" must "be kept" in {
    val loginUrl = entity(
      s"""<h4>LoginUrl</h4>
         |<p>This object represents a parameter of the inline keyboard button used to automatically authorize a user.</p>
         |<div class="blog_image_wrap"><a href="/file/1"><img src="/file/2" alt="TITLE" /></a></div>
         |<blockquote>
         |<p>Sample bot: <a href="https://t.me/discussbot">@DiscussBot</a></p>
         |</blockquote>
         |$entityTable""".stripMargin
    )
    loginUrl.desc shouldBe
      """This object represents a parameter of the inline keyboard button used to automatically authorize a user.
        |
        |Sample bot: <a href="https://t.me/discussbot">&#064;DiscussBot</a>""".stripMargin
  }

  "A list inside a note" must "be rendered as bullet paragraphs" in {
    val forceReply = entity(
      s"""<h4>ForceReply</h4>
         |<p>Upon receiving a message with this object, Telegram clients will display a reply interface.</p>
         |$entityTable
         |<blockquote>
         |<p><strong>Example:</strong> There could be two ways to create a new poll:</p>
         |<ul>
         |<li>Explain the user how to send a command with parameters.</li>
         |<li>Guide the user through a step-by-step process.</li>
         |</ul>
         |</blockquote>""".stripMargin
    )
    forceReply.desc shouldBe
      """Upon receiving a message with this object, Telegram clients will display a reply interface.
        |
        |<strong>Example:</strong> There could be two ways to create a new poll:
        |
        |- Explain the user how to send a command with parameters.
        |
        |- Guide the user through a step-by-step process.""".stripMargin
  }

  "The return type" must "be inferred from the description only, not from its notes" in {
    val answerCallbackQuery = method(
      s"""<h4>answerCallbackQuery</h4>
         |<p>Use this method to send answers to callback queries. On success, <em>True</em> is returned.</p>
         |<blockquote>
         |<p>Alternatively, see <a href="#message">Message</a> and <em>Array</em> of games, otherwise use links.</p>
         |</blockquote>
         |$methodTable""".stripMargin
    )
    answerCallbackQuery.returns shouldBe "Boolean"
    answerCallbackQuery.desc should include("otherwise use links")
  }

  // https://github.com/apimorphism/telegramium/issues/686
  "An optional array" must "be tagged (omitempty), unlike a required one" in {
    val dsl = gen(List(
      Method(
        "sendMessage",
        "Use this method to send text messages.",
        List(
          MethodParam("entities", "Array of MessageEntity", "Optional", "List of special entities"),
          MethodParam("options", "Array of String", "Yes", "List of options")
        ),
        "Message"
      ),
      Entity(
        "InputRichMessage",
        "Describes a rich message to be sent.",
        List(
          EntityParam("blocks", "Array of InputRichBlock", "Optional. Content of the rich message"),
          EntityParam("rows", "Array of Array of String", "Rows of the table")
        )
      )
    ))
    dsl should include("entities : (omitempty) List[MessageEntity]")
    dsl should include("options  : List[String]")
    dsl should include("blocks : (omitempty) List[InputRichBlock]")
    dsl should include("rows   : List[List[String]]")
  }

  "An optional array where an empty list and an omitted one mean different things" must "be an Option[List]" in {
    val dsl = gen(List(
      Method(
        "getUpdates",
        "Use this method to receive incoming updates.",
        List(MethodParam(
          "allowed_updates",
          "Array of String",
          "Optional",
          "Specify an empty list to receive all update types (default). If not specified, the previous setting will be used."
        )),
        "List[Update]"
      ),
      Method(
        "editStory",
        "Edits a story previously posted by the bot.",
        List(MethodParam("areas", "Array of StoryArea", "Optional", "A JSON-serialized list of clickable areas to be shown on the story")),
        "Story"
      )
    ))
    dsl should include("allowedUpdates : Option[List[String]]")
    dsl should include("areas : Option[List[StoryArea]]")
  }

  "An unclassified optional array whose description distinguishes empty from omitted" must "fail the scrape" in {
    val e = the[RuntimeException] thrownBy gen(List(
      Method(
        "setFoo",
        "Use this method to set foo.",
        List(MethodParam(
          "bars",
          "Array of String",
          "Optional",
          "Pass an empty list to clear the bars. If not specified, the current bars are kept."
        )),
        "Boolean"
      )
    ))
    e.getMessage should include("setFoo.bars")
    e.getMessage should include(TgBotApiOverrides.file)
  }

  it must "fail the scrape for other wordings that give an empty list a meaning of its own" in {
    List(
      "If empty, all bars are removed.",
      "If non-empty, the list of bars to keep.",
      "Defaults to all bars.",
      "Pass an <em>empty</em> list to remove all bars."
    ).foreach { desc =>
      val e = the[RuntimeException] thrownBy gen(List(
        Method("setFoo", "Use this method to set foo.", List(MethodParam("bars", "Array of String", "Optional", desc)), "Boolean")
      ))
      e.getMessage should include("setFoo.bars")
    }
  }

  "A required array whose description mentions the word optional" must "not be tagged (omitempty)" in {
    val dsl = gen(List(
      Method(
        "sendMediaGroup",
        "Use this method to send a group of media.",
        List(MethodParam("media", "Array of InputMedia", "Yes", "Media to be sent, each with an optional caption")),
        "List[Message]"
      ),
      Entity(
        "Table",
        "Describes a table.",
        List(EntityParam("rows", "Array of Array of String", "Rows of the table. Optional cells are null."))
      )
    ))
    dsl should include("media : List[InputMedia]")
    dsl should include("rows : List[List[String]]")
  }

  private def integerField(name: String, desc: String, owner: String = "Foo", kind: String = "Integer"): EntityParam =
    entity(
      s"""<h4>$owner</h4>
         |<p>This object represents a foo.</p>
         |<table class="table">
         |<thead><tr><th>Field</th><th>Type</th><th>Description</th></tr></thead>
         |<tbody><tr><td>$name</td><td>$kind</td><td>$desc</td></tr></tbody>
         |</table>""".stripMargin
    ).table.head

  // https://github.com/apimorphism/iozhik/issues/26
  "An integer ID field whose wording and name have no override" must "fail the scrape, naming the field and the overrides file" in {
    val e = the[RuntimeException] thrownBy integerField("zork_id", "Unique identifier of the zork")
    e.getMessage should include("Foo.zork_id")
    e.getMessage should include("integer = Int or Long")
    e.getMessage should include(TgBotApiOverrides.file)
  }

  "An integer ID field described with the known 32-bit wording" must "be a Long, whatever its name" in {
    integerField("zork_id", "Unique identifier of the zork. This number may have more than 32 significant bits.").kind shouldBe "Long"
  }

  "An integer ID field with an override" must "be classified by it" in {
    integerField("message_id", "Unique message identifier inside this chat").kind shouldBe "Int"
    integerField("user_id", "Unique identifier of the target user").kind shouldBe "Long"
  }

  "A generic integer ID name" must "be classified per owner" in {
    integerField("id", "Unique identifier of the task", "ChecklistTask").kind shouldBe "Int"
    val e = the[RuntimeException] thrownBy integerField("id", "Unique identifier of the task")
    e.getMessage should include("Foo.id")
  }

  "An integer described as an identifier, whatever its name," must "need classifying too" in {
    val e = the[RuntimeException] thrownBy integerField("zork", "Identifier of the zork")
    e.getMessage should include("Foo.zork")
  }

  "An array of integer IDs" must "be classified like a single one" in {
    integerField("message_ids", "A list of identifiers of messages", "Foo", "Array of Integer").kind shouldBe "Array of Int"
    the[RuntimeException] thrownBy integerField("zork_ids", "The zorks", "Foo", "Array of Integer") should have message
      "Foo.zork_ids: this integer looks like an identifier, but it isn't known whether it fits in an Int. " +
        s"Read its description and give it integer = Int or Long in ${TgBotApiOverrides.file}."
  }

  "An integer that is not an identifier" must "not need classifying" in {
    integerField("limit", "Limits the number of updates").kind shouldBe "Int"
    integerField("total_amount", "Total price in the smallest units of the currency").kind shouldBe "Long"
  }

  "An integer ID param of a method" must "need classifying too" in {
    val e = the[RuntimeException] thrownBy method(
      s"""<h4>setFoo</h4>
         |<p>Use this method to set foo. Returns <em>True</em> on success.</p>
         |<table class="table">
         |<thead><tr><th>Parameter</th><th>Type</th><th>Required</th><th>Description</th></tr></thead>
         |<tbody><tr><td>zork_id</td><td>Integer</td><td>Yes</td><td>Unique identifier of the zork</td></tr></tbody>
         |</table>""".stripMargin
    )
    e.getMessage should include("setFoo.zork_id")
  }

  "An integer ID overridden to Int whose description says it may exceed 32 bits" must "fail the scrape instead of silently becoming a Long" in {
    val e = the[RuntimeException] thrownBy
      integerField("message_id", "Unique message identifier. This number may have more than 32 significant bits.")
    e.getMessage should include("Foo.message_id")
    e.getMessage should include("integer = Int")
  }

  "An integer ID overridden to Long whose description says it may exceed 32 bits" must "still be a Long" in {
    integerField("user_id", "Identifier of the user. This number may have more than 32 significant bits.").kind shouldBe "Long"
  }

  // The Bot API server parses draft_id as int64 (telegram-bot-api Client.cpp, process_send_message_draft_query).
  "A draft_id" must "be a Long" in {
    integerField("draft_id", "Unique identifier of the message draft; must be non-zero.").kind shouldBe "Long"
  }

  private def overrides(hocon: String) = TgBotApiOverrides.parse(hocon)

  private def doc(content: String) = JsoupBrowser.typed().parseString(s"""<div id="dev_page_content">$content</div>""")

  private def tagged(name: String, tag: String, fields: EntityParam*) =
    Entity(name, s"A $tag.", EntityParam("type", "__type_tag__", tag) :: fields.toList)

  "A field's type override" must "replace the docs' type, and still be wrapped when optional" in {
    val o = overrides("fields { parse_mode { type = ParseMode } }")
    val m = TgBotApiScrapper.parse(doc(
      """<h4>sendFoo</h4>
        |<p>Use this method to send foo. Returns <em>True</em> on success.</p>
        |<table class="table">
        |<thead><tr><th>Parameter</th><th>Type</th><th>Required</th><th>Description</th></tr></thead>
        |<tbody><tr><td>parse_mode</td><td>String</td><td>Optional</td><td>Mode for parsing entities</td></tr></tbody>
        |</table>""".stripMargin
    ), o)
    gen(m, o) should include("parseMode : Option[ParseMode]")
  }

  "An Owner.name field override" must "win over a bare name one" in {
    val o = overrides("""fields { zork_id { integer = Long }, "Foo.zork_id" { integer = Int } }""")
    def kind(owner: String) = TgBotApiScrapper.parse(doc(
      s"""<h4>$owner</h4><p>This object represents a foo.</p>
         |<table class="table"><thead><tr><th>Field</th><th>Type</th><th>Description</th></tr></thead>
         |<tbody><tr><td>zork_id</td><td>Integer</td><td>Unique identifier of the zork</td></tr></tbody></table>""".stripMargin
    ), o).collect { case e: Entity => e.table.head.kind }.head
    kind("Foo") shouldBe "Int"
    kind("Bar") shouldBe "Long"
  }

  "A merged type" must "bring its items, or itself when standalone, and its fields' types" in {
    val o = overrides("types { Media { merge = [PollMedia, VoiceMedia] } }")
    val dsl = gen(List(
      Sumtyp("Media", "Some media.", List("PhotoMedia")),
      Sumtyp("PollMedia", "Poll media.", List("PhotoMedia", "VideoMedia")),
      tagged("PhotoMedia", "photo"),
      tagged("VideoMedia", "video"),
      tagged("VoiceMedia", "voice"),
      Entity("Poll", "A poll.", List(EntityParam("media", "PollMedia", "The media")))
    ), o)
    dsl should include("enum Media {")
    dsl should not include("enum PollMedia")
    dsl should include("media : Media")
    List("PhotoMedia {", "VideoMedia {", "VoiceMedia {").foreach { item =>
      dsl.indexOf(item) should be > dsl.indexOf("enum Media {")
      dsl.indexOf(item) should be < dsl.indexOf("Poll {")
    }
  }

  "A docs type that's a union of types" must "become the one sum type holding them all" in {
    val o = overrides("types { Markup { items = [Keyboard, Remove] } }")
    val dsl = gen(List(
      Sumtyp("Media", "Some media.", List("PhotoMedia", "VideoMedia", "VoiceMedia")),
      Entity("Keyboard", "A keyboard.", List(EntityParam("rows", "Array of String", "The rows"))),
      Entity("Remove", "Removes a keyboard.", List(EntityParam("remove", "Boolean", "True"))),
      Method(
        "sendFoo",
        "Use this method to send foo.",
        List(
          MethodParam("media", "Array of PhotoMedia, VideoMedia and VoiceMedia", "Yes", "The media"),
          MethodParam("reply_markup", "Keyboard or Remove", "Yes", "The markup")
        ),
        "Boolean"
      )
    ), o)
    dsl should include("media       : List[Media]")
    dsl should include("replyMarkup : Markup")
    dsl should include("enum Markup {")
  }

  it must "fail the scrape, naming the overrides file, when no type holds them all" in {
    val e = the[RuntimeException] thrownBy gen(List(
      Sumtyp("Media", "Some media.", List("PhotoMedia")),
      Entity("Foo", "A foo.", List(EntityParam("media", "PhotoMedia or VoiceMedia", "The media")))
    ), overrides(""))
    e.getMessage should include("Foo.media")
    e.getMessage should include(TgBotApiOverrides.file)
  }

  "A type's extra items" must "be generated as tagged items of it" in {
    val o = overrides(
      """types { Text { extra-items = [
        |  { name = TextPlain, desc = "Plain text.", tag = plain, fields = [{ name = text, type = String, desc = "The text" }] }
        |] } }""".stripMargin
    )
    val dsl = gen(List(Sumtyp("Text", "A text.", List("TextBold")), tagged("TextBold", "bold")), o)
    dsl should include("TextPlain {")
    dsl should include("text : String")
    dsl should include regex "type : \"plain:\\d+\""
  }

  "A type split by type" must "get a variant per value, keeping overridden fields optional" in {
    val o = overrides("""types { Entity { split-by-type = true } }, fields { "Entity.language" { optional = true } }""")
    val items = TgBotApiScrapper.parse(doc(
      """<h4>Entity</h4><p>This object represents an entity.</p>
        |<table class="table"><thead><tr><th>Field</th><th>Type</th><th>Description</th></tr></thead><tbody>
        |<tr><td>type</td><td>String</td><td>Type of the entity. Currently, can be “pre” (monowidth block) or “text_link” (for clickable text URLs)</td></tr>
        |<tr><td>offset</td><td>Integer</td><td>Offset in UTF-16 code units</td></tr>
        |<tr><td>url</td><td>String</td><td><em>Optional</em>. For “text_link” only, URL that will be opened</td></tr>
        |<tr><td>language</td><td>String</td><td><em>Optional</em>. For “pre” only, the programming language</td></tr>
        |</tbody></table>""".stripMargin), o)
    val dsl = gen(items, o)
    dsl should include("enum Entity {")
    dsl should include("TextLinkEntity {")
    dsl should include("url  : String")
    dsl should include("PreEntity {")
    dsl should include("language : Option[String]")
  }

  "The shipped overrides file" must "load" in {
    noException should be thrownBy TgBotApiOverrides.default
  }

  "An override with an unknown key" must "fail to load, naming the key and the overrides file" in {
    val e = the[RuntimeException] thrownBy overrides("""fields { "getUpdates.allowed_updates" { empty-vs-ommited = distinct } }""")
    e.getMessage should include("empty-vs-ommited")
    e.getMessage should include(TgBotApiOverrides.file)
  }

  "An extra item missing a required key" must "fail to load, naming the key" in {
    val e = the[RuntimeException] thrownBy overrides(
      """types { Text { extra-items = [{ name = TextPlain, desc = "Plain text.", fields = [{ name = text, type = String }] }] } }"""
    )
    e.getMessage should include("types.Text.extra-items: missing tag")
    e.getMessage should include("types.Text.extra-items.fields: missing desc")
  }

  "An override with an unknown value" must "fail to load, naming the allowed ones" in {
    val e = the[RuntimeException] thrownBy overrides("""fields { user_id { integer = Long64 } }""")
    e.getMessage should include("fields.\"user_id\".integer")
    e.getMessage should include("Int, Long")
  }

  private val fooPage = doc(
    """<h4>Foo</h4><p>This object represents a foo.</p>
      |<table class="table"><thead><tr><th>Field</th><th>Type</th><th>Description</th></tr></thead>
      |<tbody><tr><td>bars</td><td>Array of String</td><td><em>Optional</em>. The bars</td></tr></tbody></table>
      |<h4>Media</h4><p>This object represents some media.</p><ul><li>PhotoMedia</li></ul>""".stripMargin
  )

  "A scrape whose overrides all name things in the docs" must "succeed" in {
    TgBotApiScrapper.scrape(fooPage, overrides(
      """fields { bars { empty-vs-omitted = same }, "Foo.bars" { empty-vs-omitted = distinct } }
        |types { Media { merge = [Foo] } }""".stripMargin
    )) should include("bars : Option[List[String]]")
  }

  "A scrape whose overrides name things the docs don't have" must "fail, listing each of them and the overrides file" in {
    val e = the[RuntimeException] thrownBy TgBotApiScrapper.scrape(fooPage, overrides(
      """fields {
        |  "Foo.bazs" { empty-vs-omitted = same }
        |  "Qux.bars" { empty-vs-omitted = same }
        |  bazs { integer = Long }
        |}
        |types {
        |  Qux { merge = [Media] }
        |  Media { merge = [PollMedia] }
        |  Foo { items = [Media] }
        |}""".stripMargin
    ))
    e.getMessage should include(TgBotApiOverrides.file)
    List("Foo.bazs", "Qux.bars", "fields.bazs", "types.Qux", "types.Media.merge", "types.Foo").foreach { key =>
      e.getMessage should include(key)
    }
  }
}
