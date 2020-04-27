package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SetGameScoreReq {

  private int userId;

  private int score;

  private Boolean force;

  private Boolean disableEditMessage;

  private Integer chatId;

  private Integer messageId;

  private String inlineMessageId;

  public SetGameScoreReq() {}

  public SetGameScoreReq(
      int userId,
      int score,
      Boolean force,
      Boolean disableEditMessage,
      Integer chatId,
      Integer messageId,
      String inlineMessageId) {
    this.userId = userId;
    this.score = score;
    this.force = force;
    this.disableEditMessage = disableEditMessage;
    this.chatId = chatId;
    this.messageId = messageId;
    this.inlineMessageId = inlineMessageId;
  }

  public int getUserId() {
    return this.userId;
  }

  public SetGameScoreReq setUserId(int v) {
    this.userId = v;
    return this;
  }

  public int getScore() {
    return this.score;
  }

  public SetGameScoreReq setScore(int v) {
    this.score = v;
    return this;
  }

  public Optional<Boolean> getForce() {
    return Optional.ofNullable(force);
  }

  public SetGameScoreReq setForce(Boolean v) {
    this.force = v;
    return this;
  }

  public Optional<Boolean> getDisableEditMessage() {
    return Optional.ofNullable(disableEditMessage);
  }

  public SetGameScoreReq setDisableEditMessage(Boolean v) {
    this.disableEditMessage = v;
    return this;
  }

  public Optional<Integer> getChatId() {
    return Optional.ofNullable(chatId);
  }

  public SetGameScoreReq setChatId(Integer v) {
    this.chatId = v;
    return this;
  }

  public Optional<Integer> getMessageId() {
    return Optional.ofNullable(messageId);
  }

  public SetGameScoreReq setMessageId(Integer v) {
    this.messageId = v;
    return this;
  }

  public Optional<String> getInlineMessageId() {
    return Optional.ofNullable(inlineMessageId);
  }

  public SetGameScoreReq setInlineMessageId(String v) {
    this.inlineMessageId = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SetGameScoreReq)) return false;

    SetGameScoreReq that = (SetGameScoreReq) thatObj;

    return this.userId == that.userId
        && this.score == that.score
        && this.force.equals(that.force)
        && this.disableEditMessage.equals(that.disableEditMessage)
        && this.chatId.equals(that.chatId)
        && this.messageId.equals(that.messageId)
        && this.inlineMessageId.equals(that.inlineMessageId);
  }

  @Override
  public String toString() {
    return "SetGameScoreReq{"
        + "userId="
        + this.userId
        + ','
        + "score="
        + this.score
        + ','
        + "force="
        + this.force
        + ','
        + "disableEditMessage="
        + this.disableEditMessage
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
