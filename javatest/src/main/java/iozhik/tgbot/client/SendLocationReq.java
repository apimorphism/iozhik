package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendLocationReq {

  private ChatId chatId;

  private Float latitude;

  private Float longitude;

  private Integer livePeriod;

  private Boolean disableNotification;

  private Integer replyToMessageId;

  private KeyboardMarkup replyMarkup;

  public SendLocationReq() {}

  public SendLocationReq(
      ChatId chatId,
      Float latitude,
      Float longitude,
      Integer livePeriod,
      Boolean disableNotification,
      Integer replyToMessageId,
      KeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.latitude = latitude;
    this.longitude = longitude;
    this.livePeriod = livePeriod;
    this.disableNotification = disableNotification;
    this.replyToMessageId = replyToMessageId;
    this.replyMarkup = replyMarkup;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public SendLocationReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public Float getLatitude() {
    return this.latitude;
  }

  public SendLocationReq setLatitude(Float v) {
    this.latitude = v;
    return this;
  }

  public Float getLongitude() {
    return this.longitude;
  }

  public SendLocationReq setLongitude(Float v) {
    this.longitude = v;
    return this;
  }

  public Optional<Integer> getLivePeriod() {
    return Optional.ofNullable(livePeriod);
  }

  public SendLocationReq setLivePeriod(Integer v) {
    this.livePeriod = v;
    return this;
  }

  public Optional<Boolean> getDisableNotification() {
    return Optional.ofNullable(disableNotification);
  }

  public SendLocationReq setDisableNotification(Boolean v) {
    this.disableNotification = v;
    return this;
  }

  public Optional<Integer> getReplyToMessageId() {
    return Optional.ofNullable(replyToMessageId);
  }

  public SendLocationReq setReplyToMessageId(Integer v) {
    this.replyToMessageId = v;
    return this;
  }

  public Optional<KeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public SendLocationReq setReplyMarkup(KeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SendLocationReq)) return false;

    SendLocationReq that = (SendLocationReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.latitude.equals(that.latitude)
        && this.longitude.equals(that.longitude)
        && this.livePeriod.equals(that.livePeriod)
        && this.disableNotification.equals(that.disableNotification)
        && this.replyToMessageId.equals(that.replyToMessageId)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "SendLocationReq{"
        + "chatId="
        + this.chatId
        + ','
        + "latitude="
        + this.latitude
        + ','
        + "longitude="
        + this.longitude
        + ','
        + "livePeriod="
        + this.livePeriod
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
