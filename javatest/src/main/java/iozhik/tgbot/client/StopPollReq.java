package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StopPollReq {

  private ChatId chatId;

  private int messageId;

  private InlineKeyboardMarkup replyMarkup;

  public StopPollReq() {}

  public StopPollReq(ChatId chatId, int messageId, InlineKeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.messageId = messageId;
    this.replyMarkup = replyMarkup;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public StopPollReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public int getMessageId() {
    return this.messageId;
  }

  public StopPollReq setMessageId(int v) {
    this.messageId = v;
    return this;
  }

  public Optional<InlineKeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public StopPollReq setReplyMarkup(InlineKeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof StopPollReq)) return false;

    StopPollReq that = (StopPollReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.messageId == that.messageId
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "StopPollReq{"
        + "chatId="
        + this.chatId
        + ','
        + "messageId="
        + this.messageId
        + ','
        + "replyMarkup="
        + this.replyMarkup
        + '}';
  }
}
