package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InputLocationMessageContent implements InputMessageContent {

  private Float latitude;

  private Float longitude;

  private Integer livePeriod;

  public InputMessageContents what() {
    return InputMessageContents.INPUT_LOCATION_MESSAGE_CONTENT;
  }

  public InputLocationMessageContent() {}

  public InputLocationMessageContent(Float latitude, Float longitude, Integer livePeriod) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.livePeriod = livePeriod;
  }

  public Float getLatitude() {
    return this.latitude;
  }

  public InputLocationMessageContent setLatitude(Float v) {
    this.latitude = v;
    return this;
  }

  public Float getLongitude() {
    return this.longitude;
  }

  public InputLocationMessageContent setLongitude(Float v) {
    this.longitude = v;
    return this;
  }

  public Optional<Integer> getLivePeriod() {
    return Optional.ofNullable(livePeriod);
  }

  public InputLocationMessageContent setLivePeriod(Integer v) {
    this.livePeriod = v;
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
    return true;
  }

  public InputLocationMessageContent asInputLocationMessageContent() {
    return this;
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

    if (!(thatObj instanceof InputLocationMessageContent)) return false;

    InputLocationMessageContent that = (InputLocationMessageContent) thatObj;

    return this.latitude.equals(that.latitude)
        && this.longitude.equals(that.longitude)
        && this.livePeriod.equals(that.livePeriod);
  }

  @Override
  public String toString() {
    return "InputLocationMessageContent{"
        + "latitude="
        + this.latitude
        + ','
        + "longitude="
        + this.longitude
        + ','
        + "livePeriod="
        + this.livePeriod
        + '}';
  }
}
