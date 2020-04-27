package iozhik.tgbot;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import iozhik.tgbot.deserializators.PassportElementErrorDeserializer;

import java.util.*;

@JsonDeserialize(using = PassportElementErrorDeserializer.class)
public interface PassportElementError {

  enum PassportElementErrors {
    PASSPORT_ELEMENT_ERROR_FILES,
    PASSPORT_ELEMENT_ERROR_DATA_FIELD,
    PASSPORT_ELEMENT_ERROR_REVERSE_SIDE,
    PASSPORT_ELEMENT_ERROR_SELFIE,
    PASSPORT_ELEMENT_ERROR_FRONT_SIDE,
    PASSPORT_ELEMENT_ERROR_FILE,
    PASSPORT_ELEMENT_ERROR_UNSPECIFIED,
    PASSPORT_ELEMENT_ERROR_TRANSLATION_FILE,
    PASSPORT_ELEMENT_ERROR_TRANSLATION_FILES
  }

  PassportElementErrors what();

  boolean isPassportElementErrorFiles();

  PassportElementErrorFiles asPassportElementErrorFiles();

  boolean isPassportElementErrorDataField();

  PassportElementErrorDataField asPassportElementErrorDataField();

  boolean isPassportElementErrorReverseSide();

  PassportElementErrorReverseSide asPassportElementErrorReverseSide();

  boolean isPassportElementErrorSelfie();

  PassportElementErrorSelfie asPassportElementErrorSelfie();

  boolean isPassportElementErrorFrontSide();

  PassportElementErrorFrontSide asPassportElementErrorFrontSide();

  boolean isPassportElementErrorFile();

  PassportElementErrorFile asPassportElementErrorFile();

  boolean isPassportElementErrorUnspecified();

  PassportElementErrorUnspecified asPassportElementErrorUnspecified();

  boolean isPassportElementErrorTranslationFile();

  PassportElementErrorTranslationFile asPassportElementErrorTranslationFile();

  boolean isPassportElementErrorTranslationFiles();

  PassportElementErrorTranslationFiles asPassportElementErrorTranslationFiles();
}
