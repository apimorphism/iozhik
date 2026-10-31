package telegramium.bots

import io.circe.{Decoder, Encoder}
import io.circe.syntax._
import org.scalacheck.Gen
import org.scalatest.funsuite.AnyFunSuite

// PROTOTYPE round-trip probe (scratch only)
class GensRoundTripSpec extends AnyFunSuite {
  GenPrims.allowUnknown = false
  import CirceImplicits._

  private def diff(a: io.circe.Json, b: io.circe.Json, path: String): String =
    (a.asObject, b.asObject) match {
      case (Some(x), Some(y)) =>
        (x.keys.toSet ++ y.keys.toSet).toList.sorted.iterator.map(k => (k, x(k), y(k))).collectFirst {
          case (k, Some(l), Some(r)) if l != r => diff(l, r, path + "." + k)
          case (k, l, r) if l.isEmpty || r.isEmpty => s"$path.$k: ${l.map(_.noSpaces.take(60))} vs ${r.map(_.noSpaces.take(60))}"
        }.getOrElse("?")
      case _ =>
        (a.asArray, b.asArray) match {
          case (Some(x), Some(y)) if x.size == y.size => x.zip(y).zipWithIndex.collectFirst { case ((l, r), i) if l != r => diff(l, r, s"$path[$i]") }.getOrElse("?")
          case _ => s"$path: ${a.noSpaces.take(60)} vs ${b.noSpaces.take(60)}"
        }
    }

  private def pdiff(a: Any, b: Any): String = (a, b) match {
    case (x: Product, y: Product) if x.productPrefix == y.productPrefix && x.productArity == y.productArity =>
      x.productIterator.zip(y.productIterator).collectFirst { case (l, r) if l != r => x.productPrefix + "." + pdiff(l, r) }.getOrElse("?")
    case (x: Iterable[_], y: Iterable[_]) if x.size == y.size =>
      x.zip(y).collectFirst { case (l, r) if l != r => pdiff(l, r) }.getOrElse("?")
    case _ => s"${a.toString.take(50)} (${a.getClass.getSimpleName}) vs ${b.toString.take(50)} (${b.getClass.getSimpleName})"
  }

  private def probe[A](name: String, g: Gen[A])(implicit e: Encoder[A], d: Decoder[A]): Unit = test(s"round trip $name") {
    var failures = List.empty[String]
    for (_ <- 1 to 300) {
      val a = Gen.resize(GenPrims.Budget, g).sample.get
      val json = a.asJson
      d.decodeJson(json) match {
        case Left(err) => failures ::= s"decode error: $err | at ${err.history.reverse.map{case io.circe.CursorOp.DownField(k) => "."+k; case o => o.toString}.mkString} -> ${io.circe.HCursor.fromJson(json).replay(err.history).focus.map(_.noSpaces.take(300))}"
        case Right(b) if b != a => failures ::= s"mismatch at ${diff(json, b.asJson, "")} / ${pdiff(a, b)}"
        case _ =>
      }
    }
    println(s"$name: ${failures.size}/300 failed; distinct kinds: ${failures.map(_.take(90)).distinct.take(4).mkString(" | ")}")
    failures.headOption.foreach(f => println("  first: " + f.take(700)))
  }

  probe("User", GenUser.gen)
  probe("Message", GenMessage.gen)
  probe("Update", GenUpdate.gen)
  probe("Chat", GenChat.gen)
  probe("ChatFullInfo", GenChatFullInfo.gen)
  probe("InlineKeyboardMarkup", GenInlineKeyboardMarkup.gen)
}
