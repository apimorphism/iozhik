package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EditMessageTextReq {

  private ChatId chatId;

  private Integer messageId;

  private String inlineMessageId;

  private String text;

  private String parseMode;

  private Boolean disableWebPagePreview;

  private InlineKeyboardMarkup replyMarkup;

  public EditMessageTextReq() {}

  public EditMessageTextReq(
      ChatId chatId,
      Integer messageId,
      String inlineMessageId,
      String text,
      String parseMode,
      Boolean disableWebPagePreview,
      InlineKeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.messageId = messageId;
    this.inlineMessageId = inlineMessageId;
    this.text = text;
    this.parseMode = parseMode;
    this.disableWebPagePreview = disableWebPagePreview;
    this.replyMarkup = replyMarkup;
  }

  public Optional<ChatId> getChatId() {
    return Optional.ofNullable(chatId);
  }

  public EditMessageTextReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public Optional<Integer> getMessageId() {
    return Optional.ofNullable(messageId);
  }

  public EditMessageTextReq setMessageId(Integer v) {
    this.messageId = v;
    return this;
  }

  public Optional<String> getInlineMessageId() {
    return Optional.ofNullable(inlineMessageId);
  }

  public EditMessageTextReq setInlineMessageId(String v) {
    this.inlineMessageId = v;
    return this;
  }

  public String getText() {
    return this.text;
  }

  public EditMessageTextReq setText(String v) {
    this.text = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public EditMessageTextReq setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public Optional<Boolean> getDisableWebPagePreview() {
    return Optional.ofNullable(disableWebPagePreview);
  }

  public EditMessageTextReq setDisableWebPagePreview(Boolean v) {
    this.disableWebPagePreview = v;
    return this;
  }

  public Optional<InlineKeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public EditMessageTextReq setReplyMarkup(InlineKeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof EditMessageTextReq)) return false;

    EditMessageTextReq that = (EditMessageTextReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.messageId.equals(that.messageId)
        && this.inlineMessageId.equals(that.inlineMessageId)
        && this.text.equals(that.text)
        && this.parseMode.equals(that.parseMode)
        && this.disableWebPagePreview.equals(that.disableWebPagePreview)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "EditMessageTextReq{"
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
        + "text="
        + '\''
        + this.text
        + '\''
        + ','
        + "parseMode="
        + '\''
        + this.parseMode
        + '\''
        + ','
        + "disableWebPagePreview="
        + this.disableWebPagePreview
        + ','
        + "replyMarkup="
        + this.replyMarkup
        + '}';
  }
}
