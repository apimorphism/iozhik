import scala.jdk.CollectionConverters._
import scala.util.Try

import com.typesafe.config.{Config, ConfigFactory, ConfigObject}

import TgBotApiScrapper.{ApiItem, Entity, EntityParam, Method, Sumtyp}

// Corrections to the Bot API docs, read from tgbotapi-overrides.conf: everything the scraper knows about a specific
// field or type that the docs don't say, or say wrongly. Heuristics that read the docs' wording stay in the scraper.
object TgBotApiOverrides {

  val file = "iozh-api-scraper/src/main/resources/tgbotapi-overrides.conf"

  sealed trait EmptyVsOmitted
  case object Distinct extends EmptyVsOmitted
  case object Same extends EmptyVsOmitted

  sealed trait IntegerWidth
  case object AsInt extends IntegerWidth
  case object AsLong extends IntegerWidth

  case class FieldOverride(
    emptyVsOmitted: Option[EmptyVsOmitted] = None,
    integer: Option[IntegerWidth] = None,
    tpe: Option[String] = None,
    optional: Boolean = false
  )

  case class TypeOverride(
    merge: List[String] = List.empty,
    extraItems: List[Entity] = List.empty,
    items: List[String] = List.empty,
    splitByType: Boolean = false
  )

  case class Overrides(fields: Map[String, FieldOverride], types: Map[String, TypeOverride]) {

    // An Owner.name entry wins over a bare name one.
    def field(owner: String, name: String): FieldOverride =
      fields.get(s"$owner.$name").orElse(fields.get(name)).getOrElse(FieldOverride())

    def typeOverride(name: String): TypeOverride = types.getOrElse(name, TypeOverride())

    // Overrides that don't fit the docs: they name a field, method or type the docs don't have (any more), so they'd
    // silently do nothing, or they use a setting where it doesn't apply.
    def problems(items: List[ApiItem]): List[String] = {
      val entities = items.collect { case e: Entity => e.name -> e.table.map(_.name) }.toMap
      val methods = items.collect { case m: Method => m.name -> m.table.map(_.name) }.toMap
      val sumtyps = items.collect { case s: Sumtyp => s.name }.toSet
      val owners = entities ++ methods
      val allNames = owners.values.flatten.toSet
      val fieldProblems = fields.toList.flatMap { case (key, o) =>
        key.split('.') match {
          case Array(owner, name) =>
            if (!owners.get(owner).exists(_.contains(name))) List(s"fields.\"$key\": the docs have no such field or param")
            else if (o.optional && !typeOverride(owner).splitByType) List(s"fields.\"$key\": optional only applies to a type with split-by-type")
            else List.empty
          case _ =>
            if (!allNames.contains(key)) List(s"fields.$key: the docs have no field or param of this name")
            else if (o.optional) List(s"fields.$key: optional needs an Owner.name key")
            else List.empty
        }
      }
      val typeProblems = types.toList.flatMap { case (name, t) =>
        val inDocs = entities.contains(name) || sumtyps.contains(name)
        val existence =
          if (t.items.nonEmpty) {
            if (inDocs) List(s"types.$name: the docs define it now, so it can't be declared with items")
            else t.items.filterNot(entities.contains).map(i => s"types.$name.items: the docs have no type $i")
          } else if (!inDocs) List(s"types.$name: the docs have no such type")
          else List.empty
        val notSumtyp =
          if ((t.merge.nonEmpty || t.extraItems.nonEmpty) && !sumtyps.contains(name))
            List(s"types.$name: merge and extra-items only apply to a sum type in the docs")
          else List.empty
        existence ++ notSumtyp ++
          t.merge.filterNot(m => sumtyps.contains(m) || entities.contains(m)).map(m => s"types.$name.merge: the docs have no type $m") ++
          t.extraItems.map(_.name).filter(n => entities.contains(n) || sumtyps.contains(n))
            .map(n => s"types.$name.extra-items: the docs define $n now") ++
          (if (t.splitByType && !entities.get(name).exists(_.contains("type"))) List(s"types.$name: split-by-type needs a type field")
           else List.empty)
      }
      (fieldProblems ++ typeProblems).sorted
    }
  }

