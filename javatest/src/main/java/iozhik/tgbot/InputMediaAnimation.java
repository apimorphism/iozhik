package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InputMediaAnimation implements InputMedia {

  private String media;

  private IFile thumb;

  private String caption;

  private String parseMode;

  private Integer width;

  private Integer height;

  private Integer duration;

  public InputMedias what() {
    return InputMedias.INPUT_MEDIA_ANIMATION;
  }

  public String getDiscriminator() {
    return "animation";
  }

  public InputMediaAnimation() {}

  public InputMediaAnimation(
      String media,
      IFile thumb,
      String caption,
      String parseMode,
      Integer width,
      Integer height,
      Integer duration) {
    this.media = media;
    this.thumb = thumb;
    this.caption = caption;
    this.parseMode = parseMode;
    this.width = width;
    this.height = height;
    this.duration = duration;
  }

  public String getMedia() {
    return this.media;
  }

  public InputMediaAnimation setMedia(String v) {
    this.media = v;
    return this;
  }

  public Optional<IFile> getThumb() {
    return Optional.ofNullable(thumb);
  }

  public InputMediaAnimation setThumb(IFile v) {
    this.thumb = v;
    return this;
  }

  public Optional<String> getCaption() {
    return Optional.ofNullable(caption);
  }

  public InputMediaAnimation setCaption(String v) {
    this.caption = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public InputMediaAnimation setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public Optional<Integer> getWidth() {
    return Optional.ofNullable(width);
  }

  public InputMediaAnimation setWidth(Integer v) {
    this.width = v;
    return this;
  }

  public Optional<Integer> getHeight() {
    return Optional.ofNullable(height);
  }

  public InputMediaAnimation setHeight(Integer v) {
    this.height = v;
    return this;
  }

  public Optional<Integer> getDuration() {
    return Optional.ofNullable(duration);
  }

  public InputMediaAnimation setDuration(Integer v) {
    this.duration = v;
    return this;
  }

  public boolean isInputMediaAnimation() {
    return true;
  }

  public InputMediaAnimation asInputMediaAnimation() {
    return this;
  }

  public boolean isInputMediaPhoto() {
    return false;
  }

  public InputMediaPhoto asInputMediaPhoto() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInputMediaVideo() {
    return false;
  }

  public InputMediaVideo asInputMediaVideo() {
    throw new IllegalStateException("Not a $stName: " + this);
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

    if (!(thatObj instanceof InputMediaAnimation)) return false;

    InputMediaAnimation that = (InputMediaAnimation) thatObj;

    return this.media.equals(that.media)
        && this.thumb.equals(that.thumb)
        && this.caption.equals(that.caption)
        && this.parseMode.equals(that.parseMode)
        && this.width.equals(that.width)
        && this.height.equals(that.height)
        && this.duration.equals(that.duration);
  }

  @Override
  public String toString() {
    return "InputMediaAnimation{"
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
        + '}';
  }
}
