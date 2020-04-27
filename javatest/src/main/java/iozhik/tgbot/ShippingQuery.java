package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ShippingQuery {

  private String id;

  private User from;

  private String invoicePayload;

  private ShippingAddress shippingAddress;

  public ShippingQuery() {}

  public ShippingQuery(
      String id, User from, String invoicePayload, ShippingAddress shippingAddress) {
    this.id = id;
    this.from = from;
    this.invoicePayload = invoicePayload;
    this.shippingAddress = shippingAddress;
  }

  public String getId() {
    return this.id;
  }

  public ShippingQuery setId(String v) {
    this.id = v;
    return this;
  }

  public User getFrom() {
    return this.from;
  }

  public ShippingQuery setFrom(User v) {
    this.from = v;
    return this;
  }

  public String getInvoicePayload() {
    return this.invoicePayload;
  }

  public ShippingQuery setInvoicePayload(String v) {
    this.invoicePayload = v;
    return this;
  }

  public ShippingAddress getShippingAddress() {
    return this.shippingAddress;
  }

  public ShippingQuery setShippingAddress(ShippingAddress v) {
    this.shippingAddress = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof ShippingQuery)) return false;

    ShippingQuery that = (ShippingQuery) thatObj;

    return this.id.equals(that.id)
        && this.from.equals(that.from)
        && this.invoicePayload.equals(that.invoicePayload)
        && this.shippingAddress.equals(that.shippingAddress);
  }

  @Override
  public String toString() {
    return "ShippingQuery{"
        + "id="
        + '\''
        + this.id
        + '\''
        + ','
        + "from="
        + this.from
        + ','
        + "invoicePayload="
        + '\''
        + this.invoicePayload
        + '\''
        + ','
        + "shippingAddress="
        + this.shippingAddress
        + '}';
  }
}
