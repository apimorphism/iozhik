package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Document {

  private String fileId;

  private PhotoSize thumb;

  private String fileName;

  private String mimeType;

  private Integer fileSize;

  public Document() {}

  public Document(
      String fileId, PhotoSize thumb, String fileName, String mimeType, Integer fileSize) {
    this.fileId = fileId;
    this.thumb = thumb;
    this.fileName = fileName;
    this.mimeType = mimeType;
    this.fileSize = fileSize;
  }

  public String getFileId() {
    return this.fileId;
  }

  public Document setFileId(String v) {
    this.fileId = v;
    return this;
  }

  public Optional<PhotoSize> getThumb() {
    return Optional.ofNullable(thumb);
  }

  public Document setThumb(PhotoSize v) {
    this.thumb = v;
    return this;
  }

  public Optional<String> getFileName() {
    return Optional.ofNullable(fileName);
  }

  public Document setFileName(String v) {
    this.fileName = v;
    return this;
  }

  public Optional<String> getMimeType() {
    return Optional.ofNullable(mimeType);
  }

  public Document setMimeType(String v) {
    this.mimeType = v;
    return this;
  }

  public Optional<Integer> getFileSize() {
    return Optional.ofNullable(fileSize);
  }

  public Document setFileSize(Integer v) {
    this.fileSize = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Document)) return false;

    Document that = (Document) thatObj;

    return this.fileId.equals(that.fileId)
        && this.thumb.equals(that.thumb)
        && this.fileName.equals(that.fileName)
        && this.mimeType.equals(that.mimeType)
        && this.fileSize.equals(that.fileSize);
  }

  @Override
  public String toString() {
    return "Document{"
        + "fileId="
        + '\''
        + this.fileId
        + '\''
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
