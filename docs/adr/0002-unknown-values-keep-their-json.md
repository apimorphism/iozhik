# Unknown values keep the JSON they were decoded from

An unknown value of a tagged-object family holds the JSON Telegram sent along with its tag (`OpenEnum.Unknown(tag, raw)`) and encodes back to it. It used to keep only the tag and encode that as a bare string, which a rich text reads as plain text (apimorphism/telegramium#723). telegramium-core is generated API types that mirror the wire, so decoding and then encoding must give back what Telegram sent. Policy about content a bot can't read belongs in telegramium-high, if anywhere.

An unknown value of a string enum holds its string, and the same string as JSON: the string is the whole wire value. [ADR-0003](0003-one-openenum-for-strings-and-objects.md) records why both kinds share one type.

## Considered Options

- **An input family**: request types with no wrapper, plus a conversion that fails on the first Unknown value, optionally with an explicit pass-through variant. Only this option makes handling Unknown values mandatory on the way out. Rejected because it is policy, not a mirror of the wire: about 28 more generated types and a conversion every bot would have to call.
- **Failing to encode an Unknown value**: circe encoders can't fail, so this would mean a throw, or a separate check before sending. Rejected because a bot that only passes a received value through would fail for no reason.
- **Leaving it, and documenting that values with unknown parts shouldn't be sent back**: rejected because it leaves a silent corruption in place, and Bot API 10.4's `quote_rich_texts` asks bots to send back exactly what they received.
- **Keeping the payload in a type of our own** (a small `Document`, as smithy4s has) so the wrapper doesn't name circe's `Json`. Spiked and rejected as overkill: it reorders keys, loses `-0`, and adds a conversion whose losses the round-trip tests can't see.

Every surveyed library that can send an unknown variant back keeps its whole payload; those that keep only the tag refuse to serialize it.

## Consequences

- `raw` is public: a bot may read it to handle a type this version doesn't know.
- `Unknown.toString` shows only the tag, e.g. `Unknown(date_time)`, because `raw` may hold personal data. For the same reason, telegramium-high's `onUnknownUpdate` reports only the names of unknown keys. A bot that wants the JSON reads `raw`.
- Only Unknown values pass through unchanged. Unknown fields inside known values are dropped when a value is encoded, e.g. a field Telegram adds to `RichTextBold`. That is an accepted gap, not a harmless one:
  - Telegram compares every field of a quoted entity. Quoting a `date_time` entity back gets `QUOTE_TEXT_INVALID` if its `date_time_format` is left out, set to `""`, or sent with a different `unix_time` (checked live).
  - So once a quotable kind gains a field, a bot on an older telegramium can't quote text that has it until it upgrades. Elsewhere such a field is lost without any error.

  It was accepted for 11.1004.0 to limit its scope. The failure apimorphism/telegramium#723 hit was a new kind, which an Unknown value covers. Keeping unknown fields would mean a field on about 80 types and a change to the patterns of every bot. apimorphism/iozhik#44 records what keeping them would take. With a visible field, adding it later breaks positional patterns on those types.
- Pass-through only matters where an Unknown value can be sent back at all:
  - the two quote fields, `ReplyParameters.quoteRichTexts` and `quoteEntities`;
  - the parts nested in a `RichText`.

  See ADR-0003.
- A known tag whose object fails to decode is a decoding failure, not an Unknown value: turning it into one would hide an incompatible change as silent data loss.
