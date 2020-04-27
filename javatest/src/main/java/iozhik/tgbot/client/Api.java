package iozhik.tgbot.client;

public interface Api {
  GetWebhookInfoRes getWebhookInfo() throws Exception;

  SetChatPhotoRes setChatPhoto(SetChatPhotoReq x) throws Exception;

  GetGameHighScoresRes getGameHighScores(GetGameHighScoresReq x) throws Exception;

  AnswerCallbackQueryRes answerCallbackQuery(AnswerCallbackQueryReq x) throws Exception;

  SendMessageRes sendMessage(SendMessageReq x) throws Exception;

  GetUserProfilePhotosRes getUserProfilePhotos(GetUserProfilePhotosReq x) throws Exception;

  SendPollRes sendPoll(SendPollReq x) throws Exception;

  SendContactRes sendContact(SendContactReq x) throws Exception;

  CreateNewStickerSetRes createNewStickerSet(CreateNewStickerSetReq x) throws Exception;

  UploadStickerFileRes uploadStickerFile(UploadStickerFileReq x) throws Exception;

  SendLocationRes sendLocation(SendLocationReq x) throws Exception;

  DeleteChatStickerSetRes deleteChatStickerSet(DeleteChatStickerSetReq x) throws Exception;

  StopMessageLiveLocationRes stopMessageLiveLocation(StopMessageLiveLocationReq x) throws Exception;

  ExportChatInviteLinkRes exportChatInviteLink(ExportChatInviteLinkReq x) throws Exception;

  SendChatActionRes sendChatAction() throws Exception;

  AddStickerToSetRes addStickerToSet(AddStickerToSetReq x) throws Exception;

  DeleteStickerFromSetRes deleteStickerFromSet(DeleteStickerFromSetReq x) throws Exception;

  StopPollRes stopPoll(StopPollReq x) throws Exception;

  UnpinChatMessageRes unpinChatMessage(UnpinChatMessageReq x) throws Exception;

  SendMediaGroupRes sendMediaGroup(SendMediaGroupReq x) throws Exception;

  SendGameRes sendGame(SendGameReq x) throws Exception;

  SendVenueRes sendVenue(SendVenueReq x) throws Exception;

  UnbanChatMemberRes unbanChatMember(UnbanChatMemberReq x) throws Exception;

  SetChatDescriptionRes setChatDescription(SetChatDescriptionReq x) throws Exception;

  EditMessageTextRes editMessageText(EditMessageTextReq x) throws Exception;

  EditMessageLiveLocationRes editMessageLiveLocation(EditMessageLiveLocationReq x) throws Exception;

  GetFileRes getFile(GetFileReq x) throws Exception;

  SetGameScoreRes setGameScore(SetGameScoreReq x) throws Exception;

  LeaveChatRes leaveChat(LeaveChatReq x) throws Exception;

  SetChatTitleRes setChatTitle(SetChatTitleReq x) throws Exception;

  SendVideoNoteRes sendVideoNote(SendVideoNoteReq x) throws Exception;

  SetPassportDataErrorsRes setPassportDataErrors(SetPassportDataErrorsReq x) throws Exception;

  DeleteChatPhotoRes deleteChatPhoto(DeleteChatPhotoReq x) throws Exception;

  SendInvoiceRes sendInvoice(SendInvoiceReq x) throws Exception;

  SendDocumentRes sendDocument(SendDocumentReq x) throws Exception;

  DeleteMessageRes deleteMessage(DeleteMessageReq x) throws Exception;

  AnswerInlineQueryRes answerInlineQuery(AnswerInlineQueryReq x) throws Exception;

  KickChatMemberRes kickChatMember(KickChatMemberReq x) throws Exception;

  SendAudioRes sendAudio(SendAudioReq x) throws Exception;

  RestrictChatMemberRes restrictChatMember(RestrictChatMemberReq x) throws Exception;

  GetMeRes getMe() throws Exception;

  ForwardMessageRes forwardMessage(ForwardMessageReq x) throws Exception;

  GetChatMemberRes getChatMember(GetChatMemberReq x) throws Exception;

  GetChatAdministratorsRes getChatAdministrators(GetChatAdministratorsReq x) throws Exception;

  SendVoiceRes sendVoice(SendVoiceReq x) throws Exception;

  PromoteChatMemberRes promoteChatMember(PromoteChatMemberReq x) throws Exception;

  EditMessageCaptionRes editMessageCaption(EditMessageCaptionReq x) throws Exception;

  EditMessageMediaRes editMessageMedia(EditMessageMediaReq x) throws Exception;

  PinChatMessageRes pinChatMessage(PinChatMessageReq x) throws Exception;

  EditMessageReplyMarkupRes editMessageReplyMarkup(EditMessageReplyMarkupReq x) throws Exception;

  SendVideoRes sendVideo(SendVideoReq x) throws Exception;

  SetChatStickerSetRes setChatStickerSet(SetChatStickerSetReq x) throws Exception;

  GetChatRes getChat(GetChatReq x) throws Exception;

  DeleteWebhookRes deleteWebhook() throws Exception;

  SetStickerPositionInSetRes setStickerPositionInSet(SetStickerPositionInSetReq x) throws Exception;

  SendAnimationRes sendAnimation(SendAnimationReq x) throws Exception;

  AnswerShippingQueryRes answerShippingQuery(AnswerShippingQueryReq x) throws Exception;

  AnswerPreCheckoutQueryRes answerPreCheckoutQuery(AnswerPreCheckoutQueryReq x) throws Exception;

  SendStickerRes sendSticker(SendStickerReq x) throws Exception;

  GetChatMembersCountRes getChatMembersCount(GetChatMembersCountReq x) throws Exception;

  SendPhotoRes sendPhoto(SendPhotoReq x) throws Exception;

  GetUpdatesRes getUpdates(GetUpdatesReq x) throws Exception;

  GetStickerSetRes getStickerSet(GetStickerSetReq x) throws Exception;

  SetWebhookRes setWebhook(SetWebhookReq x) throws Exception;
}
