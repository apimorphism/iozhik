package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendInvoiceRes {

  private Message result;

  private boolean ok;

  private String description;

  public SendInvoiceRes() {}

  public SendInvoiceRes(Message result, boolean ok, String description) {
    this.result = result;
    this.ok = ok;
    this.description = description;
  }

  public Optional<Message> getResult() {
    return Optional.ofNullable(result);
  }

  public SendInvoiceRes setResult(Message v) {
    this.result = v;
    return this;
  }

  public boolean getOk() {
    return this.ok;
  }

  public SendInvoiceRes setOk(boolean v) {
    this.ok = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public SendInvoiceRes setDescription(String v) {
    this.description = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SendInvoiceRes)) return false;

    SendInvoiceRes that = (SendInvoiceRes) thatObj;

    return this.result.equals(that.result)
        && this.ok == that.ok
        && this.description.equals(that.description);
  }

  @Override
  public String toString() {
    return "SendInvoiceRes{"
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
