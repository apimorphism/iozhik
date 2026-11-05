import java.nio.file.{Path, Paths}
import java.util.StringTokenizer

import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.{Element, ElementNode, Node, TextNode}
import cats.effect._
import cats.instances.list._
import cats.syntax.foldable._
import cats.instances.string._
import cats.instances.int._
import fs2.Stream
import net.ruippeixotog.scalascraper.browser.JsoupBrowser.JsoupElement
import org.jsoup.nodes

import scala.concurrent.ExecutionContext

object TgBotApiScrapper extends IOApp {

  case class EntityParam(name: String, kind: String, desc: String)
  case class EntityParamTag(name: String, value: String)
  case class MethodParam(name: String, kind: String, required: String, desc: String)

  sealed trait ApiItem
  case class Entity(name: String, desc: String, table: List[EntityParam]) extends ApiItem
  case class Method(name: String, desc: String, table: List[MethodParam], returns: String) extends ApiItem
  case class Sumtyp(name: String, desc: String, items: List[String], table: List[EntityParam] = List.empty) extends ApiItem

  private case class Link(text: String, href: String)
  private object Desc {
    private val apiBaseUrl = "https://core.telegram.org/bots/api"
    private val siteBaseUrl = "https://core.telegram.org"

    private def resolveHref(href: String): String =
      if (href.startsWith("#")) apiBaseUrl + href
      else if (href.startsWith("/")) siteBaseUrl + href
      else href

    // Tags whose formatting is preserved as literal HTML, since scaladoc embeds raw HTML.
    private val preservedTags = Set("em", "strong", "code", "b", "i")

    // Detects tag-like tokens in angle brackets anywhere in the text: tag names mentioned
    // in prose (e.g. "<h1>", "<hr/>") and placeholders (e.g. "attach://<file_attach_name>",
    // "bot<token>/<file_path>"). Some HTML-to-markdown converters reinterpret such text as
    // real markup even when it's escaped: Metals' hover renderer decodes the entities, then
    // either rewrites the token into wiki heading/list syntax (its DangerousTags regex) or
    // drops it as an unknown tag, so a placeholder silently disappears from the tooltip.
    // Rather than hardcode which tokens a given tool currently mishandles, every such token
    // is rendered with lookalike angle-bracket characters instead of real ones, so no
    // HTML/Markdown-aware tool can mistake it for a tag.
    private val tagLikeToken = "&lt;(/?[a-zA-Z][a-zA-Z0-9_-]*/?)&gt;".r

    // Escapes literal text so it can't be mistaken for markup (e.g. a field whose
    // prose mentions "<h1>" must not be parsed as an actual tag by the HTML renderer).
    private def escapeText(s: String): String = {
      val escaped = s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
      tagLikeToken.replaceAllIn(escaped, m => s"ᐸ${m.group(1)}ᐳ")
    }

    // Renders text preserving <a> links (as absolute HTML links), <br> (as line
    // breaks) and the tags above, dropping "Learn more »" links.
    private def renderNodes(nodes: List[Node]): String = nodes match {
      case Nil => ""
      case ElementNode(el) :: rest if el.tagName == "a" && el.text.contains("»") =>
        rest match {
          case TextNode(content) :: rest2 if content.startsWith(".") =>
            renderNodes(TextNode(content.stripPrefix(".")) :: rest2)
          case _ => renderNodes(rest)
        }
      case ElementNode(el) :: rest if el.tagName == "a" =>
        s"""<a href="${resolveHref(el.attr("href"))}">${escapeText(el.text)}</a>""" + renderNodes(rest)
      case ElementNode(el) :: rest if el.tagName == "br" =>
        // A blank line forces a real paragraph break that survives both scalafmt's
        // docstring reflow and Metals' hover renderer, neither of which reliably
        // preserve a bare <br> (scalafmt only protects recognized "- " bullet lines;
        // Metals' HtmlConverter has no case for <br> at all and silently drops it).
        "<br>\n\n" + renderNodes(rest)
      case ElementNode(el) :: rest if preservedTags.contains(el.tagName) =>
        s"<${el.tagName}>${renderNodes(el.childNodes.toList)}</${el.tagName}>" + renderNodes(rest)
      case ElementNode(el) :: rest =>
        renderNodes(el.childNodes.toList) + renderNodes(rest)
      case TextNode(content) :: rest =>
        escapeText(content) + renderNodes(rest)
      case _ :: rest =>
        renderNodes(rest)
    }

