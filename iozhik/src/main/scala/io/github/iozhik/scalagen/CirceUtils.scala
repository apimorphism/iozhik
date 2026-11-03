package io.github.iozhik.scalagen

import cats.implicits._
import io.github.iozhik.Generator.Model.{Field, Struc, Typet}
import io.github.iozhik.GeneratorUtils.delim
import io.github.iozhik.scalagen.ScalaApiGeneratorUtils._

object CirceUtils {
  def genMergedCirceCodecs(parent: Struc, kind: String, typeTag: String, typet: Typet, leaves: List[Struc])(
    implicit meta: GeneratorMeta
  ): Either[String, (String, String)] = {
    val wrapEnumType = if (parent.kind.exists(k => useOpenEnum(k, struc = None))) ".map(iozhik.OpenEnum.Known(_))" else ""
    leaves.traverse { leaf =>
      val postfix = if (leaf.isCaseObject) ".type" else ""
      leaf
        .kind
        .map(_.name)
        .toRight("No kind name")
        .map { kindName =>
          if (leaf.embeds.isEmpty) {
            (
              s"case $typeTag: $kindName$postfix => $typeTag.asJson.mapObject(_.add(" +
                "\"" + typet.name + "\", Json.fromString(\"" + typeTag + "\")))",
              s"Decoder[$kindName$postfix]"
            )
          } else {
            val (enc, dec) = genEmbedsCodecs(parent, typet, leaf, kindName, typeTag, postfix)
            (enc, s"($dec)")
          }
        }
    }
      .map { codecs =>
        val (encs, decs) = codecs.unzip
        (
          encs.intercalate(delim),
          "case \"" + typeTag + "\"" + s" => List[Decoder[$kind]](" + decs.map(_ + ".widen").intercalate(",") + s").reduceLeft(_ or _)$wrapEnumType"
        )
      }
  }

  def genCirceCodecCases(parent: Struc, typeTag: String, typet: Typet, leaf: Struc)(
    implicit meta: GeneratorMeta
  ): Either[String, (String, String)] = {
    val postfix = if (leaf.isCaseObject) ".type" else ""
    val wrapEnumType = if (parent.kind.exists(k => useOpenEnum(k, struc = None))) ".map(iozhik.OpenEnum.Known(_))" else ""
    leaf
      .kind
      .map(_.name)
      .toRight("No kind name")
      .map { kindName =>
        if (leaf.embeds.isEmpty) {
          (
            s"case $typeTag: $kindName$postfix => $typeTag.asJson.mapObject(_.add(" +
              "\"" + typet.name + "\", Json.fromString(\"" + typeTag + "\")))",
            "case \"" + typeTag + "\"" + s" => Decoder[$kindName$postfix]$wrapEnumType"
          )
        } else {
          val (enc, dec) = genEmbedsCodecs(parent, typet, leaf, kindName, typeTag, postfix)
          (enc, "case \"" + typeTag + "\"" + s" => " + (if (wrapEnumType.isEmpty) dec else s"($dec)$wrapEnumType"))
        }
      }
  }

  private def genEmbedsCodecs(parent: Struc, typet: Typet, child: Struc, nn: String, tt: String, postfix: String)(
    implicit meta: GeneratorMeta
  ): (String, String) = {
    val ownFields = for {
      (name, f) <- child.embeds.flatMap { e =>
        e.struc.allFields.map(e.name -> _)
      }
      ename = "\"" + name + "\""
      fname = "\"" + f.name + "\""
      // the type the case class declares the field with, an open enum included
      kk = genFieldType(f.kind, child.kind).toOption.getOrElse("Wrong kind") // TODO: Better diagnosis, throw error
    } yield {
      (
        s"${f.name} <- Decoder[$kk].prepare(_.downField($ename).downField($fname))",
        s"${f.name} = ${f.name}",
        ename -> fname
      )
    }
    val embedFields: Seq[String] = child.embeds.flatMap(_.struc.allFields.map(_.name))
    val inheritedFields = child.allFields.filter(z => !embedFields.contains(z.name)).map { f =>
      val fname = "\"" + f.name + "\""
      val kk = genFieldType(f.kind, child.kind).toOption.getOrElse("Wrong kind") // TODO: Better diagnosis, throw error
      (
        s"${f.name} <- Decoder[$kk].prepare(_.downField($fname))",
        s"${f.name} = ${f.name}",
        "" -> fname
      )
    }
    val fields = inheritedFields ++ ownFields
    val decoders = fields.map(_._1).intercalate(delim)
    val assigns = fields.map(_._2).intercalate(",")
    val jsonItems = (fields.map(_._3) ++ inheritedFields.map(_._3))
      .groupBy(_._1)
      .map { case (k, v) => k -> v.map(_._2) }
      .toList
    val lvl0: List[String] = jsonItems.filter(_._1.isEmpty).flatMap(_._2).distinct
    val lvl1: List[(String, List[String])] = jsonItems.filter(_._1.nonEmpty)
    val lvl0Code = s"val lvl0: List[String] = $lvl0"
    val lvl1Code = s"val lvl1: List[(String, List[String])] = $lvl1"
    val ttName = "\"" + typet.name + "\""
    val ttTag = "\"" + tt + "\""
    val cc = s"case $tt: $nn$postfix"
    (
      s"""
         | $cc => {
         | import io.circe.JsonObject
         | $lvl0Code
         | $lvl1Code
         | $tt.asJson.mapObject{ o =>
         |     val map = o.toMap
         |     Json.fromFields(
         |       lvl1.map {
         |         case (k, items) => k -> Json.fromFields(items.zip(items.flatMap(map.get)))
         |       } ++ o.toList.filter(x => lvl0.contains(x._1))
         |     )
         |     .mapObject(_.add($ttName, Json.fromString($ttTag)))
         |     .asObject
         |     .getOrElse(JsonObject.empty)
         |   }
         | }
                """.stripMargin,
      s"for {$delim$decoders$delim}${delim}yield$delim{$delim$nn($assigns)$delim}"
    )
  }
}
