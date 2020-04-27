package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InputMediaDocument implements InputMedia {

  private String media;

  private IFile thumb;

  private String caption;

  private String parseMode;

  public InputMedias what() {
    return InputMedias.INPUT_MEDIA_DOCUMENT;
  }

  public String getDiscriminator() {
    return "document";
  }

  public InputMediaDocument() {}

  public InputMediaDocument(String media, IFile thumb, String caption, String parseMode) {
    this.media = media;
    this.thumb = thumb;
    this.caption = caption;
    this.parseMode = parseMode;
  }

  public String getMedia() {
    return this.media;
  }

  public InputMediaDocument setMedia(String v) {
    this.media = v;
    return this;
  }

  public Optional<IFile> getThumb() {
    return Optional.ofNullable(thumb);
  }

  public InputMediaDocument setThumb(IFile v) {
    this.thumb = v;
    return this;
  }

  public Optional<String> getCaption() {
    return Optional.ofNullable(caption);
  }

  public InputMediaDocument setCaption(String v) {
    this.caption = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public InputMediaDocument setParseMode(String v) {
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
    return true;
  }

  public InputMediaDocument asInputMediaDocument() {
    return this;
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

    if (!(thatObj instanceof InputMediaDocument)) return false;

    InputMediaDocument that = (InputMediaDocument) thatObj;

    return this.media.equals(that.media)
        && this.thumb.equals(that.thumb)
        && this.caption.equals(that.caption)
        && this.parseMode.equals(that.parseMode);
  }

  @Override
  public String toString() {
    return "InputMediaDocument{"
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
        + '}';
  }
}
