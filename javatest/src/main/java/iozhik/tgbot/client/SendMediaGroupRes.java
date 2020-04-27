package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendMediaGroupRes {

  private List<Message> result;

  private boolean ok;

  private String description;

  public SendMediaGroupRes() {}

  public SendMediaGroupRes(List<Message> result, boolean ok, String description) {
    this.result = result;
    this.ok = ok;
    this.description = description;
  }

  public List<Message> getResult() {
    return this.result;
  }

  public SendMediaGroupRes setResult(List<Message> v) {
    this.result = v;
    return this;
  }

  public boolean getOk() {
    return this.ok;
  }

  public SendMediaGroupRes setOk(boolean v) {
    this.ok = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public SendMediaGroupRes setDescription(String v) {
    this.description = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SendMediaGroupRes)) return false;

    SendMediaGroupRes that = (SendMediaGroupRes) thatObj;

    return this.result.equals(that.result)
        && this.ok == that.ok
        && this.description.equals(that.description);
  }

  @Override
  public String toString() {
    return "SendMediaGroupRes{"
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