    // A note (<blockquote>) is rendered as its own paragraphs, one per inner block,
    // with list items as "- " bullet lines like the <br>-separated lists above.
    private def renderBlock(e: Element): String = e.tagName match {
      case "blockquote" => e.children.toList.map(renderBlock).mkString("\n\n")
      case "ul"         => e.children.toList.map(li => "- " + renderNodes(li.childNodes.toList)).mkString("\n\n")
      case _            => renderNodes(e.childNodes.toList)
    }

    private def isNote(e: Element): Boolean = e.tagName == "blockquote"

    def apply(source: List[Element]): Desc = {
      val text = source.headOption.fold("") { head =>
        source.zip(source.tail).foldLeft(renderBlock(head)) { case (acc, (prev, next)) =>
          val separator = if (isNote(prev) || isNote(next)) "\n\n" else "\n"
          acc + separator + renderBlock(next)
        }
      }
      val links = source.flatMap(_ >> elements("a")).map(e => Link(e.text, e.attr("href")))
      val cleanedText = text
        .replace("@", "&#064;")
        .replace("^", "&#94;")
      Desc(source, cleanedText, links)
    }

    def apply(source: Element): Desc = Desc(List(source))

    def empty: Desc = Desc(List.empty, "", List.empty)
  }
  private case class Desc(source: List[Element], text: String, links: List[Link])

  def wrap(text: String, LineWidth: Int): String = {
    // StringTokenizer treats all whitespace alike, so a blank line (paragraph break)
    // would otherwise collapse into ordinary word-spacing. Wrap each paragraph on
    // its own and rejoin with the blank line preserved.
    def wrapParagraph(paragraph: String): String = {
      val buf = StringBuilder.newBuilder
      val spaceWidth = 1
      val tokenizer = new StringTokenizer(paragraph)
      var SpaceLeft = LineWidth
      while (tokenizer.hasMoreTokens) {
        val word: String = tokenizer.nextToken
        if ((word.length + spaceWidth) > SpaceLeft) {
          buf.append("\n    " + word + " ")
          SpaceLeft = LineWidth - word.length
        } else {
          buf.append(word + " ")
          SpaceLeft -= (word.length + spaceWidth)
        }
      }
      buf.toString
    }
    text.split("\n\n", -1).map(wrapParagraph).mkString("\n\n")
  }

  def camelify(name : String): String = {
    def loop(x : List[Char]): List[Char] = (x: @unchecked) match {
      case '_' :: '_' :: rest => loop('_' :: rest)
      case '_' :: c :: rest => Character.toUpperCase(c) :: loop(rest)
      case '_' :: Nil => Nil
      case c :: rest => c :: loop(rest)
      case Nil => Nil
    }
    if (name == null)
    ""
    else
    loop(name.toList).mkString
  }

  private val quotedPattern = "(?<=“).*?(?=”)".r
  private val forXOnlyPattern = "(?<=For “).*?(?=” only)".r
  private val forXOnlyDescPattern = "(?:<em>)?Optional(?:</em>)?\\. For “.*” only, ".r
  private val discriminatorPattern = ".*, always “(.*?)”".r
  val optionalFieldDescPattern = "^(?:<em>)?Optional\\b".r
  private val unionSeparator = ", | and | or ".r

