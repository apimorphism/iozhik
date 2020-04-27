package iozhik.fig

object ScodecImplicits {

  import scodec._
  import codecs._
  import codecs.implicits._

  implicit lazy val propsCodec = {
    ("area" | Codec[Double]) ::
      ("perimeter" | Codec[Double])
  }.as[Props]

  implicit lazy val pointCodec = {
    ("x" | Codec[Int]) ::
      ("y" | Codec[Int])
  }.as[Point]

  implicit lazy val figureCodec: Codec[Figure] = discriminated[Figure]
    .by(uint32)
    .typecase(1, squareCodec)
    .typecase(2, circleCodec)
    .typecase(3, triangleCodec)

  implicit lazy val squareCodec = {
    ("area" | Codec[Double]) ::
      ("perimeter" | Codec[Double]) ::
      ("sides" | Codec[Int]) ::
      ("width" | Codec[Int]) ::
      ("height" | Codec[Int])
  }.as[Square]

  implicit lazy val circleCodec = {
    ("area" | Codec[Double]) ::
      ("perimeter" | Codec[Double]) ::
      ("sides" | Codec[Int]) ::
      ("radius" | Codec[Int])
  }.as[Circle]

  implicit lazy val triangleCodec = {
    ("area" | Codec[Double]) ::
      ("perimeter" | Codec[Double]) ::
      ("sides" | Codec[Int]) ::
      ("vertices" | Codec[List[Point]])
  }.as[Triangle]

}

object uPickleImplicits {

  import upickle.default._

  implicit lazy val javaiofileCodec: ReadWriter[java.io.File] = {
    readwriter[upack.Msg].bimap(
      x => upack.Str(x.getName),
      msg => new java.io.File(readBinary[String](msg))
    )
  }

