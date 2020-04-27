package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendAnimationReq {

  private ChatId chatId;

  private IFile animation;

  private Integer duration;

  private Integer width;

  private Integer height;

  private IFile thumb;

  private String caption;

  private String parseMode;

  private Boolean disableNotification;

  private Integer replyToMessageId;

  private KeyboardMarkup replyMarkup;

  public SendAnimationReq() {}

  public SendAnimationReq(
      ChatId chatId,
      IFile animation,
      Integer duration,
      Integer width,
      Integer height,
      IFile thumb,
      String caption,
      String parseMode,
      Boolean disableNotification,
      Integer replyToMessageId,
      KeyboardMarkup replyMarkup) {
    this.chatId = chatId;
    this.animation = animation;
    this.duration = duration;
    this.width = width;
    this.height = height;
    this.thumb = thumb;
    this.caption = caption;
    this.parseMode = parseMode;
    this.disableNotification = disableNotification;
    this.replyToMessageId = replyToMessageId;
    this.replyMarkup = replyMarkup;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public SendAnimationReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public IFile getAnimation() {
    return this.animation;
  }

  public SendAnimationReq setAnimation(IFile v) {
    this.animation = v;
    return this;
  }

  public Optional<Integer> getDuration() {
    return Optional.ofNullable(duration);
  }

  public SendAnimationReq setDuration(Integer v) {
    this.duration = v;
    return this;
  }

  public Optional<Integer> getWidth() {
    return Optional.ofNullable(width);
  }

  public SendAnimationReq setWidth(Integer v) {
    this.width = v;
    return this;
  }

  public Optional<Integer> getHeight() {
    return Optional.ofNullable(height);
  }

  public SendAnimationReq setHeight(Integer v) {
    this.height = v;
    return this;
  }

  public Optional<IFile> getThumb() {
    return Optional.ofNullable(thumb);
  }

  public SendAnimationReq setThumb(IFile v) {
    this.thumb = v;
    return this;
  }

  public Optional<String> getCaption() {
    return Optional.ofNullable(caption);
  }

  public SendAnimationReq setCaption(String v) {
    this.caption = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public SendAnimationReq setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public Optional<Boolean> getDisableNotification() {
    return Optional.ofNullable(disableNotification);
  }

  public SendAnimationReq setDisableNotification(Boolean v) {
    this.disableNotification = v;
    return this;
  }

  public Optional<Integer> getReplyToMessageId() {
    return Optional.ofNullable(replyToMessageId);
  }

  public SendAnimationReq setReplyToMessageId(Integer v) {
    this.replyToMessageId = v;
    return this;
  }

  public Optional<KeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public SendAnimationReq setReplyMarkup(KeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SendAnimationReq)) return false;

    SendAnimationReq that = (SendAnimationReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.animation.equals(that.animation)
        && this.duration.equals(that.duration)
        && this.width.equals(that.width)
        && this.height.equals(that.height)
        && this.thumb.equals(that.thumb)
        && this.caption.equals(that.caption)
        && this.parseMode.equals(that.parseMode)
        && this.disableNotification.equals(that.disableNotification)
        && this.replyToMessageId.equals(that.replyToMessageId)
        && this.replyMarkup.equals(that.replyMarkup);
  }

  @Override
  public String toString() {
    return "SendAnimationReq{"
        + "chatId="
        + this.chatId
        + ','
        + "animation="
        + this.animation
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
