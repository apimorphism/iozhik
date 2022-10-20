package iozhik.vk.groups

object CirceImplicits {

  import io.circe.syntax._
  import io.circe.{Encoder, Decoder, Json}
  import iozhik._

  implicit lazy val getreqEncoder: Encoder[GetReq] =
    (x: GetReq) => {
      Json.fromFields(
        List(
          "access_token" -> x.accessToken.asJson,
          "v"            -> x.v.asJson,
          "user_id"      -> x.userId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getreqDecoder: Decoder[GetReq] =
    Decoder.instance { h =>
      for {
        _accessToken <- h.get[String]("access_token")
        _v           <- h.get[String]("v")
        _userId      <- h.get[Int]("user_id")
      } yield {
        GetReq(accessToken = _accessToken, v = _v, userId = _userId)
      }
    }

  implicit lazy val getresEncoder: Encoder[GetRes] =
    (x: GetRes) => {
      Json.fromFields(
        List(
          "key"    -> x.key.asJson,
          "server" -> x.server.asJson,
          "ts"     -> x.ts.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getresDecoder: Decoder[GetRes] =
    Decoder.instance { h =>
      for {
        _key    <- h.get[String]("key")
        _server <- h.get[String]("server")
        _ts     <- h.get[String]("ts")
      } yield {
        GetRes(key = _key, server = _server, ts = _ts)
      }
    }

  implicit lazy val getlongpollserverreqEncoder: Encoder[GetLongPollServerReq] =
    (x: GetLongPollServerReq) => {
      Json.fromFields(
        List(
          "access_token" -> x.accessToken.asJson,
          "v"            -> x.v.asJson,
          "group_id"     -> x.groupId.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getlongpollserverreqDecoder: Decoder[GetLongPollServerReq] =
    Decoder.instance { h =>
      for {
        _accessToken <- h.get[String]("access_token")
        _v           <- h.get[String]("v")
        _groupId     <- h.get[Int]("group_id")
      } yield {
        GetLongPollServerReq(accessToken = _accessToken, v = _v, groupId = _groupId)
      }
    }

  implicit lazy val getlongpollserverresEncoder: Encoder[GetLongPollServerRes] =
    (x: GetLongPollServerRes) => {
      Json.fromFields(
        List(
          "key"    -> x.key.asJson,
          "server" -> x.server.asJson,
          "ts"     -> x.ts.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val getlongpollserverresDecoder: Decoder[GetLongPollServerRes] =
    Decoder.instance { h =>
      for {
        _key    <- h.get[String]("key")
        _server <- h.get[String]("server")
        _ts     <- h.get[String]("ts")
      } yield {
        GetLongPollServerRes(key = _key, server = _server, ts = _ts)
      }
    }

}
