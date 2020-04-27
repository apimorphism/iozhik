package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Audio {

  private String fileId;

  private int duration;

  private String performer;

  private String title;

  private String mimeType;

  private Integer fileSize;

  private PhotoSize thumb;

  public Audio() {}

  public Audio(
      String fileId,
      int duration,
      String performer,
      String title,
      String mimeType,
      Integer fileSize,
      PhotoSize thumb) {
    this.fileId = fileId;
    this.duration = duration;
    this.performer = performer;
    this.title = title;
    this.mimeType = mimeType;
    this.fileSize = fileSize;
    this.thumb = thumb;
  }

  public String getFileId() {
    return this.fileId;
  }

  public Audio setFileId(String v) {
    this.fileId = v;
    return this;
  }

  public int getDuration() {
    return this.duration;
  }

  public Audio setDuration(int v) {
    this.duration = v;
    return this;
  }

  public Optional<String> getPerformer() {
    return Optional.ofNullable(performer);
  }

  public Audio setPerformer(String v) {
    this.performer = v;
    return this;
  }

  public Optional<String> getTitle() {
    return Optional.ofNullable(title);
  }

  public Audio setTitle(String v) {
    this.title = v;
    return this;
  }

  public Optional<String> getMimeType() {
    return Optional.ofNullable(mimeType);
  }

  public Audio setMimeType(String v) {
    this.mimeType = v;
    return this;
  }

  public Optional<Integer> getFileSize() {
    return Optional.ofNullable(fileSize);
  }

  public Audio setFileSize(Integer v) {
    this.fileSize = v;
    return this;
  }

  public Optional<PhotoSize> getThumb() {
    return Optional.ofNullable(thumb);
  }

  public Audio setThumb(PhotoSize v) {
    this.thumb = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Audio)) return false;

    Audio that = (Audio) thatObj;

    return this.fileId.equals(that.fileId)
        && this.duration == that.duration
        && this.performer.equals(that.performer)
        && this.title.equals(that.title)
        && this.mimeType.equals(that.mimeType)
        && this.fileSize.equals(that.fileSize)
        && this.thumb.equals(that.thumb);
  }

  @Override
  public String toString() {
    return "Audio{"
        + "fileId="
        + '\''
        + this.fileId
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
        + "mimeType="
        + '\''
        + this.mimeType
        + '\''
        + ','
        + "fileSize="
        + this.fileSize
        + ','
        + "thumb="
        + this.thumb
        + '}';
  }
}
