import java.nio.file.{Path, Paths}
import java.util.StringTokenizer

import cats.effect._
import cats.instances.int._
import cats.instances.list._
import cats.instances.string._
import cats.syntax.foldable._
import cats.syntax.functor._
import fs2.Stream
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element

import scala.concurrent.ExecutionContext

object VkApiScrapper extends IOApp {

  case class EntityParam(name: String, kind: String, desc: String)
  case class EntityParamTag(name: String, value: String)
  case class MethodParam(name: String, kind: String, required: String, desc: String)

  sealed trait ApiItem
  case class Entity(name: String = "", desc: String = "", table: List[EntityParam] = List.empty) extends ApiItem
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
    val leaves = items.collect{ case e: Sumtyp => e.items }.flatten.toSet
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
    val body = sumTypes.intercalate("") +
      emap.filterKeys(k => !leaves.contains(k)).values.toList.intercalate("")
    val entities = body.split("\n").map("  " + _).toList.intercalate("\n")
    val methods = mmap.values.toList.flatMap(_.split("\n")).map("      " + _).intercalate("\n")
    s"""
       |namespace iozhik {
       |  namespace vk(snake circe http4s upack) {
       |
       |    enum IFile {
       |      wrap InputPartFile (file) = java.io.File
       |      wrap InputLinkFile (file) = String
       |    }
       |    $entities
       |    namespace client {
       |      Response {
       |        ok          : Boolean
       |        description : Option[String]
       |      }
       |      Api {
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

      def trKind(k: String): String = {
        k match {
          case "string"                     => "String"
          case "boolean"                    => "Boolean"
          case "positive number"            => "Integer"
          case "int (number)"               => "Integer"
          case "array(integer)"             => "List[Integer]"
          case x if x.startsWith("integer") => "Integer"
          case x                            => x
        }
      }

      def getEntity(url: String, e: Entity): Entity = {
        println(s"Retrieving $url")
        val doc = browser.get("https://vk.com" + url)
        val items = (doc >> elements(".dev_page_block>*")).toList

        def skipFields(t: String): Boolean = {
          t.contains("API versions") || t.contains("For versions")
        }

        val content = items.collect {
            case x if
              (x.hasAttr("role") && x.attr("role") == "heading") ||
              x.tagName.toLowerCase == "table" => x
          }
          .foldLeft(("", List.empty[Element])) { case ((h, tlist), x) =>
            if (x.tagName.toLowerCase == "table") {
              if (!skipFields(h)) {
                ("", tlist :+ x)
              } else {
                ("", tlist)
              }
            } else {
              (h ++ x.text, tlist)
            }
          }
          ._2

        val fields = content.flatMap(_ >> elements("tr")).map { tr =>
          val fname = (tr >> elements("td:first-child>b")).map(_.text).mkString
          val ftype = (tr >> elements("td>span.wk_gray")).map(_.text).mkString
          val fdesc = (tr >> elements("td:last-child")).map(_.text).mkString
          (fname, ftype, fdesc)
        }
        val next = (doc >> elements("#dev_page_cont>.dev_page_block:last-child>b:last-child>a"))
                    .map(_.attr("href")).headOption
        val newE = e.copy(
          table = e.table ++ fields.map { case (fname, ftype, fdesc) =>
            EntityParam(
              name = fname,
              kind = trKind(ftype),
              desc = fdesc,
            )
          }
        )
        if (next.isEmpty) {
          newE
        } else {
          getEntity(next.get, newE)
        }
      }

      val doc = browser.get("https://vk.com/dev/objects")
      val rawItems = doc >> elements("td>b>a")

      val apiItems = rawItems.map(_.attr("href"))
        .map{ x => (x, x.split("/").last) }
        .map{ case (url, name) => getEntity(url, Entity(name = camelify(name).capitalize)) }
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
        val dst = Paths.get(iozhDir + "/iozhtest/src/main/resources/vk.api")
        dst.toFile.delete()
        write(content, dst).as(ExitCode.Success)
      }
    }

}