package io.github.iozhik

import io.github.iozhik.Generator.Model.Version

object GeneratorUtils {
  def versionPostfix(min: Option[Version], max: Option[Version]): String = {
    val mins = min.map(_.version).getOrElse("").replace(".", "")
    val maxs = max.map(_.version).map("_" + _).getOrElse("").replace(".", "")
    mins + maxs
  }

  def sanitize(name: String, keywords: Set[String]): String =
    if (keywords.contains(name)) s"`$name`" else name

  val delim: String = "\n"
}
