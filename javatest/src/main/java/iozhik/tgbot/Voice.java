package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Voice {

  private String fileId;

  private int duration;

  private String mimeType;

  private Integer fileSize;

  public Voice() {}

  public Voice(String fileId, int duration, String mimeType, Integer fileSize) {
    this.fileId = fileId;
    this.duration = duration;
    this.mimeType = mimeType;
    this.fileSize = fileSize;
  }

  public String getFileId() {
    return this.fileId;
  }

  public Voice setFileId(String v) {
    this.fileId = v;
    return this;
  }

  public int getDuration() {
    return this.duration;
  }

  public Voice setDuration(int v) {
    this.duration = v;
    return this;
  }

  public Optional<String> getMimeType() {
    return Optional.ofNullable(mimeType);
  }

  public Voice setMimeType(String v) {
    this.mimeType = v;
    return this;
  }

  public Optional<Integer> getFileSize() {
    return Optional.ofNullable(fileSize);
  }

  public Voice setFileSize(Integer v) {
    this.fileSize = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Voice)) return false;

    Voice that = (Voice) thatObj;

    return this.fileId.equals(that.fileId)
        && this.duration == that.duration
        && this.mimeType.equals(that.mimeType)
        && this.fileSize.equals(that.fileSize);
  }

  @Override
  public String toString() {
    return "Voice{"
        + "fileId="
        + '\''
        + this.fileId
        + '\''
        + ','
        + "duration="
        + this.duration
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
