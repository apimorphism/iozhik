package telegramium.bots.client

trait Api[F[_]] {

  /** Use this method to get current webhook status. Requires no parameters. On
    * success, returns a WebhookInfo object. If the bot is using getUpdates, will
    * return an object with the url field empty.*/
  def getWebhookInfo(): F[GetWebhookInfoRes]

  /** Use this method to set a new profile photo for the chat. Photos can't be
    * changed for private chats. The bot must be an administrator in the chat for this
    * to work and must have the appropriate admin rights. Returns True on success.*/
  def setChatPhoto(x: SetChatPhotoReq): F[SetChatPhotoRes]

  /** Use this method to get data for high score tables. Will return the score of the
    * specified user and several of his neighbors in a game. On success, returns an
    * Array of GameHighScore objects.*/
  def getGameHighScores(x: GetGameHighScoresReq): F[GetGameHighScoresRes]

  /** Use this method to send answers to callback queries sent from inline keyboards.
    * The answer will be displayed to the user as a notification at the top of the
    * chat screen or as an alert. On success, True is returned.*/
  def answerCallbackQuery(x: AnswerCallbackQueryReq): F[AnswerCallbackQueryRes]

  /** Use this method to send text messages. On success, the sent Message is
    * returned.*/
  def sendMessage(x: SendMessageReq): F[SendMessageRes]

  /** Use this method to get a list of profile pictures for a user. Returns a
    * UserProfilePhotos object.*/
  def getUserProfilePhotos(x: GetUserProfilePhotosReq): F[GetUserProfilePhotosRes]

  /** Use this method to send a native poll. A native poll can't be sent to a private
    * chat. On success, the sent Message is returned.*/
  def sendPoll(x: SendPollReq): F[SendPollRes]

  /** Use this method to send phone contacts. On success, the sent Message is
    * returned.*/
  def sendContact(x: SendContactReq): F[SendContactRes]

  /** Use this method to create new sticker set owned by a user. The bot will be able
    * to edit the created sticker set. Returns True on success.*/
  def createNewStickerSet(x: CreateNewStickerSetReq): F[CreateNewStickerSetRes]

  /** Use this method to upload a .png file with a sticker for later use in
    * createNewStickerSet and addStickerToSet methods (can be used multiple times).
    * Returns the uploaded File on success.*/
  def uploadStickerFile(x: UploadStickerFileReq): F[UploadStickerFileRes]

  /** Use this method to send point on the map. On success, the sent Message is
    * returned.*/
  def sendLocation(x: SendLocationReq): F[SendLocationRes]

  /** Use this method to delete a group sticker set from a supergroup. The bot must
    * be an administrator in the chat for this to work and must have the appropriate
    * admin rights. Use the field can_set_sticker_set optionally returned in getChat
    * requests to check if the bot can use this method. Returns True on success.*/
  def deleteChatStickerSet(x: DeleteChatStickerSetReq): F[DeleteChatStickerSetRes]

  /** Use this method to stop updating a live location message before live_period
    * expires. On success, if the message was sent by the bot, the sent Message is
    * returned, otherwise True is returned.*/
  def stopMessageLiveLocation(x: StopMessageLiveLocationReq): F[StopMessageLiveLocationRes]

  /** Use this method to generate a new invite link for a chat; any previously
    * generated link is revoked. The bot must be an administrator in the chat for this
    * to work and must have the appropriate admin rights. Returns the new invite link
    * as String on success.*/
  def exportChatInviteLink(x: ExportChatInviteLinkReq): F[ExportChatInviteLinkRes]

  /** Use this method when you need to tell the user that something is happening on
    * the bot's side. The status is set for 5 seconds or less (when a message arrives
    * from your bot, Telegram clients clear its typing status). Returns True on
    * success.*/
  def sendChatAction(): F[SendChatActionRes]

  /** Use this method to add a new sticker to a set created by the bot. Returns True
    * on success.*/
  def addStickerToSet(x: AddStickerToSetReq): F[AddStickerToSetRes]

  /** Use this method to delete a sticker from a set created by the bot. Returns True
    * on success.*/
  def deleteStickerFromSet(x: DeleteStickerFromSetReq): F[DeleteStickerFromSetRes]

  /** Use this method to stop a poll which was sent by the bot. On success, the
    * stopped Poll with the final results is returned.*/
  def stopPoll(x: StopPollReq): F[StopPollRes]

