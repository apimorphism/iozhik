package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetFileReq {

  private String fileId;

  public GetFileReq() {}

  public GetFileReq(String fileId) {
    this.fileId = fileId;
  }

  public String getFileId() {
    return this.fileId;
  }

  public GetFileReq setFileId(String v) {
    this.fileId = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof GetFileReq)) return false;

    GetFileReq that = (GetFileReq) thatObj;

    return this.fileId.equals(that.fileId);
  }

  @Override
  public String toString() {
    return "GetFileReq{" + "fileId=" + '\'' + this.fileId + '\'' + '}';
  }
}
