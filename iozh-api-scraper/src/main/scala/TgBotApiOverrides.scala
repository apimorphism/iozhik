import TgBotApiScrapper.{ApiItem, Entity, EntityParam, Method, Sumtyp}

// Corrections to the Bot API docs, applied by TgBotApiScrapper: what it knows about a specific field or type that the
// docs don't say, or say wrongly. The scrape fails on an entry naming a field, method or type the docs no longer have,
// or using a setting where it doesn't apply.
// Give each entry, or group of entries, a comment with its reason: the doc wording, a Bot API server reference, an issue.
// An entry is for a field or type no rule in TgBotApiScrapper covers. When several entries share a reason, make that
// reason a rule there instead, so this object doesn't grow into a copy of the docs.
object TgBotApiOverrides {

  val file = "iozh-api-scraper/src/main/scala/TgBotApiOverrides.scala"

  sealed trait EmptyVsOmitted
  case object Distinct extends EmptyVsOmitted
  case object Same extends EmptyVsOmitted

  sealed trait IntegerWidth
  case object AsInt extends IntegerWidth
  case object AsLong extends IntegerWidth

  /** A field or param, keyed by Owner.name (a type's field or a method's param), or by a bare name, for every field and
    * param of that name. An Owner.name entry wins over a bare name one.
    *
    * @param emptyVsOmitted
    *   For an optional array. Telegram treats a missing optional array the same as an empty one, so it's tagged
    *   (omitempty), and the encoder leaves an empty one out. `Distinct` makes it an Option[List] instead, for arrays
    *   where [] means something of its own. The scrape fails on an optional array whose description talks about empty
    *   or omitted lists, until it's given one or the other.
    * @param integer
    *   The width of an Integer (or of an array's). The scrape fails on an integer that looks like an identifier (named
    *   *_id or *_ids, or described as an identifier) unless the docs say it may exceed 32 bits, its name classifies it
    *   (user and chat IDs are Long; message, story and checklist task IDs are Int), or it's given here.
    * @param tpe
    *   Replaces the docs' type, written as a DSL type (e.g. List[Long], not "Array of Integer"): the scraper's
    *   conversions from the docs' wording don't apply to it. It's still wrapped in an Option when optional. A field the
    *   docs link to "Sending files" stays an IFile.
    * @param optional
    *   For a field of a type with splitByType: it stays optional in its variant ("Optional. For “X” only" otherwise
    *   means required in variant X).
    */
  case class Field(
    emptyVsOmitted: Option[EmptyVsOmitted] = None,
    integer: Option[IntegerWidth] = None,
    tpe: Option[String] = None,
    optional: Boolean = false
  )

  /** A type in the docs, or one declared here with items. A tagged type (one whose type field is fixed) has to end up an
    * item of a sum type, or the scrape fails.
    *
    * @param merge
    *   On a sum type in the docs (so is extraItems). These sum types' items join this type's, and they're dropped: a
    *   field of their type gets this one. A standalone type joins as an item itself.
    * @param extraItems
    *   Items the docs don't list; see [[item]].
    * @param items
    *   Declares a sum type the docs don't have, of these (untagged) items. A docs type that's a union of types ("A or
    *   B", "A, B and C") becomes the sum type whose items include all of them, so a union with no such type, or several,
    *   fails the scrape.
    * @param splitByType
    *   The docs give one object whose type field lists its values, and whose optional fields say "For “X” only". It
    *   becomes a sum type with a variant per value, holding the fields for that value.
    */
  case class Type(
    merge: List[String] = List.empty,
    extraItems: List[Entity] = List.empty,
    items: List[String] = List.empty,
    splitByType: Boolean = false
  )

  /** An item the docs don't list, tagged with type = tag. */
  def item(name: String, desc: String, tag: String, fields: EntityParam*): Entity =
    Entity(name, desc, EntityParam("type", "__type_tag__", tag) :: fields.toList)