  lazy val default: Overrides = load(ConfigFactory.parseResources("tgbotapi-overrides.conf"))

  def parse(hocon: String): Overrides = load(ConfigFactory.parseString(hocon))

  private val fieldKeys = Set("empty-vs-omitted", "integer", "type", "optional")
  private val typeKeys = Set("merge", "extra-items", "items", "split-by-type")
  private val extraItemKeys = Set("name", "desc", "tag", "fields")
  private val extraFieldKeys = Set("name", "type", "desc")

  private def load(raw: Config): Overrides = {
    val config = raw.resolve()
    val errors = List.newBuilder[String]

    def objects(c: Config, path: String): List[(String, Config)] =
      if (!c.hasPath(path)) List.empty
      else c.getObject(path).asScala.toList.map {
        case (k, v: ConfigObject) => k -> v.toConfig
        case (k, _)               => errors += s"$path.$k: expected an object"; k -> ConfigFactory.empty()
      }

    def checkKeys(c: Config, path: String, allowed: Set[String]): Unit =
      c.root.keySet.asScala.toList.sorted.filterNot(allowed).foreach(k => errors += s"$path: unknown key $k")

    def get[A](c: Config, path: String, key: String)(read: String => A): Option[A] =
      if (!c.hasPath(key)) None
      else Try(read(key)).fold(e => { errors += s"$path.$key: ${e.getMessage}"; None }, Some(_))

    def required(c: Config, path: String, key: String): String =
      get(c, path, key)(c.getString).getOrElse { if (!c.hasPath(key)) errors += s"$path: missing $key"; "" }

    def oneOf(c: Config, path: String, key: String, values: Set[String]): Option[String] =
      get(c, path, key)(c.getString).flatMap { v =>
        if (values.contains(v)) Some(v)
        else { errors += s"$path.$key: expected one of ${values.toList.sorted.mkString(", ")}, got $v"; None }
      }

    checkKeys(config, "overrides", Set("fields", "types"))

    val fields = objects(config, "fields").map { case (key, c) =>
      val path = s"fields.\"$key\""
      checkKeys(c, path, fieldKeys)
      key -> FieldOverride(
        emptyVsOmitted = oneOf(c, path, "empty-vs-omitted", Set("distinct", "same")).map {
          case "distinct" => Distinct
          case _          => Same
        },
        integer = oneOf(c, path, "integer", Set("Int", "Long")).map {
          case "Int" => AsInt
          case _     => AsLong
        },
        tpe = get(c, path, "type")(c.getString),
        optional = get(c, path, "optional")(c.getBoolean).getOrElse(false)
      )
    }.toMap

    val types = objects(config, "types").map { case (name, c) =>
      val path = s"types.$name"
      checkKeys(c, path, typeKeys)
      val extraItems = get(c, path, "extra-items")(c.getConfigList).toList.flatMap(_.asScala.toList).map { item =>
        val itemPath = s"$path.extra-items"
        checkKeys(item, itemPath, extraItemKeys)
        val fields = get(item, itemPath, "fields")(item.getConfigList).toList.flatMap(_.asScala.toList).map { f =>
          checkKeys(f, s"$itemPath.fields", extraFieldKeys)
          EntityParam(required(f, s"$itemPath.fields", "name"), required(f, s"$itemPath.fields", "type"), required(f, s"$itemPath.fields", "desc"))
        }
        Entity(
          required(item, itemPath, "name"),
          required(item, itemPath, "desc"),
          EntityParam("type", "__type_tag__", required(item, itemPath, "tag")) :: fields
        )
      }
      name -> TypeOverride(
        merge = get(c, path, "merge")(c.getStringList).map(_.asScala.toList).getOrElse(List.empty),
        extraItems = extraItems,
        items = get(c, path, "items")(c.getStringList).map(_.asScala.toList).getOrElse(List.empty),
        splitByType = get(c, path, "split-by-type")(c.getBoolean).getOrElse(false)
      )
    }.toMap

    errors.result() match {
      case Nil  => Overrides(fields, types)
      case errs => sys.error(s"Invalid overrides in $file:\n" + errs.mkString("\n"))
    }
  }
}
