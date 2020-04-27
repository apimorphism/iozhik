package iozhik.mat

object ScodecImplicits {

  import scodec._
  import codecs._
  import codecs.implicits._

  implicit lazy val plasticpropsCodec = {
    ("plasticity" | Codec[Double])
  }.as[PlasticProps]

  implicit lazy val metalpropsCodec = {
    ("conductivity" | Codec[Double])
  }.as[MetalProps]

  implicit lazy val woodpropsCodec = {
    ("colors" | Codec[Vector[String]])
  }.as[WoodProps]

  implicit lazy val materialCodec: Codec[Material] = discriminated[Material]
    .by(uint32)
    .typecase(1, plasticCodec)
    .typecase(2, metalCodec)
    .typecase(3, woodCodec)

  implicit lazy val plasticCodec = {
    ("plasticity" | Codec[Double]) ::
      ("density" | Codec[Double]) ::
      ("color" | Codec[String])
  }.as[Plastic]

  implicit lazy val metalCodec = {
    ("conductivity" | Codec[Double]) ::
      ("density" | Codec[Double]) ::
      ("valency" | Codec[Int]) ::
      ("strength" | Codec[Double])
  }.as[Metal]

  implicit lazy val woodCodec = {
    ("colors" | Codec[Vector[String]]) ::
      ("density" | Codec[Double])
  }.as[Wood]

}

object uPickleImplicits {

  import upickle.default._

  implicit lazy val javaiofileCodec: ReadWriter[java.io.File] = {
    readwriter[upack.Msg].bimap(
      x => upack.Str(x.getName),
      msg => new java.io.File(readBinary[String](msg))
    )
  }

