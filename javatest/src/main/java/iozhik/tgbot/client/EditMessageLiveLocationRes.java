package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EditMessageLiveLocationRes {

  private Message result;

  private boolean ok;

  private String description;

  public EditMessageLiveLocationRes() {}

  public EditMessageLiveLocationRes(Message result, boolean ok, String description) {
    this.result = result;
    this.ok = ok;
    this.description = description;
  }

  public Optional<Message> getResult() {
    return Optional.ofNullable(result);
  }

  public EditMessageLiveLocationRes setResult(Message v) {
    this.result = v;
    return this;
  }

  public boolean getOk() {
    return this.ok;
  }

  public EditMessageLiveLocationRes setOk(boolean v) {
    this.ok = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public EditMessageLiveLocationRes setDescription(String v) {
    this.description = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof EditMessageLiveLocationRes)) return false;

    EditMessageLiveLocationRes that = (EditMessageLiveLocationRes) thatObj;

    return this.result.equals(that.result)
        && this.ok == that.ok
        && this.description.equals(that.description);
  }

  @Override
  public String toString() {
    return "EditMessageLiveLocationRes{"
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
