package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InputContactMessageContent implements InputMessageContent {

  private String phoneNumber;

  private String firstName;

  private String lastName;

  private String vcard;

  public InputMessageContents what() {
    return InputMessageContents.INPUT_CONTACT_MESSAGE_CONTENT;
  }

  public InputContactMessageContent() {}

  public InputContactMessageContent(
      String phoneNumber, String firstName, String lastName, String vcard) {
    this.phoneNumber = phoneNumber;
    this.firstName = firstName;
    this.lastName = lastName;
    this.vcard = vcard;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public InputContactMessageContent setPhoneNumber(String v) {
    this.phoneNumber = v;
    return this;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public InputContactMessageContent setFirstName(String v) {
    this.firstName = v;
    return this;
  }

  public Optional<String> getLastName() {
    return Optional.ofNullable(lastName);
  }

  public InputContactMessageContent setLastName(String v) {
    this.lastName = v;
    return this;
  }

  public Optional<String> getVcard() {
    return Optional.ofNullable(vcard);
  }

  public InputContactMessageContent setVcard(String v) {
    this.vcard = v;
    return this;
  }

  public boolean isInputVenueMessageContent() {
    return false;
  }

  public InputVenueMessageContent asInputVenueMessageContent() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInputContactMessageContent() {
    return true;
  }

  public InputContactMessageContent asInputContactMessageContent() {
    return this;
  }

  public boolean isInputLocationMessageContent() {
    return false;
  }

  public InputLocationMessageContent asInputLocationMessageContent() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInputTextMessageContent() {
    return false;
  }

  public InputTextMessageContent asInputTextMessageContent() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof InputContactMessageContent)) return false;

    InputContactMessageContent that = (InputContactMessageContent) thatObj;

    return this.phoneNumber.equals(that.phoneNumber)
        && this.firstName.equals(that.firstName)
        && this.lastName.equals(that.lastName)
        && this.vcard.equals(that.vcard);
  }

  @Override
  public String toString() {
    return "InputContactMessageContent{"
        + "phoneNumber="
        + '\''
        + this.phoneNumber
        + '\''
        + ','
        + "firstName="
        + '\''
        + this.firstName
        + '\''
        + ','
        + "lastName="
        + '\''
        + this.lastName
        + '\''
        + ','
        + "vcard="
        + '\''
        + this.vcard
        + '\''
        + '}';
  }
}
