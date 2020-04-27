package iozhik

import org.scalatest.{FlatSpec, Matchers}
import org.http4s.client.blaze._
import org.http4s.client.middleware.Logger
import cats.effect.concurrent.Ref
import cats.Monad
import cats.effect.{Async, Sync, Timer}
import telegramium.bots._
import telegramium.bots.client._

import scala.concurrent.duration._


trait ApiServer[F[_]] {

  implicit def syncF: Sync[F]

  import cats.syntax.functor._
  import cats.syntax.flatMap._
  import io.circe.syntax._
  import org.http4s.circe._
  import telegramium.bots.client.CirceImplicits._

  def handleGetChat(req: GetChatReq): F[GetChatRes] = ???

  implicit val getChatReqDecoder = jsonOf[F, GetChatReq]

  def handleGetChatReq(rawReq: org.http4s.Request[F]): F[org.http4s.Response[F]] = {
    for {
      req <- rawReq.as[GetChatReq]
      res <- handleGetChat(req)
    } yield {
      org.http4s.Response()
        .withEntity(res.asJson)
    }
  }

}

class EchoBot[F[_]: Monad: Async](bot: Api[F])(implicit timer: Timer[F]) {

  import cats.syntax.apply._
  import cats.syntax.flatMap._
  import cats.syntax.functor._
  import cats.syntax.foldable._
  import cats.syntax.traverse._
  import cats.instances.list._
  import cats.instances.option._
  import cats.instances.int._

  def loop(counter: Ref[F, Int]): F[Unit] = {
    for {
      count <- counter.get
      _ <- Timer[F].sleep(50.milliseconds)
      updates <- bot.getUpdates(GetUpdatesReq(offset = Some(count)))
      _ <- updates.result.map(_.updateId).maximumOption.traverse { max =>
        counter.set(max + 1)
      }
      _ = updates.result.foreach(println)
      _ <- updates.result.flatMap(_.message).traverse{ m =>
        bot.sendPhoto(SendPhotoReq(
          chatId = ChatIntId(m.chat.id),
          photo = InputLinkFile(file = "https://picsum.photos/200/300"),
          caption = Some("Random photo"),
        )) *> bot.sendPhoto(
          SendPhotoReq(
            chatId = ChatIntId(m.chat.id),
            photo = InputPartFile(file = new java.io.File("/tmp/tg.jpg")),
            caption = Some("Nature")
          )
        ) *> bot.sendMessage(SendMessageReq(
          chatId = ChatIntId(m.chat.id),
          text = m.text.getOrElse("NO_TEXT"),
          replyMarkup = Some(MarkupInlineKeyboard(InlineKeyboardMarkup(
            inlineKeyboard = List(
              List(
                InlineKeyboardButton(
                  text = "Press ME",
                  callbackData = Some("boo")
                )
              )
            )
          )))
        ))
      }
      next <- loop(counter)
    } yield {
      next
    }
  }

  def start(): F[Unit] = {
    for {
      counter <- Ref.of[F, Int](0)
      next <- loop(counter)
    } yield {
      next
    }
  }

}

class Http4sSpec extends FlatSpec with Matchers with Model {

  import cats.effect.IO
  import cats.effect.ContextShift

  "Client" must "work" in {
    import scala.concurrent.ExecutionContext.Implicits.global
    implicit val timer: Timer[IO] = IO.timer(global)
    implicit val cs: ContextShift[IO] = IO.contextShift(global)
    val baseUrl = s"https://api.telegram.org/bot$token"
    BlazeClientBuilder[IO](global).resource.use { httpClient =>
      val http = Logger(logBody = true, logHeaders = true)(httpClient)
      val bot = new ApiHttp4sImp(http, baseUrl = baseUrl)
      val echoBot = new EchoBot(bot)
      echoBot.start()
    }.unsafeRunSync()
  }

}
