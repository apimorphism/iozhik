package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetGameHighScoresReq {

  private int userId;

  private Integer chatId;

  private Integer messageId;

  private String inlineMessageId;

  public GetGameHighScoresReq() {}

  public GetGameHighScoresReq(
      int userId, Integer chatId, Integer messageId, String inlineMessageId) {
    this.userId = userId;
    this.chatId = chatId;
    this.messageId = messageId;
    this.inlineMessageId = inlineMessageId;
  }

  public int getUserId() {
    return this.userId;
  }

  public GetGameHighScoresReq setUserId(int v) {
    this.userId = v;
    return this;
  }

  public Optional<Integer> getChatId() {
    return Optional.ofNullable(chatId);
  }

  public GetGameHighScoresReq setChatId(Integer v) {
    this.chatId = v;
    return this;
  }

  public Optional<Integer> getMessageId() {
    return Optional.ofNullable(messageId);
  }

  public GetGameHighScoresReq setMessageId(Integer v) {
    this.messageId = v;
    return this;
  }

  public Optional<String> getInlineMessageId() {
    return Optional.ofNullable(inlineMessageId);
  }

  public GetGameHighScoresReq setInlineMessageId(String v) {
    this.inlineMessageId = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof GetGameHighScoresReq)) return false;

    GetGameHighScoresReq that = (GetGameHighScoresReq) thatObj;

    return this.userId == that.userId
        && this.chatId.equals(that.chatId)
        && this.messageId.equals(that.messageId)
        && this.inlineMessageId.equals(that.inlineMessageId);
  }

  @Override
  public String toString() {
    return "GetGameHighScoresReq{"
        + "userId="
        + this.userId
        + ','
        + "chatId="
        + this.chatId
        + ','
        + "messageId="
        + this.messageId
        + ','
        + "inlineMessageId="
        + '\''
        + this.inlineMessageId
        + '\''
        + '}';
  }
}