  // Integer identifiers (fields or params named *_id/*_ids, or described as an identifier) must be classified as Int or Long:
  // by the overrides' integer, else by this wording, else by their name. The scrape fails for any left over, so a new ID is
  // never silently given the wrong type (apimorphism/telegramium e5cf9e9: fromChatId was an Int). It also fails when the
  // wording says Long for an integer the overrides or its name make an Int.
  private val longWording = List("greater than 32 bits", "more than 32 significant bits", "bigger than 2^31")
  private val identifierWording = "(?i)\\bidentifiers?\\b".r
  // Checked against the Bot API server: telegram-bot-api Client.cpp at e3e9dd8 (Bot API 10.3). User and chat IDs are int64
  // (get_user_id / get_user_ids, and chat IDs, which are resolved from strings and exceed 32 bits). Message IDs are int32
  // (get_message_id / get_message_ids), and so are td_api's story and checklist task IDs.
  private val longIdName = "(^|_)(user|chat)_ids?$".r
  private val intIdName = "(^|_)(message|story|task)_ids?$".r

  private def isIntegerId(name: String, desc: String): Boolean =
    name.endsWith("_id") || name.endsWith("_ids") || identifierWording.findFirstIn(desc).isDefined

  // A type's own id is read as <type>_id, e.g. Story.id as story_id.
  private def integerWidthByName(owner: String, name: String): Option[TgBotApiOverrides.IntegerWidth] = {
    val idName = if (name == "id") owner.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase + "_id" else name
    if (longIdName.findFirstIn(idName).isDefined) Some(TgBotApiOverrides.AsLong)
    else if (intIdName.findFirstIn(idName).isDefined) Some(TgBotApiOverrides.AsInt)
    else None
  }

  // Parses a docs page, checks the overrides still fit it, and generates the DSL.
  def scrape(doc: JsoupBrowser.JsoupDocument, overrides: TgBotApiOverrides.Overrides = TgBotApiOverrides.default): String = {
    val items = parse(doc, overrides)
    overrides.problems(items) match {
      case Nil      => gen(items, overrides)
      case problems => sys.error(s"Overrides in ${TgBotApiOverrides.file} don't fit the docs:\n" + problems.mkString("\n"))
    }
  }

