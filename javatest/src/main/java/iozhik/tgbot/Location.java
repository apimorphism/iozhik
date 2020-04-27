package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Location {

  private Float longitude;

  private Float latitude;

  public Location() {}

  public Location(Float longitude, Float latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public Float getLongitude() {
    return this.longitude;
  }

  public Location setLongitude(Float v) {
    this.longitude = v;
    return this;
  }

  public Float getLatitude() {
    return this.latitude;
  }

  public Location setLatitude(Float v) {
    this.latitude = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Location)) return false;

    Location that = (Location) thatObj;

    return this.longitude.equals(that.longitude) && this.latitude.equals(that.latitude);
  }

  @Override
  public String toString() {
    return "Location{" + "longitude=" + this.longitude + ',' + "latitude=" + this.latitude + '}';
  }
}
