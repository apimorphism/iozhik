package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MessageEntity {

  private String type;

  private int offset;

  private int length;

  private String url;

  private User user;

  public MessageEntity() {}

  public MessageEntity(String type, int offset, int length, String url, User user) {
    this.type = type;
    this.offset = offset;
    this.length = length;
    this.url = url;
    this.user = user;
  }

  public String getType() {
    return this.type;
  }

  public MessageEntity setType(String v) {
    this.type = v;
    return this;
  }

  public int getOffset() {
    return this.offset;
  }

  public MessageEntity setOffset(int v) {
    this.offset = v;
    return this;
  }

  public int getLength() {
    return this.length;
  }

  public MessageEntity setLength(int v) {
    this.length = v;
    return this;
  }

  public Optional<String> getUrl() {
    return Optional.ofNullable(url);
  }

  public MessageEntity setUrl(String v) {
    this.url = v;
    return this;
  }

  public Optional<User> getUser() {
    return Optional.ofNullable(user);
  }

  public MessageEntity setUser(User v) {
    this.user = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof MessageEntity)) return false;

    MessageEntity that = (MessageEntity) thatObj;

    return this.type.equals(that.type)
        && this.offset == that.offset
        && this.length == that.length
        && this.url.equals(that.url)
        && this.user.equals(that.user);
  }

  @Override
  public String toString() {
    return "MessageEntity{"
        + "type="
        + '\''
        + this.type
        + '\''
        + ','
        + "offset="
        + this.offset
        + ','
        + "length="
        + this.length
        + ','
        + "url="
        + '\''
        + this.url
        + '\''
        + ','
        + "user="
        + this.user
        + '}';
  }
}
