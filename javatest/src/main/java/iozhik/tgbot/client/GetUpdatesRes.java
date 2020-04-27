package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetUpdatesRes {

  private List<Update> result;

  private boolean ok;

  private String description;

  public GetUpdatesRes() {}

  public GetUpdatesRes(List<Update> result, boolean ok, String description) {
    this.result = result;
    this.ok = ok;
    this.description = description;
  }

  public List<Update> getResult() {
    return this.result;
  }

  public GetUpdatesRes setResult(List<Update> v) {
    this.result = v;
    return this;
  }

  public boolean getOk() {
    return this.ok;
  }

  public GetUpdatesRes setOk(boolean v) {
    this.ok = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public GetUpdatesRes setDescription(String v) {
    this.description = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof GetUpdatesRes)) return false;

    GetUpdatesRes that = (GetUpdatesRes) thatObj;

    return this.result.equals(that.result)
        && this.ok == that.ok
        && this.description.equals(that.description);
  }

  @Override
  public String toString() {
    return "GetUpdatesRes{"
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
