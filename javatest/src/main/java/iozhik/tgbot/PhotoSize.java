package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PhotoSize {

  private String fileId;

  private int width;

  private int height;

  private Integer fileSize;

  public PhotoSize() {}

  public PhotoSize(String fileId, int width, int height, Integer fileSize) {
    this.fileId = fileId;
    this.width = width;
    this.height = height;
    this.fileSize = fileSize;
  }

  public String getFileId() {
    return this.fileId;
  }

  public PhotoSize setFileId(String v) {
    this.fileId = v;
    return this;
  }

  public int getWidth() {
    return this.width;
  }

  public PhotoSize setWidth(int v) {
    this.width = v;
    return this;
  }

  public int getHeight() {
    return this.height;
  }

  public PhotoSize setHeight(int v) {
    this.height = v;
    return this;
  }

  public Optional<Integer> getFileSize() {
    return Optional.ofNullable(fileSize);
  }

  public PhotoSize setFileSize(Integer v) {
    this.fileSize = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof PhotoSize)) return false;

    PhotoSize that = (PhotoSize) thatObj;

    return this.fileId.equals(that.fileId)
        && this.width == that.width
        && this.height == that.height
        && this.fileSize.equals(that.fileSize);
  }

  @Override
  public String toString() {
    return "PhotoSize{"
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
        + "fileSize="
        + this.fileSize
        + '}';
  }
}
