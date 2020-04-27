package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendDocumentReq {

  private ChatId chatId;

  private IFile document;

  private IFile thumb;

  private String caption;

  private String parseMode;

  private Boolean disableNotification;

  private Integer replyToMessageId;

  private KeyboardMarkup replyMarkup;

  public SendDocumentReq() {}

  public SendDocumentReq(
      ChatId chatId,
      IFile document,
      IFile thumb,
      String caption,
      String parseMode,
      Boolean disableNotification,
      Integer replyToMessageId,
      KeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.document = document;
    this.thumb = thumb;
    this.caption = caption;
    this.parseMode = parseMode;
    this.disableNotification = disableNotification;
    this.replyToMessageId = replyToMessageId;
    this.replyMarkup = replyMarkup;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public SendDocumentReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public IFile getDocument() {
    return this.document;
  }

  public SendDocumentReq setDocument(IFile v) {
    this.document = v;
    return this;
  }

  public Optional<IFile> getThumb() {
    return Optional.ofNullable(thumb);
  }

  public SendDocumentReq setThumb(IFile v) {
    this.thumb = v;
    return this;
  }

  public Optional<String> getCaption() {
    return Optional.ofNullable(caption);
  }

  public SendDocumentReq setCaption(String v) {
    this.caption = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public SendDocumentReq setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public Optional<Boolean> getDisableNotification() {
    return Optional.ofNullable(disableNotification);
  }

  public SendDocumentReq setDisableNotification(Boolean v) {
    this.disableNotification = v;
    return this;
  }

  public Optional<Integer> getReplyToMessageId() {
    return Optional.ofNullable(replyToMessageId);
  }

  public SendDocumentReq setReplyToMessageId(Integer v) {
    this.replyToMessageId = v;
    return this;
  }

  public Optional<KeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public SendDocumentReq setReplyMarkup(KeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SendDocumentReq)) return false;

    SendDocumentReq that = (SendDocumentReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.document.equals(that.document)
        && this.thumb.equals(that.thumb)
        && this.caption.equals(that.caption)
        && this.parseMode.equals(that.parseMode)
        && this.disableNotification.equals(that.disableNotification)
        && this.replyToMessageId.equals(that.replyToMessageId)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "SendDocumentReq{"
        + "chatId="
        + this.chatId
        + ','
        + "document="
        + this.document
        + ','
        + "thumb="
        + this.thumb
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
