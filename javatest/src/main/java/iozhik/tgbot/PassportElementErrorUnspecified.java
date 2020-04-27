package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PassportElementErrorUnspecified implements PassportElementError {

  private String type;

  private String elementHash;

  private String message;

  public PassportElementErrors what() {
    return PassportElementErrors.PASSPORT_ELEMENT_ERROR_UNSPECIFIED;
  }

  public String getDiscriminator() {
    return "unspecified";
  }

  public PassportElementErrorUnspecified() {}

  public PassportElementErrorUnspecified(String type, String elementHash, String message) {
    this.type = type;
    this.elementHash = elementHash;
    this.message = message;
  }

  public String getType() {
    return this.type;
  }

  public PassportElementErrorUnspecified setType(String v) {
    this.type = v;
    return this;
  }

  public String getElementHash() {
    return this.elementHash;
  }

  public PassportElementErrorUnspecified setElementHash(String v) {
    this.elementHash = v;
    return this;
  }

  public String getMessage() {
    return this.message;
  }

  public PassportElementErrorUnspecified setMessage(String v) {
    this.message = v;
    return this;
  }

  public boolean isPassportElementErrorFiles() {
    return false;
  }

  public PassportElementErrorFiles asPassportElementErrorFiles() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isPassportElementErrorDataField() {
    return false;
  }

  public PassportElementErrorDataField asPassportElementErrorDataField() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isPassportElementErrorReverseSide() {
    return false;
  }

  public PassportElementErrorReverseSide asPassportElementErrorReverseSide() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isPassportElementErrorSelfie() {
    return false;
  }

  public PassportElementErrorSelfie asPassportElementErrorSelfie() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isPassportElementErrorFrontSide() {
    return false;
  }

  public PassportElementErrorFrontSide asPassportElementErrorFrontSide() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isPassportElementErrorFile() {
    return false;
  }

  public PassportElementErrorFile asPassportElementErrorFile() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isPassportElementErrorUnspecified() {
    return true;
  }

  public PassportElementErrorUnspecified asPassportElementErrorUnspecified() {
    return this;
  }

  public boolean isPassportElementErrorTranslationFile() {
    return false;
  }

  public PassportElementErrorTranslationFile asPassportElementErrorTranslationFile() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isPassportElementErrorTranslationFiles() {
    return false;
  }

  public PassportElementErrorTranslationFiles asPassportElementErrorTranslationFiles() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof PassportElementErrorUnspecified)) return false;

    PassportElementErrorUnspecified that = (PassportElementErrorUnspecified) thatObj;

    return this.type.equals(that.type)
        && this.elementHash.equals(that.elementHash)
        && this.message.equals(that.message);
  }

  @Override
  public String toString() {
    return "PassportElementErrorUnspecified{"
        + "type="
        + '\''
        + this.type
        + '\''
        + ','
        + "elementHash="
        + '\''
        + this.elementHash
        + '\''
        + ','
        + "message="
        + '\''
        + this.message
        + '\''
        + '}';
  }
}
