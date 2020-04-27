package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KeyboardButton {

  private String text;

  private Boolean requestContact;

  private Boolean requestLocation;

  public KeyboardButton() {}

  public KeyboardButton(String text, Boolean requestContact, Boolean requestLocation) {
    this.text = text;
    this.requestContact = requestContact;
    this.requestLocation = requestLocation;
  }

  public String getText() {
    return this.text;
  }

  public KeyboardButton setText(String v) {
    this.text = v;
    return this;
  }

  public Optional<Boolean> getRequestContact() {
    return Optional.ofNullable(requestContact);
  }

  public KeyboardButton setRequestContact(Boolean v) {
    this.requestContact = v;
    return this;
  }

  public Optional<Boolean> getRequestLocation() {
    return Optional.ofNullable(requestLocation);
  }

  public KeyboardButton setRequestLocation(Boolean v) {
    this.requestLocation = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof KeyboardButton)) return false;

    KeyboardButton that = (KeyboardButton) thatObj;

    return this.text.equals(that.text)
        && this.requestContact.equals(that.requestContact)
        && this.requestLocation.equals(that.requestLocation);
  }

  @Override
  public String toString() {
    return "KeyboardButton{"
        + "text="
        + '\''
        + this.text
        + '\''
        + ','
        + "requestContact="
        + this.requestContact
        + ','
        + "requestLocation="
        + this.requestLocation
        + '}';
  }
}
