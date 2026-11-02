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
        case e: Struc if e.isEnum && useOpenEnum(x, struc = None) =>
          wrapEnumType(sanitizedName + (if (x.params.nonEmpty) body else ""))
        }
        .getOrElse(sanitizedName + (if (x.params.nonEmpty) body else ""))
    } yield kind

  def wrapWithOpenEnum(tpe: String): String = "iozhik.OpenEnum[" + tpe + "]"

  def genFieldType(x: Kind, struc: Option[Kind], wrapEnumType: String => String = wrapWithOpenEnum)(implicit meta: GeneratorMeta): Either[String, String] =
    for {
      items <- x.params.map(p => genFieldType(p, struc, wrapEnumType)).sequence
      body = if (x.params.nonEmpty) "[" + items.mkString(", ") + "]" else ""
      fieldType = meta.symt.resolve(x).collect {
        case s: Struc
          if s.isCaseObject =>
          sanitize(x.name, keywords) + ".type" + body
        case e: Struc if e.isEnum && useOpenEnum(x, struc) =>
          wrapEnumType(sanitize(x.name, keywords) + body)
        }
        .getOrElse(sanitize(x.name, keywords) + body)
    } yield fieldType

  def requiredFieldsFirst(fields: List[Field]): List[Field] = {
    val required = fields.filterNot(f => defaults.exists(d => f.kind.name.startsWith(d._1)))
    required ++ fields.diff(required)
  }

  // the arguments of a case class, in order: genStruc lays the case class out from it, so anything positional
  // (the scodec codec) must take its fields from here, not from allFields
  def constructorFields(x: Struc): List[Field] =
    x.usingFields ++ requiredFieldsFirst(x.ownFields)

  def importKind(k: Kind)(implicit meta: GeneratorMeta): List[String] = {
    val result = meta.symt.resolve(k)
      .collect {
        case y: Struc if y.kind.nonEmpty => (y.path.reverse.filter(_.nonEmpty).intercalate("."), y.kind.get.name)
      }
      .map { case (path, name) => s"import $path.$name" }
      .toList
    result ++ k.params.flatMap(importKind)
  }

  def useOpenEnum(k: Kind, struc: Option[Kind])(implicit meta: GeneratorMeta): Boolean =
    meta.cfg.openEnum.enabled && 
      !meta.cfg.openEnum.excludeTypes.contains(k.name) && 
      struc.forall(meta.codKinds.contains) && meta.codKinds.contains(k)

  def isCodKind(struc: Struc)(implicit meta: GeneratorMeta): Boolean = struc.kind.exists(meta.codKinds.contains)

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

  private def resolveCodKinds(kind: Kind, seen: Set[Kind] = Set.empty)(implicit symt: Symtable): List[Kind] = {
    if (seen.contains(kind)) return Nil
    kind :: kind.params ++ symt.resolve(kind).collect {
      case s: Struc => resolveStrucKinds(s, seen + kind)
    }.toList.flatten
  }

  private def resolveStrucKinds(s: Struc, seen: Set[Kind] = Set.empty)(implicit symt: Symtable): List[Kind] = {
    val kinds = collection.mutable.ListBuffer[Kind]()
    s.kind.foreach(k => kinds += k)
    s.kind.foreach(k => kinds ++= k.params.flatMap(resolveCodKinds(_, seen)))
    kinds ++= s.allFields.flatMap(f => resolveCodKinds(f.kind, seen))
    kinds ++= s.embeds.flatMap(e => resolveCodKinds(Kind(e.name), seen))
    kinds ++= s.variants.flatMap {
      case l: Struc   => l.kind.toList.flatMap(resolveCodKinds(_, seen))
      case w: Wrapp   => resolveCodKinds(w.name, seen)
      case e: EnumStr => resolveCodKinds(e.name, seen)
    }
    kinds.toList
  }
}
