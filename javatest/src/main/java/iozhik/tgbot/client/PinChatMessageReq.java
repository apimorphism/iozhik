package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PinChatMessageReq {

  private ChatId chatId;

  private int messageId;

  private Boolean disableNotification;

  public PinChatMessageReq() {}

  public PinChatMessageReq(ChatId chatId, int messageId, Boolean disableNotification) {
    this.chatId = chatId;
    this.messageId = messageId;
    this.disableNotification = disableNotification;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public PinChatMessageReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public int getMessageId() {
    return this.messageId;
  }

  public PinChatMessageReq setMessageId(int v) {
    this.messageId = v;
    return this;
  }

  public Optional<Boolean> getDisableNotification() {
    return Optional.ofNullable(disableNotification);
  }

  public PinChatMessageReq setDisableNotification(Boolean v) {
    this.disableNotification = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof PinChatMessageReq)) return false;

    PinChatMessageReq that = (PinChatMessageReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.messageId == that.messageId
        && this.disableNotification.equals(that.disableNotification);
  }

  @Override
  public String toString() {
    return "PinChatMessageReq{"
        + "chatId="
        + this.chatId
        + ','
        + "messageId="
        + this.messageId
        + ','
        + "disableNotification="
        + this.disableNotification
        + '}';
  }
}
