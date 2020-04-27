package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ShippingOption {

  private String id;

  private String title;

  private List<LabeledPrice> prices;

  public ShippingOption() {}

  public ShippingOption(String id, String title, List<LabeledPrice> prices) {
    this.id = id;
    this.title = title;
    this.prices = prices;
  }

  public String getId() {
    return this.id;
  }

  public ShippingOption setId(String v) {
    this.id = v;
    return this;
  }

  public String getTitle() {
    return this.title;
  }

  public ShippingOption setTitle(String v) {
    this.title = v;
    return this;
  }

  public List<LabeledPrice> getPrices() {
    return this.prices;
  }

  public ShippingOption setPrices(List<LabeledPrice> v) {
    this.prices = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof ShippingOption)) return false;

    ShippingOption that = (ShippingOption) thatObj;

    return this.id.equals(that.id)
        && this.title.equals(that.title)
        && this.prices.equals(that.prices);
  }

  @Override
  public String toString() {
    return "ShippingOption{"
        + "id="
        + '\''
        + this.id
        + '\''
        + ','
        + "title="
        + '\''
        + this.title
        + '\''
        + ','
        + "prices="
        + this.prices
        + '}';
  }
}
