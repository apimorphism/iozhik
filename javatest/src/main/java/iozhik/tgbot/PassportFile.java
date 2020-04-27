package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PassportFile {

  private String fileId;

  private int fileSize;

  private int fileDate;

  public PassportFile() {}

  public PassportFile(String fileId, int fileSize, int fileDate) {
    this.fileId = fileId;
    this.fileSize = fileSize;
    this.fileDate = fileDate;
  }

  public String getFileId() {
    return this.fileId;
  }

  public PassportFile setFileId(String v) {
    this.fileId = v;
    return this;
  }

  public int getFileSize() {
    return this.fileSize;
  }

  public PassportFile setFileSize(int v) {
    this.fileSize = v;
    return this;
  }

  public int getFileDate() {
    return this.fileDate;
  }

  public PassportFile setFileDate(int v) {
    this.fileDate = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof PassportFile)) return false;

    PassportFile that = (PassportFile) thatObj;

    return this.fileId.equals(that.fileId)
        && this.fileSize == that.fileSize
        && this.fileDate == that.fileDate;
  }

  @Override
  public String toString() {
    return "PassportFile{"
        + "fileId="
        + '\''
        + this.fileId
        + '\''
        + ','
        + "fileSize="
        + this.fileSize
        + ','
        + "fileDate="
        + this.fileDate
        + '}';
  }
}