  implicit lazy val plasticpropsCodec: ReadWriter[PlasticProps] = {
    val plasticityKey = upack.Str("plasticity")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          plasticityKey -> writeMsg(x.plasticity)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          plasticity <- m.get(plasticityKey).map(x => readBinary[Double](x))
        } yield {
          PlasticProps(
            plasticity = plasticity
          )
        }
        result.get
      }
    )
  }

  implicit lazy val metalpropsCodec: ReadWriter[MetalProps] = {
    val conductivityKey = upack.Str("conductivity")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          conductivityKey -> writeMsg(x.conductivity)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          conductivity <- m.get(conductivityKey).map(x => readBinary[Double](x))
        } yield {
          MetalProps(
            conductivity = conductivity
          )
        }
        result.get
      }
    )
  }

  implicit lazy val woodpropsCodec: ReadWriter[WoodProps] = {
    val colorsKey = upack.Str("colors")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          colorsKey -> writeMsg(x.colors)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          colors <- m.get(colorsKey).map(x => readBinary[Vector[String]](x))
        } yield {
          WoodProps(
            colors = colors
          )
        }
        result.get
      }
    )
  }

  implicit lazy val materialCodec: ReadWriter[Material] = {
    readwriter[upack.Msg].bimap(
      {
        case x: Plastic => upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("plastic"))
        case x: Metal   => upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("metal"))
        case x: Wood    => upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("wood"))
      },
      msg => {
        val m = msg.obj
        m.get(upack.Str("type"))
          .collect {
            case upack.Str("plastic") => readBinary[Plastic](msg)
            case upack.Str("metal")   => readBinary[Metal](msg)
            case upack.Str("wood")    => readBinary[Wood](msg)
          }
          .get
      }
    )
  }

  implicit lazy val plasticCodec: ReadWriter[Plastic] = {
    val plasticityKey = upack.Str("plasticity")
    val densityKey    = upack.Str("density")
    val colorKey      = upack.Str("color")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          plasticityKey -> writeMsg(x.plasticity),
          densityKey    -> writeMsg(x.density),
          colorKey      -> writeMsg(x.color)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          plasticity <- m.get(plasticityKey).map(x => readBinary[Double](x))
          density    <- m.get(densityKey).map(x => readBinary[Double](x))
          color      <- m.get(colorKey).map(x => readBinary[String](x))
        } yield {
          Plastic(
            plasticity = plasticity,
            density = density,
            color = color
          )
        }
        result.get
      }
    )
  }

  implicit lazy val metalCodec: ReadWriter[Metal] = {
    val conductivityKey = upack.Str("conductivity")
    val densityKey      = upack.Str("density")
    val valencyKey      = upack.Str("valency")
    val strengthKey     = upack.Str("strength")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          conductivityKey -> writeMsg(x.conductivity),
          densityKey      -> writeMsg(x.density),
          valencyKey      -> writeMsg(x.valency),
          strengthKey     -> writeMsg(x.strength)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          conductivity <- m.get(conductivityKey).map(x => readBinary[Double](x))
          density      <- m.get(densityKey).map(x => readBinary[Double](x))
          valency      <- m.get(valencyKey).map(x => readBinary[Int](x))
          strength     <- m.get(strengthKey).map(x => readBinary[Double](x))
        } yield {
          Metal(
            conductivity = conductivity,
            density = density,
            valency = valency,
            strength = strength
          )
        }
        result.get
      }
    )
  }

  implicit lazy val woodCodec: ReadWriter[Wood] = {
    val colorsKey  = upack.Str("colors")
    val densityKey = upack.Str("density")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          colorsKey  -> writeMsg(x.colors),
          densityKey -> writeMsg(x.density)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          colors  <- m.get(colorsKey).map(x => readBinary[Vector[String]](x))
          density <- m.get(densityKey).map(x => readBinary[Double](x))
        } yield {
          Wood(
            colors = colors,
            density = density
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

  implicit lazy val plasticpropsEncoder: Encoder[PlasticProps] =
    (x: PlasticProps) => {
      Json.fromFields(
        List(
          "plasticity" -> x.plasticity.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val plasticpropsDecoder: Decoder[PlasticProps] =
    Decoder.instance { h =>
      for {
        _plasticity <- h.get[Double]("plasticity")
      } yield {
        PlasticProps(plasticity = _plasticity)
      }
    }

  implicit lazy val metalpropsEncoder: Encoder[MetalProps] =
    (x: MetalProps) => {
      Json.fromFields(
        List(
          "conductivity" -> x.conductivity.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val metalpropsDecoder: Decoder[MetalProps] =
    Decoder.instance { h =>
      for {
        _conductivity <- h.get[Double]("conductivity")
      } yield {
        MetalProps(conductivity = _conductivity)
      }
    }

  implicit lazy val woodpropsEncoder: Encoder[WoodProps] =
    (x: WoodProps) => {
      Json.fromFields(
        List(
          "colors" -> x.colors.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val woodpropsDecoder: Decoder[WoodProps] =
    Decoder.instance { h =>
      for {
        _colors <- h.get[Vector[String]]("colors")
      } yield {
        WoodProps(colors = _colors)
      }
    }

  implicit lazy val materialEncoder: Encoder[Material] = {

    case plastic: Plastic => {
      import io.circe.JsonObject
      val lvl0: List[String]                 = List("density", "color")
      val lvl1: List[(String, List[String])] = List(("props", List("plasticity")))
      plastic.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("plastic")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case metal: Metal => {
      import io.circe.JsonObject
      val lvl0: List[String]                 = List("density", "valency")
      val lvl1: List[(String, List[String])] = List(("props", List("strength", "conductivity")))
      metal.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("metal")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

    case wood: Wood => {
      import io.circe.JsonObject
      val lvl0: List[String]                 = List("density")
      val lvl1: List[(String, List[String])] = List(("props", List("colors")))
      wood.asJson.mapObject { o =>
        val map = o.toMap
        Json
          .fromFields(
            lvl1.map {
              case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
            } ++ o.toList.filter(x => lvl0.contains(x._1))
          )
          .mapObject(_.add("type", Json.fromString("wood")))
          .asObject
          .getOrElse(JsonObject.empty)
      }
    }

  }
  implicit lazy val materialDecoder: Decoder[Material] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "plastic" =>
        for {
          density    <- Decoder[Double].prepare(_.downField("density"))
          color      <- Decoder[String].prepare(_.downField("color"))
          plasticity <- Decoder[Double].prepare(_.downField("props").downField("plasticity"))
        } yield {
          Plastic(density = density, color = color, plasticity = plasticity)
        }
      case "metal" =>
        for {
          density      <- Decoder[Double].prepare(_.downField("density"))
          valency      <- Decoder[Int].prepare(_.downField("valency"))
          strength     <- Decoder[Double].prepare(_.downField("props").downField("strength"))
          conductivity <- Decoder[Double].prepare(_.downField("props").downField("conductivity"))
        } yield {
          Metal(density = density,
                valency = valency,
                strength = strength,
                conductivity = conductivity)
        }
      case "wood" =>
        for {
          density <- Decoder[Double].prepare(_.downField("density"))
          colors  <- Decoder[Vector[String]].prepare(_.downField("props").downField("colors"))
        } yield {
          Wood(density = density, colors = colors)
        }
    }
  } yield value

  implicit lazy val plasticEncoder: Encoder[Plastic] =
    (x: Plastic) => {
      Json.fromFields(
        List(
          "plasticity" -> x.plasticity.asJson,
          "density"    -> x.density.asJson,
          "color"      -> x.color.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val plasticDecoder: Decoder[Plastic] =
    Decoder.instance { h =>
      for {
        _plasticity <- h.get[Double]("plasticity")
        _density    <- h.get[Double]("density")
        _color      <- h.get[String]("color")
      } yield {
        Plastic(plasticity = _plasticity, density = _density, color = _color)
      }
    }

  implicit lazy val metalEncoder: Encoder[Metal] =
    (x: Metal) => {
      Json.fromFields(
        List(
          "conductivity" -> x.conductivity.asJson,
          "density"      -> x.density.asJson,
          "valency"      -> x.valency.asJson,
          "strength"     -> x.strength.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val metalDecoder: Decoder[Metal] =
    Decoder.instance { h =>
      for {
        _conductivity <- h.get[Double]("conductivity")
        _density      <- h.get[Double]("density")
        _valency      <- h.get[Int]("valency")
        _strength     <- h.get[Double]("strength")
      } yield {
        Metal(conductivity = _conductivity,
              density = _density,
              valency = _valency,
              strength = _strength)
      }
    }

  implicit lazy val woodEncoder: Encoder[Wood] =
    (x: Wood) => {
      Json.fromFields(
        List(
          "colors"  -> x.colors.asJson,
          "density" -> x.density.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val woodDecoder: Decoder[Wood] =
    Decoder.instance { h =>
      for {
        _colors  <- h.get[Vector[String]]("colors")
        _density <- h.get[Double]("density")
      } yield {
        Wood(colors = _colors, density = _density)
      }
    }

}
