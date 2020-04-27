package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InputVenueMessageContent implements InputMessageContent {

  private Float latitude;

  private Float longitude;

  private String title;

  private String address;

  private String foursquareId;

  private String foursquareType;

  public InputMessageContents what() {
    return InputMessageContents.INPUT_VENUE_MESSAGE_CONTENT;
  }

  public InputVenueMessageContent() {}

  public InputVenueMessageContent(
      Float latitude,
      Float longitude,
      String title,
      String address,
      String foursquareId,
      String foursquareType) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.title = title;
    this.address = address;
    this.foursquareId = foursquareId;
    this.foursquareType = foursquareType;
  }

  public Float getLatitude() {
    return this.latitude;
  }

  public InputVenueMessageContent setLatitude(Float v) {
    this.latitude = v;
    return this;
  }

  public Float getLongitude() {
    return this.longitude;
  }

  public InputVenueMessageContent setLongitude(Float v) {
    this.longitude = v;
    return this;
  }

  public String getTitle() {
    return this.title;
  }

  public InputVenueMessageContent setTitle(String v) {
    this.title = v;
    return this;
  }

  public String getAddress() {
    return this.address;
  }

  public InputVenueMessageContent setAddress(String v) {
    this.address = v;
    return this;
  }

  public Optional<String> getFoursquareId() {
    return Optional.ofNullable(foursquareId);
  }

  public InputVenueMessageContent setFoursquareId(String v) {
    this.foursquareId = v;
    return this;
  }

  public Optional<String> getFoursquareType() {
    return Optional.ofNullable(foursquareType);
  }

  public InputVenueMessageContent setFoursquareType(String v) {
    this.foursquareType = v;
    return this;
  }

  public boolean isInputVenueMessageContent() {
    return true;
  }

  public InputVenueMessageContent asInputVenueMessageContent() {
    return this;
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
    return false;
  }

  public InputTextMessageContent asInputTextMessageContent() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof InputVenueMessageContent)) return false;

    InputVenueMessageContent that = (InputVenueMessageContent) thatObj;

    return this.latitude.equals(that.latitude)
        && this.longitude.equals(that.longitude)
        && this.title.equals(that.title)
        && this.address.equals(that.address)
        && this.foursquareId.equals(that.foursquareId)
        && this.foursquareType.equals(that.foursquareType);
  }

  @Override
  public String toString() {
    return "InputVenueMessageContent{"
        + "latitude="
        + this.latitude
        + ','
        + "longitude="
        + this.longitude
        + ','
        + "title="
        + '\''
        + this.title
        + '\''
        + ','
        + "address="
        + '\''
        + this.address
        + '\''
        + ','
        + "foursquareId="
        + '\''
        + this.foursquareId
        + '\''
        + ','
        + "foursquareType="
        + '\''
        + this.foursquareType
        + '\''
        + '}';
  }
}
