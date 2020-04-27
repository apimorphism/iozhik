package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InputMediaAudio implements InputMedia {

  private String media;

  private IFile thumb;

  private String caption;

  private String parseMode;

  private Integer duration;

  private String performer;

  private String title;

  public InputMedias what() {
    return InputMedias.INPUT_MEDIA_AUDIO;
  }

  public String getDiscriminator() {
    return "audio";
  }

  public InputMediaAudio() {}

  public InputMediaAudio(
      String media,
      IFile thumb,
      String caption,
      String parseMode,
      Integer duration,
      String performer,
      String title) {
    this.media = media;
    this.thumb = thumb;
    this.caption = caption;
    this.parseMode = parseMode;
    this.duration = duration;
    this.performer = performer;
    this.title = title;
  }

  public String getMedia() {
    return this.media;
  }

  public InputMediaAudio setMedia(String v) {
    this.media = v;
    return this;
  }

  public Optional<IFile> getThumb() {
    return Optional.ofNullable(thumb);
  }

  public InputMediaAudio setThumb(IFile v) {
    this.thumb = v;
    return this;
  }

  public Optional<String> getCaption() {
    return Optional.ofNullable(caption);
  }

  public InputMediaAudio setCaption(String v) {
    this.caption = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public InputMediaAudio setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public Optional<Integer> getDuration() {
    return Optional.ofNullable(duration);
  }

  public InputMediaAudio setDuration(Integer v) {
    this.duration = v;
    return this;
  }

  public Optional<String> getPerformer() {
    return Optional.ofNullable(performer);
  }

  public InputMediaAudio setPerformer(String v) {
    this.performer = v;
    return this;
  }

  public Optional<String> getTitle() {
    return Optional.ofNullable(title);
  }

  public InputMediaAudio setTitle(String v) {
    this.title = v;
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
    return false;
  }

  public InputMediaDocument asInputMediaDocument() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInputMediaAudio() {
    return true;
  }

  public InputMediaAudio asInputMediaAudio() {
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof InputMediaAudio)) return false;

    InputMediaAudio that = (InputMediaAudio) thatObj;

    return this.media.equals(that.media)
        && this.thumb.equals(that.thumb)
        && this.caption.equals(that.caption)
        && this.parseMode.equals(that.parseMode)
        && this.duration.equals(that.duration)
        && this.performer.equals(that.performer)
        && this.title.equals(that.title);
  }

  @Override
  public String toString() {
    return "InputMediaAudio{"
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
        + '}';
  }
}
