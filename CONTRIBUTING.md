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

Check out `apimorphism/telegramium` next to this repo (or point
`TELEGRAMIUM_DIR` at it), enter the dev shell (`direnv allow` or
`nix develop`), and run:

```
tgbot-scrape       # re-scrapes core.telegram.org/bots/api -> iozhtest/src/main/resources/tgbot.api
tgbot-regen-core   # recompiles tgbot.api -> $TELEGRAMIUM_DIR/telegramium-core/src/main/scala (full rewrite)
# or both at once:
tgbot-refresh
```

`tgbot-regen-core` deletes and rewrites everything under
`telegramium-core/src/main/scala` in the target checkout, so run it on a clean
tree and review the diff before committing. Format and commit the result in
**both** repos:

1. Here: commit the refreshed `iozhtest/src/main/resources/tgbot.api` (run
   `iozhik-fmt` first) if it changed.
2. In the telegramium checkout: commit the regenerated
   `telegramium-core/src/main/scala` tree (run `sbt fmtAll` there, per its
   CONTRIBUTING.md).

Open a PR in each repo that changed.

## Code style

Before submitting a PR, run `iozhik-fmt` (`scalafixAll`, `scalafmtAll`,
`scalafmtSbt`) and make sure `sbt iozhik/test iozh-api-scraper/test` passes.

Note: the aggregate `sbt test` currently fails because the `iozhtest` module
(generated fixtures for other scraped APIs, e.g. VK) has a pre-existing
compile error unrelated to the Telegram pipeline - `iozhtest/src/main/scala/iozhik/vk/Poll.scala:46`,
"Poll is already defined as case class Poll". That's a separate, existing
problem; scope your own test runs to the modules above rather than relying on
`sbt test`.
