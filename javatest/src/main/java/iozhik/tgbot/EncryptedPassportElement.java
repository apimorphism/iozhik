package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EncryptedPassportElement {

  private String type;

  private String data;

  private String phoneNumber;

  private String email;

  private List<PassportFile> files;

  private PassportFile frontSide;

  private PassportFile reverseSide;

  private PassportFile selfie;

  private List<PassportFile> translation;

  private String hash;

  public EncryptedPassportElement() {}

  public EncryptedPassportElement(
      String type,
      String data,
      String phoneNumber,
      String email,
      List<PassportFile> files,
      PassportFile frontSide,
      PassportFile reverseSide,
      PassportFile selfie,
      List<PassportFile> translation,
      String hash) {
    this.type = type;
    this.data = data;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.files = files;
    this.frontSide = frontSide;
    this.reverseSide = reverseSide;
    this.selfie = selfie;
    this.translation = translation;
    this.hash = hash;
  }

  public String getType() {
    return this.type;
  }

  public EncryptedPassportElement setType(String v) {
    this.type = v;
    return this;
  }

  public Optional<String> getData() {
    return Optional.ofNullable(data);
  }

  public EncryptedPassportElement setData(String v) {
    this.data = v;
    return this;
  }

  public Optional<String> getPhoneNumber() {
    return Optional.ofNullable(phoneNumber);
  }

  public EncryptedPassportElement setPhoneNumber(String v) {
    this.phoneNumber = v;
    return this;
  }

  public Optional<String> getEmail() {
    return Optional.ofNullable(email);
  }

  public EncryptedPassportElement setEmail(String v) {
    this.email = v;
    return this;
  }

  public List<PassportFile> getFiles() {
    return this.files;
  }

  public EncryptedPassportElement setFiles(List<PassportFile> v) {
    this.files = v;
    return this;
  }

  public Optional<PassportFile> getFrontSide() {
    return Optional.ofNullable(frontSide);
  }

  public EncryptedPassportElement setFrontSide(PassportFile v) {
    this.frontSide = v;
    return this;
  }

  public Optional<PassportFile> getReverseSide() {
    return Optional.ofNullable(reverseSide);
  }

  public EncryptedPassportElement setReverseSide(PassportFile v) {
    this.reverseSide = v;
    return this;
  }

  public Optional<PassportFile> getSelfie() {
    return Optional.ofNullable(selfie);
  }

  public EncryptedPassportElement setSelfie(PassportFile v) {
    this.selfie = v;
    return this;
  }

  public List<PassportFile> getTranslation() {
    return this.translation;
  }

  public EncryptedPassportElement setTranslation(List<PassportFile> v) {
    this.translation = v;
    return this;
  }

  public String getHash() {
    return this.hash;
  }

  public EncryptedPassportElement setHash(String v) {
    this.hash = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof EncryptedPassportElement)) return false;

    EncryptedPassportElement that = (EncryptedPassportElement) thatObj;

    return this.type.equals(that.type)
        && this.data.equals(that.data)
        && this.phoneNumber.equals(that.phoneNumber)
        && this.email.equals(that.email)
        && this.files.equals(that.files)
        && this.frontSide.equals(that.frontSide)
        && this.reverseSide.equals(that.reverseSide)
        && this.selfie.equals(that.selfie)
        && this.translation.equals(that.translation)
        && this.hash.equals(that.hash);
  }

  @Override
  public String toString() {
    return "EncryptedPassportElement{"
        + "type="
        + '\''
        + this.type
        + '\''
        + ','
        + "data="
        + '\''
        + this.data
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
        + "files="
        + this.files
        + ','
        + "frontSide="
        + this.frontSide
        + ','
        + "reverseSide="
        + this.reverseSide
        + ','
        + "selfie="
        + this.selfie
        + ','
        + "translation="
        + this.translation
        + ','
        + "hash="
        + '\''
        + this.hash
        + '\''
        + '}';
  }
}
