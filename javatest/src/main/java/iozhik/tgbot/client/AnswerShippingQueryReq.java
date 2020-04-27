package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AnswerShippingQueryReq {

  private String shippingQueryId;

  private boolean ok;

  private List<ShippingOption> shippingOptions;

  private String errorMessage;

  public AnswerShippingQueryReq() {}

  public AnswerShippingQueryReq(
      String shippingQueryId,
      boolean ok,
      List<ShippingOption> shippingOptions,
      String errorMessage) {
    this.shippingQueryId = shippingQueryId;
    this.ok = ok;
    this.shippingOptions = shippingOptions;
    this.errorMessage = errorMessage;
  }

  public String getShippingQueryId() {
    return this.shippingQueryId;
  }

  public AnswerShippingQueryReq setShippingQueryId(String v) {
    this.shippingQueryId = v;
    return this;
  }

  public boolean getOk() {
    return this.ok;
  }

  public AnswerShippingQueryReq setOk(boolean v) {
    this.ok = v;
    return this;
  }

  public List<ShippingOption> getShippingOptions() {
    return this.shippingOptions;
  }

  public AnswerShippingQueryReq setShippingOptions(List<ShippingOption> v) {
    this.shippingOptions = v;
    return this;
  }

  public Optional<String> getErrorMessage() {
    return Optional.ofNullable(errorMessage);
  }

  public AnswerShippingQueryReq setErrorMessage(String v) {
    this.errorMessage = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof AnswerShippingQueryReq)) return false;

    AnswerShippingQueryReq that = (AnswerShippingQueryReq) thatObj;

    return this.shippingQueryId.equals(that.shippingQueryId)
        && this.ok == that.ok
        && this.shippingOptions.equals(that.shippingOptions)
        && this.errorMessage.equals(that.errorMessage);
  }

  @Override
  public String toString() {
    return "AnswerShippingQueryReq{"
        + "shippingQueryId="
        + '\''
        + this.shippingQueryId
        + '\''
        + ','
        + "ok="
        + this.ok
        + ','
        + "shippingOptions="
        + this.shippingOptions
        + ','
        + "errorMessage="
        + '\''
        + this.errorMessage
        + '\''
        + '}';
  }
}
