package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InputTextMessageContent implements InputMessageContent {

  private String messageText;

  private String parseMode;

  private Boolean disableWebPagePreview;

  public InputMessageContents what() {
    return InputMessageContents.INPUT_TEXT_MESSAGE_CONTENT;
  }

  public InputTextMessageContent() {}

  public InputTextMessageContent(
      String messageText, String parseMode, Boolean disableWebPagePreview) {
    this.messageText = messageText;
    this.parseMode = parseMode;
    this.disableWebPagePreview = disableWebPagePreview;
  }

  public String getMessageText() {
    return this.messageText;
  }

  public InputTextMessageContent setMessageText(String v) {
    this.messageText = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public InputTextMessageContent setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public Optional<Boolean> getDisableWebPagePreview() {
    return Optional.ofNullable(disableWebPagePreview);
  }

  public InputTextMessageContent setDisableWebPagePreview(Boolean v) {
    this.disableWebPagePreview = v;
    return this;
  }

  public boolean isInputVenueMessageContent() {
    return false;
  }

  public InputVenueMessageContent asInputVenueMessageContent() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInputContactMessageContent() {
    return false;
  }

  public InputContactMessageContent asInputContactMessageContent() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInputLocationMessageContent() {
    return false;
  }

  public InputLocationMessageContent asInputLocationMessageContent() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInputTextMessageContent() {
    return true;
  }

  public InputTextMessageContent asInputTextMessageContent() {
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof InputTextMessageContent)) return false;

    InputTextMessageContent that = (InputTextMessageContent) thatObj;

    return this.messageText.equals(that.messageText)
        && this.parseMode.equals(that.parseMode)
        && this.disableWebPagePreview.equals(that.disableWebPagePreview);
  }

  @Override
  public String toString() {
    return "InputTextMessageContent{"
        + "messageText="
        + '\''
        + this.messageText
        + '\''
        + ','
        + "parseMode="
        + '\''
        + this.parseMode
        + '\''
        + ','
        + "disableWebPagePreview="
        + this.disableWebPagePreview
        + '}';
  }
}
