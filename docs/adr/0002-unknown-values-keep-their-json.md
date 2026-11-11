# Unknown values keep the JSON they were decoded from

An `OpenEnum.Unknown` holds the JSON Telegram sent along with its tag (`Unknown(value, raw)`) and encodes back to it, where it used to keep only the tag and encode that as a bare string, which a rich text reads as plain text (apimorphism/telegramium#723). telegramium-core is generated API types that mirror the wire, so decoding and then encoding must give back what Telegram sent. Policy about content a bot can't read belongs in telegramium-high, if anywhere.

## Considered Options

- **An input family**: request types with no `OpenEnum`, plus a conversion that fails on the first Unknown value, optionally with an explicit pass-through variant. Only this option makes handling Unknown values mandatory through the types. Rejected because it is policy, not a mirror of the wire: about 28 more generated types and a conversion every bot would have to call.
- **Failing to encode an Unknown value**: circe encoders can't fail, so this would mean a throw, or a separate check before sending. Rejected because a bot that only passes a received value through would fail for no reason.
- **Leaving it, and documenting that values with unknown parts shouldn't be sent back**: rejected because it leaves a silent corruption in place, and Bot API 10.4's `quote_rich_texts` asks bots to send back exactly what they received.

## Consequences

- Breaking: `case Unknown(tag)` becomes `case Unknown(tag, _)`, and building an `Unknown` by hand needs `raw`.
- Only Unknown values pass through unchanged. Unknown fields inside known values are still dropped when a value is encoded, e.g. an optional field Telegram adds to `RichTextBold`. Telegram treats a missing optional field as absent, so the result is degraded, not wrong.
- Pass-through only matters where a type can be sent back at all. Today that is only rich text.
