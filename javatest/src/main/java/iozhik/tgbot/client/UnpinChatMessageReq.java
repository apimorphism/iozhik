package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UnpinChatMessageReq {

  private ChatId chatId;

  public UnpinChatMessageReq() {}

  public UnpinChatMessageReq(ChatId chatId) {
    this.chatId = chatId;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public UnpinChatMessageReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof UnpinChatMessageReq)) return false;

    UnpinChatMessageReq that = (UnpinChatMessageReq) thatObj;

    return this.chatId.equals(that.chatId);
  }

  @Override
  public String toString() {
    return "UnpinChatMessageReq{" + "chatId=" + this.chatId + '}';
  }
}
