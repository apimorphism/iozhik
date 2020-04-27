package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SuccessfulPayment {

  private String currency;

  private int totalAmount;

  private String invoicePayload;

  private String shippingOptionId;

  private OrderInfo orderInfo;

  private String telegramPaymentChargeId;

  private String providerPaymentChargeId;

  public SuccessfulPayment() {}

  public SuccessfulPayment(
      String currency,
      int totalAmount,
      String invoicePayload,
      String shippingOptionId,
      OrderInfo orderInfo,
      String telegramPaymentChargeId,
      String providerPaymentChargeId) {
    this.currency = currency;
    this.totalAmount = totalAmount;
    this.invoicePayload = invoicePayload;
    this.shippingOptionId = shippingOptionId;
    this.orderInfo = orderInfo;
    this.telegramPaymentChargeId = telegramPaymentChargeId;
    this.providerPaymentChargeId = providerPaymentChargeId;
  }

  public String getCurrency() {
    return this.currency;
  }

  public SuccessfulPayment setCurrency(String v) {
    this.currency = v;
    return this;
  }

  public int getTotalAmount() {
    return this.totalAmount;
  }

  public SuccessfulPayment setTotalAmount(int v) {
    this.totalAmount = v;
    return this;
  }

  public String getInvoicePayload() {
    return this.invoicePayload;
  }

  public SuccessfulPayment setInvoicePayload(String v) {
    this.invoicePayload = v;
    return this;
  }

  public Optional<String> getShippingOptionId() {
    return Optional.ofNullable(shippingOptionId);
  }

  public SuccessfulPayment setShippingOptionId(String v) {
    this.shippingOptionId = v;
    return this;
  }

  public Optional<OrderInfo> getOrderInfo() {
    return Optional.ofNullable(orderInfo);
  }

  public SuccessfulPayment setOrderInfo(OrderInfo v) {
    this.orderInfo = v;
    return this;
  }

  public String getTelegramPaymentChargeId() {
    return this.telegramPaymentChargeId;
  }

  public SuccessfulPayment setTelegramPaymentChargeId(String v) {
    this.telegramPaymentChargeId = v;
    return this;
  }

  public String getProviderPaymentChargeId() {
    return this.providerPaymentChargeId;
  }

  public SuccessfulPayment setProviderPaymentChargeId(String v) {
    this.providerPaymentChargeId = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SuccessfulPayment)) return false;

    SuccessfulPayment that = (SuccessfulPayment) thatObj;

    return this.currency.equals(that.currency)
        && this.totalAmount == that.totalAmount
        && this.invoicePayload.equals(that.invoicePayload)
        && this.shippingOptionId.equals(that.shippingOptionId)
        && this.orderInfo.equals(that.orderInfo)
        && this.telegramPaymentChargeId.equals(that.telegramPaymentChargeId)
        && this.providerPaymentChargeId.equals(that.providerPaymentChargeId);
  }

  @Override
  public String toString() {
    return "SuccessfulPayment{"
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
        + ','
        + "telegramPaymentChargeId="
        + '\''
        + this.telegramPaymentChargeId
        + '\''
        + ','
        + "providerPaymentChargeId="
        + '\''
        + this.providerPaymentChargeId
        + '\''
        + '}';
  }
}
