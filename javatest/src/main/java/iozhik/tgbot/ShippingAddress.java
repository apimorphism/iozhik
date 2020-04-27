package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ShippingAddress {

  private String countryCode;

  private String state;

  private String city;

  private String streetLine1;

  private String streetLine2;

  private String postCode;

  public ShippingAddress() {}

  public ShippingAddress(
      String countryCode,
      String state,
      String city,
      String streetLine1,
      String streetLine2,
      String postCode) {
    this.countryCode = countryCode;
    this.state = state;
    this.city = city;
    this.streetLine1 = streetLine1;
    this.streetLine2 = streetLine2;
    this.postCode = postCode;
  }

  public String getCountryCode() {
    return this.countryCode;
  }

  public ShippingAddress setCountryCode(String v) {
    this.countryCode = v;
    return this;
  }

  public String getState() {
    return this.state;
  }

  public ShippingAddress setState(String v) {
    this.state = v;
    return this;
  }

  public String getCity() {
    return this.city;
  }

  public ShippingAddress setCity(String v) {
    this.city = v;
    return this;
  }

  public String getStreetLine1() {
    return this.streetLine1;
  }

  public ShippingAddress setStreetLine1(String v) {
    this.streetLine1 = v;
    return this;
  }

  public String getStreetLine2() {
    return this.streetLine2;
  }

  public ShippingAddress setStreetLine2(String v) {
    this.streetLine2 = v;
    return this;
  }

  public String getPostCode() {
    return this.postCode;
  }

  public ShippingAddress setPostCode(String v) {
    this.postCode = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof ShippingAddress)) return false;

    ShippingAddress that = (ShippingAddress) thatObj;

    return this.countryCode.equals(that.countryCode)
        && this.state.equals(that.state)
        && this.city.equals(that.city)
        && this.streetLine1.equals(that.streetLine1)
        && this.streetLine2.equals(that.streetLine2)
        && this.postCode.equals(that.postCode);
  }

  @Override
  public String toString() {
    return "ShippingAddress{"
        + "countryCode="
        + '\''
        + this.countryCode
        + '\''
        + ','
        + "state="
        + '\''
        + this.state
        + '\''
        + ','
        + "city="
        + '\''
        + this.city
        + '\''
        + ','
        + "streetLine1="
        + '\''
        + this.streetLine1
        + '\''
        + ','
        + "streetLine2="
        + '\''
        + this.streetLine2
        + '\''
        + ','
        + "postCode="
        + '\''
        + this.postCode
        + '\''
        + '}';
  }
}
