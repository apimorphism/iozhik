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
        
        /**
          * An enum of strings that is open: a value can be one this version doesn't know. Its codecs are generated with
          * the enum's.
          */
        sealed trait OpenEnum[+T]
        
        object OpenEnum {
          final case class Known[T](value: T) extends OpenEnum[T]
          /** A string this version doesn't know. */
          final case class Unknown(value: String) extends OpenEnum[Nothing]

          def apply[T](value: T): OpenEnum[T] = Known(value)
        }""",
        name = "OpenEnum.scala",
        path = "iozhik"
      ),
      Code(
        body = """package iozhik
        
        import io.circe.Encoder
        import io.circe.Json
        import scala.language.implicitConversions

        /**
          * A family of objects told apart by a type tag that is open: a value can be one this version doesn't know.
          */
        sealed trait OpenUnion[+T]
        
        object OpenUnion {
          final case class Known[T](value: T) extends OpenUnion[T]
          /** A value this version doesn't know: its type tag, and the JSON object it was decoded from, which it encodes back to. */
          final case class Unknown(tag: String, raw: Json) extends OpenUnion[Nothing]

          def apply[T](value: T): OpenUnion[T] = Known(value)

          /**
            * A value to send is written without the wrapper: `RichTextBold(RichTextPlain("hi"))`. One way only, so a
            * received value still has to be matched to get at what it holds.
            */
          implicit def lift[T](value: T): OpenUnion[T] = Known(value)

          implicit def encoder[T: Encoder]: Encoder[OpenUnion[T]] = {
            case Known(value) => Encoder[T].apply(value)
            case Unknown(_, raw) => raw
          }
        }""",
        name = "OpenUnion.scala",
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
        |final case class MethodReq[Res](
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
    // The helpers of the attachFiles_ functions, private to the factory: only the generated code calls them.
    val attaching = items.exists(_.name.startsWith("attachFiles_"))
    val attachHelpers =
      if (!attaching) ""
      else
        """
          |  // A file embedded inside a compound parameter (e.g. InputMedia), at any depth, can't be attached by name
          |  // directly, since Telegram only recognizes "attach://<name>" references nested inside the JSON body.
          |  // These rewrite such a field to that placeholder and collect the real IFile for multipart upload,
          |  // leaving file_id/URL/already-"attach://" references (InputLinkFile) untouched.
          |  private def attachFile(name: String, file: IFile): (IFile, Map[String, IFile]) = file match {
          |    case _: InputLinkFile => (file, Map.empty)
          |    case other            => (InputLinkFile("attach://" + name), Map(name -> other))
          |  }
          |
          |  private def attachFile(name: String, file: Option[IFile]): (Option[IFile], Map[String, IFile]) =
          |    attachOption(name, file)(attachFile)
          |
          |  // Attaches the files of each element of a List, under the List's name and the element's index. A List holding no
          |  // file is returned as is.
          |  private def attachList[A](name: String, values: List[A])(
          |    attach: (String, A) => (A, Map[String, IFile])
          |  ): (List[A], Map[String, IFile]) = {
          |    val (attached, files) = values.zipWithIndex.map { case (x, i) => attach(name + "." + i, x) }.unzip
          |    val all               = files.flatten.toMap
          |    (if (all.isEmpty) values else attached, all)
          |  }
          |
          |  // Attaches the files of an Option's value, under the Option's name.
          |  private def attachOption[A](name: String, value: Option[A])(
          |    attach: (String, A) => (A, Map[String, IFile])
          |  ): (Option[A], Map[String, IFile]) = value match {
          |    case Some(x) =>
          |      val (attached, files) = attach(name, x)
          |      (Some(attached), files)
          |    case None => (None, Map.empty)
          |  }
          |
          |  // The value with its fields' files attached, or as it is, rather than copied, when it holds none.
          |  private def withAttached[A](value: A, files: Map[String, IFile])(attached: => A): (A, Map[String, IFile]) =
          |    if (files.isEmpty) (value, files) else (attached, files)
          |""".stripMargin
    // InputLinkFile is declared via a `wrap` inside the IFile enum, so the symbol table can't resolve it;
    // it lives in IFile's package, so derive its import from IFile's.
    val attachImports =
      if (!attaching) Nil
      else importKind(Kind("IFile", path = x.path)).flatMap(i => List(i, i.stripSuffix("IFile") + "InputLinkFile"))
    val body =
      s"""
         |trait $kind {
         |
         |  import io.circe.Decoder
         |  private implicit def decodeEither[A, B](
         |    implicit decoderA: Decoder[A], decoderB: Decoder[B]
         |  ): Decoder[Either[A, B]] = decoderA.either(decoderB)
         |$attachHelpers$defuns
         |}
         """.stripMargin
    List(
      Code(
        body = body,
        name = x.kind.name,
        imports = "import io.circe.syntax._" :: "import CirceImplicits._" ::
          attachImports ++ items.filter(_.packageObject == "__this_trait__").flatMap(_.imports)
      ),
      methodReqCode
    )
  }    
}
