# Agent instructions

This repo is `apimorphism/iozhik`. Agents working here should push branches
and open PRs directly against it, not through a personal fork.

## Remotes and auth

- `origin` must point at `https://github.com/apimorphism/iozhik.git`. There
  should be no `fork` remote - if one exists, it's stale, remove it
  (`git remote remove fork`).
- Use the `GH_TOKEN_APIMORPHISM` token (scoped to the `apimorphism` org) for
  anything that writes to this repo on GitHub - pushing branches, creating
  PRs, commenting. Do not use a personal `GH_TOKEN` for these; it may not have
  write access to `apimorphism/*` at all, and even if it does, commits should
  be attributed through the org-scoped token.
- Create your own branch off `origin/master` (never commit to `master`
  directly), e.g.:
  ```
  git checkout -b <branch> origin/master
  ```

## Pushing and opening PRs

From the dev shell (see below for how to enter it):

```
iozhik-push       # GH_TOKEN="$GH_TOKEN_APIMORPHISM" git push -u origin <current branch>
iozhik-pr-draft   # GH_TOKEN="$GH_TOKEN_APIMORPHISM" gh pr create --repo apimorphism/iozhik --draft --fill
```

Equivalently, without the aliases:

```
GH_TOKEN="$GH_TOKEN_APIMORPHISM" git push -u origin HEAD
GH_TOKEN="$GH_TOKEN_APIMORPHISM" gh pr create --repo apimorphism/iozhik --draft --fill
```

Open PRs as drafts by default; let a human mark them ready for review.

## Dev shell

`direnv allow` (or `nix develop`) loads a shell with `sbt`/`scala`/`gh` and
the aliases above, plus the Telegram Bot API regeneration commands
(`tgbot-scrape`, `tgbot-regen-core`, `tgbot-refresh`) documented in
[CONTRIBUTING.md](CONTRIBUTING.md). Those commands assume a sibling checkout
of `apimorphism/telegramium`; set `TELEGRAMIUM_DIR` if yours isn't at
`../telegramium`.

## Rules

- Never hand-edit generated output outside this repo
  (`apimorphism/telegramium`'s `telegramium-core`) - change the scraper or
  generator here and regenerate instead.
- Run `iozhik-fmt` and `sbt iozhik/test iozh-api-scraper/test` before opening
  a PR (the aggregate `sbt test` is currently broken for an unrelated,
  pre-existing reason - see CONTRIBUTING.md).
