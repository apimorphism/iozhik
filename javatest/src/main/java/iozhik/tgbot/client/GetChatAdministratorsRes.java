package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetChatAdministratorsRes {

  private List<ChatMember> result;

  private boolean ok;

  private String description;

  public GetChatAdministratorsRes() {}

  public GetChatAdministratorsRes(List<ChatMember> result, boolean ok, String description) {
    this.result = result;
    this.ok = ok;
    this.description = description;
  }

  public List<ChatMember> getResult() {
    return this.result;
  }

  public GetChatAdministratorsRes setResult(List<ChatMember> v) {
    this.result = v;
    return this;
  }

  public boolean getOk() {
    return this.ok;
  }

  public GetChatAdministratorsRes setOk(boolean v) {
    this.ok = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public GetChatAdministratorsRes setDescription(String v) {
    this.description = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof GetChatAdministratorsRes)) return false;

    GetChatAdministratorsRes that = (GetChatAdministratorsRes) thatObj;

    return this.result.equals(that.result)
        && this.ok == that.ok
        && this.description.equals(that.description);
  }

  @Override
  public String toString() {
    return "GetChatAdministratorsRes{"
        + "result="
        + this.result
        + ','
        + "ok="
        + this.ok
        + ','
        + "description="
        + '\''
        + this.description
        + '\''
        + '}';
  }
}
