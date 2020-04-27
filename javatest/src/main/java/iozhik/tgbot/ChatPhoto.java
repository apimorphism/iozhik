package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ChatPhoto {

  private String smallFileId;

  private String bigFileId;

  public ChatPhoto() {}

  public ChatPhoto(String smallFileId, String bigFileId) {
    this.smallFileId = smallFileId;
    this.bigFileId = bigFileId;
  }

  public String getSmallFileId() {
    return this.smallFileId;
  }

  public ChatPhoto setSmallFileId(String v) {
    this.smallFileId = v;
    return this;
  }

  public String getBigFileId() {
    return this.bigFileId;
  }

  public ChatPhoto setBigFileId(String v) {
    this.bigFileId = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof ChatPhoto)) return false;

    ChatPhoto that = (ChatPhoto) thatObj;

    return this.smallFileId.equals(that.smallFileId) && this.bigFileId.equals(that.bigFileId);
  }

  @Override
  public String toString() {
    return "ChatPhoto{"
        + "smallFileId="
        + '\''
        + this.smallFileId
        + '\''
        + ','
        + "bigFileId="
        + '\''
        + this.bigFileId
        + '\''
        + '}';
  }
}
