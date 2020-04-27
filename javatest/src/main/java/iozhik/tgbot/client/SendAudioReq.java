package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendAudioReq {

  private ChatId chatId;

  private IFile audio;

  private String caption;

  private String parseMode;

  private Integer duration;

  private String performer;

  private String title;

  private IFile thumb;

  private Boolean disableNotification;

  private Integer replyToMessageId;

  private KeyboardMarkup replyMarkup;

  public SendAudioReq() {}

  public SendAudioReq(
      ChatId chatId,
      IFile audio,
      String caption,
      String parseMode,
      Integer duration,
      String performer,
      String title,
      IFile thumb,
      Boolean disableNotification,
      Integer replyToMessageId,
      KeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.audio = audio;
    this.caption = caption;
    this.parseMode = parseMode;
    this.duration = duration;
    this.performer = performer;
    this.title = title;
    this.thumb = thumb;
    this.disableNotification = disableNotification;
    this.replyToMessageId = replyToMessageId;
    this.replyMarkup = replyMarkup;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public SendAudioReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public IFile getAudio() {
    return this.audio;
  }

  public SendAudioReq setAudio(IFile v) {
    this.audio = v;
    return this;
  }

  public Optional<String> getCaption() {
    return Optional.ofNullable(caption);
  }

  public SendAudioReq setCaption(String v) {
    this.caption = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public SendAudioReq setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public Optional<Integer> getDuration() {
    return Optional.ofNullable(duration);
  }

  public SendAudioReq setDuration(Integer v) {
    this.duration = v;
    return this;
  }

  public Optional<String> getPerformer() {
    return Optional.ofNullable(performer);
  }

  public SendAudioReq setPerformer(String v) {
    this.performer = v;
    return this;
  }

  public Optional<String> getTitle() {
    return Optional.ofNullable(title);
  }

  public SendAudioReq setTitle(String v) {
    this.title = v;
    return this;
  }

  public Optional<IFile> getThumb() {
    return Optional.ofNullable(thumb);
  }

  public SendAudioReq setThumb(IFile v) {
    this.thumb = v;
    return this;
  }

  public Optional<Boolean> getDisableNotification() {
    return Optional.ofNullable(disableNotification);
  }

  public SendAudioReq setDisableNotification(Boolean v) {
    this.disableNotification = v;
    return this;
  }

  public Optional<Integer> getReplyToMessageId() {
    return Optional.ofNullable(replyToMessageId);
  }

  public SendAudioReq setReplyToMessageId(Integer v) {
    this.replyToMessageId = v;
    return this;
  }

  public Optional<KeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public SendAudioReq setReplyMarkup(KeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SendAudioReq)) return false;

    SendAudioReq that = (SendAudioReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.audio.equals(that.audio)
        && this.caption.equals(that.caption)
        && this.parseMode.equals(that.parseMode)
        && this.duration.equals(that.duration)
        && this.performer.equals(that.performer)
        && this.title.equals(that.title)
        && this.thumb.equals(that.thumb)
        && this.disableNotification.equals(that.disableNotification)
        && this.replyToMessageId.equals(that.replyToMessageId)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "SendAudioReq{"
        + "chatId="
        + this.chatId
        + ','
        + "audio="
        + this.audio
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
        + "performer="
        + '\''
        + this.performer
        + '\''
        + ','
        + "title="
        + '\''
        + this.title
        + '\''
        + ','
        + "thumb="
        + this.thumb
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
