import java.nio.file.{Path, Paths}
import java.util.StringTokenizer

import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Element
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
  private case class Desc(source: List[Element], text: String, links: List[Link])
  private object Desc {
    def apply(source: List[Element]): Desc = {
      val text = source.map(_.text).intercalate("\n")
      val links = source.flatMap(_ >> elements("a")).map(e => Link(e.text, e.attr("href")))
      val cleanedText = removeLearnMoreLinks(text, links)
        .replace("@", "&#064;")
        .replace("^", "&#94;")
      Desc(source, cleanedText, links)
    }

    def apply(source: Element): Desc = Desc(List(source))

    def empty: Desc = Desc(List.empty, "", List.empty)

    private def removeLearnMoreLinks(text: String, links: List[Link]): String = {
      val learnMoreLinks = links.filter(_.text.contains("»"))
      learnMoreLinks.foldLeft(text) { (txt, link) =>
        txt.replaceAll(s"${link.text}\\.?", "")
      }
    }
  }

  def wrap(text: String, LineWidth: Int): String = {
    val buf = StringBuilder.newBuilder
    val spaceWidth = 1
    val tokenizer = new StringTokenizer(text)
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
  private val messageEntityDescPattern = "Optional. For “.*” only, ".r
  private val discriminatorPattern = ".*, always “(.*?)”".r

  def gen(items: List[ApiItem]): String = {
    def trarr(s: String): String = {
      if (s.startsWith("Array of ")) {
        val body = trarr(s.drop("Array of ".length))
        s"""List[$body]"""
      } else {
        s
      }
    }

    def isParseMode(name: String) = name == "parse_mode" || name == "quote_parse_mode" || name == "explanation_parse_mode" || name == "text_parse_mode"

    def genFields(params: List[EntityParam]) = {
      val maxLen = params.map(p => camelify(p.name).length).maximumOption.getOrElse(0)
      params
        .map { f =>
          val kind = if (f.kind.startsWith("Array of"))
            trarr(f.kind)
          else if (isParseMode(f.name))
            "Option[ParseMode]"
          else {
            if (f.desc.contains("Optional"))
              s"Option[${f.kind}]"
            else
              f.kind
          }
          if (f.kind == "__type_tag__")
            s"""    ${f.name} : """" + f.desc + ":__type_id_placeholder__\""
          else
            s"    /* ${wrap(f.desc, 60)}*/\n    ${camelify(f.name).padTo(maxLen, ' ')} : $kind"
        }
        .intercalate("\n")
    }

    val mmap = items.collect{
        case m: Method => m
      }
      .map { m =>
        val maxLen = m.table.map(x => camelify(x.name).length).maximumOption.getOrElse(0)
        val fields = m.table
          .map { f =>
            val kind = if (f.kind.startsWith("Array of")) {
              trarr(f.kind)
            } else if (isParseMode(f.name)) {
              "Option[ParseMode]"
            } else {
              if (f.desc.toLowerCase.contains("optional") || f.required.toLowerCase.contains("optional")) {
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
    val messageEntityItem = items.collect {
      case e: Entity => e
    }
      .find(_.name == "MessageEntity")
    val messageEntities = messageEntityItem
      .flatMap { e =>
        e.table
          .find(_.name == "type")
          .map { t =>
            quotedPattern.findAllIn(t.desc)
              .toList
              .map { child =>
                Entity(
                  name = camelify(child).capitalize + "MessageEntity",
                  child,
                  table = t.copy(desc = child) :: e.table
                    .filter(f => f.desc.contains("Optional") && forXOnlyPattern.findFirstIn(f.desc).contains(child))
                    .map { f =>
                      val optional = if (f.name == "language") "Optional, " else ""
                      f.copy(desc = optional + messageEntityDescPattern.replaceFirstIn(f.desc, ""))
                    }
                )
              }
          }
      }
      .getOrElse(List.empty)
    val messageEntityParent = messageEntityItem.map { e =>
      Sumtyp(
        "MessageEntity",
        e.desc,
        messageEntities.map(_.name),
        e.table.filterNot(f => f.desc.contains("Optional") || f.name == "type")
      )
    }
      .toList
    val emap = (items.collect{
        case e: Entity => e
      }
      .filterNot(messageEntityItem.contains) ++ messageEntities)
      .map { e =>
        val fields = genFields(e.table)
        val body = s"""
           |  /* ${wrap(e.desc, 80)} */
           |  ${e.name} {
           |$fields
           |  }
         """.stripMargin
        e.name -> body
      }.toMap
    val inlines = Set("InlineKeyboardMarkup", "ReplyKeyboardMarkup", "ReplyKeyboardRemove", "ForceReply")
    val leaves = (items.collect{ case e: Sumtyp => e.items } ++ messageEntityParent.map(_.items)).flatten.toSet ++ inlines
    val sumTypes = (items.collect{ case e: Sumtyp => e } ++ messageEntityParent)
      .sortBy(_.name)
      .map { e =>
        val fields = genFields(e.table)
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
    val markups = emap.filterKeys(inlines.contains).values.toList
      .flatMap(_.split("\n")).map("    " + _).intercalate("\n")
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
       |    enum KeyboardMarkup {
       |      $markups
       |    }
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

  override def run(args: List[String]): IO[ExitCode] =
    IO {
      val browser = JsoupBrowser.typed()
      val doc = browser.get("https://core.telegram.org/bots/api")
      doc.underlying.select("img.emoji").forEach(e => e.replaceWith(new nodes.TextNode(e.attr("alt"))))
      val rawItems = doc >> elements("div#dev_page_content > *")

      case class Item(name: Element, desc: List[Element], table: Element)
      case class Sumt(name: Element, desc: Element, items: Element)

      def mkType(name: String, desc: Desc, s: String): String = {
        val descText = desc.text
        val mustBeLong = descText.contains("greater than 32 bits") ||
          descText.contains("more than 32 significant bits") ||
          descText.contains("bigger than 2^31") ||
          descText.contains("size in bytes") ||
          name == "user_id" ||
          name == "chat_id" ||
          name == "sender_chat_id" ||
          name == "new_owner_chat_id"
        val isFile = desc.links.exists(_.href == "#sending-files")
        if (isFile)
            "IFile"  
        else {
          val result = if (s == "List[Messages]") "List[Message]" else s
          result
            .replace(" number", "") // Float number => Float
            .replace("True", "Boolean")
            .replace("False", "Boolean")
            .replace("Integer or String", "ChatId")
            .replace("Integer", if (mustBeLong) "Long" else "Int")
            .replace("InputFile or String", "IFile")
            .replace("InputFile", "IFile")
            .replace("InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply", "KeyboardMarkup")
            .replace("InputMediaAudio, InputMediaDocument, InputMediaPhoto and InputMediaVideo", "InputMedia")
        }
      }

      def isH4(e: Element): Boolean = e.tagName.toLowerCase == "h4"
      def isP(e: Element): Boolean = e.tagName.toLowerCase == "p"
      def isTable(e: Element): Boolean = e.tagName.toLowerCase == "table" && !e.attrs("class").contains("table-bordered")
      def isUL(e: Element): Boolean = e.tagName.toLowerCase == "ul"
      def isBQ(e: Element): Boolean = e.tagName.toLowerCase == "blockquote"
      def isDiv(e: Element): Boolean = e.tagName.toLowerCase == "div"

      val fake: Element = JsoupElement(new nodes.Element("section"))

      val apiItems = (rawItems.toList ++ List(fake, fake, fake)).sliding(6, 1)
        .map(_.toList)
        .collect {
          case x1 :: x2 :: x3 :: x4 :: x5 :: x6 :: Nil if isH4(x1) && isP(x2) &&
            isDiv(x3) && isP(x4) && isBQ(x5) && isTable(x6) =>
            Item(name = x1, desc = List(x2), table = x6)
          case x1 :: x2 :: x3 :: x4 :: x5 :: _ if isH4(x1) && isP(x2) && isP(x3) && isP(x4) && isTable(x5) =>
            Item(name = x1, desc = List(x2, x3, x4), table = x5)
          case x1 :: x2 :: x3 :: x4 :: _ if isH4(x1) && isP(x2) && isP(x3) && isTable(x4) =>
            Item(name = x1, desc = List(x2, x3), table = x4)
          case x1 :: x2 :: x3 :: x4 :: x5 :: _ if isH4(x1) && isP(x2) && isBQ(x3) && isP(x4) && isTable(x5) =>
            Item(name = x1, desc = List(x2, x4), table = x5)
          case x1 :: x2 :: x3 :: x4 :: _ if isH4(x1) && isP(x2) && isBQ(x3) && isTable(x4) =>
            Item(name = x1, desc = List(x2), table = x4)
          case x1 :: x2 :: x3 :: _ if isH4(x1) && isP(x2) && isTable(x3) =>
            Item(name = x1, desc = List(x2), table = x3)  
          case x1 :: x2 :: x3 :: _ if isH4(x1) && isP(x2) && isUL(x3) =>
            Sumt(name = x1, desc = x2, items = x3)
          case x1 :: x2 :: x3 :: _ if isH4(x1) && isP(x2) && !x1.text.contains(" ") =>
            Item(name = x1, desc = List(x2), table = x3)
          case x1 :: x2 :: _ if isH4(x1) && isTable(x2) =>
            Item(name = x1, desc = List.empty, table = x2)
        }
        .collect {
          case x: Item if x.name.text.head.isLower =>
            val params = x.table >> elements("tbody > tr")
            val types = x.desc.flatMap(y => y >> elements("a"))
              .filter(_.attr("href").startsWith("#"))
              .map(_.text)
              .filter(_.headOption.exists(_.isUpper))
            val resA = types.find(_ == "Message").orElse(types.headOption)
            val resEm = x.desc.flatMap(y => y >> elements("em"))
              .map(_.text)
              .find(_.head.isUpper)
            val useEither = x.desc.map(_.text).exists(_.contains("otherwise"))
            val res = (resA, resEm) match {
              case (Some(a), Some(em)) if useEither     => s"Either[$em, $a]"
              case (Some(a), Some(em)) if em == "Array" => a
              case (_, Some(em))                        => em
              case (Some(a), None)                      => a
              case _                                    => "CANT_PARSE_RETURN_VALUE"
            }
            val returns = if (x.desc.map(_.text.toLowerCase).intercalate(" ").contains("array of")) {
              s"List[$res]"
            } else {
              res
            }
            Method(
              name = x.name.text,
              desc = x.desc.map(_.text).intercalate("\n"),
              table = params.map { y =>
                val k = y.children.toList(1).text
                val name = y.children.toList.head.text
                val desc = Desc(y.children.toList(3))
                MethodParam(
                  name = name,
                  kind = mkType(name, desc, k),
                  required = y.children.toList(2).text,
                  desc = desc.text,
                )
              }.toList,
              returns = mkType("", Desc.empty, returns)
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
                    (descText.contains(", must be ") || descText.contains(", always ") || itemName == "MessageEntity")
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
                    kind = mkType(name, desc, kind),
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

      gen(apiItems)
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
