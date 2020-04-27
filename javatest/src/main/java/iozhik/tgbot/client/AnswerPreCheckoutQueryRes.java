package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AnswerPreCheckoutQueryRes {

  private Update result;

  private boolean ok;

  private String description;

  public AnswerPreCheckoutQueryRes() {}

  public AnswerPreCheckoutQueryRes(Update result, boolean ok, String description) {
    this.result = result;
    this.ok = ok;
    this.description = description;
  }

  public Optional<Update> getResult() {
    return Optional.ofNullable(result);
  }

  public AnswerPreCheckoutQueryRes setResult(Update v) {
    this.result = v;
    return this;
  }

  public boolean getOk() {
    return this.ok;
  }

  public AnswerPreCheckoutQueryRes setOk(boolean v) {
    this.ok = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public AnswerPreCheckoutQueryRes setDescription(String v) {
    this.description = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof AnswerPreCheckoutQueryRes)) return false;

    AnswerPreCheckoutQueryRes that = (AnswerPreCheckoutQueryRes) thatObj;

    return this.result.equals(that.result)
        && this.ok == that.ok
        && this.description.equals(that.description);
  }

  @Override
  public String toString() {
    return "AnswerPreCheckoutQueryRes{"
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
