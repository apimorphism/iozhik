package iozhik.pet

object ScodecImplicits {

  import scodec._
  import codecs._
  import codecs.implicits._

  implicit lazy val petCodec: Codec[Pet] = discriminated[Pet]
    .by(uint32)
    .typecase(1, dogCodec)
    .typecase(2, catCodec)

  implicit lazy val dogCodec = {
    ("name" | Codec[String])
  }.as[Dog]

  implicit lazy val catCodec = {
    ("name" | Codec[String])
  }.as[Cat]

}

object uPickleImplicits {

  import upickle.default._

  implicit lazy val javaiofileCodec: ReadWriter[java.io.File] = {
    readwriter[upack.Msg].bimap(
      x => upack.Str(x.getName),
      msg => new java.io.File(readBinary[String](msg))
    )
  }

  implicit lazy val petCodec: ReadWriter[Pet] = {
    readwriter[upack.Msg].bimap(
      {
        case x: Dog => upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("dog"))
        case x: Cat => upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("cat"))
      },
      msg => {
        val m = msg.obj
        m.get(upack.Str("type"))
          .collect {
            case upack.Str("dog") => readBinary[Dog](msg)
            case upack.Str("cat") => readBinary[Cat](msg)
          }
          .get
      }
    )
  }

  implicit lazy val dogCodec: ReadWriter[Dog] = {
    val nameKey = upack.Str("name")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          nameKey -> writeMsg(x.name)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          name <- m.get(nameKey).map(x => readBinary[String](x))
        } yield {
          Dog(
            name = name
          )
        }
        result.get
      }
    )
  }

  implicit lazy val catCodec: ReadWriter[Cat] = {
    val nameKey = upack.Str("name")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          nameKey -> writeMsg(x.name)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          name <- m.get(nameKey).map(x => readBinary[String](x))
        } yield {
          Cat(
            name = name
          )
        }
        result.get
      }
    )
  }

}

object CirceImplicits {

  import io.circe.syntax._
  import io.circe.{Encoder, Decoder, Json}
  import iozhik._

  implicit lazy val petEncoder: Encoder[Pet] = {
    case cat: Cat => cat.asJson.mapObject(_.add("type", Json.fromString("cat")))
    case dog: Dog => dog.asJson.mapObject(_.add("type", Json.fromString("dog")))
  }

  implicit lazy val petDecoder: Decoder[Pet] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "cat"   => Decoder[Cat]
      case "dog"   => Decoder[Dog]
      case unknown => throw DecodingError(s"Unknown type for Pet: $unknown")
    }
  } yield value

  implicit lazy val dogEncoder: Encoder[Dog] =
    (x: Dog) => {
      Json.fromFields(
        List(
          "name" -> x.name.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val dogDecoder: Decoder[Dog] =
    Decoder.instance { h =>
      for {
        _name <- h.get[String]("name")
      } yield {
        Dog(name = _name)
      }
    }

  implicit lazy val catEncoder: Encoder[Cat] =
    (x: Cat) => {
      Json.fromFields(
        List(
          "name" -> x.name.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val catDecoder: Decoder[Cat] =
    Decoder.instance { h =>
      for {
        _name <- h.get[String]("name")
      } yield {
        Cat(name = _name)
      }
    }

}
