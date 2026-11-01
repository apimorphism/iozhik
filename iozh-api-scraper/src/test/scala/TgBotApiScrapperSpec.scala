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
  "An integer ID field whose wording and name are in neither list" must "fail the scrape, naming the field and the lists" in {
    val e = the[RuntimeException] thrownBy integerField("zork_id", "Unique identifier of the zork")
    e.getMessage should include("Foo.zork_id")
    e.getMessage should include("intIds")
    e.getMessage should include("longIds")
  }

  "An integer ID field described with the known 32-bit wording" must "be a Long, whatever its name" in {
    integerField("zork_id", "Unique identifier of the zork. This number may have more than 32 significant bits.").kind shouldBe "Long"
  }

  "An integer ID field in one of the lists" must "be classified by it" in {
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
        "Read its description and add it to intIds or longIds in TgBotApiScrapper."
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
}
