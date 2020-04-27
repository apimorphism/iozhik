package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InputMediaVideo implements InputMedia {

  private String media;

  private IFile thumb;

  private String caption;

  private String parseMode;

  private Integer width;

  private Integer height;

  private Integer duration;

  private Boolean supportsStreaming;

  public InputMedias what() {
    return InputMedias.INPUT_MEDIA_VIDEO;
  }

  public String getDiscriminator() {
    return "video";
  }

  public InputMediaVideo() {}

  public InputMediaVideo(
      String media,
      IFile thumb,
      String caption,
      String parseMode,
      Integer width,
      Integer height,
      Integer duration,
      Boolean supportsStreaming) {
    this.media = media;
    this.thumb = thumb;
    this.caption = caption;
    this.parseMode = parseMode;
    this.width = width;
    this.height = height;
    this.duration = duration;
    this.supportsStreaming = supportsStreaming;
  }

  public String getMedia() {
    return this.media;
  }

  public InputMediaVideo setMedia(String v) {
    this.media = v;
    return this;
  }

  public Optional<IFile> getThumb() {
    return Optional.ofNullable(thumb);
  }

  public InputMediaVideo setThumb(IFile v) {
    this.thumb = v;
    return this;
  }

  public Optional<String> getCaption() {
    return Optional.ofNullable(caption);
  }

  public InputMediaVideo setCaption(String v) {
    this.caption = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public InputMediaVideo setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public Optional<Integer> getWidth() {
    return Optional.ofNullable(width);
  }

  public InputMediaVideo setWidth(Integer v) {
    this.width = v;
    return this;
  }

  public Optional<Integer> getHeight() {
    return Optional.ofNullable(height);
  }

  public InputMediaVideo setHeight(Integer v) {
    this.height = v;
    return this;
  }

  public Optional<Integer> getDuration() {
    return Optional.ofNullable(duration);
  }

  public InputMediaVideo setDuration(Integer v) {
    this.duration = v;
    return this;
  }

  public Optional<Boolean> getSupportsStreaming() {
    return Optional.ofNullable(supportsStreaming);
  }

  public InputMediaVideo setSupportsStreaming(Boolean v) {
    this.supportsStreaming = v;
    return this;
  }

  public boolean isInputMediaAnimation() {
    return false;
  }

  public InputMediaAnimation asInputMediaAnimation() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInputMediaPhoto() {
    return false;
  }

  public InputMediaPhoto asInputMediaPhoto() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInputMediaVideo() {
    return true;
  }

  public InputMediaVideo asInputMediaVideo() {
    return this;
  }

  public boolean isInputMediaDocument() {
    return false;
  }

  public InputMediaDocument asInputMediaDocument() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInputMediaAudio() {
    return false;
  }

  public InputMediaAudio asInputMediaAudio() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof InputMediaVideo)) return false;

    InputMediaVideo that = (InputMediaVideo) thatObj;

    return this.media.equals(that.media)
        && this.thumb.equals(that.thumb)
        && this.caption.equals(that.caption)
        && this.parseMode.equals(that.parseMode)
        && this.width.equals(that.width)
        && this.height.equals(that.height)
        && this.duration.equals(that.duration)
        && this.supportsStreaming.equals(that.supportsStreaming);
  }

  @Override
  public String toString() {
    return "InputMediaVideo{"
        + "media="
        + '\''
        + this.media
        + '\''
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
        + "width="
        + this.width
        + ','
        + "height="
        + this.height
        + ','
        + "duration="
        + this.duration
        + ','
        + "supportsStreaming="
        + this.supportsStreaming
        + '}';
  }
}
