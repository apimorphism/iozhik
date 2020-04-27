package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendVenueReq {

  private ChatId chatId;

  private Float latitude;

  private Float longitude;

  private String title;

  private String address;

  private String foursquareId;

  private String foursquareType;

  private Boolean disableNotification;

  private Integer replyToMessageId;

  private KeyboardMarkup replyMarkup;

  public SendVenueReq() {}

  public SendVenueReq(
      ChatId chatId,
      Float latitude,
      Float longitude,
      String title,
      String address,
      String foursquareId,
      String foursquareType,
      Boolean disableNotification,
      Integer replyToMessageId,
      KeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.latitude = latitude;
    this.longitude = longitude;
    this.title = title;
    this.address = address;
    this.foursquareId = foursquareId;
    this.foursquareType = foursquareType;
    this.disableNotification = disableNotification;
    this.replyToMessageId = replyToMessageId;
    this.replyMarkup = replyMarkup;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public SendVenueReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public Float getLatitude() {
    return this.latitude;
  }

  public SendVenueReq setLatitude(Float v) {
    this.latitude = v;
    return this;
  }

  public Float getLongitude() {
    return this.longitude;
  }

  public SendVenueReq setLongitude(Float v) {
    this.longitude = v;
    return this;
  }

  public String getTitle() {
    return this.title;
  }

  public SendVenueReq setTitle(String v) {
    this.title = v;
    return this;
  }

  public String getAddress() {
    return this.address;
  }

  public SendVenueReq setAddress(String v) {
    this.address = v;
    return this;
  }

  public Optional<String> getFoursquareId() {
    return Optional.ofNullable(foursquareId);
  }

  public SendVenueReq setFoursquareId(String v) {
    this.foursquareId = v;
    return this;
  }

  public Optional<String> getFoursquareType() {
    return Optional.ofNullable(foursquareType);
  }

  public SendVenueReq setFoursquareType(String v) {
    this.foursquareType = v;
    return this;
  }

  public Optional<Boolean> getDisableNotification() {
    return Optional.ofNullable(disableNotification);
  }

  public SendVenueReq setDisableNotification(Boolean v) {
    this.disableNotification = v;
    return this;
  }

  public Optional<Integer> getReplyToMessageId() {
    return Optional.ofNullable(replyToMessageId);
  }

  public SendVenueReq setReplyToMessageId(Integer v) {
    this.replyToMessageId = v;
    return this;
  }

  public Optional<KeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public SendVenueReq setReplyMarkup(KeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SendVenueReq)) return false;

    SendVenueReq that = (SendVenueReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.latitude.equals(that.latitude)
        && this.longitude.equals(that.longitude)
        && this.title.equals(that.title)
        && this.address.equals(that.address)
        && this.foursquareId.equals(that.foursquareId)
        && this.foursquareType.equals(that.foursquareType)
        && this.disableNotification.equals(that.disableNotification)
        && this.replyToMessageId.equals(that.replyToMessageId)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "SendVenueReq{"
        + "chatId="
        + this.chatId
        + ','
        + "latitude="
        + this.latitude
        + ','
        + "longitude="
        + this.longitude
        + ','
        + "title="
        + '\''
        + this.title
        + '\''
        + ','
        + "address="
        + '\''
        + this.address
        + '\''
        + ','
        + "foursquareId="
        + '\''
        + this.foursquareId
        + '\''
        + ','
        + "foursquareType="
        + '\''
        + this.foursquareType
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
