package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetChatMemberRes {

  private ChatMember result;

  private boolean ok;

  private String description;

  public GetChatMemberRes() {}

  public GetChatMemberRes(ChatMember result, boolean ok, String description) {
    this.result = result;
    this.ok = ok;
    this.description = description;
  }

  public Optional<ChatMember> getResult() {
    return Optional.ofNullable(result);
  }

  public GetChatMemberRes setResult(ChatMember v) {
    this.result = v;
    return this;
  }

  public boolean getOk() {
    return this.ok;
  }

  public GetChatMemberRes setOk(boolean v) {
    this.ok = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public GetChatMemberRes setDescription(String v) {
    this.description = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof GetChatMemberRes)) return false;

    GetChatMemberRes that = (GetChatMemberRes) thatObj;

    return this.result.equals(that.result)
        && this.ok == that.ok
        && this.description.equals(that.description);
  }

  @Override
  public String toString() {
    return "GetChatMemberRes{"
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
