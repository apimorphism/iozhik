package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Video {

  private String fileId;

  private int width;

  private int height;

  private int duration;

  private PhotoSize thumb;

  private String mimeType;

  private Integer fileSize;

  public Video() {}

  public Video(
      String fileId,
      int width,
      int height,
      int duration,
      PhotoSize thumb,
      String mimeType,
      Integer fileSize) {
    this.fileId = fileId;
    this.width = width;
    this.height = height;
    this.duration = duration;
    this.thumb = thumb;
    this.mimeType = mimeType;
    this.fileSize = fileSize;
  }

  public String getFileId() {
    return this.fileId;
  }

  public Video setFileId(String v) {
    this.fileId = v;
    return this;
  }

  public int getWidth() {
    return this.width;
  }

  public Video setWidth(int v) {
    this.width = v;
    return this;
  }

  public int getHeight() {
    return this.height;
  }

  public Video setHeight(int v) {
    this.height = v;
    return this;
  }

  public int getDuration() {
    return this.duration;
  }

  public Video setDuration(int v) {
    this.duration = v;
    return this;
  }

  public Optional<PhotoSize> getThumb() {
    return Optional.ofNullable(thumb);
  }

  public Video setThumb(PhotoSize v) {
    this.thumb = v;
    return this;
  }

  public Optional<String> getMimeType() {
    return Optional.ofNullable(mimeType);
  }

  public Video setMimeType(String v) {
    this.mimeType = v;
    return this;
  }

  public Optional<Integer> getFileSize() {
    return Optional.ofNullable(fileSize);
  }

  public Video setFileSize(Integer v) {
    this.fileSize = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Video)) return false;

    Video that = (Video) thatObj;

    return this.fileId.equals(that.fileId)
        && this.width == that.width
        && this.height == that.height
        && this.duration == that.duration
        && this.thumb.equals(that.thumb)
        && this.mimeType.equals(that.mimeType)
        && this.fileSize.equals(that.fileSize);
  }

  @Override
  public String toString() {
    return "Video{"
        + "fileId="
        + '\''
        + this.fileId
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
        + "thumb="
        + this.thumb
        + ','
        + "mimeType="
        + '\''
        + this.mimeType
        + '\''
        + ','
        + "fileSize="
        + this.fileSize
        + '}';
  }
}
