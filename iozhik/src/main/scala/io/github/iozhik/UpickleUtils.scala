package io.github.iozhik

import cats.implicits._
import io.github.iozhik.Generator.Model.Struc
import io.github.iozhik.GeneratorUtils.{delim, versionPostfix}

object UpickleUtils {
  def genMergedUpickleCodecs(struc: Struc, kindName: String): Either[String, String] =
    struc.leavesForBins
      .traverse { leaf =>
        leaf.kind.map(_.name).toRight("No kind name")
          .map { leafKindName =>
            val name = leafKindName + versionPostfix(leaf.minVersion, leaf.maxVersion)
            name.toLowerCase + "Codec"
          }
      }
      .map { cases =>
        s"""
           | implicit lazy val ${kindName.toLowerCase}Codec: ReadWriter[$kindName] = ReadWriter.merge(
           |   ${cases.intercalate(",")}
           | )
        """.stripMargin
      }

  def genUpickleCodecCases(struc: Struc, kindName: String): Either[String, String] =
    struc.leavesForBins
      .traverse { leaf =>
        for {
          kindName <- leaf.kind.map(_.name).toRight("No kind name")
          typeTag <- leaf.typet().map(_.tag).toRight(s"No type id found for $kindName")
          typeName <- leaf.typet().map(_.name).toRight(s"No type id found for $kindName")
        } yield {
          if (leaf.fields.nonEmpty) {
            (
              s"""case x: $kindName => upack.Obj(writeMsg(x).obj += upack.Str("$typeName") -> writeMsg("$typeTag"))""",
              s"""case upack.Str("$typeTag") => readBinary[$kindName](msg)"""
            )
          } else {
            (
              s"""case $kindName => upack.Obj(upack.Obj().obj += upack.Str("$typeName") -> writeMsg("$typeTag"))""",
              s"""case upack.Str("$typeTag") => $kindName"""
            )
          }
        }
      }
      .map { cases =>
        s"""
           | implicit lazy val ${kindName.toLowerCase}Codec: ReadWriter[$kindName] = {
           |   readwriter[upack.Msg].bimap(
           |     {
           |       ${cases.map(_._1).intercalate(delim)}
           |     },
           |     msg => {
           |       val m = msg.obj
           |       m.get(upack.Str("type")).collect {
           |         ${cases.map(_._2).intercalate(delim)}
           |       }.get
           |     }
           |   )
           | }
        """.stripMargin
      }
}
