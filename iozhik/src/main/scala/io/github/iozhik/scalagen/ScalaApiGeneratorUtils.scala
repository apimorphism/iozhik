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

  def camel2snake(name: String): String = name
    .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
    .replaceAll("([a-z\\d])([A-Z])", "$1_$2")
    .toLowerCase

  // a field's key in the JSON, which attachment names are built from
  def jsonName(f: Field)(implicit meta: GeneratorMeta): String = jsonName(f.name)

  def jsonName(name: String)(implicit meta: GeneratorMeta): String =
    if (meta.space.opts.contains("snake")) camel2snake(name) else name

  def genKind(x: Kind, wrap: (Struc, String) => String = noWrap)(implicit meta: GeneratorMeta): Either[String, String] =
    for {
      items <- x.params.map(p => genKind(p, wrap)).sequence
      body = "[" + items.mkString(", ") + "]"
      sanitizedName = sanitize(x.name, keywords)
      kind = meta.symt.resolve(x).collect {
        case e: Struc if e.shape.isEnum && useOpenEnum(x, struc = None) =>
          wrap(e, sanitizedName + (if (x.params.nonEmpty) body else ""))
        }
        .getOrElse(sanitizedName + (if (x.params.nonEmpty) body else ""))
    } yield kind

  // How an open family is wrapped where a bot receives it.
  sealed trait OpenKind { def wrapper: String }
  object OpenKind {
    // A family of objects told apart by a type tag, held in `tagField`: its Unknown keeps the JSON object.
    final case class Union(tagField: String) extends OpenKind { val wrapper = "iozhik.OpenUnion" }
    // An enum of strings: its Unknown keeps the string.
    case object Enum extends OpenKind { val wrapper = "iozhik.OpenEnum" }
  }

  def openKind(e: Struc): OpenKind = e.typet().fold[OpenKind](OpenKind.Enum)(t => OpenKind.Union(t.name))

  def wrapOpen(e: Struc, tpe: String): String = openKind(e).wrapper + "[" + tpe + "]"

  val noWrap: (Struc, String) => String = (_, tpe) => tpe

  def genFieldType(x: Kind, struc: Option[Kind], wrap: (Struc, String) => String = wrapOpen)(implicit meta: GeneratorMeta): Either[String, String] =
    for {
      items <- x.params.map(p => genFieldType(p, struc, wrap)).sequence
      body = if (x.params.nonEmpty) "[" + items.mkString(", ") + "]" else ""
      fieldType = meta.symt.resolve(x).collect {
        case s: Struc
          if s.isCaseObject =>
          sanitize(x.name, keywords) + ".type" + body
        case e: Struc if e.shape.isEnum && useOpenEnum(x, struc) =>
          wrap(e, sanitize(x.name, keywords) + body)
        }
        .getOrElse(sanitize(x.name, keywords) + body)
    } yield fieldType

  def requiredFieldsFirst(fields: List[Field]): List[Field] = {
    val required = fields.filterNot(f => defaults.exists(d => f.kind.name.startsWith(d._1)))
    required ++ fields.diff(required)
  }

  // the constructor arguments of a case class, in the order genStruc lays them out
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
