package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PreCheckoutQuery {

  private String id;

  private User from;

  private String currency;

  private int totalAmount;

  private String invoicePayload;

  private String shippingOptionId;

  private OrderInfo orderInfo;

  public PreCheckoutQuery() {}

  public PreCheckoutQuery(
      String id,
      User from,
      String currency,
      int totalAmount,
      String invoicePayload,
      String shippingOptionId,
      OrderInfo orderInfo) {
    this.id = id;
    this.from = from;
    this.currency = currency;
    this.totalAmount = totalAmount;
    this.invoicePayload = invoicePayload;
    this.shippingOptionId = shippingOptionId;
    this.orderInfo = orderInfo;
  }

  public String getId() {
    return this.id;
  }

  public PreCheckoutQuery setId(String v) {
    this.id = v;
    return this;
  }

  public User getFrom() {
    return this.from;
  }

  public PreCheckoutQuery setFrom(User v) {
    this.from = v;
    return this;
  }

  public String getCurrency() {
    return this.currency;
  }

  public PreCheckoutQuery setCurrency(String v) {
    this.currency = v;
    return this;
  }

  public int getTotalAmount() {
    return this.totalAmount;
  }

  public PreCheckoutQuery setTotalAmount(int v) {
    this.totalAmount = v;
    return this;
  }

  public String getInvoicePayload() {
    return this.invoicePayload;
  }

  public PreCheckoutQuery setInvoicePayload(String v) {
    this.invoicePayload = v;
    return this;
  }

  public Optional<String> getShippingOptionId() {
    return Optional.ofNullable(shippingOptionId);
  }

  public PreCheckoutQuery setShippingOptionId(String v) {
    this.shippingOptionId = v;
    return this;
  }

  public Optional<OrderInfo> getOrderInfo() {
    return Optional.ofNullable(orderInfo);
  }

  public PreCheckoutQuery setOrderInfo(OrderInfo v) {
    this.orderInfo = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof PreCheckoutQuery)) return false;

    PreCheckoutQuery that = (PreCheckoutQuery) thatObj;

    return this.id.equals(that.id)
        && this.from.equals(that.from)
        && this.currency.equals(that.currency)
        && this.totalAmount == that.totalAmount
        && this.invoicePayload.equals(that.invoicePayload)
        && this.shippingOptionId.equals(that.shippingOptionId)
        && this.orderInfo.equals(that.orderInfo);
  }

  @Override
  public String toString() {
    return "PreCheckoutQuery{"
        + "id="
        + '\''
        + this.id
        + '\''
        + ','
        + "from="
        + this.from
        + ','
        + "currency="
        + '\''
        + this.currency
        + '\''
        + ','
        + "totalAmount="
        + this.totalAmount
        + ','
        + "invoicePayload="
        + '\''
        + this.invoicePayload
        + '\''
        + ','
        + "shippingOptionId="
        + '\''
        + this.shippingOptionId
        + '\''
        + ','
        + "orderInfo="
        + this.orderInfo
        + '}';
  }
}
