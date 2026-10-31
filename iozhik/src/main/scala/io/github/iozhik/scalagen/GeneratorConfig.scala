package io.github.iozhik.scalagen

import pureconfig._
import pureconfig.generic.semiauto._

final case class GeneratorConfig(apiPath: String, fmtConfigPath: String, openEnum: OpenEnumConfig, customDecoders: Map[String, String] = Map.empty, customEncoders: Map[String, String] = Map.empty, customGens: Map[String, String] = Map.empty)

object GeneratorConfig {
  implicit val generatorConfigReader: ConfigReader[GeneratorConfig] = deriveReader
}

final case class OpenEnumConfig(enabled: Boolean, excludeTypes: List[String])

object OpenEnumConfig {
  implicit val openEnumConfigReader: ConfigReader[OpenEnumConfig] = deriveReader
}
