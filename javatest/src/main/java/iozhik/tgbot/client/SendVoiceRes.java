package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendVoiceRes {

  private Audio result;

  private boolean ok;

  private String description;

  public SendVoiceRes() {}

  public SendVoiceRes(Audio result, boolean ok, String description) {
    this.result = result;
    this.ok = ok;
    this.description = description;
  }

  public Optional<Audio> getResult() {
    return Optional.ofNullable(result);
  }

  public SendVoiceRes setResult(Audio v) {
    this.result = v;
    return this;
  }

  public boolean getOk() {
    return this.ok;
  }

  public SendVoiceRes setOk(boolean v) {
    this.ok = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public SendVoiceRes setDescription(String v) {
    this.description = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SendVoiceRes)) return false;

    SendVoiceRes that = (SendVoiceRes) thatObj;

    return this.result.equals(that.result)
        && this.ok == that.ok
        && this.description.equals(that.description);
  }

  @Override
  public String toString() {
    return "SendVoiceRes{"
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
