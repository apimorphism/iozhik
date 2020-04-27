package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LabeledPrice {

  private String label;

  private int amount;

  public LabeledPrice() {}

  public LabeledPrice(String label, int amount) {
    this.label = label;
    this.amount = amount;
  }

  public String getLabel() {
    return this.label;
  }

  public LabeledPrice setLabel(String v) {
    this.label = v;
    return this;
  }

  public int getAmount() {
    return this.amount;
  }

  public LabeledPrice setAmount(int v) {
    this.amount = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof LabeledPrice)) return false;

    LabeledPrice that = (LabeledPrice) thatObj;

    return this.label.equals(that.label) && this.amount == that.amount;
  }

  @Override
  public String toString() {
    return "LabeledPrice{"
        + "label="
        + '\''
        + this.label
        + '\''
        + ','
        + "amount="
        + this.amount
        + '}';
  }
}
