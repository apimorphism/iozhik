package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendContactReq {

  private ChatId chatId;

  private String phoneNumber;

  private String firstName;

  private String lastName;

  private String vcard;

  private Boolean disableNotification;

  private Integer replyToMessageId;

  private KeyboardMarkup replyMarkup;

  public SendContactReq() {}

  public SendContactReq(
      ChatId chatId,
      String phoneNumber,
      String firstName,
      String lastName,
      String vcard,
      Boolean disableNotification,
      Integer replyToMessageId,
      KeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.phoneNumber = phoneNumber;
    this.firstName = firstName;
    this.lastName = lastName;
    this.vcard = vcard;
    this.disableNotification = disableNotification;
    this.replyToMessageId = replyToMessageId;
    this.replyMarkup = replyMarkup;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public SendContactReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public SendContactReq setPhoneNumber(String v) {
    this.phoneNumber = v;
    return this;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public SendContactReq setFirstName(String v) {
    this.firstName = v;
    return this;
  }

  public Optional<String> getLastName() {
    return Optional.ofNullable(lastName);
  }

  public SendContactReq setLastName(String v) {
    this.lastName = v;
    return this;
  }

  public Optional<String> getVcard() {
    return Optional.ofNullable(vcard);
  }

  public SendContactReq setVcard(String v) {
    this.vcard = v;
    return this;
  }

  public Optional<Boolean> getDisableNotification() {
    return Optional.ofNullable(disableNotification);
  }

  public SendContactReq setDisableNotification(Boolean v) {
    this.disableNotification = v;
    return this;
  }

  public Optional<Integer> getReplyToMessageId() {
    return Optional.ofNullable(replyToMessageId);
  }

  public SendContactReq setReplyToMessageId(Integer v) {
    this.replyToMessageId = v;
    return this;
  }

  public Optional<KeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public SendContactReq setReplyMarkup(KeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SendContactReq)) return false;

    SendContactReq that = (SendContactReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.phoneNumber.equals(that.phoneNumber)
        && this.firstName.equals(that.firstName)
        && this.lastName.equals(that.lastName)
        && this.vcard.equals(that.vcard)
        && this.disableNotification.equals(that.disableNotification)
        && this.replyToMessageId.equals(that.replyToMessageId)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "SendContactReq{"
        + "chatId="
        + this.chatId
        + ','
        + "phoneNumber="
        + '\''
        + this.phoneNumber
        + '\''
        + ','
        + "firstName="
        + '\''
        + this.firstName
        + '\''
        + ','
        + "lastName="
        + '\''
        + this.lastName
        + '\''
        + ','
        + "vcard="
        + '\''
        + this.vcard
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
