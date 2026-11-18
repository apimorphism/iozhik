# One OpenEnum wraps open enums and open unions alike

A value Telegram may extend is wrapped where a bot receives it, and plain where a bot sends it. One wrapper covers both kinds: `OpenEnum[T]`, either `Known(value)` or `Unknown(value: String, raw: Json)`. For a string enum `value` is the string; for a family of objects told apart by a type tag it is the tag. `raw` is the JSON the value was decoded from, which it encodes back to (ADR-0002). The family `T` itself stays closed and sealed.

The aim, in the maintainer's words: no noise on the request path, forced handling on the response path; don't crash first, send unknown values back second. A wrapper on received fields does that: a bot can't reach the value without matching on `Known` and `Unknown`, and request parameters keep the plain type.

Both cases are plain case classes, so `OpenEnum` is an ADT that derivation (magnolia, kittens, Scala 3 `Mirror`) can see into, with the usual `equals`, `toString` and `copy`. The price is a second field in the pattern: a bot's `case Unknown(value)` becomes `case Unknown(value, _)`, which the compiler reports at each site.

`OpenEnum` has a one-way implicit conversion from a plain value to `Known`. Telegram has no input twin of `RichText`, so the cases a bot builds to send have wrapped children, and without the conversion every nested child would need `OpenEnum(...)`. It goes from plain to wrapped only, so it doesn't weaken the forced handling of received values.

## Considered Options

- **Two wrappers**, `OpenEnum[T]` with `Unknown(value: String)` for string enums and `OpenUnion[T]` with `Unknown(tag, raw: Json)` for families of objects. Each type says exactly what it holds. Rejected because every bot would have to rename the wrapper of object families, which `OpenEnum` has always been, to buy precision nobody reads: a bot has no use for `raw` of a string enum, and duplicating a short string costs nothing.
- **`raw` in a second parameter list**, `Unknown(value)(raw)`, so a match keeps reading `case Unknown(value)`. Spiked; it works, but a case class leaves a second list out of `equals`, `hashCode`, `toString` and the pattern, so each needs care, and `copy` needs both lists. Too many gotchas for one field in patterns.
- **A plain class with a hand-written one-field `unapply`**: keeps `case Unknown(value)` with exhaustiveness intact (a case class's own `unapply` can't be replaced on Scala 2.12). Rejected because a case that isn't a case class hides `OpenEnum` from derivation.
- **`raw` private to `iozhik`**: keeps the type precise for string enums. Rejected because tests read it, and a pattern binds it anyway.
- **A type parameter for the payload** (`OpenEnum[T, Raw]`, with an alias): the second parameter shows in signatures. No surveyed library does this.
- **An unknown case inside each family, with a known-only sub-trait** (`MessageEntityUnknown`, `KnownMessageEntity`; what ScalaPB and ktgbotapi do). Spiked; it works and reads cleanly on the response path. Rejected because it moves the extra type to the request path: parameters and user code that names a family for values it sends must say `KnownX`. It is also twice the generator change and breaks telegramium-high's API.
- **An unknown case inside each family, with no sub-trait**: a function that takes `MessageEntity` can't say "known only", and unknown values could be sent from anywhere.
- **Typing request fields as `OpenEnum[T]`** so a bot can send a string this version doesn't know: rejected as unneeded. The library supports one Bot API version and tries not to fail on newer ones, no more.
- **Hiding the `Unknown` constructor**: it would guard the sending of hand-built unknown values, which isn't supported, and push tests to decode JSON literals instead. It is a plain case class.

Research: most libraries keep a string for an unknown enum value and raw JSON for an unknown object; a generic wrapper around an object family is rare. Among Telegramium's public consumers the wrapper is rarely met and nobody has written a helper to hide it.

## Consequences

- A field is wrapped only when its type and the structure holding it are reachable from a method's result. `MaybeInaccessibleMessage` is the exception: its two cases are told apart by `date`, not by a type tag, so Telegram can't add a third that way, and it stays plain. Method parameters and types that are only sent take the plain type: a string enum there is closed, as `ParseMode` is, so a bot can't send a value this version doesn't know. `ReplyParameters.quoteRichTexts` is the exception, named in the generator's `include-fields`: Telegram wants a quote's rich texts back one for each and nested as received, so an unknown one has to be sendable, or the quote fails. With the lift, a list a bot writes still takes plain values.
- An Unknown value of a string enum holds its string twice, as `value` and as a JSON string in `raw`.
- No field of the Bot API is an open string enum yet: the fields that hold one, such as `Chat.type` and `Dice.emoji`, are still a `String`. The generator supports it, and apimorphism/iozhik#43 types those fields in one release, once the shape of a generated string enum is settled, so a bot's code breaks once and not twice. When it does, `chat.type == "private"` still compiles on Scala 2 with only a warning, and is always false.
- An Unknown value and the `Known` value of the same string or tag are different values that may encode the same. Only the decoder is expected to build Unknown values, apart from tests.
