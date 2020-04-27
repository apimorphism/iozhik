package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendMediaGroupReq {

  private ChatId chatId;

  private List<InputMedia> media;

  private Boolean disableNotification;

  private Integer replyToMessageId;

  public SendMediaGroupReq() {}

  public SendMediaGroupReq(
      ChatId chatId,
      List<InputMedia> media,
      Boolean disableNotification,
      Integer replyToMessageId) {
    this.chatId = chatId;
    this.media = media;
    this.disableNotification = disableNotification;
    this.replyToMessageId = replyToMessageId;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public SendMediaGroupReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public List<InputMedia> getMedia() {
    return this.media;
  }

  public SendMediaGroupReq setMedia(List<InputMedia> v) {
    this.media = v;
    return this;
  }

  public Optional<Boolean> getDisableNotification() {
    return Optional.ofNullable(disableNotification);
  }

  public SendMediaGroupReq setDisableNotification(Boolean v) {
    this.disableNotification = v;
    return this;
  }

  public Optional<Integer> getReplyToMessageId() {
    return Optional.ofNullable(replyToMessageId);
  }

  public SendMediaGroupReq setReplyToMessageId(Integer v) {
    this.replyToMessageId = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SendMediaGroupReq)) return false;

    SendMediaGroupReq that = (SendMediaGroupReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.media.equals(that.media)
        && this.disableNotification.equals(that.disableNotification)
        && this.replyToMessageId.equals(that.replyToMessageId);
  }

  @Override
  public String toString() {
    return "SendMediaGroupReq{"
        + "chatId="
        + this.chatId
        + ','
        + "media="
        + this.media
        + ','
        + "disableNotification="
        + this.disableNotification
        + ','
        + "replyToMessageId="
        + this.replyToMessageId
        + '}';
  }
}
