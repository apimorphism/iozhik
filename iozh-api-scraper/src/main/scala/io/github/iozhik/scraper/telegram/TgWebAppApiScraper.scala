package io.github.iozhik.scraper.telegram

import cats.effect.ExitCode
import cats.effect.IO
import cats.effect.IOApp
import cats.syntax.all._
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element

import java.nio.file.Paths

object TgWebAppApiScraper extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = 
    IO {
      val browser = JsoupBrowser.typed()
      browser.get("https://core.telegram.org/bots/webapps")
    }
      .map { doc =>
        val rawItems = doc >> elements("div#dev_page_content > *")
        val htmlItems = extractItemsFromHtmlSequences(rawItems.toList)
        val apiItems = htmlItemsToApiItems(htmlItems)
        gen(apiItems)  
      }
      .flatMap { content =>
        IO {
          Option(System.getenv("IOZHDIR"))
        }.flatMap { iozhDir =>
          iozhDir.fold(
            IO.raiseError[String](new RuntimeException(
              "Please set IOZHDIR environment variable to the absolute path of the project"
            ))
          )(x => IO(x))
        }.flatMap { iozhDir =>
          val dst = Paths.get(iozhDir + "/iozhtest/src/main/resources/tgwebapp.api")
          dst.toFile.delete()
          write[IO](content, dst).as(ExitCode.Success)
        }
      }

  private def extractItemsFromHtmlSequences(elements: List[Element]): List[Item] =
    elements
      .sliding(4)
      .map(_.toList)
      .collect {
        case H4(x1) :: P(x2) :: Table(x3) :: _ if x1.text != "Events Available for Mini Apps" => Item(name = x1, desc = List(x2), table = x3)
      }
      .toList

  private def htmlItemsToApiItems(items: List[Item]): List[ApiItem] = 
    items
      .collect {
        case x: Item if x.name.text.head.isUpper =>
          val params = x.table >> elements("tbody > tr")
          val itemName = x.name.text
          Entity(
            name = itemName,
            desc = x.desc.map(_.text).intercalate("\n"),
            table = params.map { p =>
              val name = p.children.toList.head.text
              val kind = p.children.toList(1).text
              val desc = p.children.toList(2).text
              EntityParam(name = name, kind = mkType(name, desc, kind), desc = desc)
            }
            .filterNot(_.kind == "Function")
            .toList
          )
      }

  private def gen(items: List[ApiItem]): String = {
    val entityMap = (items.collect {
        case e: Entity => e
      })
      .map { entity =>
        val fields = genFields(entity.table)
        val body = s"""
          |  /* ${wrap(entity.desc, 80)} */
          |  ${entity.name} {
          |$fields
          |  }
        """.stripMargin
        entity.name -> body
      }
      .toMap
    val body = entityMap.toList.sortBy(_._1).map(_._2).intercalate("")
    val entities = body.split("\n").map("  " + _).toList.intercalate("\n")

    s"""namespace telegramium {
      |  namespace webapps(snake circe http4s) {
      |    $entities
      |  }
      |}
    """.stripMargin
  }    

  
  private object H4 {
    def unapply(e: Element): Option[Element] = 
      if (e.tagName.toLowerCase == "h4") Some(e) else None
  }

  private object P {
    def unapply(e: Element): Option[Element] = 
      if (e.tagName.toLowerCase == "p") Some(e) else None
  }

  private object Table {
    def unapply(e: Element): Option[Element] = 
      if (e.tagName.toLowerCase == "table" && !e.attrs("class").contains("table-bordered")) Some(e) else None
  } 
}
