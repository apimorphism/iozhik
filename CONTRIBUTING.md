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

- `iozh-api-scraper/src/main/scala/TgBotApiScrapper.scala` - how the Bot API
  HTML page is parsed and turned into `.api` DSL text.
- `iozhik/src/main/resources/tgbotapi.conf` - generator config for the
  Telegram pipeline (open-enum settings, custom encoders/decoders).
- `iozhik/src/main/scala/io/github/iozhik/scalagen/` - the DSL -> Scala
  codegen itself.

## Regenerating telegramium-core

Check out `apimorphism/telegramium` next to this repo, then from this repo's
root run:

```
# re-scrape core.telegram.org/bots/api -> iozhtest/src/main/resources/tgbot.api
IOZHDIR="$PWD" sbt "iozh-api-scraper/runMain TgBotApiScrapper"

# recompile tgbot.api -> <telegramium checkout>/telegramium-core/src/main/scala
# (full rewrite - everything under src/main/scala there is deleted and regenerated)
sbt "iozhik/run iozhik/src/main/resources/tgbotapi.conf <path-to-telegramium>/telegramium-core/src/main/scala"
```

Run the regen step on a clean `telegramium-core` tree and review the diff
before committing. Format and commit the result in **both** repos:

1. Here: commit the refreshed `iozhtest/src/main/resources/tgbot.api` (format
   first, see Code style below) if it changed.
2. In the telegramium checkout: commit the regenerated
   `telegramium-core/src/main/scala` tree (run `sbt fmtAll` there, per its
   CONTRIBUTING.md).

Open a PR in each repo that changed.

(Optional: `nix develop` / `direnv allow` loads a dev shell with these two
steps as `tgbot-scrape` and `tgbot-regen-core` - the latter reads a
`TELEGRAMIUM_DIR` env var instead of a positional path, defaulting to
`../telegramium` - plus a combined `tgbot-refresh`. Not required; the raw
`sbt` commands above work the same without Nix.)

## Code style

Before submitting a PR, run:

```
sbt scalafixAll scalafmtAll scalafmtSbt
sbt iozhik/test iozh-api-scraper/test
```

(Optional: the dev shell's `iozhik-fmt` alias runs the three formatting
commands above.)

Note: the aggregate `sbt test` currently fails because the `iozhtest` module
(generated fixtures for other scraped APIs, e.g. VK) has a pre-existing
compile error unrelated to the Telegram pipeline - `iozhtest/src/main/scala/iozhik/vk/Poll.scala:46`,
"Poll is already defined as case class Poll". That's a separate, existing
problem; scope your own test runs to the modules above rather than relying on
`sbt test`.
