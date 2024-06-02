package io.github.iozhik

import io.github.iozhik.Generator.Model._
import cats.implicits._
import io.github.iozhik.GeneratorUtils.sanitize

object ScalaApiGeneratorUtils {
  val keywords: Set[String] = Set(
    "case", "class", "type", "object", "def"
  )

  val defaults = List(
    "List" -> "List.empty",
    "Option" -> "Option.empty"
  )

  def genKind(x: Kind, wrapEnumType: String => String = identity)(implicit symt: Symtable, space: Space): Either[String, String] =
    for {
      items <- x.params.map(p => genKind(p, wrapEnumType)).sequence
      body = "[" + items.mkString(", ") + "]"
      ol = symt.resolve(x).collect {
        case e: Struc if e.isEnum =>
          wrapEnumType(sanitize(x.name, keywords) + (if (x.params.nonEmpty) body else ""))
        }
        .getOrElse(sanitize(x.name, keywords) + (if (x.params.nonEmpty) body else ""))
    } yield ol

  def wrapWithOpenEnum(tpe: String): String = "iozhik.OpenEnum[" + tpe + "]"   

  def genFieldType(x: Kind, wrapEnumType: String => String = wrapWithOpenEnum)(implicit symt: Symtable, space: Space): Either[String, String] =
    for {
      items <- x.params.map(p => genFieldType(p, wrapEnumType)).sequence
      body = if (x.params.nonEmpty) "[" + items.mkString(", ") + "]" else ""
      fieldType = symt.resolve(x).collect {
        case s: Struc
          if s.fields.isEmpty && s.usings.isEmpty && s.leaves.isEmpty && s.wrapps.isEmpty && s.leavesForBins.isEmpty =>
          sanitize(x.name, keywords) + ".type" + body
        case e: Struc if e.isEnum =>
          wrapEnumType(sanitize(x.name, keywords) + body)
        }
        .getOrElse(sanitize(x.name, keywords) + body)
    } yield fieldType

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

  def requiredFieldsFirst(fields: List[Field]): List[Field] = {
    val required = fields.filterNot(f => defaults.exists(d => f.kind.name.startsWith(d._1)))
    required ++ fields.diff(required)
  }
}
