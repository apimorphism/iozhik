package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InputMediaPhoto implements InputMedia {

  private String media;

  private String caption;

  private String parseMode;

  public InputMedias what() {
    return InputMedias.INPUT_MEDIA_PHOTO;
  }

  public String getDiscriminator() {
    return "photo";
  }

  public InputMediaPhoto() {}

  public InputMediaPhoto(String media, String caption, String parseMode) {
    this.media = media;
    this.caption = caption;
    this.parseMode = parseMode;
  }

  public String getMedia() {
    return this.media;
  }

  public InputMediaPhoto setMedia(String v) {
    this.media = v;
    return this;
  }

  public Optional<String> getCaption() {
    return Optional.ofNullable(caption);
  }

  public InputMediaPhoto setCaption(String v) {
    this.caption = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public InputMediaPhoto setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public boolean isInputMediaAnimation() {
    return false;
  }

  public InputMediaAnimation asInputMediaAnimation() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInputMediaPhoto() {
    return true;
  }

  public InputMediaPhoto asInputMediaPhoto() {
    return this;
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

    if (!(thatObj instanceof InputMediaPhoto)) return false;

    InputMediaPhoto that = (InputMediaPhoto) thatObj;

    return this.media.equals(that.media)
        && this.caption.equals(that.caption)
        && this.parseMode.equals(that.parseMode);
  }

  @Override
  public String toString() {
    return "InputMediaPhoto{"
        + "media="
        + '\''
        + this.media
        + '\''
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
        + '}';
  }
}
