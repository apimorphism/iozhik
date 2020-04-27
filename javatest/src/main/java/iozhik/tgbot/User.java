package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User {

  private int id;

  private boolean isBot;

  private String firstName;

  private String lastName;

  private String username;

  private String languageCode;

  public User() {}

  public User(
      int id,
      boolean isBot,
      String firstName,
      String lastName,
      String username,
      String languageCode) {
    this.id = id;
    this.isBot = isBot;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.languageCode = languageCode;
  }

  public int getId() {
    return this.id;
  }

  public User setId(int v) {
    this.id = v;
    return this;
  }

  public boolean getIsBot() {
    return this.isBot;
  }

  public User setIsBot(boolean v) {
    this.isBot = v;
    return this;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public User setFirstName(String v) {
    this.firstName = v;
    return this;
  }

  public Optional<String> getLastName() {
    return Optional.ofNullable(lastName);
  }

  public User setLastName(String v) {
    this.lastName = v;
    return this;
  }

  public Optional<String> getUsername() {
    return Optional.ofNullable(username);
  }

  public User setUsername(String v) {
    this.username = v;
    return this;
  }

  public Optional<String> getLanguageCode() {
    return Optional.ofNullable(languageCode);
  }

  public User setLanguageCode(String v) {
    this.languageCode = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof User)) return false;

    User that = (User) thatObj;

    return this.id == that.id
        && this.isBot == that.isBot
        && this.firstName.equals(that.firstName)
        && this.lastName.equals(that.lastName)
        && this.username.equals(that.username)
        && this.languageCode.equals(that.languageCode);
  }

  @Override
  public String toString() {
    return "User{"
        + "id="
        + this.id
        + ','
        + "isBot="
        + this.isBot
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
        + "username="
        + '\''
        + this.username
        + '\''
        + ','
        + "languageCode="
        + '\''
        + this.languageCode
        + '\''
        + '}';
  }
}
