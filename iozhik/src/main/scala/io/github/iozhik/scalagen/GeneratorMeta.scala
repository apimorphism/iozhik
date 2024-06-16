package io.github.iozhik.scalagen

import io.github.iozhik.Generator.Model.Kind
import io.github.iozhik.Generator.Model.Space
import io.github.iozhik.Generator.Model.Symtable

final case class GeneratorMeta(
  space: Space,
  symt: Symtable,
  cfg: GeneratorConfig,
  codKinds: Set[Kind]
)
