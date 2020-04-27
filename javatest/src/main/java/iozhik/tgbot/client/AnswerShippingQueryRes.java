package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AnswerShippingQueryRes {

  private Update result;

  private boolean ok;

  private String description;

  public AnswerShippingQueryRes() {}

  public AnswerShippingQueryRes(Update result, boolean ok, String description) {
    this.result = result;
    this.ok = ok;
    this.description = description;
  }

  public Optional<Update> getResult() {
    return Optional.ofNullable(result);
  }

  public AnswerShippingQueryRes setResult(Update v) {
    this.result = v;
    return this;
  }

  public boolean getOk() {
    return this.ok;
  }

  public AnswerShippingQueryRes setOk(boolean v) {
    this.ok = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public AnswerShippingQueryRes setDescription(String v) {
    this.description = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof AnswerShippingQueryRes)) return false;

    AnswerShippingQueryRes that = (AnswerShippingQueryRes) thatObj;

    return this.result.equals(that.result)
        && this.ok == that.ok
        && this.description.equals(that.description);
  }

  @Override
  public String toString() {
    return "AnswerShippingQueryRes{"
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
