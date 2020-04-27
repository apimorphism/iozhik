package iozhik

import org.scalatest.{FlatSpec, Matchers}

class CirceJsonSpec extends FlatSpec with Matchers with Model {

  import io.circe._
  import io.circe.syntax._

  import pet.CirceImplicits._
  import fig.CirceImplicits._
  import bio.CirceImplicits._
  import mat.CirceImplicits._

  import pet.Pet
  import fig.Figure
  import bio.Mammal
  import mat.Material

  private val tomJ = Json.obj(
    "type" -> Json.fromString("cat"),
    "name" -> Json.fromString("Tom"),
  )

  private val pitJ = Json.obj(
    "type" -> Json.fromString("dog"),
    "name" -> Json.fromString("Pit"),
  )

  private val circleJ = Json.obj(
    "type" -> Json.fromString("circle"),
    "area" -> Json.fromDoubleOrNull(circle.area),
    "perimeter" -> Json.fromDoubleOrNull(circle.perimeter),
    "sides" -> Json.fromInt(circle.sides),
    "radius" -> Json.fromInt(circle.radius),
  )

  private val squareJ = Json.obj(
    "type" -> Json.fromString("square"),
    "area" -> Json.fromDoubleOrNull(square.area),
    "perimeter" -> Json.fromDoubleOrNull(square.perimeter),
    "sides" -> Json.fromInt(square.sides),
    "width" -> Json.fromInt(square.width),
    "height" -> Json.fromInt(square.height),
  )

  private val triangleJ = Json.obj(
    "type" -> Json.fromString("triangle"),
    "area" -> Json.fromDoubleOrNull(triangle.area),
    "perimeter" -> Json.fromDoubleOrNull(triangle.perimeter),
    "sides" -> Json.fromInt(triangle.sides),
    "vertices" -> Json.arr(triangle.vertices.map{ p =>
      Json.obj("x" -> Json.fromInt(p.x), "y" -> Json.fromInt(p.y))
    }: _*)
  )

  private val elephantJ = Json.obj(
    "type" -> Json.fromString("elephant")
  )

  private val dolphinJ = Json.obj(
    "type" -> Json.fromString("dolphin")
  )

  private val plasticJ = Json.obj(
    "type" -> Json.fromString("plastic"),
    "density" -> Json.fromDoubleOrNull(plastic.density),
    "color" -> Json.fromString(plastic.color),
    "props" -> Json.obj(
      "plasticity" -> Json.fromDoubleOrNull(plastic.plasticity)
    )
  )

  private val metalJ = Json.obj(
    "type" -> Json.fromString("metal"),
    "density" -> Json.fromDoubleOrNull(metal.density),
    "valency" -> Json.fromInt(metal.valency),
    "props" -> Json.obj(
      "conductivity" -> Json.fromDoubleOrNull(metal.conductivity),
      "strength" -> Json.fromDoubleOrNull(metal.strength)
    )
  )

  private val woodJ = Json.obj(
    "type" -> Json.fromString("wood"),
    "density" -> Json.fromDoubleOrNull(wood.density),
    "props" -> Json.obj(
      "colors" -> Json.arr(wood.colors.map(Json.fromString): _*)
    )
  )

  "Decoder" must "decode sum types" in {
    tomJ.as[Pet] shouldBe Right(tom)
    pitJ.as[Pet] shouldBe Right(pit)
    circleJ.as[Figure] shouldBe Right(circle)
    squareJ.as[Figure] shouldBe Right(square)
    triangleJ.as[Figure] shouldBe Right(triangle)
    elephantJ.as[Mammal] shouldBe Right(elephant)
    dolphinJ.as[Mammal] shouldBe Right(dolphin)
    plasticJ.as[Material] shouldBe Right(plastic)
    metalJ.as[Material] shouldBe Right(metal)
    woodJ.as[Material] shouldBe Right(wood)
  }

  "Encoder" must "encode sum types" in {
    (tom: Pet).asJson shouldBe tomJ
    (pit: Pet).asJson shouldBe pitJ
    (circle: Figure).asJson shouldBe circleJ
    (triangle: Figure).asJson shouldBe triangleJ
    (square: Figure).asJson shouldBe squareJ
    (elephant: Mammal).asJson shouldBe elephantJ
    (dolphin: Mammal).asJson shouldBe dolphinJ
    (plastic: Material).asJson shouldBe plasticJ
    (wood: Material).asJson shouldBe woodJ
  }
}
