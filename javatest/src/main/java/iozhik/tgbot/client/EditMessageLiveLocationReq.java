package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EditMessageLiveLocationReq {

  private ChatId chatId;

  private Integer messageId;

  private String inlineMessageId;

  private Float latitude;

  private Float longitude;

  private InlineKeyboardMarkup replyMarkup;

  public EditMessageLiveLocationReq() {}

  public EditMessageLiveLocationReq(
      ChatId chatId,
      Integer messageId,
      String inlineMessageId,
      Float latitude,
      Float longitude,
      InlineKeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.messageId = messageId;
    this.inlineMessageId = inlineMessageId;
    this.latitude = latitude;
    this.longitude = longitude;
    this.replyMarkup = replyMarkup;
  }

  public Optional<ChatId> getChatId() {
    return Optional.ofNullable(chatId);
  }

  public EditMessageLiveLocationReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public Optional<Integer> getMessageId() {
    return Optional.ofNullable(messageId);
  }

  public EditMessageLiveLocationReq setMessageId(Integer v) {
    this.messageId = v;
    return this;
  }

  public Optional<String> getInlineMessageId() {
    return Optional.ofNullable(inlineMessageId);
  }

  public EditMessageLiveLocationReq setInlineMessageId(String v) {
    this.inlineMessageId = v;
    return this;
  }

  public Float getLatitude() {
    return this.latitude;
  }

  public EditMessageLiveLocationReq setLatitude(Float v) {
    this.latitude = v;
    return this;
  }

  public Float getLongitude() {
    return this.longitude;
  }

  public EditMessageLiveLocationReq setLongitude(Float v) {
    this.longitude = v;
    return this;
  }

  public Optional<InlineKeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public EditMessageLiveLocationReq setReplyMarkup(InlineKeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof EditMessageLiveLocationReq)) return false;

    EditMessageLiveLocationReq that = (EditMessageLiveLocationReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.messageId.equals(that.messageId)
        && this.inlineMessageId.equals(that.inlineMessageId)
        && this.latitude.equals(that.latitude)
        && this.longitude.equals(that.longitude)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "EditMessageLiveLocationReq{"
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
        + "latitude="
        + this.latitude
        + ','
        + "longitude="
        + this.longitude
        + ','
        + "replyMarkup="
        + this.replyMarkup
        + '}';
  }
}
