package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StopMessageLiveLocationReq {

  private ChatId chatId;

  private Integer messageId;

  private String inlineMessageId;

  private InlineKeyboardMarkup replyMarkup;

  public StopMessageLiveLocationReq() {}

  public StopMessageLiveLocationReq(
      ChatId chatId, Integer messageId, String inlineMessageId, InlineKeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.messageId = messageId;
    this.inlineMessageId = inlineMessageId;
    this.replyMarkup = replyMarkup;
  }

  public Optional<ChatId> getChatId() {
    return Optional.ofNullable(chatId);
  }

  public StopMessageLiveLocationReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public Optional<Integer> getMessageId() {
    return Optional.ofNullable(messageId);
  }

  public StopMessageLiveLocationReq setMessageId(Integer v) {
    this.messageId = v;
    return this;
  }

  public Optional<String> getInlineMessageId() {
    return Optional.ofNullable(inlineMessageId);
  }

  public StopMessageLiveLocationReq setInlineMessageId(String v) {
    this.inlineMessageId = v;
    return this;
  }

  public Optional<InlineKeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public StopMessageLiveLocationReq setReplyMarkup(InlineKeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof StopMessageLiveLocationReq)) return false;

    StopMessageLiveLocationReq that = (StopMessageLiveLocationReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.messageId.equals(that.messageId)
        && this.inlineMessageId.equals(that.inlineMessageId)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "StopMessageLiveLocationReq{"
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
        + ','
        + "replyMarkup="
        + this.replyMarkup
        + '}';
  }
}
