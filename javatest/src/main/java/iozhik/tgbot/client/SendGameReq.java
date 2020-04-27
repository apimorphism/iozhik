package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendGameReq {

  private int chatId;

  private String gameShortName;

  private Boolean disableNotification;

  private Integer replyToMessageId;

  private InlineKeyboardMarkup replyMarkup;

  public SendGameReq() {}

  public SendGameReq(
      int chatId,
      String gameShortName,
      Boolean disableNotification,
      Integer replyToMessageId,
      InlineKeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.gameShortName = gameShortName;
    this.disableNotification = disableNotification;
    this.replyToMessageId = replyToMessageId;
    this.replyMarkup = replyMarkup;
  }

  public int getChatId() {
    return this.chatId;
  }

  public SendGameReq setChatId(int v) {
    this.chatId = v;
    return this;
  }

  public String getGameShortName() {
    return this.gameShortName;
  }

  public SendGameReq setGameShortName(String v) {
    this.gameShortName = v;
    return this;
  }

  public Optional<Boolean> getDisableNotification() {
    return Optional.ofNullable(disableNotification);
  }

  public SendGameReq setDisableNotification(Boolean v) {
    this.disableNotification = v;
    return this;
  }

  public Optional<Integer> getReplyToMessageId() {
    return Optional.ofNullable(replyToMessageId);
  }

  public SendGameReq setReplyToMessageId(Integer v) {
    this.replyToMessageId = v;
    return this;
  }

  public Optional<InlineKeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public SendGameReq setReplyMarkup(InlineKeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SendGameReq)) return false;

    SendGameReq that = (SendGameReq) thatObj;

    return this.chatId == that.chatId
        && this.gameShortName.equals(that.gameShortName)
        && this.disableNotification.equals(that.disableNotification)
        && this.replyToMessageId.equals(that.replyToMessageId)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "SendGameReq{"
        + "chatId="
        + this.chatId
        + ','
        + "gameShortName="
        + '\''
        + this.gameShortName
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
