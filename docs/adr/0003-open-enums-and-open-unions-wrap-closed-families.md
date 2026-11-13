# Open enums and open unions wrap closed families

A value Telegram may extend is wrapped where a bot receives it, and plain where a bot sends it. There are two wrappers, because the two kinds hold different things when the value is unknown: `OpenEnum[T]` for string enums (`Known(value)` or `Unknown(value: String)`) and `OpenUnion[T]` for tagged-object families (`Known(value)` or `Unknown(tag, raw: Json)`). The family `T` itself stays closed and sealed.

The aim, in the maintainer's words: no noise on the request path, forced handling on the response path; don't crash first, send unknown values back second. A wrapper on received fields does that: a bot can't reach the value without matching on `Known` and `Unknown`, and request parameters keep the plain type.

`OpenUnion` has a one-way implicit conversion from a plain value to `Known`. Telegram has no input twin of `RichText`, so the cases a bot builds to send have wrapped children, and without the conversion every nested child would need `OpenUnion(...)`. It goes from plain to wrapped only, so it doesn't weaken the forced handling of received values.

## Considered Options

- **One wrapper, `Unknown(value, raw: Json)`**: a string enum would carry its value twice, and JSON for a string.
- **One wrapper with a type parameter for the payload** (`OpenEnum[T, Raw]`, with an alias): the second parameter shows in signatures. No surveyed library does this.
- **One wrapper with two unknown classes**: `OpenEnum[ChatType]` could then hold an object payload.
- **An unknown case inside each family, with a known-only sub-trait** (`MessageEntityUnknown`, `KnownMessageEntity`; what ScalaPB and ktgbotapi do). Spiked; it works and reads cleanly on the response path. Rejected because it moves the extra type to the request path: parameters and user code that names a family for values it sends must say `KnownX`. It is also twice the generator change and breaks telegramium-high's API.
- **An unknown case inside each family, with no sub-trait**: a function that takes `MessageEntity` can't say "known only", and unknown values could be sent from anywhere.
- **Typing request fields as `OpenEnum[T]`** so a bot can send a string this version doesn't know: rejected as unneeded. The library supports one Bot API version and tries not to fail on newer ones, no more.
- **Hiding the `Unknown` constructors** behind a normalising constructor: it guarded the sending of hand-built unknown strings, which isn't supported. They are plain case classes.

Research: the payload split (a string for enums, raw JSON for objects) is the norm; a generic wrapper around an object union is rare, and no surveyed library ships this exact pair ([precedents](../research/two-wrapper-design-precedents.md)). Among Telegramium's public consumers the wrapper is rarely met and nobody has written a helper to hide it ([usage](../research/openenum-consumer-usage.md)).

## Consequences

- A field is wrapped only when its type and the structure holding it are reachable from a method's result. Method parameters and types that are only sent take the plain type: a string enum there is closed (`sendDice.emoji: Option[Emoji]`), so a bot can't send a value this version doesn't know.
- The `OpenEnum` file has no circe import: a string enum's codecs are generated with the enum. `OpenUnion` names `Json` because its unknown value is a JSON object.
- Typing a string field (`Chat.type` as `OpenEnum[ChatType]`) takes an enum in the scraper's header and an override. `chat.type == "private"` then still compiles on Scala 2 with only a warning, and is always false.
- `OpenEnum.Unknown("private")` and `Known(ChatTypePrivate)` are different values that encode the same. Only the decoder is expected to build unknown values, apart from tests.
