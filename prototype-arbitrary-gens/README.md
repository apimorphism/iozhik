# PROTOTYPE - throwaway, do not merge

Spike answering "can iozhik generate ScalaCheck Gen/Arbitrary instances for every telegramium type?".
Generator: `iozhik/.../scalagen/ArbitraryGenerator.scala` (hooked into `ScalaApiGeneratorV1.gen`), `custom-gens` in
`tgbotapi.conf`. It emits `telegramium-core/src/test/scala/telegramium/bots/Gens.scala` relative to the dst dir
(`../../test/...`). The two specs here were run in a scratch copy of telegramium (not part of iozhik).

Run: `sbt "iozhik/runMain io.github.iozhik.Application iozhik/src/main/resources/tgbotapi.conf <scratch>/telegramium-core/src/main/scala"`
(wipes the dst dir - use a scratch copy). Findings: see the handoff / implementation ticket.