  def gen(items: List[ApiItem], overrides: TgBotApiOverrides.Overrides = TgBotApiOverrides.default): String = {
    def trarr(s: String): String = {
      if (s.startsWith("Array of ")) {
        val body = trarr(s.drop("Array of ".length))
        s"""List[$body]"""
      } else {
        s
      }
    }

    // Optional arrays are tagged (omitempty) so the generated encoder can omit them when empty, as Telegram treats a
    // missing optional array the same as an empty one - unless the overrides say otherwise.
    val emptyVsOmittedWording = "(?i)\\bempty\\b|not specified|omitted|previous setting|defaults? to".r

    def isOptionalField(f: EntityParam) = optionalFieldDescPattern.findFirstIn(f.desc).isDefined
    def isOptionalParam(f: MethodParam) = f.required.toLowerCase.contains("optional")

    def optionalArray(owner: String, f: String, kind: String, desc: String): String =
      overrides.field(owner, f).emptyVsOmitted match {
        case Some(TgBotApiOverrides.Distinct) => s"Option[${trarr(kind)}]"
        case Some(TgBotApiOverrides.Same)     => s"(omitempty) ${trarr(kind)}"
        case None if emptyVsOmittedWording.findFirstIn(desc).isEmpty => s"(omitempty) ${trarr(kind)}"
        case None => sys.error(
          s"$owner.$f: the description may give an empty list and an omitted one different meanings. " +
            s"Give it empty-vs-omitted = distinct or same in ${TgBotApiOverrides.file}."
        )
      }

    // Sum types merged into another: a field of one of them gets the one they're merged into.
    val allSumtyps = items.collect{ case e: Sumtyp => e }
    val mergedInto = overrides.types.toList.flatMap { case (name, t) =>
      t.merge.filter(m => allSumtyps.exists(_.name == m)).map(_ -> name)
    }
    val resolvedSumtyps = allSumtyps
      .map { s =>
        val t = overrides.typeOverride(s.name)
        // A merged sum type brings its items, a merged standalone type itself.
        val extraItems = t.merge
          .flatMap(m => allSumtyps.find(_.name == m).fold(List(m))(_.items))
          .filterNot(s.items.contains)
          .distinct
        s.copy(items = s.items ++ extraItems ++ t.extraItems.map(_.name))
      }
      .filterNot(s => mergedInto.exists(_._1 == s.name))
    val declaredTypes = overrides.types.toList.filter(_._2.items.nonEmpty).map { case (name, t) => name -> t.items }.sortBy(_._1)
    val unionTargets = resolvedSumtyps.map(s => s.name -> s.items) ++ declaredTypes

    // A docs type that's a union of types becomes the sum type whose items include them all.
    def resolveKind(owner: String, name: String, kind: String): String = {
      val renamed = mergedInto.foldLeft(kind) { case (k, (from, to)) => k.replaceAll(s"\\b$from\\b", to) }
      val arrays = "^(Array of )*".r.findFirstIn(renamed).getOrElse("")
      val members = unionSeparator.split(renamed.drop(arrays.length)).toList
      if (members.size < 2) renamed
      else unionTargets.collect { case (target, targetItems) if members.forall(targetItems.contains) => target } match {
        case target :: Nil => arrays + target
        case targets => sys.error(
          s"$owner.$name: its type ${renamed.drop(arrays.length)} should be the items of exactly one type, but is of " +
            s"${if (targets.isEmpty) "none" else targets.mkString(", ")}. Declare one with these items in ${TgBotApiOverrides.file}."
        )
      }
    }
    // A type override is a DSL type already, so it's never a union.
    def resolve(owner: String, name: String, kind: String) =
      if (overrides.field(owner, name).tpe.isDefined) kind else resolveKind(owner, name, kind)
    val resolvedItems = items.map {
      case m: Method => m.copy(table = m.table.map(p => p.copy(kind = resolve(m.name, p.name, p.kind))))
      case e: Entity => e.copy(table = e.table.map(f => f.copy(kind = resolve(e.name, f.name, f.kind))))
      case s: Sumtyp => s
    }

    def genFields(owner: String, params: List[EntityParam]) = {
      val maxLen = params.map(p => camelify(p.name).length).maximumOption.getOrElse(0)
      params
        .map { f =>
          val kind = if (f.kind.startsWith("Array of"))
            if (isOptionalField(f)) optionalArray(owner, f.name, f.kind, f.desc) else trarr(f.kind)
          else {
            if (isOptionalField(f)) {
              if (f.kind == "IFile") s"(file) Option[${f.kind}]" else s"Option[${f.kind}]"
            } else {
              if (f.kind == "IFile") s"(file) ${f.kind}" else f.kind
            }
          }
          if (f.kind == "__type_tag__")
            s"""    ${f.name} : """" + f.desc + ":__type_id_placeholder__\""
          else
            s"    /* ${wrap(f.desc, 60)}*/\n    ${camelify(f.name).padTo(maxLen, ' ')} : $kind"
        }
        .intercalate("\n")
    }

    val mmap = resolvedItems.collect{
        case m: Method => m
      }
      .map { m =>
        val maxLen = m.table.map(x => camelify(x.name).length).maximumOption.getOrElse(0)
        val fields = m.table
          .map { f =>
            val kind = if (f.kind.startsWith("Array of")) {
              if (isOptionalParam(f)) {
                optionalArray(m.name, f.name, f.kind, f.desc)
              } else {
                trarr(f.kind)
              }
            } else {
              if (isOptionalParam(f)) {
                if (f.kind == "IFile") {
                  s"(file) Option[${f.kind}]"
                } else {
                  s"Option[${f.kind}]"
                }
              } else {
                if (f.kind == "IFile") {
                  s"(file) ${f.kind}"
                } else {
                  f.kind
                }
              }
            }
            if (f.kind == "__type_tag__") {
              s"""    ${f.name} : """" + f.desc + ":__type_id_placeholder__\""
            } else {
              s"    /* ${wrap(f.desc, 60)}*/\n    ${camelify(f.name).padTo(maxLen, ' ')} : $kind"
            }
          }
          .intercalate("\n")
        val body =
          s"""
            |  /* ${wrap(m.desc, 80)} */
            |  def ${m.name} {
            |$fields
            |  } => Method[${m.returns}]""".stripMargin
        m.name -> body
      }.toMap
    val splitItems = resolvedItems.collect {
      case e: Entity if overrides.typeOverride(e.name).splitByType => e
    }
    val splitVariants = splitItems.map { e =>
      e -> e.table
        .find(_.name == "type")
        .map { t =>
          quotedPattern.findAllIn(t.desc)
            .toList
            .map { child =>
              Entity(
                name = camelify(child).capitalize + e.name,
                child,
                table = t.copy(desc = child) :: e.table
                  .filter(f => f.desc.contains("Optional") && forXOnlyPattern.findFirstIn(f.desc).contains(child))
                  .map { f =>
                    val optional = if (overrides.field(e.name, f.name).optional) "Optional, " else ""
                    f.copy(desc = optional + forXOnlyDescPattern.replaceFirstIn(f.desc, ""))
                  }
              )
            }
        }
        .getOrElse(List.empty)
    }
    val splitParents = splitVariants.map { case (e, variants) =>
      Sumtyp(
        e.name,
        e.desc,
        variants.map(_.name),
        e.table.filterNot(f => f.desc.contains("Optional") || f.name == "type")
      )
    }
    val emap = (resolvedItems.collect{
        case e: Entity => e
      }
      .filterNot(splitItems.contains) ++ splitVariants.flatMap(_._2) ++ resolvedSumtyps.flatMap(s => overrides.typeOverride(s.name).extraItems))
      .map { e =>
        val fields = genFields(e.name, e.table)
        val body = s"""
           |  /* ${wrap(e.desc, 80)} */
           |  ${e.name} {
           |$fields
           |  }
         """.stripMargin
        e.name -> body
      }.toMap
    val leaves = (resolvedSumtyps.map(_.items) ++ splitParents.map(_.items) ++ declaredTypes.map(_._2)).flatten.toSet
    // A tagged type gets its type id as an item of a sum type: one outside them all would keep the placeholder.
    val sumItems = (resolvedSumtyps ++ splitParents).flatMap(_.items).toSet
    val untagged = emap.collect { case (name, b) if b.contains("__type_id_placeholder__") && !sumItems(name) => name }
    if (untagged.nonEmpty) sys.error(
      s"${untagged.toList.sorted.mkString(", ")}: tagged, but an item of no sum type. " +
        s"Add each to one with merge in ${TgBotApiOverrides.file}."
    )
    val sumTypes = (resolvedSumtyps ++ splitParents)
      .sortBy(_.name)
      .map { e =>
        val fields = genFields(e.name, e.table)
        val children = emap.filterKeys{ k =>
            e.items.contains(k)
          }
          .values
          .toList
          .zipWithIndex
          .map { case (b, idx) => b.replace("__type_id_placeholder__", idx.toString) }
          .intercalate("")
        val fieldsText = if (fields.isEmpty) fields else fields + "\n"
        s"""
          |  enum ${e.name} {
          |$fieldsText${children.split("\n").map(x => "  " + x).toList.intercalate("\n")}
          |  }
        """.stripMargin
      }
    val body = sumTypes.intercalate("") + emap.filterKeys(k => !leaves.contains(k)).toList.sortBy(_._1).map(_._2).intercalate("")
    val entities = body.split("\n").map("  " + _).toList.intercalate("\n")
    val methods = mmap.toList.sortBy(_._1).map(_._2).flatMap(_.split("\n")).map("      " + _).intercalate("\n")
    val declared = declaredTypes
      .map { case (name, typeItems) =>
        val children = emap.filterKeys(typeItems.contains).values.toList
          .flatMap(_.split("\n")).map("    " + _).intercalate("\n")
        s"enum $name {\n      $children\n    }"
      }
      .intercalate("\n\n    ")
    s"""
       |namespace telegramium {
       |  namespace bots(snake circe http4s) {
       |
       |    enum Emoji {
       |      item EmojiDice        = "🎲"
       |      item EmojiDarts       = "🎯"
       |      item EmojiBasketball  = "🏀"
       |      item EmojiFootball    = "⚽"
       |      item EmojiSlotMachine = "🎰"
       |      item EmojiBowling     = "🎳"
       |    }
       |
       |    enum ParseMode {
       |      item Markdown  = "Markdown"
       |      item Markdown2 = "MarkdownV2"
       |      item Html      = "HTML"
       |    }
       |
       |    /* Chat ID. */
       |    enum ChatId {
       |      /* Integer chat ID */
       |      wrap ChatIntId (id) = Long
       |      /* Chat ID starting with @. */
       |      wrap ChatStrId (id) = String
       |    }
       |
       |    $declared
       |
       |    /** A file to send. */
       |    enum IFile {
       |      /* Use InputPartFile to send file on a local file system. */
       |      wrap InputPartFile (file) = java.io.File
       |      /* Use InputLinkFile to send a file accessible via some URL. */
       |      wrap InputLinkFile (file) = String
       |    }
       |
       |    $entities
       |
       |    namespace client {
       |      Methods (methodsFactory) {
       |        $methods
       |      }
       |    }
       |  }
       |}
     """.stripMargin
  }

