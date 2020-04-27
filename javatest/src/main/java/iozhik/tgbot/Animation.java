package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Animation {

  private String fileId;

  private int width;

  private int height;

  private int duration;

  private PhotoSize thumb;

  private String fileName;

  private String mimeType;

  private Integer fileSize;

  public Animation() {}

  public Animation(
      String fileId,
      int width,
      int height,
      int duration,
      PhotoSize thumb,
      String fileName,
      String mimeType,
      Integer fileSize) {
    this.fileId = fileId;
    this.width = width;
    this.height = height;
    this.duration = duration;
    this.thumb = thumb;
    this.fileName = fileName;
    this.mimeType = mimeType;
    this.fileSize = fileSize;
  }

  public String getFileId() {
    return this.fileId;
  }

  public Animation setFileId(String v) {
    this.fileId = v;
    return this;
  }

  public int getWidth() {
    return this.width;
  }

  public Animation setWidth(int v) {
    this.width = v;
    return this;
  }

  public int getHeight() {
    return this.height;
  }

  public Animation setHeight(int v) {
    this.height = v;
    return this;
  }

  public int getDuration() {
    return this.duration;
  }

  public Animation setDuration(int v) {
    this.duration = v;
    return this;
  }

  public Optional<PhotoSize> getThumb() {
    return Optional.ofNullable(thumb);
  }

  public Animation setThumb(PhotoSize v) {
    this.thumb = v;
    return this;
  }

  public Optional<String> getFileName() {
    return Optional.ofNullable(fileName);
  }

  public Animation setFileName(String v) {
    this.fileName = v;
    return this;
  }

  public Optional<String> getMimeType() {
    return Optional.ofNullable(mimeType);
  }

  public Animation setMimeType(String v) {
    this.mimeType = v;
    return this;
  }

  public Optional<Integer> getFileSize() {
    return Optional.ofNullable(fileSize);
  }

  public Animation setFileSize(Integer v) {
    this.fileSize = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Animation)) return false;

    Animation that = (Animation) thatObj;

    return this.fileId.equals(that.fileId)
        && this.width == that.width
        && this.height == that.height
        && this.duration == that.duration
        && this.thumb.equals(that.thumb)
        && this.fileName.equals(that.fileName)
        && this.mimeType.equals(that.mimeType)
        && this.fileSize.equals(that.fileSize);
  }

  @Override
  public String toString() {
    return "Animation{"
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
        + "fileName="
        + '\''
        + this.fileName
        + '\''
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
