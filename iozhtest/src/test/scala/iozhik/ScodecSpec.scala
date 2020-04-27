package iozhik

import org.scalatest.{FlatSpec, Matchers}
import scodec.bits.BitVector

class ScodecSpec extends FlatSpec with Matchers with Model {

  import scodec._

  import pet.ScodecImplicits._
  import fig.ScodecImplicits._
  import mat.ScodecImplicits._
  import bio.ScodecImplicits._

  import pet.Pet
  import fig.Figure
  import mat.Material
  import bio.Mammal

  def same[A](a: A): Attempt[DecodeResult[A]] = Attempt.successful(DecodeResult(a, BitVector.empty))

  "Codec" must "encode/decode ADTs" in {
    Codec.encode(tom: Pet).flatMap(Codec.decode[Pet]) shouldBe same(tom)
    Codec.encode(pit: Pet).flatMap(Codec.decode[Pet]) shouldBe same(pit)
    Codec.encode(circle: Figure).flatMap(Codec.decode[Figure]) shouldBe same(circle)
    Codec.encode(square: Figure).flatMap(Codec.decode[Figure]) shouldBe same(square)
    Codec.encode(triangle: Figure).flatMap(Codec.decode[Figure]) shouldBe same(triangle)
    Codec.encode(elephant: Mammal).flatMap(Codec.decode[Mammal]) shouldBe same(elephant)
    Codec.encode(dolphin: Mammal).flatMap(Codec.decode[Mammal]) shouldBe same(dolphin)
    Codec.encode(plastic: Material).flatMap(Codec.decode[Material]) shouldBe same(plastic)
    Codec.encode(metal: Material).flatMap(Codec.decode[Material]) shouldBe same(metal)
    Codec.encode(wood: Material).flatMap(Codec.decode[Material]) shouldBe same(wood)
  }

}
