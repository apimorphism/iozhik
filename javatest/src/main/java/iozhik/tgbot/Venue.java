package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Venue {

  private Location location;

  private String title;

  private String address;

  private String foursquareId;

  private String foursquareType;

  public Venue() {}

  public Venue(
      Location location, String title, String address, String foursquareId, String foursquareType) {
    this.location = location;
    this.title = title;
    this.address = address;
    this.foursquareId = foursquareId;
    this.foursquareType = foursquareType;
  }

  public Location getLocation() {
    return this.location;
  }

  public Venue setLocation(Location v) {
    this.location = v;
    return this;
  }

  public String getTitle() {
    return this.title;
  }

  public Venue setTitle(String v) {
    this.title = v;
    return this;
  }

  public String getAddress() {
    return this.address;
  }

  public Venue setAddress(String v) {
    this.address = v;
    return this;
  }

  public Optional<String> getFoursquareId() {
    return Optional.ofNullable(foursquareId);
  }

  public Venue setFoursquareId(String v) {
    this.foursquareId = v;
    return this;
  }

  public Optional<String> getFoursquareType() {
    return Optional.ofNullable(foursquareType);
  }

  public Venue setFoursquareType(String v) {
    this.foursquareType = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Venue)) return false;

    Venue that = (Venue) thatObj;

    return this.location.equals(that.location)
        && this.title.equals(that.title)
        && this.address.equals(that.address)
        && this.foursquareId.equals(that.foursquareId)
        && this.foursquareType.equals(that.foursquareType);
  }

  @Override
  public String toString() {
    return "Venue{"
        + "location="
        + this.location
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
