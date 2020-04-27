package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EditMessageCaptionReq {

  private ChatId chatId;

  private Integer messageId;

  private String inlineMessageId;

  private String caption;

  private String parseMode;

  private InlineKeyboardMarkup replyMarkup;

  public EditMessageCaptionReq() {}

  public EditMessageCaptionReq(
      ChatId chatId,
      Integer messageId,
      String inlineMessageId,
      String caption,
      String parseMode,
      InlineKeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.messageId = messageId;
    this.inlineMessageId = inlineMessageId;
    this.caption = caption;
    this.parseMode = parseMode;
    this.replyMarkup = replyMarkup;
  }

  public Optional<ChatId> getChatId() {
    return Optional.ofNullable(chatId);
  }

  public EditMessageCaptionReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public Optional<Integer> getMessageId() {
    return Optional.ofNullable(messageId);
  }

  public EditMessageCaptionReq setMessageId(Integer v) {
    this.messageId = v;
    return this;
  }

  public Optional<String> getInlineMessageId() {
    return Optional.ofNullable(inlineMessageId);
  }

  public EditMessageCaptionReq setInlineMessageId(String v) {
    this.inlineMessageId = v;
    return this;
  }

  public Optional<String> getCaption() {
    return Optional.ofNullable(caption);
  }

  public EditMessageCaptionReq setCaption(String v) {
    this.caption = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public EditMessageCaptionReq setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public Optional<InlineKeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public EditMessageCaptionReq setReplyMarkup(InlineKeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof EditMessageCaptionReq)) return false;

    EditMessageCaptionReq that = (EditMessageCaptionReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.messageId.equals(that.messageId)
        && this.inlineMessageId.equals(that.inlineMessageId)
        && this.caption.equals(that.caption)
        && this.parseMode.equals(that.parseMode)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "EditMessageCaptionReq{"
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
        + "caption="
        + '\''
        + this.caption
        + '\''
        + ','
        + "parseMode="
        + '\''
        + this.parseMode
        + '\''
        + ','
        + "replyMarkup="
        + this.replyMarkup
        + '}';
  }
}
