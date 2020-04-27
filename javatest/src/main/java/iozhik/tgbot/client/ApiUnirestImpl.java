package iozhik.tgbot.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ApiUnirestImpl implements Api {

  String baseUrl;

  public ApiUnirestImpl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public GetWebhookInfoRes getWebhookInfo() throws UnirestException {
    return post("getWebhookInfo", GetWebhookInfoRes.class);
  }

  public SetChatPhotoRes setChatPhoto(SetChatPhotoReq x) throws UnirestException {
    return post(x, "setChatPhoto", SetChatPhotoRes.class);
  }

  public GetGameHighScoresRes getGameHighScores(GetGameHighScoresReq x) throws UnirestException {
    return post(x, "getGameHighScores", GetGameHighScoresRes.class);
  }

  public AnswerCallbackQueryRes answerCallbackQuery(AnswerCallbackQueryReq x)
      throws UnirestException {
    return post(x, "answerCallbackQuery", AnswerCallbackQueryRes.class);
  }

  public SendMessageRes sendMessage(SendMessageReq x) throws UnirestException {
    return post(x, "sendMessage", SendMessageRes.class);
  }

  public GetUserProfilePhotosRes getUserProfilePhotos(GetUserProfilePhotosReq x)
      throws UnirestException {
    return post(x, "getUserProfilePhotos", GetUserProfilePhotosRes.class);
  }

  public SendPollRes sendPoll(SendPollReq x) throws UnirestException {
    return post(x, "sendPoll", SendPollRes.class);
  }

  public SendContactRes sendContact(SendContactReq x) throws UnirestException {
    return post(x, "sendContact", SendContactRes.class);
  }

  public CreateNewStickerSetRes createNewStickerSet(CreateNewStickerSetReq x)
      throws UnirestException {
    return post(x, "createNewStickerSet", CreateNewStickerSetRes.class);
  }

  public UploadStickerFileRes uploadStickerFile(UploadStickerFileReq x) throws UnirestException {
    return post(x, "uploadStickerFile", UploadStickerFileRes.class);
  }

  public SendLocationRes sendLocation(SendLocationReq x) throws UnirestException {
    return post(x, "sendLocation", SendLocationRes.class);
  }

  public DeleteChatStickerSetRes deleteChatStickerSet(DeleteChatStickerSetReq x)
      throws UnirestException {
    return post(x, "deleteChatStickerSet", DeleteChatStickerSetRes.class);
  }

  public StopMessageLiveLocationRes stopMessageLiveLocation(StopMessageLiveLocationReq x)
      throws UnirestException {
    return post(x, "stopMessageLiveLocation", StopMessageLiveLocationRes.class);
  }

  public ExportChatInviteLinkRes exportChatInviteLink(ExportChatInviteLinkReq x)
      throws UnirestException {
    return post(x, "exportChatInviteLink", ExportChatInviteLinkRes.class);
  }

  public SendChatActionRes sendChatAction() throws UnirestException {
    return post("sendChatAction", SendChatActionRes.class);
  }

  public AddStickerToSetRes addStickerToSet(AddStickerToSetReq x) throws UnirestException {
    return post(x, "addStickerToSet", AddStickerToSetRes.class);
  }

  public DeleteStickerFromSetRes deleteStickerFromSet(DeleteStickerFromSetReq x)
      throws UnirestException {
    return post(x, "deleteStickerFromSet", DeleteStickerFromSetRes.class);
  }

  public StopPollRes stopPoll(StopPollReq x) throws UnirestException {
    return post(x, "stopPoll", StopPollRes.class);
  }

  public UnpinChatMessageRes unpinChatMessage(UnpinChatMessageReq x) throws UnirestException {
    return post(x, "unpinChatMessage", UnpinChatMessageRes.class);
  }

  public SendMediaGroupRes sendMediaGroup(SendMediaGroupReq x) throws UnirestException {
    return post(x, "sendMediaGroup", SendMediaGroupRes.class);
  }

  public SendGameRes sendGame(SendGameReq x) throws UnirestException {
    return post(x, "sendGame", SendGameRes.class);
  }

  public SendVenueRes sendVenue(SendVenueReq x) throws UnirestException {
    return post(x, "sendVenue", SendVenueRes.class);
  }

  public UnbanChatMemberRes unbanChatMember(UnbanChatMemberReq x) throws UnirestException {
    return post(x, "unbanChatMember", UnbanChatMemberRes.class);
  }

  public SetChatDescriptionRes setChatDescription(SetChatDescriptionReq x) throws UnirestException {
    return post(x, "setChatDescription", SetChatDescriptionRes.class);
  }

  public EditMessageTextRes editMessageText(EditMessageTextReq x) throws UnirestException {
    return post(x, "editMessageText", EditMessageTextRes.class);
  }

  public EditMessageLiveLocationRes editMessageLiveLocation(EditMessageLiveLocationReq x)
      throws UnirestException {
    return post(x, "editMessageLiveLocation", EditMessageLiveLocationRes.class);
  }

  public GetFileRes getFile(GetFileReq x) throws UnirestException {
    return post(x, "getFile", GetFileRes.class);
  }

  public SetGameScoreRes setGameScore(SetGameScoreReq x) throws UnirestException {
    return post(x, "setGameScore", SetGameScoreRes.class);
  }

  public LeaveChatRes leaveChat(LeaveChatReq x) throws UnirestException {
    return post(x, "leaveChat", LeaveChatRes.class);
  }

  public SetChatTitleRes setChatTitle(SetChatTitleReq x) throws UnirestException {
    return post(x, "setChatTitle", SetChatTitleRes.class);
  }

  public SendVideoNoteRes sendVideoNote(SendVideoNoteReq x) throws UnirestException {
    return post(x, "sendVideoNote", SendVideoNoteRes.class);
  }

  public SetPassportDataErrorsRes setPassportDataErrors(SetPassportDataErrorsReq x)
      throws UnirestException {
    return post(x, "setPassportDataErrors", SetPassportDataErrorsRes.class);
  }

  public DeleteChatPhotoRes deleteChatPhoto(DeleteChatPhotoReq x) throws UnirestException {
    return post(x, "deleteChatPhoto", DeleteChatPhotoRes.class);
  }

  public SendInvoiceRes sendInvoice(SendInvoiceReq x) throws UnirestException {
    return post(x, "sendInvoice", SendInvoiceRes.class);
  }

  public SendDocumentRes sendDocument(SendDocumentReq x) throws UnirestException {
    return post(x, "sendDocument", SendDocumentRes.class);
  }

  public DeleteMessageRes deleteMessage(DeleteMessageReq x) throws UnirestException {
    return post(x, "deleteMessage", DeleteMessageRes.class);
  }

  public AnswerInlineQueryRes answerInlineQuery(AnswerInlineQueryReq x) throws UnirestException {
    return post(x, "answerInlineQuery", AnswerInlineQueryRes.class);
  }

  public KickChatMemberRes kickChatMember(KickChatMemberReq x) throws UnirestException {
    return post(x, "kickChatMember", KickChatMemberRes.class);
  }

  public SendAudioRes sendAudio(SendAudioReq x) throws UnirestException {
    return post(x, "sendAudio", SendAudioRes.class);
  }

  public RestrictChatMemberRes restrictChatMember(RestrictChatMemberReq x) throws UnirestException {
    return post(x, "restrictChatMember", RestrictChatMemberRes.class);
  }

  public GetMeRes getMe() throws UnirestException {
    return post("getMe", GetMeRes.class);
  }

  public ForwardMessageRes forwardMessage(ForwardMessageReq x) throws UnirestException {
    return post(x, "forwardMessage", ForwardMessageRes.class);
  }

  public GetChatMemberRes getChatMember(GetChatMemberReq x) throws UnirestException {
    return post(x, "getChatMember", GetChatMemberRes.class);
  }

  public GetChatAdministratorsRes getChatAdministrators(GetChatAdministratorsReq x)
      throws UnirestException {
    return post(x, "getChatAdministrators", GetChatAdministratorsRes.class);
  }

  public SendVoiceRes sendVoice(SendVoiceReq x) throws UnirestException {
    return post(x, "sendVoice", SendVoiceRes.class);
  }

  public PromoteChatMemberRes promoteChatMember(PromoteChatMemberReq x) throws UnirestException {
    return post(x, "promoteChatMember", PromoteChatMemberRes.class);
  }

  public EditMessageCaptionRes editMessageCaption(EditMessageCaptionReq x) throws UnirestException {
    return post(x, "editMessageCaption", EditMessageCaptionRes.class);
  }

  public EditMessageMediaRes editMessageMedia(EditMessageMediaReq x) throws UnirestException {
    return post(x, "editMessageMedia", EditMessageMediaRes.class);
  }

  public PinChatMessageRes pinChatMessage(PinChatMessageReq x) throws UnirestException {
    return post(x, "pinChatMessage", PinChatMessageRes.class);
  }

  public EditMessageReplyMarkupRes editMessageReplyMarkup(EditMessageReplyMarkupReq x)
      throws UnirestException {
    return post(x, "editMessageReplyMarkup", EditMessageReplyMarkupRes.class);
  }

  public SendVideoRes sendVideo(SendVideoReq x) throws UnirestException {
    return post(x, "sendVideo", SendVideoRes.class);
  }

  public SetChatStickerSetRes setChatStickerSet(SetChatStickerSetReq x) throws UnirestException {
    return post(x, "setChatStickerSet", SetChatStickerSetRes.class);
  }

  public GetChatRes getChat(GetChatReq x) throws UnirestException {
    return post(x, "getChat", GetChatRes.class);
  }

  public DeleteWebhookRes deleteWebhook() throws UnirestException {
    return post("deleteWebhook", DeleteWebhookRes.class);
  }

  public SetStickerPositionInSetRes setStickerPositionInSet(SetStickerPositionInSetReq x)
      throws UnirestException {
    return post(x, "setStickerPositionInSet", SetStickerPositionInSetRes.class);
  }

  public SendAnimationRes sendAnimation(SendAnimationReq x) throws UnirestException {
    return post(x, "sendAnimation", SendAnimationRes.class);
  }

  public AnswerShippingQueryRes answerShippingQuery(AnswerShippingQueryReq x)
      throws UnirestException {
    return post(x, "answerShippingQuery", AnswerShippingQueryRes.class);
  }

  public AnswerPreCheckoutQueryRes answerPreCheckoutQuery(AnswerPreCheckoutQueryReq x)
      throws UnirestException {
    return post(x, "answerPreCheckoutQuery", AnswerPreCheckoutQueryRes.class);
  }

  public SendStickerRes sendSticker(SendStickerReq x) throws UnirestException {
    return post(x, "sendSticker", SendStickerRes.class);
  }

  public GetChatMembersCountRes getChatMembersCount(GetChatMembersCountReq x)
      throws UnirestException {
    return post(x, "getChatMembersCount", GetChatMembersCountRes.class);
  }

  public SendPhotoRes sendPhoto(SendPhotoReq x) throws UnirestException {
    return post(x, "sendPhoto", SendPhotoRes.class);
  }

  public GetUpdatesRes getUpdates(GetUpdatesReq x) throws UnirestException {
    return post(x, "getUpdates", GetUpdatesRes.class);
  }

  public GetStickerSetRes getStickerSet(GetStickerSetReq x) throws UnirestException {
    return post(x, "getStickerSet", GetStickerSetRes.class);
  }

  public SetWebhookRes setWebhook(SetWebhookReq x) throws UnirestException {
    return post(x, "setWebhook", SetWebhookRes.class);
  }

  // TODO: implement Either, wrap result to it, eliminate exception throwing
  private <S, Q> S post(Q x, String method, Class<? extends S> resClass) throws UnirestException {
    HttpResponse<S> update =
        Unirest.post(baseUrl + "/" + method)
            .header("Content-Type", "application/json")
            .body(x)
            .asObject(resClass);

    return update.getBody();
  }

  private <S> S post(String method, Class<? extends S> resClass) throws UnirestException {
    HttpResponse<S> update =
        Unirest.post(baseUrl + "/" + method)
            .header("Content-Type", "application/json")
            .asObject(resClass);

    return update.getBody();
  }
}
