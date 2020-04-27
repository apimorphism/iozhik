package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PassportElementErrorDataField implements PassportElementError {

  private String type;

  private String fieldName;

  private String dataHash;

  private String message;

  public PassportElementErrors what() {
    return PassportElementErrors.PASSPORT_ELEMENT_ERROR_DATA_FIELD;
  }

  public String getDiscriminator() {
    return "data";
  }

  public PassportElementErrorDataField() {}

  public PassportElementErrorDataField(
      String type, String fieldName, String dataHash, String message) {
    this.type = type;
    this.fieldName = fieldName;
    this.dataHash = dataHash;
    this.message = message;
  }

  public String getType() {
    return this.type;
  }

  public PassportElementErrorDataField setType(String v) {
    this.type = v;
    return this;
  }

  public String getFieldName() {
    return this.fieldName;
  }

  public PassportElementErrorDataField setFieldName(String v) {
    this.fieldName = v;
    return this;
  }

  public String getDataHash() {
    return this.dataHash;
  }

  public PassportElementErrorDataField setDataHash(String v) {
    this.dataHash = v;
    return this;
  }

  public String getMessage() {
    return this.message;
  }

  public PassportElementErrorDataField setMessage(String v) {
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
    return true;
  }

  public PassportElementErrorDataField asPassportElementErrorDataField() {
    return this;
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
    return false;
  }

  public PassportElementErrorUnspecified asPassportElementErrorUnspecified() {
    throw new IllegalStateException("Not a $stName: " + this);
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

    if (!(thatObj instanceof PassportElementErrorDataField)) return false;

    PassportElementErrorDataField that = (PassportElementErrorDataField) thatObj;

    return this.type.equals(that.type)
        && this.fieldName.equals(that.fieldName)
        && this.dataHash.equals(that.dataHash)
        && this.message.equals(that.message);
  }

  @Override
  public String toString() {
    return "PassportElementErrorDataField{"
        + "type="
        + '\''
        + this.type
        + '\''
        + ','
        + "fieldName="
        + '\''
        + this.fieldName
        + '\''
        + ','
        + "dataHash="
        + '\''
        + this.dataHash
        + '\''
        + ','
        + "message="
        + '\''
        + this.message
        + '\''
        + '}';
  }
}
