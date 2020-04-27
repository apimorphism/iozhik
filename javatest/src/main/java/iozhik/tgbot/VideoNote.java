package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class VideoNote {

  private String fileId;

  private int length;

  private int duration;

  private PhotoSize thumb;

  private Integer fileSize;

  public VideoNote() {}

  public VideoNote(String fileId, int length, int duration, PhotoSize thumb, Integer fileSize) {
    this.fileId = fileId;
    this.length = length;
    this.duration = duration;
    this.thumb = thumb;
    this.fileSize = fileSize;
  }

  public String getFileId() {
    return this.fileId;
  }

  public VideoNote setFileId(String v) {
    this.fileId = v;
    return this;
  }

  public int getLength() {
    return this.length;
  }

  public VideoNote setLength(int v) {
    this.length = v;
    return this;
  }

  public int getDuration() {
    return this.duration;
  }

  public VideoNote setDuration(int v) {
    this.duration = v;
    return this;
  }

  public Optional<PhotoSize> getThumb() {
    return Optional.ofNullable(thumb);
  }

  public VideoNote setThumb(PhotoSize v) {
    this.thumb = v;
    return this;
  }

  public Optional<Integer> getFileSize() {
    return Optional.ofNullable(fileSize);
  }

  public VideoNote setFileSize(Integer v) {
    this.fileSize = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof VideoNote)) return false;

    VideoNote that = (VideoNote) thatObj;

    return this.fileId.equals(that.fileId)
        && this.length == that.length
        && this.duration == that.duration
        && this.thumb.equals(that.thumb)
        && this.fileSize.equals(that.fileSize);
  }

  @Override
  public String toString() {
    return "VideoNote{"
        + "fileId="
        + '\''
        + this.fileId
        + '\''
        + ','
        + "length="
        + this.length
        + ','
        + "duration="
        + this.duration
        + ','
        + "thumb="
        + this.thumb
        + ','
        + "fileSize="
        + this.fileSize
        + '}';
  }
}
