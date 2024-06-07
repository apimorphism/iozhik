package io.github.iozhik

import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ParserSpec extends AnyFlatSpec with Matchers {

  import Parser.Model._

  def parserFor[A](p: Parser.Grammar.type => atto.Parser[A])(checks: (String => Either[String, A]) => Assertion): Unit = {
    checks(txt => atto.Parser.parse(p(Parser.Grammar), txt).done.either)
    ()
  }

  "Multiline comment parser" must "parse multiline comments" in {
    parserFor(_.mComment) { parse =>
      parse("/* Hello, World! */") shouldBe Right(" Hello, World! ")
      parse(
        """/* Hello,
          | World!
          |*/
          |"""".stripMargin
      ) shouldBe Right(" Hello,\n World!\n")
    }
  }

  "One line comment parser" must "parse one line comments" in {
    parserFor(_.lComment) { parse =>
      parse("-- Hello, World!") shouldBe Right(" Hello, World!")
    }
  }

  "Identifier parser" must "parse identifiers" in {
    parserFor(_.identifier) { parse =>
      parse("internal") shouldBe Right("internal")
      parse("D1") shouldBe Right("D1")
      parse("d1") shouldBe Right("d1")
    }
  }

  "Path parser" must "parse package qualified identifier" in {
    parserFor(_.path) { parse =>
      parse("io.github.iozhik") shouldBe Right("io.github.iozhik")
    }
  }

  "Kind parser" must "parse type, type constructor or higher kind definitions" in {
    parserFor(_.kind) { parse =>
      parse("Bigint") shouldBe Right(Kind("Bigint"))
      parse("List[Bigint]") shouldBe Right(Kind("List", List(Kind("Bigint"))))
      parse("Either[String, Bigint]") shouldBe Right(
        Kind("Either", List(
          Kind("String"),
          Kind("Bigint")
        ))
      )
      parse("Tuple3[String, Bigint, Double]") shouldBe Right(
        Kind("Tuple3", List(
          Kind("String"),
          Kind("Bigint"),
          Kind("Double"),
        ))
      )
      parse("List[List[Int]]") shouldBe Right(
        Kind("List", List(
          Kind("List", List(
            Kind("Int")
          ))
        ))
      )
      parse("Either[String, List[Int]]") shouldBe Right(
        Kind("Either", List(
          Kind("String"),
          Kind("List", List(
            Kind("Int")
          ))
        ))
      )
    }
  }

  "Type alias parser" must "parse type aliases" in {
    parserFor(_.alias) { parse =>
      parse("type MyInt = Int") shouldBe Right(Alias("", Kind("MyInt"), Kind("Int")))
      parse(
        """
          | /* MyInt is the best integer */
          | type MyInt = Int
        """.stripMargin) shouldBe Right(
        Alias(" MyInt is the best integer ", Kind("MyInt"), Kind("Int"))
      )
      parse(
        """
          | -- MyInt is the best integer
          |
          | -- ever
          | type MyInt = Int
        """.stripMargin) shouldBe Right(
        Alias(" MyInt is the best integer\n ever", Kind("MyInt"), Kind("Int"))
      )
      parse("type MyInt = tpl.Int") shouldBe Right(Alias("", Kind("MyInt"), Kind("tpl.Int")))
      parse("type IntList = List[Int]") shouldBe Right(Alias(
        "",
        Kind("IntList"),
        Kind("List", List(Kind("Int")))
      ))
      parse("type AList[A] = List[A]") shouldBe Right(Alias(
        "",
        Kind("AList", List(Kind("A"))),
        Kind("List", List(Kind("A")))
      ))
      parse("type FailOrList[A] = Either[String, List[A]]") shouldBe Right(Alias(
        "",
        Kind("FailOrList", List(Kind("A"))),
        Kind("Either", List(
          Kind("String"),
          Kind("List", List(
            Kind("A")
          ))
        ))
      ))
    }
  }

  "Exter symbol parser" must "parse provided symbol declarations" in {
    parserFor(_.exter) { parse =>
      parse("provided MyInt") shouldBe Right(Exter("", Kind("MyInt")))
      parse("provided tpl.Int") shouldBe Right(Exter("", Kind("tpl.Int")))
      parse("provided List[Int]") shouldBe Right(Exter("", Kind("List", List(Kind("Int")))))
      parse("provided Either[String, List[A]]") shouldBe Right(Exter(
        "",
        Kind("Either", List(
          Kind("String"),
          Kind("List", List(
            Kind("A")
          ))
        ))
      ))
      parse(
        """
          | /* MyInt is the best integer */
          | provided MyInt
        """.stripMargin) shouldBe Right(
        Exter(" MyInt is the best integer ", Kind("MyInt"))
      )
      parse(
        """
          | -- MyInt is the best integer
          |
          | -- ever
          | provided Int
        """.stripMargin) shouldBe Right(
        Exter(" MyInt is the best integer\n ever", Kind("Int"))
      )
    }
  }

  "maxVer parser" must "parse version upper bound" in {
    parserFor(_.maxVer) { parse =>
      parse("<50") shouldBe Right(Version(inclusive = false, "50"))
      parse("<5.0") shouldBe Right(Version(inclusive = false, "5.0"))
      parse("<=50") shouldBe Right(Version(inclusive = true, "50"))
      parse("<=5.0") shouldBe Right(Version(inclusive = true, "5.0"))
      parse("<5.0.0") shouldBe Right(Version(inclusive = false, "5.0.0"))
    }
  }

  "minVer parser" must "parse version lower bound" in {
    parserFor(_.minVer) { parse =>
      parse(">50") shouldBe Right(Version(inclusive = false, "50"))
      parse(">=50") shouldBe Right(Version(inclusive = true, "50"))
      parse(">=5.0") shouldBe Right(Version(inclusive = true, "5.0"))
      parse(">5.0.0") shouldBe Right(Version(inclusive = false, "5.0.0"))
    }
  }

  "Options parser" must "parse list of options" in {
    parserFor(_.options) { parse =>
      parse(
        """
          |  (booo fooo)
        """.stripMargin
      ) shouldBe Right(List("booo", "fooo"))
    }
  }

  "Struc items parser" must "parse list of struc items" in {
    parserFor(_.ditems) { parse =>
      parse(
        """
          |  {
          |      type    : "message_new"
          |      groupId : Int
          |  }
        """.stripMargin
      ) shouldBe Right(
        List(
          Typet(doc = "", name = "type", opts = List.empty[String], tag = "message_new"),
          Field(doc = "", name = "groupId", opts = List.empty[String], kind = Kind("Int")),
        )
      )
    }
  }

  "Struc body parser" must "parse struc body" in {
    parserFor(_.strucBody) { parse =>
      parse(
        """
          |  <5.0 {
          |      type    : "message_new"
          |      groupId : Int
          |      object  : (flatten) {
          |         photoId      : Int
          |         photoOwnerId : Int
          |      }
          |  }
        """.stripMargin
      ) shouldBe Right(
        Struc(
          doc = "",
          kind = None,
          minVersion = Option.empty[Version],
          maxVersion = Some(Version(inclusive = false, "5.0")),
          items = List(
            Typet(doc = "", name = "type", opts = List.empty[String], tag = "message_new"),
            Field(doc = "", name = "groupId", opts = List.empty[String], kind = Kind("Int")),
            Embed(doc = "",
              name = "object",
              struc = Struc(
                doc = "",
                kind = Option.empty[Kind],
                minVersion = Option.empty[Version],
                maxVersion = Option.empty[Version],
                opts = List("flatten"),
                items = List(
                  Field(doc = "", name = "photoId", opts = List.empty[String], kind = Kind("Int")),
                  Field(doc = "", name = "photoOwnerId", opts = List.empty[String], kind = Kind("Int"))
                )
              )
            )
          )
        )
      )
    }
  }

  "Struc parser" must "parse simple names" in {
    parserFor(_.struc) { parse =>
      parse(
        """
          |  MessageNew
        """.stripMargin
      ) shouldBe Right(
        Struc(doc = "", kind = Some(Kind(name = "MessageNew")))
      )
    }
  }

  "Struc parser" must "parse struc" in {
    parserFor(_.struc) { parse =>
      parse(
        """
          |  MessageNew[A] <5.0 {
          |      type    : "message_new"
          |      groupId : Int
          |      object  : ( flatten ) {
          |          photoId      : Int
          |          photoOwnerId : Int
          |      }
          |
          |      abstract Id[A]
          |  }
        """.stripMargin) shouldBe Right(
        Struc(
          doc = "",
          kind = Some(Kind(name = "MessageNew", params = List(Kind("A")))),
          minVersion = Option.empty[Version],
          maxVersion = Some(Version(inclusive = false, "5.0")),
          items = List(
            Typet(doc = "", name = "type", opts = List.empty[String], tag = "message_new"),
            Field(doc = "", name = "groupId", opts = List.empty[String], kind = Kind("Int")),
            Embed(doc = "",
              name = "object",
              struc = Struc(
                doc = "",
                kind = Option.empty[Kind],
                minVersion = Option.empty[Version],
                maxVersion = Option.empty[Version],
                opts = List("flatten"),
                items = List(
                  Field(doc = "", name = "photoId", opts = List.empty[String], kind = Kind("Int")),
                  Field(doc = "", name = "photoOwnerId", opts = List.empty[String], kind = Kind("Int"))
                )
              )
            ),
            Struc(doc = "", isAbstract = true, kind = Some(Kind(name = "Id", params = List(Kind("A")))))
          )
        )
      )
    }
  }

  "Struc parser" must "parse ADT sums" in {
    parserFor(_.struc) { parse =>
      parse(
        """
          |  Message {
          |      MessageNew[A] <5.0 {
          |          type    : "message_new"
          |          groupId : Int
          |          object  : ( flatten ) {
          |              photoId      : Int
          |              photoOwnerId : Int
          |          }
          |      }
          |  }
        """.stripMargin) shouldBe Right(
        Struc(
          doc = "",
          kind = Some(Kind("Message")),
          minVersion = Option.empty[Version],
          maxVersion = Option.empty[Version],
          items = List(
            Struc(
              doc = "",
              kind = Some(Kind(name = "MessageNew", params = List(Kind("A")))),
              minVersion = Option.empty[Version],
              maxVersion = Some(Version(inclusive = false, "5.0")),
              items = List(
                Typet(doc = "", name = "type", opts = List.empty[String], tag = "message_new"),
                Field(doc = "", name = "groupId", opts = List.empty[String], kind = Kind("Int")),
                Embed(doc = "",
                  name = "object",
                  struc = Struc(
                    doc = "",
                    kind = Option.empty[Kind],
                    minVersion = Option.empty[Version],
                    maxVersion = Option.empty[Version],
                    opts = List("flatten"),
                    items = List(
                      Field(doc = "", name = "photoId", opts = List.empty[String], kind = Kind("Int")),
                      Field(doc = "", name = "photoOwnerId", opts = List.empty[String], kind = Kind("Int"))
                    )
                  )
                )
              )
            )
          )
        )
      )
    }
  }

  "Struc parser" must "parse fields in traits" in {
    parserFor(_.struc) { parse =>
      parse(
        """
          |  Message {
          |    id : Int
          |    MessageNew {
          |      groupId : Int
          |    }
          |  }
        """.stripMargin) shouldBe Right(
        Struc(
          doc = "",
          kind = Some(Kind("Message")),
          minVersion = Option.empty[Version],
          maxVersion = Option.empty[Version],
          items = List(
            Field(doc = "", name = "id", opts = List.empty[String], kind = Kind("Int")),
            Struc(
              doc = "",
              kind = Some(Kind(name = "MessageNew")),
              minVersion = Option.empty[Version],
              maxVersion = Option.empty[Version],
              items = List(
                Field(doc = "", name = "groupId", opts = List.empty[String], kind = Kind("Int")),
              )
            )
          )
        )
      )
    }
  }

  "Space parser" must "parse namespaces with content" in {
    parserFor(_.space) { parse =>
      parse(
        """
          |  namespace vk {
          |
          |      provided Double
          |
          |      type Maybe[A] = Option[A]
          |
          |      Message {
          |
          |          MessageNew[A] <5.0 {
          |              type    : "message_new"
          |              groupId : Int
          |              amount  : Double
          |              object  : ( flatten ) {
          |                  photoId      : Int
          |                  photoOwnerId : Int
          |              }
          |          }
          |
          |          MessageOld
          |      }
          |  }
        """.stripMargin) shouldBe Right(
        Space(
          name = "vk",
          items = List(
            Exter(doc = "", name = Kind("Double")),
            Alias(
              doc = "",
              alias = Kind(name = "Maybe", params = List(Kind("A"))),
              name = Kind(name = "Option", params = List(Kind("A")))
            ),
            Struc(
              doc = "",
              kind = Some(Kind("Message")),
              minVersion = Option.empty[Version],
              maxVersion = Option.empty[Version],
              items = List(
                Struc(
                  doc = "",
                  kind = Some(Kind(name = "MessageNew", params = List(Kind("A")))),
                  minVersion = Option.empty[Version],
                  maxVersion = Some(Version(inclusive = false, "5.0")),
                  items = List(
                    Typet(doc = "", name = "type", opts = List.empty[String], tag = "message_new"),
                    Field(doc = "", name = "groupId", opts = List.empty[String], kind = Kind("Int")),
                    Field(doc = "", name = "amount", opts = List.empty[String], kind = Kind("Double")),
                    Embed(doc = "",
                      name = "object",
                      struc = Struc(
                        doc = "",
                        kind = Option.empty[Kind],
                        minVersion = Option.empty[Version],
                        maxVersion = Option.empty[Version],
                        opts = List("flatten"),
                        items = List(
                          Field(doc = "", name = "photoId", opts = List.empty[String], kind = Kind("Int")),
                          Field(doc = "", name = "photoOwnerId", opts = List.empty[String], kind = Kind("Int"))
                        )
                      )
                    )
                  )
                ),
                Struc(doc = "", kind = Some(Kind("MessageOld")))
              )
            )
          )
        )
      )
    }
  }

  "Space parser" must "parse mixins and usings" in {
    parserFor(_.space) { parse =>
      parse(
        """
          |  namespace vk {
          |
          |    Identity {
          |      id   : Int
          |      name : String
          |    }
          |
          |    Message {
          |      MessageNew {
          |        use Identity
          |        userId: Int
          |        mix Identity
          |        mixerId: Int
          |      }
          |    }
          |
          |  }
        """.stripMargin) shouldBe Right(
        Space(
          name = "vk",
          items = List(
            Struc(
              doc = "",
              kind = Some(Kind("Identity")),
              minVersion = Option.empty[Version],
              maxVersion = Option.empty[Version],
              items = List(
                Field(doc = "", name = "id", opts = List.empty[String], kind = Kind("Int")),
                Field(doc = "", name = "name", opts = List.empty[String], kind = Kind("String")),
              )
            ),
            Struc(
              doc = "",
              kind = Some(Kind("Message")),
              minVersion = Option.empty[Version],
              maxVersion = Option.empty[Version],
              items = List(
                Struc(
                  doc = "",
                  kind = Some(Kind("MessageNew")),
                  minVersion = Option.empty[Version],
                  maxVersion = Option.empty[Version],
                  items = List(
                    Using(doc = "", kind = Kind("Identity")),
                    Field(doc = "", name = "userId", opts = List.empty[String], kind = Kind("Int")),
                    Mixin(doc = "", kind = Kind("Identity")),
                    Field(doc = "", name = "mixerId", opts = List.empty[String], kind = Kind("Int")),
                  )
                )
              )
            )
          )
        )
      )
    }
  }

  "Parser" must "parse namespaces with imports, provided types, options, defuns" in {
    Parser.Grammar.parse(
      """
        | namespace basic {
        |   provided Double
        | }
        |
        | namespace api {
        |   import basic.Double
        | }
        |
        | namespace groups (camelToSnake) {
        |
        |   Layer {
        |     -- Get group by id
        |     def get (endpoint) {
        |       userId      : Int
        |     } => {
        |       key    : String
        |       server : String
        |       ts     : String
        |     }
        |   }
        | }
      """.stripMargin
    ) shouldBe Right(Space(name = "", items = List(
      Space(
        name = "basic",
        items = List(
          Exter(doc = "", name = Kind("Double"))
        )
      ), Space(
        name = "api",
        items = List(
          Impor(doc = "", name = Kind("basic.Double"))
        )
      ), Space(
        name = "groups",
        opts = List("camelToSnake"),
        items = List(
          Servc(
            doc = "",
            kind = Kind("Layer"),
            minVersion = Option.empty[Version],
            maxVersion = Option.empty[Version],
            opts = List.empty[String],
            items = List(
              Defun(
                doc = " Get group by id",
                kind = Kind("get"),
                minVersion = Option.empty[Version],
                maxVersion = Option.empty[Version],
                opts = List("endpoint"),
                dom = Right(Struc(
                    doc = "",
                    kind = Option.empty[Kind],
                    minVersion = Option.empty[Version],
                    maxVersion = Option.empty[Version],
                    items = List(
                      Field(doc = "", name = "userId", opts = List.empty[String], kind = Kind("Int")),
                    )
                )),
                cod = Right(Struc(
                  doc = "",
                  kind = Option.empty[Kind],
                  minVersion = Option.empty[Version],
                  maxVersion = Option.empty[Version],
                  items = List(
                    Field(doc = "", name = "key", opts = List.empty[String], kind = Kind("String")),
                    Field(doc = "", name = "server", opts = List.empty[String], kind = Kind("String")),
                    Field(doc = "", name = "ts", opts = List.empty[String], kind = Kind("String")),
                  )
                )),
              )
            )
          )
        )
      )
    )))
  }

}
