# Unknown values keep the JSON they were decoded from

An unknown value of a tagged-object family holds the JSON Telegram sent along with its tag (`OpenUnion.Unknown(tag, raw)`) and encodes back to it. It used to keep only the tag and encode that as a bare string, which a rich text reads as plain text (apimorphism/telegramium#723). telegramium-core is generated API types that mirror the wire, so decoding and then encoding must give back what Telegram sent. Policy about content a bot can't read belongs in telegramium-high, if anywhere.

An unknown value of a string enum is only its string (`OpenEnum.Unknown(value)`): the string is the whole wire value. [ADR-0003](0003-open-enums-and-open-unions-wrap-closed-families.md) records why these are two types.

## Considered Options

- **An input family**: request types with no wrapper, plus a conversion that fails on the first Unknown value, optionally with an explicit pass-through variant. Only this option makes handling Unknown values mandatory on the way out. Rejected because it is policy, not a mirror of the wire: about 28 more generated types and a conversion every bot would have to call.
- **Failing to encode an Unknown value**: circe encoders can't fail, so this would mean a throw, or a separate check before sending. Rejected because a bot that only passes a received value through would fail for no reason.
- **Leaving it, and documenting that values with unknown parts shouldn't be sent back**: rejected because it leaves a silent corruption in place, and Bot API 10.4's `quote_rich_texts` asks bots to send back exactly what they received.
- **Keeping the payload in a type of our own** (a small `Document`, as smithy4s has) so the wrapper doesn't name circe's `Json`. Spiked and rejected as overkill: it reorders keys, loses `-0`, and adds a conversion whose losses the round-trip tests can't see.

Every surveyed library that can send an unknown variant back keeps its whole payload; those that keep only the tag refuse to serialize it ([research](../research/forward-compatible-enums.md), sections 2 and 5).

## Consequences

- `raw` is public: a bot may read it to handle a type this version doesn't know.
- Only Unknown values pass through unchanged. Unknown fields inside known values are still dropped when a value is encoded, e.g. an optional field Telegram adds to `RichTextBold`. Telegram treats a missing optional field as absent, so the result is degraded, not wrong.
- Pass-through only matters where a type can be sent back at all. Today that is only rich text.
- A known tag whose object fails to decode is a decoding failure, not an Unknown value: turning it into one would hide an incompatible change as silent data loss.
