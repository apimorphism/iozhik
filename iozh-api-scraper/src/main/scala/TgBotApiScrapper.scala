import java.nio.file.{Path, Paths}
import java.util.StringTokenizer

import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Element
import cats.effect._
import cats.instances.list._
import cats.syntax.functor._
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
  case class Sumtyp(name: String, desc: String, items: List[String]) extends ApiItem

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

  def gen(items: List[ApiItem]): String = {
    def trarr(s: String): String = {
      if (s.startsWith("Array of ")) {
        val body = trarr(s.drop("Array of ".length))
        s"""List[$body]"""
      } else {
        s
      }
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
            } else if (f.name == "parse_mode") {
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
              "    type : \"" + f.desc + ":__type_id_placeholder__\""
            } else {
              s"    /* ${f.desc}*/\n    ${camelify(f.name).padTo(maxLen, ' ')} : $kind"
            }
          }
          .intercalate("\n")
        val body =
          s"""
            |  /* ${m.desc} */
            |  def ${m.name} {
            |$fields
            |  } => {
            |    use Response
            |    result: ${m.returns}
            |  }
          """.stripMargin
        m.name -> body
      }.toMap
    val emap = items.collect{
        case e: Entity => e
      }
      .map { e =>
        val maxLen = e.table.map(x => camelify(x.name).length).maximumOption.getOrElse(0)
        val fields = e.table
          .map { f =>
            val kind = if (f.kind.startsWith("Array of")) {
              trarr(f.kind)
            } else if (f.name == "parse_mode") {
              "Option[ParseMode]"
            } else {
              if (f.desc.contains("Optional")) {
                s"Option[${f.kind}]"
              } else {
                f.kind
              }
            }
            if (f.kind == "__type_tag__") {
              "    type : \"" + f.desc + ":__type_id_placeholder__\""
            } else {
              s"    /* ${f.desc}*/\n    ${camelify(f.name).padTo(maxLen, ' ')} : $kind"
            }
          }
          .intercalate("\n")
        val body = s"""
           |  /* ${e.desc} */
           |  ${e.name} {
           |$fields
           |  }
         """.stripMargin
        e.name -> body
      }.toMap
    val inlines = Set("InlineKeyboardMarkup", "ReplyKeyboardMarkup", "ReplyKeyboardRemove", "ForceReply")
    val leaves = items.collect{ case e: Sumtyp => e.items }.flatten.toSet ++ inlines
    val sumTypes = items.collect{ case e: Sumtyp => e }
      .map{ e =>
        val children = emap.filterKeys{ k =>
            e.items.contains(k)
          }
          .values
          .toList
          .zipWithIndex
          .map { case (b, idx) => b.replace("__type_id_placeholder__", idx.toString) }
          .intercalate("")
        s"""
          |  enum ${e.name} {
          |${children.split("\n").map(x => "  " + x).toList.intercalate("\n")}
          |  }
        """.stripMargin
      }
    val body = sumTypes.intercalate("") + emap.filterKeys(k => !leaves.contains(k)).values.toList.intercalate("")
    val entities = body.split("\n").map("  " + _).toList.intercalate("\n")
    val methods = mmap.values.toList.flatMap(_.split("\n")).map("      " + _).intercalate("\n")
    val markups = emap.filterKeys(inlines.contains).values.toList
      .flatMap(_.split("\n")).map("    " + _).intercalate("\n")
    s"""
       |namespace telegramium {
       |  namespace bots(snake circe http4s upack) {
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
       |    WebhookService (server) {
       |      def handleUpdate {
       |        use Update
       |      } => {
       |        result: String
       |      }
       |    }
       |
       |    namespace client {
       |      Response {
       |        ok          : Boolean
       |        description : Option[String]
       |      }
       |
       |      Api (client) {
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

  def write[F[_]: ContextShift : Sync](content: String, file: Path): F[Unit] = {
    Stream.resource(mkPool)
      .flatMap { implicit ec =>
        Stream
          .emit(content)
          .through(fs2.text.utf8Encode)
          .to(fs2.io.file.writeAll(file, ec))
      }
      .compile
      .drain
  }

  override def run(args: List[String]): IO[ExitCode] =
    IO {
      val browser = JsoupBrowser()
      val doc = browser.get("https://core.telegram.org/bots/api")
      val rawItems = doc >> elements("div#dev_page_content > *")

      case class Item(name: Element, desc: List[Element], table: Element)
      case class Sumt(name: Element, desc: Element, items: Element)

      def mkType(desc: String, s: String): String = {
        val mustBeLong = desc.contains("greater than 32 bits")
        s
          .replace(" number", "") // Float number => Float
          .replace("True", "Boolean")
          .replace("False", "Boolean")
          .replace("Integer or String", "ChatId")
          .replace("Integer", if (mustBeLong) "Long" else "Int")
          .replace("InputFile or String", "IFile")
          .replace("InputFile", "IFile")
          .replace("CallbackGame", "String")
          .replace("Messages", "Message")
          .replace("InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply", "KeyboardMarkup")
          .replace("InputMediaPhoto and InputMediaVideo", "InputMedia")
      }

      def isH4(e: Element): Boolean = e.tagName.toLowerCase == "h4"
      def isP(e: Element): Boolean = e.tagName.toLowerCase == "p"
      def isTable(e: Element): Boolean = e.tagName.toLowerCase == "table"
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
          case x1 :: x2 :: x3 :: x4 :: _ if isH4(x1) && isP(x2) && isBQ(x3) && isTable(x4) =>
            Item(name = x1, desc = List(x2), table = x4)
          case x1 :: x2 :: x3 :: _ if isH4(x1) && isP(x2) && isTable(x3) =>
            Item(name = x1, desc = List(x2), table = x3)
          case x1 :: x2 :: x3 :: _ if isH4(x1) && isP(x2) && isUL(x3) =>
            Sumt(name = x1, desc = x2, items = x3)
          case x1 :: x2 :: x3 :: _ if isH4(x1) && isP(x2) && !x1.text.contains(" ") =>
            Item(name = x1, desc = List(x2), table = x3)
        }
        .collect {
          case x: Item if x.name.text.head.isLower =>
            val params = x.table >> elements("tbody > tr")
            val resA = x.desc.flatMap(y => y >> elements("a"))
              .map(_.text)
              .find(_.head.isUpper)
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
              desc = wrap(x.desc.map(_.text).intercalate("\n"), 80),
              table = params.map { y =>
                val k = y.children.toList(1).text
                val name = y.children.toList(0).text
                val desc = y.children.toList(3).text
                MethodParam(
                  name = name,
                  kind = mkType(desc, k),
                  required = y.children.toList(2).text,
                  desc = wrap(desc, 60),
                )
              }.toList,
              returns = mkType("", returns)
            )
          case x: Item if x.name.text.head.isUpper =>
            val params = x.table >> elements("tbody > tr")
            Entity(
              name = x.name.text,
              desc = wrap(x.desc.map(_.text).intercalate("\n"), 80),
              table = params.map { y =>
                val name = y.children.toList(0).text
                val kind = y.children.toList(1).text
                val desc = y.children.toList(2).text
                if ((name == "type" || name == "source") && desc.contains(", must be ")) {
                  EntityParam(
                    name = name,
                    kind = "__type_tag__",
                    desc = desc.replaceAll(".*must be ", "")
                  )
                } else {
                  EntityParam(
                    name = name,
                    kind = mkType(desc, kind),
                    desc = wrap(desc, 60),
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
        write(content, dst).as(ExitCode.Success)
      }
    }

}
