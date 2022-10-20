package io.github.iozhik

import io.github.iozhik.Generator.Model._
import cats.implicits._
import io.github.iozhik.GeneratorUtils.sanitize

object ScalaApiGeneratorUtils {
  val keywords: Set[String] = Set(
    "case", "class", "type", "object", "def"
  )

  def genKind(x: Kind)(implicit symt: Symtable, space: Space): Either[String, String] =
    for {
      items <- x.params.map(genKind).sequence
      body = "[" + items.mkString(", ") + "]"
    } yield sanitize(x.name, keywords) + (if (x.params.nonEmpty) body else "")

  def genFieldType(x: Kind)(implicit symt: Symtable, space: Space): Either[String, String] =
    for {
      items <- x.params.map(genFieldType).sequence
      modifier = symt.resolve(x).collect {
        case s: Struc
          if s.fields.isEmpty && s.usings.isEmpty && s.leaves.isEmpty && s.wrapps.isEmpty && s.leavesForBins.isEmpty =>
          ".type"
      }
        .getOrElse("")
      body = "[" + items.mkString(", ") + "]"
    } yield sanitize(x.name, keywords) + modifier + (if (x.params.nonEmpty) body else "")

  val predef: List[Code] = 
    List(
      Code(
        body = """package iozhik
        
        final case class DecodingError(message: String) extends Throwable(message)""",
        name = "DecodingError.scala",
        path = "iozhik"
      )
    )  
}
