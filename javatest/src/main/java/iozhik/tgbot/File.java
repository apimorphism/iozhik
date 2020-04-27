package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class File {

  private String fileId;

  private Integer fileSize;

  private String filePath;

  public File() {}

  public File(String fileId, Integer fileSize, String filePath) {
    this.fileId = fileId;
    this.fileSize = fileSize;
    this.filePath = filePath;
  }

  public String getFileId() {
    return this.fileId;
  }

  public File setFileId(String v) {
    this.fileId = v;
    return this;
  }

  public Optional<Integer> getFileSize() {
    return Optional.ofNullable(fileSize);
  }

  public File setFileSize(Integer v) {
    this.fileSize = v;
    return this;
  }

  public Optional<String> getFilePath() {
    return Optional.ofNullable(filePath);
  }

  public File setFilePath(String v) {
    this.filePath = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof File)) return false;

    File that = (File) thatObj;

    return this.fileId.equals(that.fileId)
        && this.fileSize.equals(that.fileSize)
        && this.filePath.equals(that.filePath);
  }

  @Override
  public String toString() {
    return "File{"
        + "fileId="
        + '\''
        + this.fileId
        + '\''
        + ','
        + "fileSize="
        + this.fileSize
        + ','
        + "filePath="
        + '\''
        + this.filePath
        + '\''
        + '}';
  }
}