  case class Overrides(fields: Map[String, Field] = Map.empty, types: Map[String, Type] = Map.empty) {

    // An Owner.name entry wins over a bare name one.
    def field(owner: String, name: String): Field =
      fields.get(s"$owner.$name").orElse(fields.get(name)).getOrElse(Field())

    def typeOverride(name: String): Type = types.getOrElse(name, Type())

    // Overrides that don't fit the docs: they name a field, method or type the docs don't have (any more), so they'd
    // silently do nothing, or they use a setting where it doesn't apply.
    def problems(items: List[ApiItem]): List[String] = {
      val entities = items.collect { case e: Entity => e.name -> e.table.map(_.name) }.toMap
      val methods = items.collect { case m: Method => m.name -> m.table.map(_.name) }.toMap
      val sumtyps = items.collect { case s: Sumtyp => s.name }.toSet
      val owners = entities ++ methods
      val allNames = owners.values.flatten.toSet
      // Each field and param as (owner, name, parsed type, optional).
      val params = items.flatMap {
        case e: Entity => e.table.map(f => (e.name, f.name, f.kind, TgBotApiScrapper.optionalFieldDescPattern.findFirstIn(f.desc).isDefined))
        case m: Method => m.table.map(p => (m.name, p.name, p.kind, p.required.toLowerCase.contains("optional")))
        case _: Sumtyp => List.empty
      }
      // A setting needs at least one of the fields its key names to be of a type it applies to.
      def settingProblems(path: String, o: Field, named: List[(String, Boolean)]): List[String] =
        o.integer.filterNot(_ => named.exists { case (kind, _) => "\\b(Int|Long)\\b".r.findFirstIn(kind).isDefined })
          .map(_ => s"$path: integer only applies to an Integer").toList ++
          o.emptyVsOmitted.filterNot(_ => named.exists { case (kind, optional) => optional && kind.startsWith("Array of") })
            .map(_ => s"$path: emptyVsOmitted only applies to an optional array").toList
      val fieldProblems = fields.toList.flatMap { case (key, o) =>
        key.split('.') match {
          case Array(owner, name) =>
            val path = s"fields.\"$key\""
            if (!owners.get(owner).exists(_.contains(name))) List(s"$path: the docs have no such field or param")
            else if (o.optional && !typeOverride(owner).splitByType) List(s"$path: optional only applies to a type with splitByType")
            else settingProblems(path, o, params.collect { case (`owner`, `name`, kind, optional) => kind -> optional })
          case _ =>
            if (!allNames.contains(key)) List(s"fields.$key: the docs have no field or param of this name")
            else if (o.optional) List(s"fields.$key: optional needs an Owner.name key")
            else settingProblems(s"fields.$key", o, params.collect { case (_, `key`, kind, optional) => kind -> optional })
        }
      }
      val typeProblems = types.toList.flatMap { case (name, t) =>
        val inDocs = entities.contains(name) || sumtyps.contains(name)
        val existence =
          if (t.items.nonEmpty) {
            if (inDocs) List(s"types.$name: the docs define it now, so it can't be declared with items")
            else t.items.filterNot(entities.contains).map(i => s"types.$name.items: the docs have no type $i")
          } else if (!inDocs) List(s"types.$name: the docs have no such type")
          else List.empty
        val notSumtyp =
          if ((t.merge.nonEmpty || t.extraItems.nonEmpty) && !sumtyps.contains(name))
            List(s"types.$name: merge and extraItems only apply to a sum type in the docs")
          else List.empty
        existence ++ notSumtyp ++
          t.merge.filterNot(m => sumtyps.contains(m) || entities.contains(m)).map(m => s"types.$name.merge: the docs have no type $m") ++
          t.extraItems.map(_.name).filter(n => entities.contains(n) || sumtyps.contains(n))
            .map(n => s"types.$name.extraItems: the docs define $n now") ++
          (if (t.splitByType && !entities.get(name).exists(_.contains("type"))) List(s"types.$name: splitByType needs a type field")
           else List.empty)
      }
      (fieldProblems ++ typeProblems).sorted
    }
  }

