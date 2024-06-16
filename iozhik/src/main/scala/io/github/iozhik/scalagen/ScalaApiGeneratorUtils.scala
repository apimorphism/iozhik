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

  def genKind(x: Kind, wrapEnumType: String => String = identity)(implicit meta: GeneratorMeta): Either[String, String] =
    for {
      items <- x.params.map(p => genKind(p, wrapEnumType)).sequence
      body = "[" + items.mkString(", ") + "]"
      sanitizedName = sanitize(x.name, keywords)
      kind = meta.symt.resolve(x).collect {
        case e: Struc if e.isEnum && useOpenEnum(x) =>
          wrapEnumType(sanitizedName + (if (x.params.nonEmpty) body else ""))
        }
        .getOrElse(sanitizedName + (if (x.params.nonEmpty) body else ""))
    } yield kind

  def wrapWithOpenEnum(tpe: String): String = "iozhik.OpenEnum[" + tpe + "]"

  def genFieldType(x: Kind, wrapEnumType: String => String = wrapWithOpenEnum)(implicit meta: GeneratorMeta): Either[String, String] =
    for {
      items <- x.params.map(p => genFieldType(p, wrapEnumType)).sequence
      body = if (x.params.nonEmpty) "[" + items.mkString(", ") + "]" else ""
      fieldType = meta.symt.resolve(x).collect {
        case s: Struc
          if s.fields.isEmpty && s.usings.isEmpty && s.leaves.isEmpty && s.wrapps.isEmpty && s.leavesForBins.isEmpty =>
          sanitize(x.name, keywords) + ".type" + body
        case e: Struc if e.isEnum && useOpenEnum(x) =>
          wrapEnumType(sanitize(x.name, keywords) + body)
        }
        .getOrElse(sanitize(x.name, keywords) + body)
    } yield fieldType

  def requiredFieldsFirst(fields: List[Field]): List[Field] = {
    val required = fields.filterNot(f => defaults.exists(d => f.kind.name.startsWith(d._1)))
    required ++ fields.diff(required)
  }

  def importKind(k: Kind)(implicit meta: GeneratorMeta): List[String] = {
    val result = meta.symt.resolve(k)
      .collect {
        case y: Struc if y.kind.nonEmpty => (y.path.reverse.filter(_.nonEmpty).intercalate("."), y.kind.get.name)
      }
      .map { case (path, name) => s"import $path.$name" }
      .toList
    result ++ k.params.flatMap(importKind)
  }

  def useOpenEnum(k: Kind)(implicit meta: GeneratorMeta): Boolean =
    meta.cfg.openEnum.enabled && meta.codKinds.contains(k) && !meta.cfg.openEnum.excludeTypes.contains(k.name)

  def collectCodKinds(space: Space)(implicit symt: Symtable): Set[Kind] = {
    val resultSet = collection.mutable.Set[Kind]()
    val queue = collection.mutable.Queue[Kind]()

    def enqueueKinds(kinds: List[Kind]): Unit = {
      kinds.foreach { kind =>
        if (!resultSet.contains(kind)) {
          resultSet += kind
          queue.enqueue(kind)
        }
      }
    }

    (space :: space.spaces).foreach { s =>
      s.servcs.foreach { servc =>
        servc.items.collect {
          case defun: Defun =>
            defun.cod match {
              case Left(codKind) =>
                enqueueKinds(List(codKind) ++ codKind.params)
              case Right(codStruc) =>
                enqueueKinds(resolveStrucKinds(codStruc))
            }
        }
      }
    }

    while (queue.nonEmpty) {
      val current = queue.dequeue()
      enqueueKinds(resolveCodKinds(current))
    }

    resultSet.toSet
  }

  private def resolveCodKinds(kind: Kind)(implicit symt: Symtable): List[Kind] = {
    kind :: kind.params ++ symt.resolve(kind).collect {
      case s: Struc => resolveStrucKinds(s)
    }.toList.flatten
  }

  private def resolveStrucKinds(s: Struc)(implicit symt: Symtable): List[Kind] = {
    val kinds = collection.mutable.ListBuffer[Kind]()
    s.kind.foreach(k => kinds += k)
    s.kind.foreach(k => kinds ++= k.params.flatMap(resolveCodKinds))
    kinds ++= s.fields.flatMap(f => resolveCodKinds(f.kind))
    kinds ++= s.leaves.flatMap(l => l.kind.map(resolveCodKinds).toList.flatten)
    kinds ++= s.embeds.flatMap(e => resolveCodKinds(Kind(e.name)))
    kinds ++= s.wrapps.flatMap(w => resolveCodKinds(w.name))
    kinds ++= s.enumstrs.flatMap(e => resolveCodKinds(e.name))
    kinds.toList
  }
}