  /** Use this method to unpin a message in a group, a supergroup, or a channel. The
    * bot must be an administrator in the chat for this to work and must have the
    * ‘can_pin_messages’ admin right in the supergroup or ‘can_edit_messages’ admin
    * right in the channel. Returns True on success.*/
  def unpinChatMessage(x: UnpinChatMessageReq): F[UnpinChatMessageRes]

  /** Use this method to send a group of photos or videos as an album. On success, an
    * array of the sent Messages is returned.*/
  def sendMediaGroup(x: SendMediaGroupReq): F[SendMediaGroupRes]

  /** Use this method to send a game. On success, the sent Message is returned.*/
  def sendGame(x: SendGameReq): F[SendGameRes]

  /** Use this method to send information about a venue. On success, the sent Message
    * is returned.*/
  def sendVenue(x: SendVenueReq): F[SendVenueRes]

  /** Use this method to unban a previously kicked user in a supergroup or channel.
    * The user will not return to the group or channel automatically, but will be able
    * to join via link, etc. The bot must be an administrator for this to work.
    * Returns True on success.*/
  def unbanChatMember(x: UnbanChatMemberReq): F[UnbanChatMemberRes]

  /** Use this method to change the description of a supergroup or a channel. The bot
    * must be an administrator in the chat for this to work and must have the
    * appropriate admin rights. Returns True on success.*/
  def setChatDescription(x: SetChatDescriptionReq): F[SetChatDescriptionRes]

  /** Use this method to edit text and game messages. On success, if edited message
    * is sent by the bot, the edited Message is returned, otherwise True is returned.*/
  def editMessageText(x: EditMessageTextReq): F[EditMessageTextRes]

  /** Use this method to edit live location messages. A location can be edited until
    * its live_period expires or editing is explicitly disabled by a call to
    * stopMessageLiveLocation. On success, if the edited message was sent by the bot,
    * the edited Message is returned, otherwise True is returned.*/
  def editMessageLiveLocation(x: EditMessageLiveLocationReq): F[EditMessageLiveLocationRes]

  /** Use this method to get basic info about a file and prepare it for downloading.
    * For the moment, bots can download files of up to 20MB in size. On success, a
    * File object is returned. The file can then be downloaded via the link
    * https://api.telegram.org/file/bot<token>/<file_path>, where <file_path> is taken
    * from the response. It is guaranteed that the link will be valid for at least 1
    * hour. When the link expires, a new one can be requested by calling getFile
    * again.*/
  def getFile(x: GetFileReq): F[GetFileRes]

  /** Use this method to set the score of the specified user in a game. On success,
    * if the message was sent by the bot, returns the edited Message, otherwise
    * returns True. Returns an error, if the new score is not greater than the user's
    * current score in the chat and force is False.*/
  def setGameScore(x: SetGameScoreReq): F[SetGameScoreRes]

  /** Use this method for your bot to leave a group, supergroup or channel. Returns
    * True on success.*/
  def leaveChat(x: LeaveChatReq): F[LeaveChatRes]

  /** Use this method to change the title of a chat. Titles can't be changed for
    * private chats. The bot must be an administrator in the chat for this to work and
    * must have the appropriate admin rights. Returns True on success.*/
  def setChatTitle(x: SetChatTitleReq): F[SetChatTitleRes]

  /** As of v.4.0, Telegram clients support rounded square mp4 videos of up to 1
    * minute long. Use this method to send video messages. On success, the sent
    * Message is returned.*/
  def sendVideoNote(x: SendVideoNoteReq): F[SendVideoNoteRes]

  /** Informs a user that some of the Telegram Passport elements they provided
    * contains errors. The user will not be able to re-submit their Passport to you
    * until the errors are fixed (the contents of the field for which you returned the
    * error must change). Returns True on success. Use this if the data submitted by
    * the user doesn't satisfy the standards your service requires for any reason. For
    * example, if a birthday date seems invalid, a submitted document is blurry, a
    * scan shows evidence of tampering, etc. Supply some details in the error message
    * to make sure the user knows how to correct the issues.*/
  def setPassportDataErrors(x: SetPassportDataErrorsReq): F[SetPassportDataErrorsRes]

  /** Use this method to delete a chat photo. Photos can't be changed for private
    * chats. The bot must be an administrator in the chat for this to work and must
    * have the appropriate admin rights. Returns True on success.*/
  def deleteChatPhoto(x: DeleteChatPhotoReq): F[DeleteChatPhotoRes]

  /** Use this method to send invoices. On success, the sent Message is returned.*/
  def sendInvoice(x: SendInvoiceReq): F[SendInvoiceRes]

