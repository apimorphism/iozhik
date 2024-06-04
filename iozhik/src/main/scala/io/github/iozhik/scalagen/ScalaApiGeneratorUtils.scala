package io.github.iozhik.scalagen

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
      kind = symt.resolve(x).collect {
        case e: Struc if e.isEnum && space.opts.contains("openEnums") =>
          wrapEnumType(sanitize(x.name, keywords) + (if (x.params.nonEmpty) body else ""))
        }
        .getOrElse(sanitize(x.name, keywords) + (if (x.params.nonEmpty) body else ""))
    } yield kind

  def wrapWithOpenEnum(tpe: String): String = "iozhik.OpenEnum[" + tpe + "]"

  def genFieldType(x: Kind, wrapEnumType: String => String = wrapWithOpenEnum)(implicit symt: Symtable, space: Space): Either[String, String] =
    for {
      items <- x.params.map(p => genFieldType(p, wrapEnumType)).sequence
      body = if (x.params.nonEmpty) "[" + items.mkString(", ") + "]" else ""
      fieldType = symt.resolve(x).collect {
        case s: Struc
          if s.fields.isEmpty && s.usings.isEmpty && s.leaves.isEmpty && s.wrapps.isEmpty && s.leavesForBins.isEmpty =>
          sanitize(x.name, keywords) + ".type" + body
        case e: Struc if e.isEnum && space.opts.contains("openEnums") =>
          wrapEnumType(sanitize(x.name, keywords) + body)
        }
        .getOrElse(sanitize(x.name, keywords) + body)
    } yield fieldType

  def requiredFieldsFirst(fields: List[Field]): List[Field] = {
    val required = fields.filterNot(f => defaults.exists(d => f.kind.name.startsWith(d._1)))
    required ++ fields.diff(required)
  }

  def importKind(k: Kind)(implicit symt: Symtable): List[String] = {
    val result = symt.resolve(k)
      .collect {
        case y: Struc if y.kind.nonEmpty => (y.path.reverse.filter(_.nonEmpty).intercalate("."), y.kind.get.name)
      }
      .map { case (path, name) => s"import $path.$name" }
      .toList
    result ++ k.params.flatMap(importKind)
  }
}
