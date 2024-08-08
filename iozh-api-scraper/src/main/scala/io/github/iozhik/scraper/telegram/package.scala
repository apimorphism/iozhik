package io.github.iozhik.scraper

import cats.effect._
import cats.syntax.all._
import fs2.Stream
import fs2.io.file.Files

import java.nio.file.Path
import java.util.StringTokenizer
import scala.concurrent.ExecutionContext

package object telegram {
  def mkType(name: String, desc: String, s: String): String = {
    val mustBeLong = desc.contains("greater than 32 bits") ||
      desc.contains("more than 32 significant bits") ||
      desc.contains("bigger than 2^31") ||
      desc.contains("size in bytes") ||
      name == "user_id" ||
      name == "chat_id" ||
      name == "sender_chat_id"
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

  def wrap(text: String, LineWidth: Int): String = {
    val buf = new StringBuilder()
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
    if (name == null) "" else loop(name.toList).mkString
  }

  def isParseMode(name: String) = name == "parse_mode" || name == "quote_parse_mode" || name == "explanation_parse_mode"

  def trarr(s: String): String = {
    if (s.startsWith("Array of ")) {
      val body = trarr(s.drop("Array of ".length))
      s"""List[$body]"""
    } else {
      s
    }
  }

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

  private def mkPool[F[_]](implicit S: Sync[F]): Resource[F, ExecutionContext] = {
    Resource(S.delay {
      val executor = ExecutionContext.fromExecutor((command: Runnable) => command.run())
      val ec = ExecutionContext.fromExecutor(executor)
      (ec, S.delay(()))
    })
  }
  
  def write[F[_]: Async: Files](content: String, file: Path): F[Unit] = {
    Stream.resource(mkPool)
      .flatMap { _ =>
        Stream
          .emit(content)
          .through(fs2.text.utf8.encode)
          .through(Files[F].writeAll(fs2.io.file.Path.fromNioPath(file)))
      }
      .compile
      .drain
  }
}
