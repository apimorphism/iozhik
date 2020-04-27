package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EditMessageMediaReq {

  private ChatId chatId;

  private Integer messageId;

  private String inlineMessageId;

  private InputMedia media;

  private InlineKeyboardMarkup replyMarkup;

  public EditMessageMediaReq() {}

  public EditMessageMediaReq(
      ChatId chatId,
      Integer messageId,
      String inlineMessageId,
      InputMedia media,
      InlineKeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.messageId = messageId;
    this.inlineMessageId = inlineMessageId;
    this.media = media;
    this.replyMarkup = replyMarkup;
  }

  public Optional<ChatId> getChatId() {
    return Optional.ofNullable(chatId);
  }

  public EditMessageMediaReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public Optional<Integer> getMessageId() {
    return Optional.ofNullable(messageId);
  }

  public EditMessageMediaReq setMessageId(Integer v) {
    this.messageId = v;
    return this;
  }

  public Optional<String> getInlineMessageId() {
    return Optional.ofNullable(inlineMessageId);
  }

  public EditMessageMediaReq setInlineMessageId(String v) {
    this.inlineMessageId = v;
    return this;
  }

  public InputMedia getMedia() {
    return this.media;
  }

  public EditMessageMediaReq setMedia(InputMedia v) {
    this.media = v;
    return this;
  }

  public Optional<InlineKeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public EditMessageMediaReq setReplyMarkup(InlineKeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof EditMessageMediaReq)) return false;

    EditMessageMediaReq that = (EditMessageMediaReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.messageId.equals(that.messageId)
        && this.inlineMessageId.equals(that.inlineMessageId)
        && this.media.equals(that.media)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "EditMessageMediaReq{"
        + "chatId="
        + this.chatId
        + ','
        + "messageId="
        + this.messageId
        + ','
        + "inlineMessageId="
        + '\''
        + this.inlineMessageId
        + '\''
        + ','
        + "media="
        + this.media
        + ','
        + "replyMarkup="
        + this.replyMarkup
        + '}';
  }
}
