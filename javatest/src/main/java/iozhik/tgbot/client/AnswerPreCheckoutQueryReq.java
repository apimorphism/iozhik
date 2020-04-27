package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AnswerPreCheckoutQueryReq {

  private String preCheckoutQueryId;

  private boolean ok;

  private String errorMessage;

  public AnswerPreCheckoutQueryReq() {}

  public AnswerPreCheckoutQueryReq(String preCheckoutQueryId, boolean ok, String errorMessage) {
    this.preCheckoutQueryId = preCheckoutQueryId;
    this.ok = ok;
    this.errorMessage = errorMessage;
  }

  public String getPreCheckoutQueryId() {
    return this.preCheckoutQueryId;
  }

  public AnswerPreCheckoutQueryReq setPreCheckoutQueryId(String v) {
    this.preCheckoutQueryId = v;
    return this;
  }

  public boolean getOk() {
    return this.ok;
  }

  public AnswerPreCheckoutQueryReq setOk(boolean v) {
    this.ok = v;
    return this;
  }

  public Optional<String> getErrorMessage() {
    return Optional.ofNullable(errorMessage);
  }

  public AnswerPreCheckoutQueryReq setErrorMessage(String v) {
    this.errorMessage = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof AnswerPreCheckoutQueryReq)) return false;

    AnswerPreCheckoutQueryReq that = (AnswerPreCheckoutQueryReq) thatObj;

    return this.preCheckoutQueryId.equals(that.preCheckoutQueryId)
        && this.ok == that.ok
        && this.errorMessage.equals(that.errorMessage);
  }

  @Override
  public String toString() {
    return "AnswerPreCheckoutQueryReq{"
        + "preCheckoutQueryId="
        + '\''
        + this.preCheckoutQueryId
        + '\''
        + ','
        + "ok="
        + this.ok
        + ','
        + "errorMessage="
        + '\''
        + this.errorMessage
        + '\''
        + '}';
  }
}
