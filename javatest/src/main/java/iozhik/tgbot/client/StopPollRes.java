package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StopPollRes {

  private Poll result;

  private boolean ok;

  private String description;

  public StopPollRes() {}

  public StopPollRes(Poll result, boolean ok, String description) {
    this.result = result;
    this.ok = ok;
    this.description = description;
  }

  public Optional<Poll> getResult() {
    return Optional.ofNullable(result);
  }

  public StopPollRes setResult(Poll v) {
    this.result = v;
    return this;
  }

  public boolean getOk() {
    return this.ok;
  }

  public StopPollRes setOk(boolean v) {
    this.ok = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public StopPollRes setDescription(String v) {
    this.description = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof StopPollRes)) return false;

    StopPollRes that = (StopPollRes) thatObj;

    return this.result.equals(that.result)
        && this.ok == that.ok
        && this.description.equals(that.description);
  }

  @Override
  public String toString() {
    return "StopPollRes{"
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
