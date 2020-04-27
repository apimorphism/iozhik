package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendMessageReq {

  private ChatId chatId;

  private String text;

  private String parseMode;

  private Boolean disableWebPagePreview;

  private Boolean disableNotification;

  private Integer replyToMessageId;

  private KeyboardMarkup replyMarkup;

  public SendMessageReq() {}

  public SendMessageReq(
      ChatId chatId,
      String text,
      String parseMode,
      Boolean disableWebPagePreview,
      Boolean disableNotification,
      Integer replyToMessageId,
      KeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.text = text;
    this.parseMode = parseMode;
    this.disableWebPagePreview = disableWebPagePreview;
    this.disableNotification = disableNotification;
    this.replyToMessageId = replyToMessageId;
    this.replyMarkup = replyMarkup;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public SendMessageReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public String getText() {
    return this.text;
  }

  public SendMessageReq setText(String v) {
    this.text = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public SendMessageReq setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public Optional<Boolean> getDisableWebPagePreview() {
    return Optional.ofNullable(disableWebPagePreview);
  }

  public SendMessageReq setDisableWebPagePreview(Boolean v) {
    this.disableWebPagePreview = v;
    return this;
  }

  public Optional<Boolean> getDisableNotification() {
    return Optional.ofNullable(disableNotification);
  }

  public SendMessageReq setDisableNotification(Boolean v) {
    this.disableNotification = v;
    return this;
  }

  public Optional<Integer> getReplyToMessageId() {
    return Optional.ofNullable(replyToMessageId);
  }

  public SendMessageReq setReplyToMessageId(Integer v) {
    this.replyToMessageId = v;
    return this;
  }

  public Optional<KeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public SendMessageReq setReplyMarkup(KeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SendMessageReq)) return false;

    SendMessageReq that = (SendMessageReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.text.equals(that.text)
        && this.parseMode.equals(that.parseMode)
        && this.disableWebPagePreview.equals(that.disableWebPagePreview)
        && this.disableNotification.equals(that.disableNotification)
        && this.replyToMessageId.equals(that.replyToMessageId)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "SendMessageReq{"
        + "chatId="
        + this.chatId
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
        + "disableNotification="
        + this.disableNotification
        + ','
        + "replyToMessageId="
        + this.replyToMessageId
        + ','
        + "replyMarkup="
        + this.replyMarkup
        + '}';
  }
}
