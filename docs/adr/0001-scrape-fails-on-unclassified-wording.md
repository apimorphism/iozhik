# The scrape fails on wording it can't classify

The scraper infers types from prose in the Bot API docs, and guessing has shipped bugs: `fromChatId: Int` because no "32 bits" phrase was present, and empty optional arrays sent as `[]` (apimorphism/telegramium#686). So wherever a type hinges on prose, the scraper classifies a field automatically only from known wording, otherwise from an explicit list in the scraper, and fails the scrape for anything left unclassified. iozhik#22 did this for empty-vs-omitted arrays; integer identifiers follow the same rule: an `Integer` field or param named `*_id`/`*_ids`, or described as an identifier, must be classified as `Int` or `Long`.

A failing scrape on a Bot API update is intended: someone has to read the new field's description and decide, instead of the generator silently picking a type.