  /** Use this method to send general files. On success, the sent Message is
    * returned. Bots can currently send files of any type of up to 50 MB in size, this
    * limit may be changed in the future.*/
  def sendDocument(x: SendDocumentReq): F[SendDocumentRes]

  /** Use this method to delete a message, including service messages, with the
    * following limitations: - A message can only be deleted if it was sent less than
    * 48 hours ago. - Bots can delete outgoing messages in private chats, groups, and
    * supergroups. - Bots can delete incoming messages in private chats. - Bots
    * granted can_post_messages permissions can delete outgoing messages in channels.
    * - If the bot is an administrator of a group, it can delete any message there. -
    * If the bot has can_delete_messages permission in a supergroup or a channel, it
    * can delete any message there. Returns True on success.*/
  def deleteMessage(x: DeleteMessageReq): F[DeleteMessageRes]

  /** Use this method to send answers to an inline query. On success, True is
    * returned. No more than 50 results per query are allowed.*/
  def answerInlineQuery(x: AnswerInlineQueryReq): F[AnswerInlineQueryRes]

  /** Use this method to kick a user from a group, a supergroup or a channel. In the
    * case of supergroups and channels, the user will not be able to return to the
    * group on their own using invite links, etc., unless unbanned first. The bot must
    * be an administrator in the chat for this to work and must have the appropriate
    * admin rights. Returns True on success.*/
  def kickChatMember(x: KickChatMemberReq): F[KickChatMemberRes]

  /** Use this method to send audio files, if you want Telegram clients to display
    * them in the music player. Your audio must be in the .mp3 format. On success, the
    * sent Message is returned. Bots can currently send audio files of up to 50 MB in
    * size, this limit may be changed in the future. For sending voice messages, use
    * the sendVoice method instead.*/
  def sendAudio(x: SendAudioReq): F[SendAudioRes]

  /** Use this method to restrict a user in a supergroup. The bot must be an
    * administrator in the supergroup for this to work and must have the appropriate
    * admin rights. Pass True for all boolean parameters to lift restrictions from a
    * user. Returns True on success.*/
  def restrictChatMember(x: RestrictChatMemberReq): F[RestrictChatMemberRes]

  /** A simple method for testing your bot's auth token. Requires no parameters.
    * Returns basic information about the bot in form of a User object.*/
  def getMe(): F[GetMeRes]

  /** Use this method to forward messages of any kind. On success, the sent Message
    * is returned.*/
  def forwardMessage(x: ForwardMessageReq): F[ForwardMessageRes]

  /** Use this method to get information about a member of a chat. Returns a
    * ChatMember object on success.*/
  def getChatMember(x: GetChatMemberReq): F[GetChatMemberRes]

  /** Use this method to get a list of administrators in a chat. On success, returns
    * an Array of ChatMember objects that contains information about all chat
    * administrators except other bots. If the chat is a group or a supergroup and no
    * administrators were appointed, only the creator will be returned.*/
  def getChatAdministrators(x: GetChatAdministratorsReq): F[GetChatAdministratorsRes]

  /** Use this method to send audio files, if you want Telegram clients to display
    * the file as a playable voice message. For this to work, your audio must be in an
    * .ogg file encoded with OPUS (other formats may be sent as Audio or Document). On
    * success, the sent Message is returned. Bots can currently send voice messages of
    * up to 50 MB in size, this limit may be changed in the future.*/
  def sendVoice(x: SendVoiceReq): F[SendVoiceRes]

  /** Use this method to promote or demote a user in a supergroup or a channel. The
    * bot must be an administrator in the chat for this to work and must have the
    * appropriate admin rights. Pass False for all boolean parameters to demote a
    * user. Returns True on success.*/
  def promoteChatMember(x: PromoteChatMemberReq): F[PromoteChatMemberRes]

  /** Use this method to edit captions of messages. On success, if edited message is
    * sent by the bot, the edited Message is returned, otherwise True is returned.*/
  def editMessageCaption(x: EditMessageCaptionReq): F[EditMessageCaptionRes]

  /** Use this method to edit animation, audio, document, photo, or video messages.
    * If a message is a part of a message album, then it can be edited only to a photo
    * or a video. Otherwise, message type can be changed arbitrarily. When inline
    * message is edited, new file can't be uploaded. Use previously uploaded file via
    * its file_id or specify a URL. On success, if the edited message was sent by the
    * bot, the edited Message is returned, otherwise True is returned.*/
  def editMessageMedia(x: EditMessageMediaReq): F[EditMessageMediaRes]

