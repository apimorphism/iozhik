package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PassportElementErrorTranslationFiles implements PassportElementError {

  private String type;

  private List<String> fileHashes;

  private String message;

  public PassportElementErrors what() {
    return PassportElementErrors.PASSPORT_ELEMENT_ERROR_TRANSLATION_FILES;
  }

  public String getDiscriminator() {
    return "translation_files";
  }

  public PassportElementErrorTranslationFiles() {}

  public PassportElementErrorTranslationFiles(
      String type, List<String> fileHashes, String message) {
    this.type = type;
    this.fileHashes = fileHashes;
    this.message = message;
  }

  public String getType() {
    return this.type;
  }

  public PassportElementErrorTranslationFiles setType(String v) {
    this.type = v;
    return this;
  }

  public List<String> getFileHashes() {
    return this.fileHashes;
  }

  public PassportElementErrorTranslationFiles setFileHashes(List<String> v) {
    this.fileHashes = v;
    return this;
  }

  public String getMessage() {
    return this.message;
  }

  public PassportElementErrorTranslationFiles setMessage(String v) {
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
    return true;
  }

  public PassportElementErrorTranslationFiles asPassportElementErrorTranslationFiles() {
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof PassportElementErrorTranslationFiles)) return false;

    PassportElementErrorTranslationFiles that = (PassportElementErrorTranslationFiles) thatObj;

    return this.type.equals(that.type)
        && this.fileHashes.equals(that.fileHashes)
        && this.message.equals(that.message);
  }

  @Override
  public String toString() {
    return "PassportElementErrorTranslationFiles{"
        + "type="
        + '\''
        + this.type
        + '\''
        + ','
        + "fileHashes="
        + this.fileHashes
        + ','
        + "message="
        + '\''
        + this.message
        + '\''
        + '}';
  }
}
