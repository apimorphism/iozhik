package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetChatMembersCountReq {

  private ChatId chatId;

  public GetChatMembersCountReq() {}

  public GetChatMembersCountReq(ChatId chatId) {
    this.chatId = chatId;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public GetChatMembersCountReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof GetChatMembersCountReq)) return false;

    GetChatMembersCountReq that = (GetChatMembersCountReq) thatObj;

    return this.chatId.equals(that.chatId);
  }

  @Override
  public String toString() {
    return "GetChatMembersCountReq{" + "chatId=" + this.chatId + '}';
  }
}
