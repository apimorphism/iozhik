package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KickChatMemberReq {

  private ChatId chatId;

  private int userId;

  private Integer untilDate;

  public KickChatMemberReq() {}

  public KickChatMemberReq(ChatId chatId, int userId, Integer untilDate) {
    this.chatId = chatId;
    this.userId = userId;
    this.untilDate = untilDate;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public KickChatMemberReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public int getUserId() {
    return this.userId;
  }

  public KickChatMemberReq setUserId(int v) {
    this.userId = v;
    return this;
  }

  public Optional<Integer> getUntilDate() {
    return Optional.ofNullable(untilDate);
  }

  public KickChatMemberReq setUntilDate(Integer v) {
    this.untilDate = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof KickChatMemberReq)) return false;

    KickChatMemberReq that = (KickChatMemberReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.userId == that.userId
        && this.untilDate.equals(that.untilDate);
  }

  @Override
  public String toString() {
    return "KickChatMemberReq{"
        + "chatId="
        + this.chatId
        + ','
        + "userId="
        + this.userId
        + ','
        + "untilDate="
        + this.untilDate
        + '}';
  }
}
