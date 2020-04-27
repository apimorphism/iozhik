package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Invoice {

  private String title;

  private String description;

  private String startParameter;

  private String currency;

  private int totalAmount;

  public Invoice() {}

  public Invoice(
      String title, String description, String startParameter, String currency, int totalAmount) {
    this.title = title;
    this.description = description;
    this.startParameter = startParameter;
    this.currency = currency;
    this.totalAmount = totalAmount;
  }

  public String getTitle() {
    return this.title;
  }

  public Invoice setTitle(String v) {
    this.title = v;
    return this;
  }

  public String getDescription() {
    return this.description;
  }

  public Invoice setDescription(String v) {
    this.description = v;
    return this;
  }

  public String getStartParameter() {
    return this.startParameter;
  }

  public Invoice setStartParameter(String v) {
    this.startParameter = v;
    return this;
  }

  public String getCurrency() {
    return this.currency;
  }

  public Invoice setCurrency(String v) {
    this.currency = v;
    return this;
  }

  public int getTotalAmount() {
    return this.totalAmount;
  }

  public Invoice setTotalAmount(int v) {
    this.totalAmount = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Invoice)) return false;

    Invoice that = (Invoice) thatObj;

    return this.title.equals(that.title)
        && this.description.equals(that.description)
        && this.startParameter.equals(that.startParameter)
        && this.currency.equals(that.currency)
        && this.totalAmount == that.totalAmount;
  }

  @Override
  public String toString() {
    return "Invoice{"
        + "title="
        + '\''
        + this.title
        + '\''
        + ','
        + "description="
        + '\''
        + this.description
        + '\''
        + ','
        + "startParameter="
        + '\''
        + this.startParameter
        + '\''
        + ','
        + "currency="
        + '\''
        + this.currency
        + '\''
        + ','
        + "totalAmount="
        + this.totalAmount
        + '}';
  }
}
