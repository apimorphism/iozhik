package iozhik

import org.scalatest.{FlatSpec, Matchers}

trait Fig
case object Goo extends Fig
case class Boo(name: String) extends Fig
case class Foo(name: String) extends Fig

case class Babulka(name: String, age: Int, pensia: Option[Double])

object uPickleImplicits {

  import upickle.default._

  implicit val figCodec: ReadWriter[Fig] = {
    readwriter[upack.Msg].bimap(
      {
        case Goo => upack.Obj(upack.Obj().obj += upack.Str("type") -> writeMsg("boo"))
        case x: Boo => upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("boo"))
        case x: Foo => upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("foo"))
      },
      msg => {
        val m = msg.obj
        m.get(upack.Str("type")).collect {
          case upack.Str("boo") => readBinary[Boo](msg)
          case upack.Str("foo") => readBinary[Foo](msg)
          case upack.Str("goo") => Goo
        }.get
      }
    )
  }

  implicit val fooCodec: ReadWriter[Foo] = {
    val nameKey = upack.Str("name")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          nameKey   -> writeMsg(x.name),
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          name   <- m.get(nameKey).map(x => readBinary[String](x))
        } yield {
          Foo(
            name = name,
          )
        }
        result.get
      }
    )
  }

  implicit val booCodec: ReadWriter[Boo] = {
    val nameKey = upack.Str("name")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          nameKey   -> writeMsg(x.name),
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          name   <- m.get(nameKey).map(x => readBinary[String](x))
        } yield {
          Boo(
            name = name,
          )
        }
        result.get
      }
    )
  }

  implicit val babulkaCodec: ReadWriter[Babulka] = {
    val nameKey = upack.Str("name")
    val ageKey = upack.Str("age")
    val pensiaKey = upack.Str("pensia")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          nameKey   -> writeMsg(x.name),
          ageKey    -> writeMsg(x.age),
          pensiaKey -> writeMsg(x.pensia),
        )
      },
      msg => {
        val m = msg.obj
        val babulka = for {
          name   <- m.get(nameKey).map(x => readBinary[String](x))
          age    <- m.get(ageKey).map(x => readBinary[Int](x))
          pensia <- m.get(pensiaKey).map(x => readBinary[Option[Double]](x))
        } yield {
          Babulka(
            name = name,
            age = age,
            pensia = pensia
          )
        }
        babulka.get
      }
    )
  }

}

class BinCodecExperiments extends FlatSpec with Matchers with Model {

  import upickle.default._
  import uPickleImplicits._

  "Codec" must "encode/decode ADTs" in {
    val foo = Foo("foo")
    val bin = writeBinary(foo: Fig)
    val nib = readBinary[Fig](bin)
    println(nib)
  }

}
