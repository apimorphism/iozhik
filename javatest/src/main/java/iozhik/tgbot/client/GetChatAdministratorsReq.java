package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetChatAdministratorsReq {

  private ChatId chatId;

  public GetChatAdministratorsReq() {}

  public GetChatAdministratorsReq(ChatId chatId) {
    this.chatId = chatId;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public GetChatAdministratorsReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof GetChatAdministratorsReq)) return false;

    GetChatAdministratorsReq that = (GetChatAdministratorsReq) thatObj;

    return this.chatId.equals(that.chatId);
  }

  @Override
  public String toString() {
    return "GetChatAdministratorsReq{" + "chatId=" + this.chatId + '}';
  }
}
