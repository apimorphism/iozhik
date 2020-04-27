package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderInfo {

  private String name;

  private String phoneNumber;

  private String email;

  private ShippingAddress shippingAddress;

  public OrderInfo() {}

  public OrderInfo(String name, String phoneNumber, String email, ShippingAddress shippingAddress) {
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.shippingAddress = shippingAddress;
  }

  public Optional<String> getName() {
    return Optional.ofNullable(name);
  }

  public OrderInfo setName(String v) {
    this.name = v;
    return this;
  }

  public Optional<String> getPhoneNumber() {
    return Optional.ofNullable(phoneNumber);
  }

  public OrderInfo setPhoneNumber(String v) {
    this.phoneNumber = v;
    return this;
  }

  public Optional<String> getEmail() {
    return Optional.ofNullable(email);
  }

  public OrderInfo setEmail(String v) {
    this.email = v;
    return this;
  }

  public Optional<ShippingAddress> getShippingAddress() {
    return Optional.ofNullable(shippingAddress);
  }

  public OrderInfo setShippingAddress(ShippingAddress v) {
    this.shippingAddress = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof OrderInfo)) return false;

    OrderInfo that = (OrderInfo) thatObj;

    return this.name.equals(that.name)
        && this.phoneNumber.equals(that.phoneNumber)
        && this.email.equals(that.email)
        && this.shippingAddress.equals(that.shippingAddress);
  }

  @Override
  public String toString() {
    return "OrderInfo{"
        + "name="
        + '\''
        + this.name
        + '\''
        + ','
        + "phoneNumber="
        + '\''
        + this.phoneNumber
        + '\''
        + ','
        + "email="
        + '\''
        + this.email
        + '\''
        + ','
        + "shippingAddress="
        + this.shippingAddress
        + '}';
  }
}
