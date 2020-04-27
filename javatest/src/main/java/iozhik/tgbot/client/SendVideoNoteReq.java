package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendVideoNoteReq {

  private ChatId chatId;

  private IFile videoNote;

  private Integer duration;

  private Integer length;

  private IFile thumb;

  private Boolean disableNotification;

  private Integer replyToMessageId;

  private KeyboardMarkup replyMarkup;

  public SendVideoNoteReq() {}

  public SendVideoNoteReq(
      ChatId chatId,
      IFile videoNote,
      Integer duration,
      Integer length,
      IFile thumb,
      Boolean disableNotification,
      Integer replyToMessageId,
      KeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.videoNote = videoNote;
    this.duration = duration;
    this.length = length;
    this.thumb = thumb;
    this.disableNotification = disableNotification;
    this.replyToMessageId = replyToMessageId;
    this.replyMarkup = replyMarkup;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public SendVideoNoteReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public IFile getVideoNote() {
    return this.videoNote;
  }

  public SendVideoNoteReq setVideoNote(IFile v) {
    this.videoNote = v;
    return this;
  }

  public Optional<Integer> getDuration() {
    return Optional.ofNullable(duration);
  }

  public SendVideoNoteReq setDuration(Integer v) {
    this.duration = v;
    return this;
  }

  public Optional<Integer> getLength() {
    return Optional.ofNullable(length);
  }

  public SendVideoNoteReq setLength(Integer v) {
    this.length = v;
    return this;
  }

  public Optional<IFile> getThumb() {
    return Optional.ofNullable(thumb);
  }

  public SendVideoNoteReq setThumb(IFile v) {
    this.thumb = v;
    return this;
  }

  public Optional<Boolean> getDisableNotification() {
    return Optional.ofNullable(disableNotification);
  }

  public SendVideoNoteReq setDisableNotification(Boolean v) {
    this.disableNotification = v;
    return this;
  }

  public Optional<Integer> getReplyToMessageId() {
    return Optional.ofNullable(replyToMessageId);
  }

  public SendVideoNoteReq setReplyToMessageId(Integer v) {
    this.replyToMessageId = v;
    return this;
  }

  public Optional<KeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public SendVideoNoteReq setReplyMarkup(KeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SendVideoNoteReq)) return false;

    SendVideoNoteReq that = (SendVideoNoteReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.videoNote.equals(that.videoNote)
        && this.duration.equals(that.duration)
        && this.length.equals(that.length)
        && this.thumb.equals(that.thumb)
        && this.disableNotification.equals(that.disableNotification)
        && this.replyToMessageId.equals(that.replyToMessageId)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "SendVideoNoteReq{"
        + "chatId="
        + this.chatId
        + ','
        + "videoNote="
        + this.videoNote
        + ','
        + "duration="
        + this.duration
        + ','
        + "length="
        + this.length
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
