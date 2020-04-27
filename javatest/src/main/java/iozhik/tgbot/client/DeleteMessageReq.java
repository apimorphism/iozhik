package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DeleteMessageReq {

  private ChatId chatId;

  private int messageId;

  public DeleteMessageReq() {}

  public DeleteMessageReq(ChatId chatId, int messageId) {
    this.chatId = chatId;
    this.messageId = messageId;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public DeleteMessageReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public int getMessageId() {
    return this.messageId;
  }

  public DeleteMessageReq setMessageId(int v) {
    this.messageId = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof DeleteMessageReq)) return false;

    DeleteMessageReq that = (DeleteMessageReq) thatObj;

    return this.chatId.equals(that.chatId) && this.messageId == that.messageId;
  }

  @Override
  public String toString() {
    return "DeleteMessageReq{"
        + "chatId="
        + this.chatId
        + ','
        + "messageId="
        + this.messageId
        + '}';
  }
}
