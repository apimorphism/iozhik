package io.github.iozhik

import cats.implicits._
import io.github.iozhik.Generator.Model.{Field, Space, Struc, Symtable, Typet}
import io.github.iozhik.GeneratorUtils.delim
import io.github.iozhik.ScalaApiGeneratorUtils.genKind

object CirceUtils {
  def genMergedCirceCodecs(parent: Struc, kind: String, typeTag: String, typet: Typet, leaves: List[Struc])(
    implicit symt: Symtable, space: Space
  ): Either[String, (String, String)] = {
    val wrapEnumType = if (space.opts.contains("openEnums")) ".map(iozhik.OpenEnum.Known(_))" else ""
    leaves.traverse { leaf =>
      val postfix = if (leaf.fields.nonEmpty) "" else ".type"
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
    implicit symt: Symtable, space: Space
  ): Either[String, (String, String)] = {
    val postfix = if (leaf.fields.nonEmpty) "" else ".type"
    val wrapEnumType = if (space.opts.contains("openEnums")) ".map(iozhik.OpenEnum.Known(_))" else ""
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
          (enc, "case \"" + typeTag + "\"" + s" => " + dec)
        }
      }
  }

  private def genEmbedsCodecs(parent: Struc, typet: Typet, child: Struc, nn: String, tt: String, postfix: String)(
    implicit symt: Symtable, space: Space
  ): (String, String) = {
    val ownFields = for {
      (name, f) <- child.embeds.flatMap { e =>
        val usingf: Seq[Field] = e.struc.usings
          .flatMap(y => symt.find(y.kind, parent.kind))
          .collect { case s: Struc => s }
          .flatMap(_.fields)
        (e.struc.fields ++ usingf).map(e.name -> _)
      }
      ename = "\"" + name + "\""
      fname = "\"" + f.name + "\""
      kk = genKind(f.kind).toOption.getOrElse("Wrong kind") // TODO: Better diagnosis, throw error
    } yield {
      (
        s"${f.name} <- Decoder[$kk].prepare(_.downField($ename).downField($fname))",
        s"${f.name} = ${f.name}",
        ename -> fname
      )
    }
    val embedFields: Seq[String] = child.embeds.flatMap(_.struc.fields.map(_.name))
    val inheritedFields = child.fields.filter(z => !embedFields.contains(z.name)).map { f =>
      val fname = "\"" + f.name + "\""
      (
        s"${f.name} <- Decoder[${f.kind.name}].prepare(_.downField($fname))",
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
