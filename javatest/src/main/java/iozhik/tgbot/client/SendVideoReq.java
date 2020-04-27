package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendVideoReq {

  private ChatId chatId;

  private IFile video;

  private Integer duration;

  private Integer width;

  private Integer height;

  private IFile thumb;

  private String caption;

  private String parseMode;

  private Boolean supportsStreaming;

  private Boolean disableNotification;

  private Integer replyToMessageId;

  private KeyboardMarkup replyMarkup;

  public SendVideoReq() {}

  public SendVideoReq(
      ChatId chatId,
      IFile video,
      Integer duration,
      Integer width,
      Integer height,
      IFile thumb,
      String caption,
      String parseMode,
      Boolean supportsStreaming,
      Boolean disableNotification,
      Integer replyToMessageId,
      KeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.video = video;
    this.duration = duration;
    this.width = width;
    this.height = height;
    this.thumb = thumb;
    this.caption = caption;
    this.parseMode = parseMode;
    this.supportsStreaming = supportsStreaming;
    this.disableNotification = disableNotification;
    this.replyToMessageId = replyToMessageId;
    this.replyMarkup = replyMarkup;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public SendVideoReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public IFile getVideo() {
    return this.video;
  }

  public SendVideoReq setVideo(IFile v) {
    this.video = v;
    return this;
  }

  public Optional<Integer> getDuration() {
    return Optional.ofNullable(duration);
  }

  public SendVideoReq setDuration(Integer v) {
    this.duration = v;
    return this;
  }

  public Optional<Integer> getWidth() {
    return Optional.ofNullable(width);
  }

  public SendVideoReq setWidth(Integer v) {
    this.width = v;
    return this;
  }

  public Optional<Integer> getHeight() {
    return Optional.ofNullable(height);
  }

  public SendVideoReq setHeight(Integer v) {
    this.height = v;
    return this;
  }

  public Optional<IFile> getThumb() {
    return Optional.ofNullable(thumb);
  }

  public SendVideoReq setThumb(IFile v) {
    this.thumb = v;
    return this;
  }

  public Optional<String> getCaption() {
    return Optional.ofNullable(caption);
  }

  public SendVideoReq setCaption(String v) {
    this.caption = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public SendVideoReq setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public Optional<Boolean> getSupportsStreaming() {
    return Optional.ofNullable(supportsStreaming);
  }

  public SendVideoReq setSupportsStreaming(Boolean v) {
    this.supportsStreaming = v;
    return this;
  }

  public Optional<Boolean> getDisableNotification() {
    return Optional.ofNullable(disableNotification);
  }

  public SendVideoReq setDisableNotification(Boolean v) {
    this.disableNotification = v;
    return this;
  }

  public Optional<Integer> getReplyToMessageId() {
    return Optional.ofNullable(replyToMessageId);
  }

  public SendVideoReq setReplyToMessageId(Integer v) {
    this.replyToMessageId = v;
    return this;
  }

  public Optional<KeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public SendVideoReq setReplyMarkup(KeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SendVideoReq)) return false;

    SendVideoReq that = (SendVideoReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.video.equals(that.video)
        && this.duration.equals(that.duration)
        && this.width.equals(that.width)
        && this.height.equals(that.height)
        && this.thumb.equals(that.thumb)
        && this.caption.equals(that.caption)
        && this.parseMode.equals(that.parseMode)
        && this.supportsStreaming.equals(that.supportsStreaming)
        && this.disableNotification.equals(that.disableNotification)
        && this.replyToMessageId.equals(that.replyToMessageId)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "SendVideoReq{"
        + "chatId="
        + this.chatId
        + ','
        + "video="
        + this.video
        + ','
        + "duration="
        + this.duration
        + ','
        + "width="
        + this.width
        + ','
        + "height="
        + this.height
        + ','
        + "thumb="
        + this.thumb
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
        + "supportsStreaming="
        + this.supportsStreaming
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
