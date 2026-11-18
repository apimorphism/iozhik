package io.github.iozhik.scalagen

import pureconfig._
import pureconfig.generic.semiauto._

final case class GeneratorConfig(apiPath: String, fmtConfigPath: String, openEnum: OpenEnumConfig, customDecoders: Map[String, String] = Map.empty, customEncoders: Map[String, String] = Map.empty, customGens: Map[String, String] = Map.empty, generateGens: Boolean = false)

object GeneratorConfig {
  implicit val generatorConfigReader: ConfigReader[GeneratorConfig] = deriveReader
}

// includeFields: fields of a type that is only sent, as "Type.field", that are wrapped as if received
final case class OpenEnumConfig(enabled: Boolean, excludeTypes: List[String], includeFields: List[String] = List.empty)

object OpenEnumConfig {
  implicit val openEnumConfigReader: ConfigReader[OpenEnumConfig] = deriveReader
}
