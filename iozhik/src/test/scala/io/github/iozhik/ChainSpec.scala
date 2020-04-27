package io.github.iozhik

import org.scalatest.{FlatSpec, Matchers}

class ChainSpec extends FlatSpec with Matchers {

  val scalaGen = new ScalaApiGeneratorV1

  "Generator" must "handle imports correctly" in {

    Parser.Grammar.parse(
      """
        | namespace basic {
        |   provided Double
        |   type Doo = Double
        | }
        | namespace api {
        |   import basic.Doo
        |   Message {
        |     id : Int
        |     MessageNew {
        |       groupId : Int
        |       amount  : Doo
        |     }
        |   }
        | }
      """.stripMargin
    ).flatMap(Generator.Model.translate).flatMap(scalaGen.gen) shouldBe Right(
      "package basic { type Doo = Double };package api { import basic.Doo;sealed trait Message { def id: Int };case class MessageNew(id: Int, groupId: Int, amount: Doo) extends Message; }"
    )

  }

}
