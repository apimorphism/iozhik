package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SetChatDescriptionRes {

  private Boolean result;

  private boolean ok;

  private String description;

  public SetChatDescriptionRes() {}

  public SetChatDescriptionRes(Boolean result, boolean ok, String description) {
    this.result = result;
    this.ok = ok;
    this.description = description;
  }

  public Optional<Boolean> getResult() {
    return Optional.ofNullable(result);
  }

  public SetChatDescriptionRes setResult(Boolean v) {
    this.result = v;
    return this;
  }

  public boolean getOk() {
    return this.ok;
  }

  public SetChatDescriptionRes setOk(boolean v) {
    this.ok = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public SetChatDescriptionRes setDescription(String v) {
    this.description = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SetChatDescriptionRes)) return false;

    SetChatDescriptionRes that = (SetChatDescriptionRes) thatObj;

    return this.result.equals(that.result)
        && this.ok == that.ok
        && this.description.equals(that.description);
  }

  @Override
  public String toString() {
    return "SetChatDescriptionRes{"
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
