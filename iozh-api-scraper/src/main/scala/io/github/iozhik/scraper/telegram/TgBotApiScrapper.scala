package io.github.iozhik.scraper.telegram

import cats.effect._
import cats.syntax.all._
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.browser.JsoupBrowser.JsoupElement
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element
import org.jsoup.nodes

import java.nio.file.Paths

object TgBotApiScrapper extends IOApp {
  private val quotedPattern = "(?<=“).*?(?=”)".r
  private val forXOnlyPattern = "(?<=For “).*?(?=” only)".r
  private val messageEntityDescPattern = "Optional. For “.*” only, ".r
  private val discriminatorPattern = ".*, always “(.*?)”".r

  def gen(items: List[ApiItem]): String = {
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
        val children = emap.view.filterKeys{ k =>
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
    val body = sumTypes.intercalate("") + emap.view.filterKeys(k => !leaves.contains(k)).toList.sortBy(_._1).map(_._2).intercalate("")
    val entities = body.split("\n").map("  " + _).toList.intercalate("\n")
    val methods = mmap.toList.sortBy(_._1).map(_._2).flatMap(_.split("\n")).map("      " + _).intercalate("\n")
    val markups = emap.view.filterKeys(inlines.contains).values.toList
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
       |      Response {
       |        ok          : Boolean
       |        description : Option[String]
       |      }
       |
       |      Methods (methodsFactory) {
       |        $methods
       |      }
       |    }
       |  }
       |}
     """.stripMargin
  }

  override def run(args: List[String]): IO[ExitCode] =
    IO {
      val browser = JsoupBrowser.typed()
      val doc = browser.get("https://core.telegram.org/bots/api")
      doc.underlying.select("img.emoji").forEach(e => e.replaceWith(new nodes.TextNode(e.attr("alt"))))
      val rawItems = doc >> elements("div#dev_page_content > *")

      case class Item(name: Element, desc: List[Element], table: Element)
      case class Sumt(name: Element, desc: Element, items: Element)

      def fixDesc(desc: String): String = {
        desc
          .replace("@", "&#064;")
          .replace("^", "&#94;")
      }

      def removeMoreLinks(desc: Element) = {
        val moreLinks =
          (desc >> elements("a"))
            .map(_.text)
            .filter(_.contains("»"))
        moreLinks.foldLeft(desc.text) { (txt, link) =>
          txt.replaceAll(s"$link\\.?", "")
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
                val desc = removeMoreLinks(y.children.toList(3))
                MethodParam(
                  name = name,
                  kind = mkType(name, desc, k),
                  required = y.children.toList(2).text,
                  desc = fixDesc(desc),
                )
              }.toList,
              returns = mkType("", "", returns)
            )
          case x: Item if x.name.text.head.isUpper =>
            val params = x.table >> elements("tbody > tr")
            val itemName = x.name.text
            Entity(
              name = itemName,
              desc = x.desc.map(removeMoreLinks).intercalate("\n"),
              table = params.map { y =>
                val name = y.children.toList.head.text
                val kind = y.children.toList(1).text
                val desc = removeMoreLinks(y.children.toList(2))
                if (
                  (name == "type" || name == "source" || name == "status") && 
                    (desc.contains(", must be ") || desc.contains(", always ") || itemName == "MessageEntity")
                ) {
                  val cleanedDesc = desc.replaceAll(".*must be ", "")
                  val typeDesc = discriminatorPattern.findFirstMatchIn(cleanedDesc).map(_.group(1)).getOrElse(cleanedDesc)
                  EntityParam(
                    name = name,
                    kind = "__type_tag__",
                    desc = typeDesc
                  )
                } else {
                  EntityParam(
                    name = name,
                    kind = mkType(name, desc, kind),
                    desc = fixDesc(desc),
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
