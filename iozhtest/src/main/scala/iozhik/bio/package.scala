package iozhik.bio

object ScodecImplicits {

  import scodec._
  import codecs._

  implicit lazy val mammalCodec: Codec[Mammal] = discriminated[Mammal]
    .by(uint32)
    .typecase(1, provide(Elephant))
    .typecase(2, provide(Dolphin))

  implicit lazy val elephantCodec = provide(Elephant)
  implicit lazy val dolphinCodec  = provide(Dolphin)
}

object uPickleImplicits {

  import upickle.default._

  implicit lazy val javaiofileCodec: ReadWriter[java.io.File] = {
    readwriter[upack.Msg].bimap(
      x => upack.Str(x.getName),
      msg => new java.io.File(readBinary[String](msg))
    )
  }

  implicit lazy val mammalCodec: ReadWriter[Mammal] = {
    readwriter[upack.Msg].bimap(
      {
        case Elephant => upack.Obj(upack.Obj().obj += upack.Str("type") -> writeMsg("elephant"))
        case Dolphin  => upack.Obj(upack.Obj().obj += upack.Str("type") -> writeMsg("dolphin"))
      },
      msg => {
        val m = msg.obj
        m.get(upack.Str("type"))
          .collect {
            case upack.Str("elephant") => Elephant
            case upack.Str("dolphin")  => Dolphin
          }
          .get
      }
    )
  }

  implicit lazy val elephantCodec: ReadWriter[Elephant.type] = macroRW
  implicit lazy val dolphinCodec: ReadWriter[Dolphin.type]   = macroRW
}

object CirceImplicits {

  import io.circe.syntax._
  import io.circe.{Encoder, Decoder, Json}
  import io.circe.HCursor

  implicit lazy val mammalEncoder: Encoder[Mammal] = {
    case elephant: Elephant.type =>
      elephant.asJson.mapObject(_.add("type", Json.fromString("elephant")))
    case dolphin: Dolphin.type =>
      dolphin.asJson.mapObject(_.add("type", Json.fromString("dolphin")))
  }
  implicit lazy val mammalDecoder: Decoder[Mammal] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "elephant" => Decoder[Elephant.type]
      case "dolphin"  => Decoder[Dolphin.type]
    }
  } yield value

  implicit lazy val elephantEncoder: Encoder[Elephant.type] = (_: Elephant.type) => ().asJson
  implicit lazy val elephantDecoder: Decoder[Elephant.type] = (_: HCursor) => Right(Elephant)
  implicit lazy val dolphinEncoder: Encoder[Dolphin.type]   = (_: Dolphin.type) => ().asJson
  implicit lazy val dolphinDecoder: Decoder[Dolphin.type]   = (_: HCursor) => Right(Dolphin)
}
