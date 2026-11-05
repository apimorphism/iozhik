# Contributing

## What lives here

`iozhik` is the IOzhik DSL/compiler (`.api` spec -> generated source code). The
`iozh-api-scraper` module scrapes third-party API docs (currently the
[Telegram Bot API](https://core.telegram.org/bots/api) and VK API) into `.api`
specs that `iozhik` then compiles.

The main consumer of the Telegram Bot API pipeline is
[apimorphism/telegramium](https://github.com/apimorphism/telegramium):
`telegramium-core` is entirely generated from this repo and must not be
hand-edited there (see that repo's CONTRIBUTING.md). If you need a change in
`telegramium-core`, the fix belongs in this repo, one of:

- `iozh-api-scraper/src/main/resources/tgbotapi-overrides.conf` - corrections
  to the Bot API docs for a specific field or type (integer widths, empty vs
  omitted arrays, types merged or added); the file describes each kind. Give
  each entry a comment with its reason. The scrape fails on an entry naming something the docs
  no longer have.
- `iozh-api-scraper/src/main/scala/TgBotApiScrapper.scala` - how the Bot API
  HTML page is parsed and turned into `.api` DSL text.
- `iozhik/src/main/resources/tgbotapi.conf` - generator config for the
  Telegram pipeline (open-enum settings, custom encoders/decoders, and
  `custom-gens`, which restrict a type's generated ScalaCheck generator: a
  function from the type to itself, applied to every generated and shrunk
  value - a map, never a filter like `suchThat`; comment each restriction with
  its reason or a linked issue).
- `iozhik/src/main/scala/io/github/iozhik/scalagen/` - the DSL -> Scala
  codegen itself.

## Regenerating telegramium-core

Check out `apimorphism/telegramium` next to this repo, then from this repo's
root run:

```
# re-scrape core.telegram.org/bots/api -> iozhtest/src/main/resources/tgbot.api
IOZHDIR="$PWD" sbt "iozh-api-scraper/runMain TgBotApiScrapper"

# recompile tgbot.api -> <telegramium checkout>/telegramium-core/src/main/scala,
# and the generators, the round-trip list, the Methods list and the optional arrays
# -> src/test/scala-generated next to it
# (full rewrite - everything under both trees is deleted and regenerated; the
# third argument is required while the config has generate-gens on)
sbt "iozhik/run iozhik/src/main/resources/tgbotapi.conf <path-to-telegramium>/telegramium-core/src/main/scala <path-to-telegramium>/telegramium-core/src/test/scala-generated"
```

Run the regen step on a clean `telegramium-core` tree and review the diff
before committing. Format and commit the result in **both** repos:

1. Here: commit the refreshed `iozhtest/src/main/resources/tgbot.api` if it
   changed.
2. In the telegramium checkout: commit the regenerated
   `telegramium-core/src/main/scala` and `telegramium-core/src/test/scala-generated`
   trees (run `sbt fmtAll` there, per its CONTRIBUTING.md; it leaves
   `src/test/scala-generated` alone, as the regen formats that tree itself).

Open a PR in each repo that changed.

(Optional: `nix develop` / `direnv allow` loads a dev shell with these two
steps as `tgbot-scrape` and `tgbot-regen-core` - the latter reads a
`TELEGRAMIUM_DIR` env var instead of a positional path, defaulting to
`../telegramium` - plus a combined `tgbot-refresh`. Not required; the raw
`sbt` commands above work the same without Nix.)

## Code style

This repo has no configured formatter/linter (no `.scalafmt.conf`, no
scalafmt/scalafix sbt plugin) - match the surrounding code's style. Before
submitting a PR, run:

```
sbt test
```