  /** Use this method to pin a message in a group, a supergroup, or a channel. The
    * bot must be an administrator in the chat for this to work and must have the
    * ‘can_pin_messages’ admin right in the supergroup or ‘can_edit_messages’ admin
    * right in the channel. Returns True on success.*/
  def pinChatMessage(x: PinChatMessageReq): F[PinChatMessageRes]

  /** Use this method to edit only the reply markup of messages. On success, if
    * edited message is sent by the bot, the edited Message is returned, otherwise
    * True is returned.*/
  def editMessageReplyMarkup(x: EditMessageReplyMarkupReq): F[EditMessageReplyMarkupRes]

  /** Use this method to send video files, Telegram clients support mp4 videos (other
    * formats may be sent as Document). On success, the sent Message is returned. Bots
    * can currently send video files of up to 50 MB in size, this limit may be changed
    * in the future.*/
  def sendVideo(x: SendVideoReq): F[SendVideoRes]

  /** Use this method to set a new group sticker set for a supergroup. The bot must
    * be an administrator in the chat for this to work and must have the appropriate
    * admin rights. Use the field can_set_sticker_set optionally returned in getChat
    * requests to check if the bot can use this method. Returns True on success.*/
  def setChatStickerSet(x: SetChatStickerSetReq): F[SetChatStickerSetRes]

  /** Use this method to get up to date information about the chat (current name of
    * the user for one-on-one conversations, current username of a user, group or
    * channel, etc.). Returns a Chat object on success.*/
  def getChat(x: GetChatReq): F[GetChatRes]

  /** Use this method to remove webhook integration if you decide to switch back to
    * getUpdates. Returns True on success. Requires no parameters.*/
  def deleteWebhook(): F[DeleteWebhookRes]

  /** Use this method to move a sticker in a set created by the bot to a specific
    * position . Returns True on success.*/
  def setStickerPositionInSet(x: SetStickerPositionInSetReq): F[SetStickerPositionInSetRes]

  /** Use this method to send animation files (GIF or H.264/MPEG-4 AVC video without
    * sound). On success, the sent Message is returned. Bots can currently send
    * animation files of up to 50 MB in size, this limit may be changed in the future.*/
  def sendAnimation(x: SendAnimationReq): F[SendAnimationRes]

  /** If you sent an invoice requesting a shipping address and the parameter
    * is_flexible was specified, the Bot API will send an Update with a shipping_query
    * field to the bot. Use this method to reply to shipping queries. On success, True
    * is returned.*/
  def answerShippingQuery(x: AnswerShippingQueryReq): F[AnswerShippingQueryRes]

  /** Once the user has confirmed their payment and shipping details, the Bot API
    * sends the final confirmation in the form of an Update with the field
    * pre_checkout_query. Use this method to respond to such pre-checkout queries. On
    * success, True is returned. Note: The Bot API must receive an answer within 10
    * seconds after the pre-checkout query was sent.*/
  def answerPreCheckoutQuery(x: AnswerPreCheckoutQueryReq): F[AnswerPreCheckoutQueryRes]

  /** Use this method to send .webp stickers. On success, the sent Message is
    * returned.*/
  def sendSticker(x: SendStickerReq): F[SendStickerRes]

  /** Use this method to get the number of members in a chat. Returns Int on success.*/
  def getChatMembersCount(x: GetChatMembersCountReq): F[GetChatMembersCountRes]

  /** Use this method to send photos. On success, the sent Message is returned.*/
  def sendPhoto(x: SendPhotoReq): F[SendPhotoRes]

  /** Use this method to receive incoming updates using long polling (wiki). An Array
    * of Update objects is returned.*/
  def getUpdates(x: GetUpdatesReq): F[GetUpdatesRes]

  /** Use this method to get a sticker set. On success, a StickerSet object is
    * returned.*/
  def getStickerSet(x: GetStickerSetReq): F[GetStickerSetRes]

  /** Use this method to specify a url and receive incoming updates via an outgoing
    * webhook. Whenever there is an update for the bot, we will send an HTTPS POST
    * request to the specified url, containing a JSON-serialized Update. In case of an
    * unsuccessful request, we will give up after a reasonable amount of attempts.
    * Returns True on success. If you'd like to make sure that the Webhook request
    * comes from Telegram, we recommend using a secret path in the URL, e.g.
    * https://www.example.com/<token>. Since nobody else knows your bot‘s token, you
    * can be pretty sure it’s us.*/
  def setWebhook(x: SetWebhookReq): F[SetWebhookRes]
}