  implicit lazy val propsCodec: ReadWriter[Props] = {
    val areaKey      = upack.Str("area")
    val perimeterKey = upack.Str("perimeter")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          areaKey      -> writeMsg(x.area),
          perimeterKey -> writeMsg(x.perimeter)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          area      <- m.get(areaKey).map(x => readBinary[Double](x))
          perimeter <- m.get(perimeterKey).map(x => readBinary[Double](x))
        } yield {
          Props(
            area = area,
            perimeter = perimeter
          )
        }
        result.get
      }
    )
  }

  implicit lazy val pointCodec: ReadWriter[Point] = {
    val xKey = upack.Str("x")
    val yKey = upack.Str("y")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          xKey -> writeMsg(x.x),
          yKey -> writeMsg(x.y)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          x <- m.get(xKey).map(x => readBinary[Int](x))
          y <- m.get(yKey).map(x => readBinary[Int](x))
        } yield {
          Point(
            x = x,
            y = y
          )
        }
        result.get
      }
    )
  }

  implicit lazy val figureCodec: ReadWriter[Figure] = {
    readwriter[upack.Msg].bimap(
      {
        case x: Square   => upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("square"))
        case x: Circle   => upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("circle"))
        case x: Triangle => upack.Obj(writeMsg(x).obj += upack.Str("type") -> writeMsg("triangle"))
      },
      msg => {
        val m = msg.obj
        m.get(upack.Str("type"))
          .collect {
            case upack.Str("square")   => readBinary[Square](msg)
            case upack.Str("circle")   => readBinary[Circle](msg)
            case upack.Str("triangle") => readBinary[Triangle](msg)
          }
          .get
      }
    )
  }

  implicit lazy val squareCodec: ReadWriter[Square] = {
    val areaKey      = upack.Str("area")
    val perimeterKey = upack.Str("perimeter")
    val sidesKey     = upack.Str("sides")
    val widthKey     = upack.Str("width")
    val heightKey    = upack.Str("height")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          areaKey      -> writeMsg(x.area),
          perimeterKey -> writeMsg(x.perimeter),
          sidesKey     -> writeMsg(x.sides),
          widthKey     -> writeMsg(x.width),
          heightKey    -> writeMsg(x.height)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          area      <- m.get(areaKey).map(x => readBinary[Double](x))
          perimeter <- m.get(perimeterKey).map(x => readBinary[Double](x))
          sides     <- m.get(sidesKey).map(x => readBinary[Int](x))
          width     <- m.get(widthKey).map(x => readBinary[Int](x))
          height    <- m.get(heightKey).map(x => readBinary[Int](x))
        } yield {
          Square(
            area = area,
            perimeter = perimeter,
            sides = sides,
            width = width,
            height = height
          )
        }
        result.get
      }
    )
  }

  implicit lazy val circleCodec: ReadWriter[Circle] = {
    val areaKey      = upack.Str("area")
    val perimeterKey = upack.Str("perimeter")
    val sidesKey     = upack.Str("sides")
    val radiusKey    = upack.Str("radius")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          areaKey      -> writeMsg(x.area),
          perimeterKey -> writeMsg(x.perimeter),
          sidesKey     -> writeMsg(x.sides),
          radiusKey    -> writeMsg(x.radius)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          area      <- m.get(areaKey).map(x => readBinary[Double](x))
          perimeter <- m.get(perimeterKey).map(x => readBinary[Double](x))
          sides     <- m.get(sidesKey).map(x => readBinary[Int](x))
          radius    <- m.get(radiusKey).map(x => readBinary[Int](x))
        } yield {
          Circle(
            area = area,
            perimeter = perimeter,
            sides = sides,
            radius = radius
          )
        }
        result.get
      }
    )
  }

  implicit lazy val triangleCodec: ReadWriter[Triangle] = {
    val areaKey      = upack.Str("area")
    val perimeterKey = upack.Str("perimeter")
    val sidesKey     = upack.Str("sides")
    val verticesKey  = upack.Str("vertices")
    readwriter[upack.Msg].bimap(
      x => {
        upack.Obj(
          areaKey      -> writeMsg(x.area),
          perimeterKey -> writeMsg(x.perimeter),
          sidesKey     -> writeMsg(x.sides),
          verticesKey  -> writeMsg(x.vertices)
        )
      },
      msg => {
        val m = msg.obj
        val result = for {
          area      <- m.get(areaKey).map(x => readBinary[Double](x))
          perimeter <- m.get(perimeterKey).map(x => readBinary[Double](x))
          sides     <- m.get(sidesKey).map(x => readBinary[Int](x))
          vertices  <- m.get(verticesKey).map(x => readBinary[List[Point]](x))
        } yield {
          Triangle(
            area = area,
            perimeter = perimeter,
            sides = sides,
            vertices = vertices
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

  implicit lazy val propsEncoder: Encoder[Props] =
    (x: Props) => {
      Json.fromFields(
        List(
          "area"      -> x.area.asJson,
          "perimeter" -> x.perimeter.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val propsDecoder: Decoder[Props] =
    Decoder.instance { h =>
      for {
        _area      <- h.get[Double]("area")
        _perimeter <- h.get[Double]("perimeter")
      } yield {
        Props(area = _area, perimeter = _perimeter)
      }
    }

  implicit lazy val pointEncoder: Encoder[Point] =
    (x: Point) => {
      Json.fromFields(
        List(
          "x" -> x.x.asJson,
          "y" -> x.y.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val pointDecoder: Decoder[Point] =
    Decoder.instance { h =>
      for {
        _x <- h.get[Int]("x")
        _y <- h.get[Int]("y")
      } yield {
        Point(x = _x, y = _y)
      }
    }

  implicit lazy val figureEncoder: Encoder[Figure] = {
    case square: Square     => square.asJson.mapObject(_.add("type", Json.fromString("square")))
    case circle: Circle     => circle.asJson.mapObject(_.add("type", Json.fromString("circle")))
    case triangle: Triangle => triangle.asJson.mapObject(_.add("type", Json.fromString("triangle")))
  }
  implicit lazy val figureDecoder: Decoder[Figure] = for {
    fType <- Decoder[String].prepare(_.downField("type"))
    value <- fType match {
      case "square"   => Decoder[Square]
      case "circle"   => Decoder[Circle]
      case "triangle" => Decoder[Triangle]
    }
  } yield value

  implicit lazy val squareEncoder: Encoder[Square] =
    (x: Square) => {
      Json.fromFields(
        List(
          "area"      -> x.area.asJson,
          "perimeter" -> x.perimeter.asJson,
          "sides"     -> x.sides.asJson,
          "width"     -> x.width.asJson,
          "height"    -> x.height.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val squareDecoder: Decoder[Square] =
    Decoder.instance { h =>
      for {
        _area      <- h.get[Double]("area")
        _perimeter <- h.get[Double]("perimeter")
        _sides     <- h.get[Int]("sides")
        _width     <- h.get[Int]("width")
        _height    <- h.get[Int]("height")
      } yield {
        Square(area = _area,
               perimeter = _perimeter,
               sides = _sides,
               width = _width,
               height = _height)
      }
    }

  implicit lazy val circleEncoder: Encoder[Circle] =
    (x: Circle) => {
      Json.fromFields(
        List(
          "area"      -> x.area.asJson,
          "perimeter" -> x.perimeter.asJson,
          "sides"     -> x.sides.asJson,
          "radius"    -> x.radius.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val circleDecoder: Decoder[Circle] =
    Decoder.instance { h =>
      for {
        _area      <- h.get[Double]("area")
        _perimeter <- h.get[Double]("perimeter")
        _sides     <- h.get[Int]("sides")
        _radius    <- h.get[Int]("radius")
      } yield {
        Circle(area = _area, perimeter = _perimeter, sides = _sides, radius = _radius)
      }
    }

  implicit lazy val triangleEncoder: Encoder[Triangle] =
    (x: Triangle) => {
      Json.fromFields(
        List(
          "area"      -> x.area.asJson,
          "perimeter" -> x.perimeter.asJson,
          "sides"     -> x.sides.asJson,
          "vertices"  -> x.vertices.asJson
        ).filter(!_._2.isNull)
      )
    }

  implicit lazy val triangleDecoder: Decoder[Triangle] =
    Decoder.instance { h =>
      for {
        _area      <- h.get[Double]("area")
        _perimeter <- h.get[Double]("perimeter")
        _sides     <- h.get[Int]("sides")
        _vertices  <- h.getOrElse[List[Point]]("vertices")(List.empty)
      } yield {
        Triangle(area = _area, perimeter = _perimeter, sides = _sides, vertices = _vertices)
      }
    }

}
