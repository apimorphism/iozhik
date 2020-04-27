package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ForwardMessageReq {

  private ChatId chatId;

  private ChatId fromChatId;

  private Boolean disableNotification;

  private int messageId;

  public ForwardMessageReq() {}

  public ForwardMessageReq(
      ChatId chatId, ChatId fromChatId, Boolean disableNotification, int messageId) {
    this.chatId = chatId;
    this.fromChatId = fromChatId;
    this.disableNotification = disableNotification;
    this.messageId = messageId;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public ForwardMessageReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public ChatId getFromChatId() {
    return this.fromChatId;
  }

  public ForwardMessageReq setFromChatId(ChatId v) {
    this.fromChatId = v;
    return this;
  }

  public Optional<Boolean> getDisableNotification() {
    return Optional.ofNullable(disableNotification);
  }

  public ForwardMessageReq setDisableNotification(Boolean v) {
    this.disableNotification = v;
    return this;
  }

  public int getMessageId() {
    return this.messageId;
  }

  public ForwardMessageReq setMessageId(int v) {
    this.messageId = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof ForwardMessageReq)) return false;

    ForwardMessageReq that = (ForwardMessageReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.fromChatId.equals(that.fromChatId)
        && this.disableNotification.equals(that.disableNotification)
        && this.messageId == that.messageId;
  }

  @Override
  public String toString() {
    return "ForwardMessageReq{"
        + "chatId="
        + this.chatId
        + ','
        + "fromChatId="
        + this.fromChatId
        + ','
        + "disableNotification="
        + this.disableNotification
        + ','
        + "messageId="
        + this.messageId
        + '}';
  }
}
