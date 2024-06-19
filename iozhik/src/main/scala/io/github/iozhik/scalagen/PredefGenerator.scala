package io.github.iozhik.scalagen

import io.github.iozhik.Generator.Model._
import io.github.iozhik.scalagen.ScalaApiGeneratorUtils.importKind

object PredefGenerator {
  val predef: List[Code] = 
    List(
      Code(
        body = """package iozhik
        
        final case class DecodingError(message: String) extends Throwable(message)""",
        name = "DecodingError.scala",
        path = "iozhik"
      ),
      Code(
        body = """package iozhik
        
        import io.circe.Encoder
        
        /**
          * Indicates that the enumeration is open and can accept values beyond those already specified.
          */
        sealed trait OpenEnum[+T]
        
        object OpenEnum {
          case class Known[T](value: T) extends OpenEnum[T]
          case class Unknown[T](value: String) extends OpenEnum[T]

          def apply[T](value: T): OpenEnum[T] = Known(value)

          implicit def encoder[T: Encoder]: Encoder[OpenEnum[T]] = {
            case Known(value) => Encoder[T].apply(value)
            case Unknown(value) => Encoder[String].apply(value)
          }
        }""",
        name = "OpenEnum.scala",
        path = "iozhik"
      )
    )

  def genMethodsFactory(x: Servc, defuns: String, kind: String, items: List[Code])(
    implicit meta: GeneratorMeta
  ): List[Code] = {
    val methodReq =
      """
        |trait Method[Res] {
        |  def payload: MethodPayload
        |  def decoder: Decoder[Res]
        |}
        |
        |final case class MethodReq[Res] private (
        |  payload: MethodPayload,
        |  decoder: Decoder[Res]
        |) extends Method[Res]
        |
        |final case class MethodPayload(
        |  name: String,
        |  json: Json,
        |  files: Map[String, IFile]
        |)
        |
        |object MethodReq {
        |
        |  def apply[Res](
        |    name: String,
        |    json: Json,
        |    files: Map[String, IFile] = Map.empty
        |  )(implicit decoder: Decoder[Res]): MethodReq[Res] =
        |    new MethodReq(MethodPayload(name, json, files), decoder)
        |
        |}
        """.stripMargin
    val methodReqCode = Code(
      body = methodReq,
      name = "Method",
      imports = "import io.circe.{Decoder, Json}" :: importKind(Kind("IFile", path = x.path))
    )
    val body =
      s"""
         |trait $kind {
         |
         |  import io.circe.Decoder
         |  private implicit def decodeEither[A, B](
         |    implicit decoderA: Decoder[A], decoderB: Decoder[B]
         |  ): Decoder[Either[A, B]] = decoderA.either(decoderB)
         |$defuns
         |}
         """.stripMargin
    List(
      Code(
        body = body,
        name = x.kind.name,
        imports = "import io.circe.syntax._" :: "import CirceImplicits._" ::
          items.filter(_.packageObject == "__this_trait__").flatMap(_.imports)
      ),
      methodReqCode
    )
  }    
}
