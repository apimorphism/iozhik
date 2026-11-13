# How Telegramium's consumers use `OpenEnum`

## 1. Question and scope

The maintainers are choosing between keeping a wrapper for received tagged-object families (today `iozhik.OpenEnum[T]`) and replacing it with an unknown case inside each family plus a known-only sub-trait. The question here is narrower: **how bad and how verbose is wrapper usage in practice today, and how do Telegramium's consumers actually deal with `OpenEnum`?** This note reports evidence only; it does not argue for either design.

Sources:

- the local checkouts of `apimorphism/telegramium` (master at [`68a6b98`](https://github.com/apimorphism/telegramium/tree/68a6b98202618d882b1e72c9a0e70bc2e568b705)) and `apimorphism/iozhik`;
- public repositories that depend on Telegramium, found through GitHub code search and Scaladex and read at their default-branch HEAD (SHAs in the links);
- issues, pull requests and discussions of `apimorphism/telegramium` and `apimorphism/iozhik`.

Researched on 2026-11-13. "Verified" means read in source, history or an API response. "Inferred" means a conclusion drawn from that evidence.

## 2. Summary

- **Few consumers touch `OpenEnum` at all.** Of 35 public consumer repositories found, 21 depend on a version that has `OpenEnum` (9.74.0 or later). **3 of them write `OpenEnum` anywhere**, and one of those 3 is the maintainer's own bot (taskobot-scala). The other 18 never read a wrapped field in a way that needs unwrapping. Verified.
- **Usage sites in consumer code at HEAD: 6.** Three are in main code and three in tests:

  | Category | Sites | Where |
  |---|---|---|
  | discard unknowns (`collect { case Known(...) }`) | 1 | ukrnastup-comments |
  | match known, ignore rest (`case Known(...) => …; case _ =>`) | 1 | greenpanditabot |
  | handle unknown explicitly | 1 | taskobot-scala (maintainer) |
  | wrap to construct (tests) | 2 | taskobot-scala (maintainer) |
  | helper (own extractor, extension, implicit) | **0** | none |
  | unwrap to send | **0** | none |
  | avoid (raw JSON or field ignored to dodge the wrapper) | 0 attributable | see §5.6 |

  Two more wrap-to-send sites existed in taskobot-scala between 9.74.0 and 9.803.0. They were needed because input types were wrapped then, and they went away once input types were unwrapped in 9.900.0. Verified.
- **Verbosity is low where it occurs.**
  - Patterns are at most two deep (`Known(a: ChatMemberAdministrator)`).
  - Every consumer imports `iozhik.OpenEnum.Known` or `iozhik.OpenEnum`, and none writes the fully qualified `iozhik.OpenEnum.Known` inline.
  - The two third-party upgrades cost **2 changed lines plus 1 import** ([ukrnastup-comments `2298e0c`](https://github.com/ElijahLaMoon/ukrnastup-comments/commit/2298e0c50c1092236801bd6887a7e45a2643d9b4)) and **one `Known(...)` around an existing union pattern** (greenpanditabot, written fresh against 10.905.0).
  - The one site that handles `Unknown` explicitly adds one `match` level and one indentation level around an existing 4-case match.

  Verified.
- **No stated friction from users.** All matches for `OpenEnum` in issues and PRs come from the maintainer, about encoding fidelity (#723, iozhik#41) or test infrastructure. No user has complained, asked how to use it, or asked for a helper, and the repository has no discussions. Since mid-2024, 4 issues have been opened by non-maintainers, and none of them concerns `OpenEnum`. Verified.
- **Some code reads wrapped fields without ever matching on them.**
  - `m.forwardOrigin.nonEmpty` in sBots and logging `ChatMemberUpdated` through `toString` in fin-stat read wrapped fields.
  - Two consumers (fin-stat, library-of-our-own) declare their own `entities: List[MessageEntity]` and pass it to `sendMessage` / `editMessageText`.

  None of this changes under Design A. Under Design C, the send-side declarations would have to become `List[KnownMessageEntity]`. Verified for the code, inferred for the effect under C.
- **Inferred overall:** today's wrapper is light in practice because almost nobody looks inside wrapped fields. What consumers do is mostly match a couple of known cases and drop the rest. The surface is 85 wrapped fields across 14 families on master (§3). That is much larger than what consumers exercise, which is ChatMember (2 sites), MessageOrigin (1) and MessageEntity (tests only).

## 3. The library itself

### 3.1 History

- **Introduced in 9.74.0 (tagged 2024-06-20).** The change came in [`d4b5e55` "Use OpenEnum"](https://github.com/apimorphism/telegramium/commit/d4b5e5501557f94f6a3095a11a6feed58e8b1d53) on 2024-06-19, and README docs followed in [`1ed86bb`](https://github.com/apimorphism/telegramium/commit/1ed86bb) on 2024-06-20. On the generator side, iozhik has `5756f55` "Prototype OpenEnum" (2024-06-02) and `dc528fa` "Add GeneratorConfig/GeneratorMeta; Use OpenEnum for API result types only" (2024-06-16). Every tag from 9.74.0 on contains it. Verified.
- **The shape has never changed.** Released and master share it, and it differs slightly from the sketch in the brief: `Unknown` is generic and is not `OpenEnum[Nothing]`. The file's only later change was scaladoc. Verified ([OpenEnum.scala](https://github.com/apimorphism/telegramium/blob/68a6b98202618d882b1e72c9a0e70bc2e568b705/telegramium-core/src/main/scala/iozhik/OpenEnum.scala)):

  ```scala
  sealed trait OpenEnum[+T]
  object OpenEnum {
    case class Known[T](value: T)        extends OpenEnum[T]
    case class Unknown[T](value: String) extends OpenEnum[T]
    def apply[T](value: T): OpenEnum[T] = Known(value)
    implicit def encoder[T: Encoder]: Encoder[OpenEnum[T]] = { ... case Unknown(value) => Encoder[String].apply(value) }
  }
  ```

  The decoders read the `type` discriminator and map unrecognised tags to `Unknown(tag)`, so the rest of the object is dropped ([package.scala L2315](https://github.com/apimorphism/telegramium/blob/68a6b98202618d882b1e72c9a0e70bc2e568b705/telegramium-core/src/main/scala/telegramium/bots/package.scala#L2315)). Only tagged-object families are wrapped. String enums such as `Chat.type` are plain `String`.
- **Input types were wrapped at first, then unwrapped.** From 9.74.0 to 9.803.0, request-side types also carried `OpenEnum[MessageEntity]`: 14 fields in `InlineQueryResult*`, 5 in `InputMedia*`, and fields in `InputTextMessageContent`, `ReplyParameters.quoteEntities` and `InputPollOption`. Method parameters (`sendMessage(entities: List[MessageEntity])`) were plain throughout. 9.900.0 ([`b4a14b3`](https://github.com/apimorphism/telegramium/commit/b4a14b3), Bot API 9.0, 2025-04-21) made those input fields plain (`captionEntities: List[iozhik.OpenEnum[MessageEntity]]` → `List[MessageEntity]`). Verified by diff.
- **Rich text, added in 10.1002.0/10.1003.x (Bot API 10.1–10.3), brought the wrapper back on the send side.** `RichText` variants nest `OpenEnum[RichText]`, and `InputRichBlockParagraph(text: RichText)` and similar take `RichText`. Building a nested rich text to send therefore needs `OpenEnum(...)` around the inner texts. The same nesting is behind [#723](https://github.com/apimorphism/telegramium/issues/723). Verified in source. No consumer builds rich text this way (§4).

### 3.2 Wrapped fields

Counted as occurrences of `OpenEnum[X]` in telegramium-core case-class sources, with codecs (`package.scala`) excluded. Verified:

| Version | Wrapped fields | Families | Breakdown |
|---|---:|---:|---|
| 9.74.0 | 48 | 8 | MessageEntity 29 (20 of them in input types), ChatMember 6, ReactionType 4, MessageOrigin 2, MenuButton 2, ChatBoostSource 2, BackgroundFill 2, BackgroundType 1 |
| 9.803.0 | 53 | 11 | + TransactionPartner 2, PaidMedia 2, RevenueWithdrawalState 1 |
| 10.1000.0 | 39 | 12 | MessageEntity drops to 14 (input types unwrapped in 9.900.0); + OwnedGift 1 |
| 10.1003.2 / master | **85** | **14** | **RichText 39**, **MessageEntity 15**, RichBlock 6, ChatMember 6, ReactionType 4, TransactionPartner 2, PaidMedia 2, MessageOrigin 2, MenuButton 2, ChatBoostSource 2, BackgroundFill 2, RevenueWithdrawalState 1, OwnedGift 1, BackgroundType 1 |

On master, `Message` alone has three wrapped fields: `forwardOrigin: Option[OpenEnum[MessageOrigin]]`, `entities` and `captionEntities: List[OpenEnum[MessageEntity]]` ([MaybeInaccessibleMessage.scala L319-L356](https://github.com/apimorphism/telegramium/blob/68a6b98202618d882b1e72c9a0e70bc2e568b705/telegramium-core/src/main/scala/telegramium/bots/MaybeInaccessibleMessage.scala#L319-L356)). `getChatMember` returns `OpenEnum[ChatMember]` and `getChatAdministrators` returns `List[OpenEnum[ChatMember]]`.

### 3.3 The library's own modules

Counts on master `68a6b98`. Verified:

| Module | `OpenEnum` | `Known(` | `Unknown(` |
|---|---:|---:|---:|
| telegramium-high main | 0 | 0 | 0 |
| telegramium-high tests | 11 | 1 | 0 |
| telegramium-examples main | 2 | 1 | 0 |

- **Examples: one site, of the discard-unknowns kind.** [UrlShortenerBot.scala L32](https://github.com/apimorphism/telegramium/blob/68a6b98202618d882b1e72c9a0e70bc2e568b705/telegramium-examples/src/main/scala/telegramium/bots/examples/UrlShortenerBot.scala#L32):

  ```scala
  url  <- msg.entities.collectFirst { case OpenEnum.Known(UrlMessageEntity(offset, length)) =>
    text.slice(offset, offset + length)
  }
  ```

  The 9.74.0 migration of this line was a one-token change, from `case UrlMessageEntity(...)` to `case OpenEnum.Known(UrlMessageEntity(...))`, plus an import ([`d4b5e55`](https://github.com/apimorphism/telegramium/commit/d4b5e5501557f94f6a3095a11a6feed58e8b1d53)).
- **High tests: wrap to construct, 8 sites.** For example [LongPollBotISpec.scala L71-L72](https://github.com/apimorphism/telegramium/blob/68a6b98202618d882b1e72c9a0e70bc2e568b705/telegramium-high/src/test/scala/telegramium/bots/high/LongPollBotISpec.scala#L71-L72) `OpenEnum(ChatMemberMember(testUser))` and [L354](https://github.com/apimorphism/telegramium/blob/68a6b98202618d882b1e72c9a0e70bc2e568b705/telegramium-high/src/test/scala/telegramium/bots/high/LongPollBotISpec.scala#L354) `ChatBoost("", 0, 0, OpenEnum(ChatBoostSourcePremium(testUser)))`. WebhookBotISpec has the same pattern at L86-87, L446 and L458.
- **High tests: one generic unwrap**, in reflective wire-check code ([WireSpec.scala L167](https://github.com/apimorphism/telegramium/blob/68a6b98202618d882b1e72c9a0e70bc2e568b705/telegramium-high/src/test/scala/telegramium/bots/high/WireSpec.scala#L167)): `case iozhik.OpenEnum.Known(v) => List(node.copy(value = v, field = None))`, treated like `Some(v)`.
- **telegramium-high main has no `OpenEnum`.** Its `MessageEntities` builder returns plain `List[MessageEntity]` for sending ([MessageEntities.scala L45](https://github.com/apimorphism/telegramium/blob/68a6b98202618d882b1e72c9a0e70bc2e568b705/telegramium-high/src/main/scala/telegramium/bots/high/messageentities/MessageEntities.scala#L45)). It offers no helper for received wrapped values.

### 3.4 Documentation

- The README has a prose section, "OpenEnum" ([README.md L90-L94](https://github.com/apimorphism/telegramium/blob/68a6b98202618d882b1e72c9a0e70bc2e568b705/README.md#L90-L94)). It explains why the wrapper exists and that it has `Known` and `Unknown`, "similar to `Option` or `Either`". It gives **no code example**, says nothing about unwrapping before sending, and names no helper. Verified.
- The only usage example anywhere is UrlShortenerBot (above). Verified.

## 4. Consumers found

The 21 consumers on 9.74.0 or later are listed below. For each, the "how" column was checked by grepping the whole checked-out Scala source for `OpenEnum`, `iozhik`, every wrapped family name and the field names that hold them (`entities`, `captionEntities`, `forwardOrigin`, `newChatMember`, `oldChatMember`, `newReaction`, `getChatMember`, `getChatAdministrators`, …), and then reading each hit.

| Repo | ★ | Last push | Telegramium | Touches `OpenEnum` | How |
|---|---:|---|---|---|---|
| [johnspade/taskobot-scala](https://github.com/johnspade/taskobot-scala/tree/995a7b9a0b39d72c02a7161f50df4a225ed925fe) (maintainer) | 10 | 2026-10-30 | 10.1003.0 | yes | handles `Unknown` explicitly (1); wrap to construct in tests (2); earlier wrap to send (2, removed) |
| [ElijahLaMoon/ukrnastup-comments](https://github.com/ElijahLaMoon/ukrnastup-comments/tree/1854bf974847cb80961d5492695a42fb9027879c) | 2 | 2024-10-01 | 9.710.0 | yes | discard unknowns: `collect { case Known(a: …) }` |
| [cadcc/greenpanditabot](https://github.com/cadcc/greenpanditabot/tree/9655539507eed39dec4a0dda7cfe332a589acfae) | 0 | 2026-04-11 | 10.905.0 | yes | match known, ignore rest |
| [benkio/sBots](https://github.com/benkio/sBots/tree/443355aa35ef32b510fdb4e0c0383a7de9b5d50b) | 2 | 2026-11-13 | 10.1003.2 | no | reads `m.forwardOrigin.nonEmpty` only; sends rich messages as Markdown strings |
| [a-khakimov/fin-stat](https://github.com/a-khakimov/fin-stat/tree/54f8b6a116d822e33efbea4c6d70dc5c5ad650ed) | 1 | 2024-09-08 | 9.77.0 | no | logs `ChatMemberUpdated` via `toString`; own send-side `entities: List[MessageEntity]` |
| [Kapibarabanka/library-of-our-own](https://github.com/Kapibarabanka/library-of-our-own/tree/00960260330029166a80238cc522c96fab4cbe5d) | 2 | 2026-07-26 | 9.77.0 | no | own send-side `entities: List[MessageEntity]` passed to `sendMessage`/`editMessageText` |
| johnspade/s10ns_bot (maintainer) | 8 | 2026-10-30 | 9.74.0 | no | 50 files use Telegramium; no wrapped field read |
| johnspade/tgbot-utils (maintainer, library) | 8 | 2024-06-20 | 9.74.0 | no | callback-data utilities only |
| rleibman/jorlan | 4 | 2026-09-14 | 10.1002.0 | no | — |
| jpaju/seat-stalker | 2 | 2026-11-08 | 10.1002.0 | no | entities only in a JSON test fixture |
| wolfrepos/BendingUnit22 | 2 | 2025-06-05 | 9.900.0 | no | — |
| Armeddon/Inline-Typst-Bot | 2 | 2025-07-20 | 9.802.0 | no | — |
| merlijn/scala-llm-api | 2 | 2024-07-31 | 9.77.0 | no | — |
| dinaraparanid/Password-Manager | 1 | 2025-01-08 | 9.802.0 | no | one file uses Telegramium (bot4s in tests) |
| zaquest/tgchat | 0 | 2025-06-24 | 9.900.0 | no | — |
| Manykeys/Scala-Telegram-Notifier | 0 | 2025-08-30 | 9.803.0 | no | — |
| IvanTrofimov052/ArticleNotificationBot | 0 | 2025-05-08 | 9.801.0 | no | — |
| dartt0n/sclaus | 0 | 2024-12-13 | 9.801.0 | no | — |
| B1bizka/Resume | 0 | 2025-07-03 | 9.77.0 | no | — |
| delirium4/RandomDnDEventGeneratorBot | 0 | 2025-01-30 | 9.77.0 | no | — |
| kirdinDaniil/Dnd-character-telegram-bot | 0 | 2025-01-30 | 9.77.0 | no | — |

These consumers were also found, but **never met the wrapper**: their Telegramium is older than 9.74.0, or its version is unknown.

- **Older than 9.74.0 (13):**
  - wolfrepos/gdetram 7.57.0 (★6)
  - wolfrepos/playcs 7.62.0 (★8)
  - a-khakimov/undicator 7.62.0
  - a-khakimov/tg-bot-template 7.62.0
  - XGZzs0ocFyf1/zbot 7.66.0
  - johnspade/nastenka 8.67.0
  - frog-da/WeatherBotProject 8.69.0
  - nikolaiser/tldr-bot 8.69.0
  - vanichvi/cinemotion_bot 8.69.0
  - vitaliihonta/zio-temporal-samples 8.69.0 (★6)
  - Undiy/just-another-ai-bot 8.74.0
  - a-khakimov/tinvest-bot 2.49.0
  - b1nd/stonks 2.50.0
- **Version unknown (1):** ArtemTelevnoy/Tg-Bot has no build file in the repository.

Observations (inferred):

- **The ecosystem is small and low-star.** The most-starred consumer has 10 stars and is the maintainer's own. Only 7 of the 21 have been pushed since 2026-01-01.
- **Most bots are text- and callback-driven.** They read `msg.text` and callback data and never look at entities, chat members, reactions or origins. Unwrapping is rare for that reason, not because of avoidance.

## 5. Usage categories with snippets

All counts are sites in consumer code at HEAD, unless marked historical. Library-internal sites are in §3.3.

### 5.1 Discard unknowns: 1 consumer site (plus 1 in telegramium-examples)

[ukrnastup-comments CommentsBot.scala L206-L209](https://github.com/ElijahLaMoon/ukrnastup-comments/blob/1854bf974847cb80961d5492695a42fb9027879c/comments/src/main/scala/org/ukrnastup/comments/CommentsBot.scala#L206-L209):

```scala
import iozhik.OpenEnum.Known
...
val tgAdminsAsUsers = tgAdmins.collect {
  case Known(a: ChatMemberAdministrator) => a.user
  case Known(o: ChatMemberOwner)         => o.user
}
```

The pattern is two deep and the import is aliased. The upgrade diff ([`2298e0c` "fix after upgrading telegramium"](https://github.com/ElijahLaMoon/ukrnastup-comments/commit/2298e0c50c1092236801bd6887a7e45a2643d9b4), 8.71.0 → 9.77.0) changed exactly these two lines, and the import was added in the neighbouring commit `ba03284`. The `collect` was already there before the wrapper existed, because `getChatAdministrators` returns several ChatMember kinds.

### 5.2 Match known, ignore rest: 1 site

[greenpanditabot TelegramBot.scala L120-L126](https://github.com/cadcc/greenpanditabot/blob/9655539507eed39dec4a0dda7cfe332a589acfae/src/main/scala/cl/cadcc/greenpandita/TelegramBot.scala#L120-L126):

```scala
import iozhik.OpenEnum.Known
...
private def authenticate(msg: MessageData): F[Boolean] =
  Methods.getChatMember(authorizedGroup, msg.user.id)
    .exec
    .map {
      case Known(_ : ChatMemberMember | _ : ChatMemberAdministrator | _ : ChatMemberOwner) => true
      case _ => false
    }
```

This file was written fresh on 10.905.0 in [`0854c44`](https://github.com/cadcc/greenpanditabot/commit/0854c44a9325fc47ba6792517073175c13ae81be), so there is no upgrade diff. The `case _ => false` would be needed even without the wrapper, because `ChatMemberLeft`, `ChatMemberBanned` and `ChatMemberRestricted` exist.

### 5.3 Handle unknown explicitly: 1 site (maintainer's bot)

[taskobot-scala Taskobot.scala L133-L144](https://github.com/johnspade/taskobot-scala/blob/995a7b9a0b39d72c02a7161f50df4a225ed925fe/src/main/scala/ru/johnspade/taskobot/Taskobot.scala#L133-L144):

```scala
def handleForward() =
  msg.forwardOrigin.fold(None): originOpen =>
    originOpen match
      case OpenEnum.Unknown(_) => Some(ZIO.some(sendMessage(ChatIntId(msg.chat.id), Errors.NotSupported)))
      case OpenEnum.Known(origin) =>
        val (senderName, forwardFromId) = origin match
          case MessageOriginUser(_, senderUser) => ...
          case MessageOriginChannel(_, chat, _, _)        => ...
          case MessageOriginHiddenUser(_, senderUserName) => ...
          case MessageOriginChat(_, senderChat, _) => ...
        for ... // ~25 more lines, all re-indented one level
```

This is the only site in any consumer that acts on `Unknown`. It replies "This message is not supported.", and `Errors.NotSupported` was added in the same upgrade.

- **Upgrade cost.** The 9.74.0 upgrade ([`9fe8f49`](https://github.com/johnspade/taskobot-scala/commit/9fe8f498aea2ebec0caaf803231db4fd735e5eb2)) rewrote a flat `for` comprehension (`forwardOrigin <- msg.forwardOrigin; (senderName, …) = forwardOrigin match {…}`) into `fold` + `match` + inner `match`. That diff touches ~40 lines, almost all re-indentation. The net additions are 3 lines: the `fold`, the outer `match` and the `Unknown` branch.
- **Nesting.** One extra match level.

### 5.4 Wrap to construct: 2 consumer sites (tests), 8 in telegramium-high tests

- [taskobot-scala TestHelpers.scala L32-L33](https://github.com/johnspade/taskobot-scala/blob/995a7b9a0b39d72c02a7161f50df4a225ed925fe/src/test/scala/ru/johnspade/taskobot/TestHelpers.scala#L32-L33):

  ```scala
  entities =
    if (isCommand) List(OpenEnum(BotCommandMessageEntity(offset = 0, length = text.length))) else List.empty,
  ```

  Before the upgrade this was one line without `OpenEnum(...)`. scalafmt split it over two lines.
- [taskobot-scala TaskobotISpec.scala L269](https://github.com/johnspade/taskobot-scala/blob/995a7b9a0b39d72c02a7161f50df4a225ed925fe/src/test/scala/ru/johnspade/taskobot/TaskobotISpec.scala#L269): `forwardOrigin = OpenEnum(MessageOriginUser(0, johnTg)).some`.

### 5.5 Wrap to send: 2 historical sites, now 0

Between 9.74.0 and 9.803.0, request-side types were wrapped (§3.1). taskobot-scala therefore had to wrap the entities it built itself, in [`9fe8f49`](https://github.com/johnspade/taskobot-scala/commit/9fe8f498aea2ebec0caaf803231db4fd735e5eb2), in an inline-query answer (`Taskobot.scala` L71 at that commit) and in a test:

```scala
inputMessageContent = InputTextMessageContent(
  messageEntities.toPlainText(),
  entities = messageEntities.toTelegramEntities().map(OpenEnum(_))
),
```

Both `.map(OpenEnum(_))` calls were deleted in `e924e6a` (2026-05-28) after the move past 9.900.0. Verified. Today a site like this exists only for rich text (`InputRichBlock*` taking `RichText` with nested `OpenEnum[RichText]`), and no consumer builds rich text from blocks: sBots sends `InputRichMessage(markdown = …)`.

### 5.6 Unwrap to send, helper, avoid: 0 sites each

- **Unwrap to send: 0.** No consumer echoes received entities, quotes (`TextQuote.entities` → `ReplyParameters.quoteEntities`), reactions or rich text back to Telegram.
- **Helper: 0.** No consumer defines an extension method, extractor, implicit conversion or type alias for `OpenEnum`. telegramium-high has none either.
- **Avoid: none attributable.** Three consumers touch wrapped fields without unwrapping, all of which work unchanged with the wrapper:
  - sBots `isForward = m.forwardOrigin.nonEmpty` ([MessageConversions.scala L47](https://github.com/benkio/sBots/blob/443355aa35ef32b510fdb4e0c0383a7de9b5d50b/modules/chatTelegramAdapter/src/main/scala/com/benkio/chattelegramadapter/conversions/MessageConversions.scala#L47));
  - fin-stat `logger.info(s"ChatMember - $chatMember")` ([LongPollBot.scala L79-L83](https://github.com/a-khakimov/fin-stat/blob/54f8b6a116d822e33efbea4c6d70dc5c5ad650ed/RublePulseBot/src/main/scala/org/github/ainr/telegram/LongPollBot.scala#L79-L83));
  - jpaju/seat-stalker, with entities only inside a JSON fixture.

  Nothing suggests these were written to dodge the wrapper. Inferred.
- **Send-side plain types (relevant to Design C): 2 consumers.**
  - library-of-our-own declares `entities: List[MessageEntity]` in its own `MessageData` ([MessageData.scala L10](https://github.com/Kapibarabanka/library-of-our-own/blob/00960260330029166a80238cc522c96fab4cbe5d/lo3common/src/main/scala/models/tg/MessageData.scala#L10)) and passes it to `sendMessage` ([MyBotApi.scala L41](https://github.com/Kapibarabanka/library-of-our-own/blob/00960260330029166a80238cc522c96fab4cbe5d/lo3common/src/main/scala/services/MyBotApi.scala#L41)).
  - fin-stat does the same in `EditMessage.entities`.

## 6. Stated friction

- **Issues and PRs, telegramium.** Searches for `OpenEnum` and "Unknown enum" across all states (with `GH_TOKEN_APIMORPHISM`) return the following. Every one is by the maintainer (johnspade) and none is a usage complaint:
  - [#723](https://github.com/apimorphism/telegramium/issues/723) ("A nested OpenEnum.Unknown rich text sent back encodes as its bare tag"), a fidelity bug and the origin of this design question;
  - PR [#724](https://github.com/apimorphism/telegramium/pull/724);
  - #692, #689, #698, #714, PRs #713, #687, #716, #664 (test infrastructure and drift).
- **Issues and PRs, iozhik.** PRs [#41](https://github.com/apimorphism/iozhik/pull/41), #33 and #13, all by the maintainer. There are no issues.
- **Other users.** Since 2024-05-01, non-maintainers have opened 4 issues (#686 and #653 by benkio, #493 by merlijn, #483 by lolgab) and no PRs other than bot-generated ones. None mentions `OpenEnum`, `Known` or unknown values.
- **Discussions.** None: `discussions.totalCount = 0`.
- **Upgrade diffs.** These are the only migration evidence:
  - ukrnastup-comments: 2 lines plus an import (§5.1). The commit message, "fix after upgrading telegramium", is neutral.
  - taskobot-scala: 3 net lines, ~40 re-indented, 3 wraps in tests and 1 wrap to send (§5.3–5.5).
  - telegramium-examples: 1 line.

  No consumer left a comment or commit message expressing difficulty. Verified.

## 7. Samples rewritten

**Design A** keeps today's code for object families, with `OpenEnum` renamed to `OpenUnion` and `Unknown(tag, raw)`. **Design C** has no wrapper: received fields are typed `MessageEntity` / `ChatMember` / `MessageOrigin`, each family has `…Unknown(tag, raw)` and a `Known…` sub-trait, and requests take `Known…`.

### 7.1 Discard unknowns (ukrnastup-comments)

```scala
// Today
import iozhik.OpenEnum.Known
tgAdmins.collect {
  case Known(a: ChatMemberAdministrator) => a.user
  case Known(o: ChatMemberOwner)         => o.user
}

// Design A
import iozhik.OpenUnion.Known
tgAdmins.collect {
  case Known(a: ChatMemberAdministrator) => a.user
  case Known(o: ChatMemberOwner)         => o.user
}

// Design C (identical to this repo's pre-9.74 code, per the 2298e0c diff)
tgAdmins.collect {
  case a: ChatMemberAdministrator => a.user
  case o: ChatMemberOwner         => o.user
}
```

Design C saves the import and one pattern level on each line. The line count is the same in all three.

### 7.2 Match known, ignore rest (greenpanditabot)

```scala
// Today
import iozhik.OpenEnum.Known
.map {
  case Known(_ : ChatMemberMember | _ : ChatMemberAdministrator | _ : ChatMemberOwner) => true
  case _ => false
}

// Design A: same, with OpenUnion.Known

// Design C
.map {
  case _ : ChatMemberMember | _ : ChatMemberAdministrator | _ : ChatMemberOwner => true
  case _ => false
}
```

Design C again saves the import and one pattern level. The `case _` stays in every design, because it also covers the known non-member kinds.

### 7.3 Handle unknown explicitly (taskobot-scala)

```scala
// Today
msg.forwardOrigin.fold(None): originOpen =>
  originOpen match
    case OpenEnum.Unknown(_) => Some(ZIO.some(sendMessage(ChatIntId(msg.chat.id), Errors.NotSupported)))
    case OpenEnum.Known(origin) =>
      val (senderName, forwardFromId) = origin match
        case MessageOriginUser(_, senderUser)           => ...
        case MessageOriginChannel(_, chat, _, _)        => ...
        case MessageOriginHiddenUser(_, senderUserName) => ...
        case MessageOriginChat(_, senderChat, _)        => ...
      for ... // body

// Design A
msg.forwardOrigin.fold(None): originOpen =>
  originOpen match
    case OpenUnion.Unknown(_, _) => Some(ZIO.some(sendMessage(ChatIntId(msg.chat.id), Errors.NotSupported)))
    case OpenUnion.Known(origin) =>
      val (senderName, forwardFromId) = origin match
        ... // unchanged

// Design C
msg.forwardOrigin.fold(None):
  case _: MessageOriginUnknown => Some(ZIO.some(sendMessage(ChatIntId(msg.chat.id), Errors.NotSupported)))
  case origin: KnownMessageOrigin =>
    val (senderName, forwardFromId) = origin match
      ... // unchanged, still exhaustive over the four known cases
    for ... // body
```

- **Design A** costs one extra `_` in the `Unknown` pattern (`Unknown(_, _)`) for this repository.
- **Design C** saves one line (`originOpen match`) and one indentation level. It still needs the two-stage match unless the unknown branch can be folded into the inner match. Here it cannot, because the branches produce different types.

Under C, a match on `MessageOrigin` that omits `MessageOriginUnknown` gets the same non-exhaustive warning that today's match omitting `Unknown` gets. Inferred from sealed-trait semantics.

### 7.4 Wrap to construct (taskobot-scala tests)

```scala
// Today
entities =
  if (isCommand) List(OpenEnum(BotCommandMessageEntity(offset = 0, length = text.length))) else List.empty,
forwardOrigin = OpenEnum(MessageOriginUser(0, johnTg)).some

// Design A
entities =
  if (isCommand) List(OpenUnion(BotCommandMessageEntity(offset = 0, length = text.length))) else List.empty,
forwardOrigin = OpenUnion(MessageOriginUser(0, johnTg)).some

// Design C (identical to the pre-9.74 code in 9fe8f49's diff)
entities = if (isCommand) List(BotCommandMessageEntity(offset = 0, length = text.length)) else List.empty,
forwardOrigin = MessageOriginUser(0, johnTg).some
```

### 7.5 Send-side declaration (library-of-our-own; same in fin-stat)

```scala
// Today and Design A: no change
case class MessageData(text: String, ..., entities: List[MessageEntity] = List.empty, ...)
Methods.sendMessage(ChatStrId(chatId), msg.text, ..., msg.entities, ...)

// Design C: sendMessage takes List[KnownMessageEntity]
case class MessageData(text: String, ..., entities: List[KnownMessageEntity] = List.empty, ...)
```

Design C makes this worse: it forces a source change in code that never touched `OpenEnum`. The change is mechanical, but it fails to compile until made. Values built with telegramium-high's `MessageEntities.toTelegramEntities()` would type as `List[KnownMessageEntity]` once that method's return type follows, so they need no change. Inferred.

A hypothetical unwrap-to-send site, such as quoting `TextQuote.entities` back via `ReplyParameters.quoteEntities`, has no consumer instance. It would read `collect { case Known(e) => e }` today and in Design A, and `collect { case e: KnownMessageEntity => e }` in Design C, the same length.

## 8. Open or unverified points

- **Completeness of the search.**
  - What worked:
    - GitHub code search: about 25 queries. They covered `iozhik.OpenEnum`, `OpenEnum.Known`, `OpenEnum.Unknown`, `import iozhik`, `OpenEnum(`, `OpenEnum Known`, and `telegramium` in `build.sbt`, `build.sc`, `build.mill`, `Dependencies.scala`, `Deps.scala`, `Versions.scala`, `project.scala` and `build.gradle`. They also covered `telegramium-core`, `telegramium-high`, `telegramium.bots.high`, and `import telegramium.bots` paginated to 300 results. The rate limit is 10 per minute; failed queries were retried.
    - Scaladex, which lists 1 dependent (johnspade/tgbot-utils).
  - What did not:
    - The GitHub dependents page shows 0 repositories, because sbt is not supported.
    - Sourcegraph public search returned 0 matches for every query.
    - grep.app was behind a Vercel challenge.
  - What GitHub code search misses: it indexes default branches only, skips forks with fewer stars than their parent, and skips large files. Private and closed-source bots are invisible.
- **Repositories examined.**
  - 43 distinct non-apimorphism repositories surfaced.
  - 7 of them are not consumers but index or list files: aboutcode-data/purls-maven-0896, ArjunDandagi/search-maven-org, veerakrishna222/search-maven-org, mehdikeshani/LightWeightCGs, noti0na1/dynamic-eval-scala-3, scalacenter/scala3-binary-decoder and xuhongxu96/bazel-repos.
  - No telegramium forks or vendored copies surfaced, so 0 were excluded on that ground (the repository has 9 forks).
  - Of the 36 left, 35 are consumers and 1 is apimorphism/iozhik.
  - All 21 on 9.74.0 or later were cloned (blobless, sparse checkout of Scala and build files) and grepped in full. The 14 older or unknown-version consumers were not read beyond their build files.
- **HEAD only.** Code was read at default-branch HEAD. Upgrade history was examined only for the three repositories that touch `OpenEnum` and for telegramium-examples. A consumer that used `OpenEnum` in the past and later removed the code would be missed, except for taskobot-scala, whose history was read.
- **Text-command bots.** Most of the 18 non-touching consumers parse commands from `msg.text` and never read `entities`. Inferred from handler names and field greps; not every file was read line by line.
- **Rich text.** The send-side wrapping in rich text, added in 10.1002/10.1003, is too recent to show up in consumers. Only sBots is on 10.1003.2, and it sends Markdown. The lack of friction evidence there says nothing about future rich-text users.
- **Maintainer weight.** Three of the 21 relevant consumers are the maintainer's own (taskobot-scala, s10ns_bot, tgbot-utils), as is the only explicit-`Unknown` site. Third-party evidence is therefore 2 sites in 2 small repositories.