  val default: Overrides = Overrides(
    fields = Map(
      // "If not specified, the previous setting will be used"
      "getUpdates.allowed_updates" -> Field(emptyVsOmitted = Some(Distinct)),
      "setWebhook.allowed_updates" -> Field(emptyVsOmitted = Some(Distinct)),
      // "If omitted, then all emoji reactions are allowed"
      "ChatFullInfo.available_reactions" -> Field(emptyVsOmitted = Some(Distinct)),
      // Not in the docs: the Bot API server keeps the current areas when it's omitted, and removes them on [].
      "editStory.areas" -> Field(emptyVsOmitted = Some(Distinct)),
      // These descriptions talk about empty or omitted lists, but mean the same by both.
      "sendPoll.country_codes" -> Field(emptyVsOmitted = Some(Same)),
      "Poll.country_codes" -> Field(emptyVsOmitted = Some(Same)),
      "Giveaway.country_codes" -> Field(emptyVsOmitted = Some(Same)),
      "ChatFullInfo.active_usernames" -> Field(emptyVsOmitted = Some(Same)),
      "WebhookInfo.allowed_updates" -> Field(emptyVsOmitted = Some(Same)),
      "ReplyParameters.quote_rich_texts" -> Field(emptyVsOmitted = Some(Same)),

      // The docs rarely state an integer's width, and no naming rule covers these, so they're checked against the Bot
      // API server instead: telegram-bot-api Client.cpp at e3e9dd8 (Bot API 10.3), where each handler parses params and
      // serializes fields.
      // int64: parsed with to_integer<int64>, serialized from JsonDirectMessagesTopic.topic_id
      "direct_messages_topic_id" -> Field(integer = Some(AsLong)),
      // int64: parsed with to_integer<int64> in process_send_message_draft_query (the docs don't say)
      "draft_id" -> Field(integer = Some(AsLong)),
      // int32: get_forum_topic_id, serialized from td forum_topic_id:int32
      "message_thread_id" -> Field(integer = Some(AsInt)),
      // int32: JsonUpdate(td::int32 id, ...), and get_integer_arg for getUpdates offset
      "update_id" -> Field(integer = Some(AsInt)),
      "getUpdates.offset" -> Field(integer = Some(AsInt)),
      // int32: td_api accent color ids
      "accent_color_id" -> Field(integer = Some(AsInt)),
      "profile_accent_color_id" -> Field(integer = Some(AsInt)),
      // int32: get_integer_arg / get_required_int_field; some descriptions also say "Signed 32-bit identifier"
      "request_id" -> Field(integer = Some(AsInt)),
      // int32: poll option indexes
      "option_ids" -> Field(integer = Some(AsInt)),
      "correct_option_ids" -> Field(integer = Some(AsInt)),
      // Bot API 10.4, checked against telegram-bot-api Client.cpp at 7232881.
      // int32: td_api profileShareThemeEmoji background_backdrop_id:int32
      "background_backdrop_id" -> Field(integer = Some(AsInt)),
      // int32: td_api profileShareThemeBackdrop id:int32
      "ChatShareThemeBackdrop.id" -> Field(integer = Some(AsInt)),

      // A Unix time, though the description doesn't say so
      "send_date" -> Field(integer = Some(AsLong)),

      // The docs only say "Optional. For “pre” only", as for every other variant's field, but a pre may have no
      // language.
      "MessageEntity.language" -> Field(optional = true)
    ),
    types = Map(
      // sendPoll's media params take an InputPollMedia, and InputPollOption's an InputPollOptionMedia: both are subsets
      // of the InputMedia* types, so a single InputMedia serves them all. The docs list InputMediaVoiceNote in no sum
      // type, though InputRichMessageMedia.media takes it among the others.
      "InputMedia" -> Type(merge = List("InputPollMedia", "InputPollOptionMedia", "InputMediaVoiceNote")),
      // Telegram sends a plain text as a bare string and a concatenation as an array; see the custom RichText codecs in
      // iozhik/src/main/resources/tgbotapi.conf.
      "RichText" -> Type(extraItems = List(
        item("RichTextPlain", "Plain text.", "plain", EntityParam("text", "String", "The text")),
        item("RichTextConcat", "Concatenation of rich texts.", "concat", EntityParam("texts", "List[RichText]", "The texts"))
      )),
      // The reply_markup params' "InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply"
      "KeyboardMarkup" -> Type(items = List("InlineKeyboardMarkup", "ReplyKeyboardMarkup", "ReplyKeyboardRemove", "ForceReply")),
      // The docs give one MessageEntity, whose url, user, language and custom_emoji_id apply to one type each.
      "MessageEntity" -> Type(splitByType = true)
    )
  )
}