  def mkPool[F[_]](implicit S: Sync[F]): Resource[F, ExecutionContext] = {
    Resource(S.delay {
      val executor = ExecutionContext.fromExecutor((command: Runnable) => command.run())
      val ec = ExecutionContext.fromExecutor(executor)
      (ec, S.delay(()))
    })
  }

  def write[F[_]: Async](content: String, file: Path): F[Unit] = {
    Stream.resource(mkPool)
      .flatMap { implicit ec =>
        Stream
          .emit(content)
          .through(fs2.text.utf8Encode)
          .through(fs2.io.file.writeAll(file))
      }
      .compile
      .drain
  }

  def parse(doc: JsoupBrowser.JsoupDocument, overrides: TgBotApiOverrides.Overrides = TgBotApiOverrides.default): List[ApiItem] = {
    doc.underlying.select("img.emoji").forEach(e => e.replaceWith(new nodes.TextNode(e.attr("alt"))))
    val rawItems = doc >> elements("div#dev_page_content > *")

    case class Item(name: Element, desc: List[Element], table: Element)
    case class Sumt(name: Element, desc: Element, items: Element)

    def mkType(owner: String, name: String, desc: Desc, s: String): String = {
      val descText = desc.text
      val hasLongWording = longWording.exists(descText.contains)
      val fieldOverride = overrides.field(owner, name)
      // "Integer or String" is a ChatId, not an integer to classify, and a type override replaces the docs' type.
      val isInteger = fieldOverride.tpe.isEmpty && s.replace("Integer or String", "").contains("Integer")
      val width = fieldOverride.integer.orElse(integerWidthByName(owner, name))
      val mustBeLong =
        if (!isInteger) false
        else if (hasLongWording && width.contains(TgBotApiOverrides.AsInt)) sys.error(
          s"$owner.$name: the description says it may not fit in an Int, but " +
            (if (fieldOverride.integer.isDefined) s"it has integer = Int in ${TgBotApiOverrides.file}"
             else "its name makes it an Int (TgBotApiScrapper.intIdName)") +
            ". Check the Bot API server source and change it to Long."
        )
        else if (hasLongWording || width.contains(TgBotApiOverrides.AsLong)) true
        else if (width.contains(TgBotApiOverrides.AsInt)) false
        else if (isIntegerId(name, descText)) sys.error(
          s"$owner.$name: this integer looks like an identifier, but it isn't known whether it fits in an Int. " +
            s"Read its description and give it integer = Int or Long in ${TgBotApiOverrides.file}."
        )
        else {
          descText.contains("size in bytes") ||
            descText.contains("Unix time") ||
            name.toLowerCase().contains("amount")
        }
      val isFile = desc.links.exists(_.href == "#sending-files")
      if (isFile)
          "IFile"
      else if (fieldOverride.tpe.isDefined)
        fieldOverride.tpe.get
      // The docs give these as a String; ParseMode lists the values they name under "Formatting options".
      else if (name.endsWith("parse_mode"))
        "ParseMode"
      else {
        s
          .replace(" number", "") // Float number => Float
          .replace("True", "Boolean")
          .replace("False", "Boolean")
          .replace("Integer or String", "ChatId")
          .replace("Integer", if (mustBeLong) "Long" else "Int")
          .replace("InputFile or String", "IFile")
          .replace("InputFile", "IFile")
      }
    }

    def isH4(e: Element): Boolean = e.tagName.toLowerCase == "h4"
    def isP(e: Element): Boolean = e.tagName.toLowerCase == "p"
    def isTable(e: Element): Boolean = e.tagName.toLowerCase == "table" && !e.attrs("class").contains("table-bordered")
    def isUL(e: Element): Boolean = e.tagName.toLowerCase == "ul"
    def isBQ(e: Element): Boolean = e.tagName.toLowerCase == "blockquote"
    def isDiv(e: Element): Boolean = e.tagName.toLowerCase == "div"

    val fake: Element = JsoupElement(new nodes.Element("section"))

    // Notes that follow an item's table (e.g. getUpdates, CallbackQuery) belong to its description.
    def trailingBQ(rest: List[Element]): List[Element] = rest.headOption.filter(isBQ).toList

    val apiItems = (rawItems.toList ++ List(fake, fake, fake, fake)).sliding(7, 1)
      .map(_.toList)
      .collect {
        case x1 :: x2 :: x3 :: x4 :: x5 :: x6 :: rest if isH4(x1) && isP(x2) &&
          isDiv(x3) && isP(x4) && isBQ(x5) && isTable(x6) =>
          Item(name = x1, desc = List(x2, x4, x5) ++ trailingBQ(rest), table = x6)
        case x1 :: x2 :: x3 :: x4 :: x5 :: rest if isH4(x1) && isP(x2) && isDiv(x3) && isBQ(x4) && isTable(x5) =>
          Item(name = x1, desc = List(x2, x4) ++ trailingBQ(rest), table = x5)
        case x1 :: x2 :: x3 :: x4 :: x5 :: rest if isH4(x1) && isP(x2) && isP(x3) && isP(x4) && isTable(x5) =>
          Item(name = x1, desc = List(x2, x3, x4) ++ trailingBQ(rest), table = x5)
        case x1 :: x2 :: x3 :: x4 :: rest if isH4(x1) && isP(x2) && isP(x3) && isTable(x4) =>
          Item(name = x1, desc = List(x2, x3) ++ trailingBQ(rest), table = x4)
        case x1 :: x2 :: x3 :: x4 :: x5 :: rest if isH4(x1) && isP(x2) && isBQ(x3) && isP(x4) && isTable(x5) =>
          Item(name = x1, desc = List(x2, x3, x4) ++ trailingBQ(rest), table = x5)
        case x1 :: x2 :: x3 :: x4 :: rest if isH4(x1) && isP(x2) && isBQ(x3) && isTable(x4) =>
          Item(name = x1, desc = List(x2, x3) ++ trailingBQ(rest), table = x4)
        case x1 :: x2 :: x3 :: rest if isH4(x1) && isP(x2) && isTable(x3) =>
          Item(name = x1, desc = List(x2) ++ trailingBQ(rest), table = x3)
        case x1 :: x2 :: x3 :: _ if isH4(x1) && isP(x2) && isUL(x3) =>
          Sumt(name = x1, desc = x2, items = x3)
        case x1 :: x2 :: x3 :: _ if isH4(x1) && isP(x2) && !x1.text.contains(" ") =>
          Item(name = x1, desc = List(x2), table = x3)
        case x1 :: x2 :: rest if isH4(x1) && isTable(x2) =>
          Item(name = x1, desc = trailingBQ(rest), table = x2)
      }
      .collect {
        case x: Item if x.name.text.head.isLower =>
          val params = x.table >> elements("tbody > tr")
          // Notes may mention other types and words like "otherwise"; infer the return type from the main text only.
          val retDesc = x.desc.filterNot(isBQ)
          val types = retDesc.flatMap(y => y >> elements("a"))
            .filter(_.attr("href").startsWith("#"))
            .map(_.text)
            .filter(_.headOption.exists(_.isUpper))
          val resA = types.find(_ == "Message").orElse(types.headOption)
          val resEm = retDesc.flatMap(y => y >> elements("em"))
            .map(_.text)
            .find(_.head.isUpper)
          val useEither = retDesc.map(_.text).exists(_.contains("otherwise"))
          val res = (resA, resEm) match {
            case (Some(a), Some(em)) if useEither     => s"Either[$em, $a]"
            case (Some(a), Some(em)) if em == "Array" => a
            case (_, Some(em))                        => em
            case (Some(a), None)                      => a
            case _                                    => "CANT_PARSE_RETURN_VALUE"
          }
          val returns = if (retDesc.map(_.text.toLowerCase).intercalate(" ").contains("array of")) {
            s"List[$res]"
          } else {
            res
          }
          Method(
            name = x.name.text,
            desc = Desc(x.desc).text,
            table = params.map { y =>
              val k = y.children.toList(1).text
              val name = y.children.toList.head.text
              val desc = Desc(y.children.toList(3))
              MethodParam(
                name = name,
                kind = mkType(x.name.text, name, desc, k),
                required = y.children.toList(2).text,
                desc = desc.text,
              )
            }.toList,
            returns = mkType("", "", Desc.empty, returns)
          )
        case x: Item if x.name.text.head.isUpper =>
          val params = x.table >> elements("tbody > tr")
          val itemName = x.name.text
          Entity(
            name = itemName,
            desc = Desc(x.desc).text,
            table = params.map { y =>
              val name = y.children.toList.head.text
              val kind = y.children.toList(1).text
              val desc = Desc(y.children.toList(2))
              val descText = desc.text
              if (
                (name == "type" || name == "source" || name == "status") && 
                  (descText.contains(", must be ") || descText.contains(", always ") || overrides.typeOverride(itemName).splitByType)
              ) {
                val cleanedDesc = descText.replaceAll(".*must be ", "")
                val typeDesc = discriminatorPattern
                  .findFirstMatchIn(cleanedDesc)
                  .map(_.group(1))
                  .getOrElse(cleanedDesc)
                  .stripPrefix("“")
                  .stripSuffix("”")
                  .stripPrefix("<em>")
                  .stripSuffix("</em>")
                EntityParam(
                  name = name,
                  kind = "__type_tag__",
                  desc = typeDesc
                )
              } else {
                EntityParam(
                  name = name,
                  kind = mkType(itemName, name, desc, kind),
                  desc = descText,
                )
              }
            }.toList
          )
        case x: Sumt if !x.name.text.contains(" ") =>
          val items = x.items >> elements("li")
          Sumtyp(
            name = x.name.text,
            desc = x.desc.text,
            items = items
              .map(_.text)
              .toList
          )
      }
      .toList

    apiItems
  }

  override def run(args: List[String]): IO[ExitCode] =
    IO {
      val browser = JsoupBrowser.typed()
      scrape(browser.get("https://core.telegram.org/bots/api"))
    }.flatMap { content =>
      IO {
        Option(System.getenv("IOZHDIR"))
      }.flatMap { iozhDir =>
        iozhDir.fold(
          IO.raiseError[String](new RuntimeException(
            "Please set IOZHDIR environment variable to the absolute path of the project"
          ))
        )(x => IO(x))
      }.flatMap { iozhDir =>
        val dst = Paths.get(iozhDir + "/iozhtest/src/main/resources/tgbot.api")
        dst.toFile.delete()
        write[IO](content, dst).as(ExitCode.Success)
      }
    }

}
