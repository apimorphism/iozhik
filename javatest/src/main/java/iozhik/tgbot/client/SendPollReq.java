package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendPollReq {

  private ChatId chatId;

  private String question;

  private List<String> options;

  private Boolean disableNotification;

  private Integer replyToMessageId;

  private KeyboardMarkup replyMarkup;

  public SendPollReq() {}

  public SendPollReq(
      ChatId chatId,
      String question,
      List<String> options,
      Boolean disableNotification,
      Integer replyToMessageId,
      KeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.question = question;
    this.options = options;
    this.disableNotification = disableNotification;
    this.replyToMessageId = replyToMessageId;
    this.replyMarkup = replyMarkup;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public SendPollReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public String getQuestion() {
    return this.question;
  }

  public SendPollReq setQuestion(String v) {
    this.question = v;
    return this;
  }

  public List<String> getOptions() {
    return this.options;
  }

  public SendPollReq setOptions(List<String> v) {
    this.options = v;
    return this;
  }

  public Optional<Boolean> getDisableNotification() {
    return Optional.ofNullable(disableNotification);
  }

  public SendPollReq setDisableNotification(Boolean v) {
    this.disableNotification = v;
    return this;
  }

  public Optional<Integer> getReplyToMessageId() {
    return Optional.ofNullable(replyToMessageId);
  }

  public SendPollReq setReplyToMessageId(Integer v) {
    this.replyToMessageId = v;
    return this;
  }

  public Optional<KeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public SendPollReq setReplyMarkup(KeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SendPollReq)) return false;

    SendPollReq that = (SendPollReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.question.equals(that.question)
        && this.options.equals(that.options)
        && this.disableNotification.equals(that.disableNotification)
        && this.replyToMessageId.equals(that.replyToMessageId)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "SendPollReq{"
        + "chatId="
        + this.chatId
        + ','
        + "question="
        + '\''
        + this.question
        + '\''
        + ','
        + "options="
        + this.options
        + ','
        + "disableNotification="
        + this.disableNotification
        + ','
        + "replyToMessageId="
        + this.replyToMessageId
        + ','
        + "replyMarkup="
        + this.replyMarkup
        + '}';
  }
}
