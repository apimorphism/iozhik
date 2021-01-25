package io.github.iozhik

import io.github.iozhik.Generator.Model.{Kind, Space, Symtable}
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
}
