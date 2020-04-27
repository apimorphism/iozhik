package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Contact {

  private String phoneNumber;

  private String firstName;

  private String lastName;

  private Integer userId;

  private String vcard;

  public Contact() {}

  public Contact(
      String phoneNumber, String firstName, String lastName, Integer userId, String vcard) {
    this.phoneNumber = phoneNumber;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userId = userId;
    this.vcard = vcard;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public Contact setPhoneNumber(String v) {
    this.phoneNumber = v;
    return this;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public Contact setFirstName(String v) {
    this.firstName = v;
    return this;
  }

  public Optional<String> getLastName() {
    return Optional.ofNullable(lastName);
  }

  public Contact setLastName(String v) {
    this.lastName = v;
    return this;
  }

  public Optional<Integer> getUserId() {
    return Optional.ofNullable(userId);
  }

  public Contact setUserId(Integer v) {
    this.userId = v;
    return this;
  }

  public Optional<String> getVcard() {
    return Optional.ofNullable(vcard);
  }

  public Contact setVcard(String v) {
    this.vcard = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Contact)) return false;

    Contact that = (Contact) thatObj;

    return this.phoneNumber.equals(that.phoneNumber)
        && this.firstName.equals(that.firstName)
        && this.lastName.equals(that.lastName)
        && this.userId.equals(that.userId)
        && this.vcard.equals(that.vcard);
  }

  @Override
  public String toString() {
    return "Contact{"
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
        + "userId="
        + this.userId
        + ','
        + "vcard="
        + '\''
        + this.vcard
        + '\''
        + '}';
  }
}
