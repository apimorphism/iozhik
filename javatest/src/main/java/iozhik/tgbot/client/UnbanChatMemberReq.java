package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UnbanChatMemberReq {

  private ChatId chatId;

  private int userId;

  public UnbanChatMemberReq() {}

  public UnbanChatMemberReq(ChatId chatId, int userId) {
    this.chatId = chatId;
    this.userId = userId;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public UnbanChatMemberReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public int getUserId() {
    return this.userId;
  }

  public UnbanChatMemberReq setUserId(int v) {
    this.userId = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof UnbanChatMemberReq)) return false;

    UnbanChatMemberReq that = (UnbanChatMemberReq) thatObj;

    return this.chatId.equals(that.chatId) && this.userId == that.userId;
  }

  @Override
  public String toString() {
    return "UnbanChatMemberReq{" + "chatId=" + this.chatId + ',' + "userId=" + this.userId + '}';
  }
}
