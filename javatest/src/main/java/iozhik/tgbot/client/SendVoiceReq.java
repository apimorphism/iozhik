package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendVoiceReq {

  private ChatId chatId;

  private IFile voice;

  private String caption;

  private String parseMode;

  private Integer duration;

  private Boolean disableNotification;

  private Integer replyToMessageId;

  private KeyboardMarkup replyMarkup;

  public SendVoiceReq() {}

  public SendVoiceReq(
      ChatId chatId,
      IFile voice,
      String caption,
      String parseMode,
      Integer duration,
      Boolean disableNotification,
      Integer replyToMessageId,
      KeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.voice = voice;
    this.caption = caption;
    this.parseMode = parseMode;
    this.duration = duration;
    this.disableNotification = disableNotification;
    this.replyToMessageId = replyToMessageId;
    this.replyMarkup = replyMarkup;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public SendVoiceReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public IFile getVoice() {
    return this.voice;
  }

  public SendVoiceReq setVoice(IFile v) {
    this.voice = v;
    return this;
  }

  public Optional<String> getCaption() {
    return Optional.ofNullable(caption);
  }

  public SendVoiceReq setCaption(String v) {
    this.caption = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public SendVoiceReq setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public Optional<Integer> getDuration() {
    return Optional.ofNullable(duration);
  }

  public SendVoiceReq setDuration(Integer v) {
    this.duration = v;
    return this;
  }

  public Optional<Boolean> getDisableNotification() {
    return Optional.ofNullable(disableNotification);
  }

  public SendVoiceReq setDisableNotification(Boolean v) {
    this.disableNotification = v;
    return this;
  }

  public Optional<Integer> getReplyToMessageId() {
    return Optional.ofNullable(replyToMessageId);
  }

  public SendVoiceReq setReplyToMessageId(Integer v) {
    this.replyToMessageId = v;
    return this;
  }

  public Optional<KeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public SendVoiceReq setReplyMarkup(KeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SendVoiceReq)) return false;

    SendVoiceReq that = (SendVoiceReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.voice.equals(that.voice)
        && this.caption.equals(that.caption)
        && this.parseMode.equals(that.parseMode)
        && this.duration.equals(that.duration)
        && this.disableNotification.equals(that.disableNotification)
        && this.replyToMessageId.equals(that.replyToMessageId)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "SendVoiceReq{"
        + "chatId="
        + this.chatId
        + ','
        + "voice="
        + this.voice
        + ','
        + "caption="
        + '\''
        + this.caption
        + '\''
        + ','
        + "parseMode="
        + '\''
        + this.parseMode
        + '\''
        + ','
        + "duration="
        + this.duration
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
