package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InlineQuery {

  private String id;

  private User from;

  private Location location;

  private String query;

  private String offset;

  public InlineQuery() {}

  public InlineQuery(String id, User from, Location location, String query, String offset) {
    this.id = id;
    this.from = from;
    this.location = location;
    this.query = query;
    this.offset = offset;
  }

  public String getId() {
    return this.id;
  }

  public InlineQuery setId(String v) {
    this.id = v;
    return this;
  }

  public User getFrom() {
    return this.from;
  }

  public InlineQuery setFrom(User v) {
    this.from = v;
    return this;
  }

  public Optional<Location> getLocation() {
    return Optional.ofNullable(location);
  }

  public InlineQuery setLocation(Location v) {
    this.location = v;
    return this;
  }

  public String getQuery() {
    return this.query;
  }

  public InlineQuery setQuery(String v) {
    this.query = v;
    return this;
  }

  public String getOffset() {
    return this.offset;
  }

  public InlineQuery setOffset(String v) {
    this.offset = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof InlineQuery)) return false;

    InlineQuery that = (InlineQuery) thatObj;

    return this.id.equals(that.id)
        && this.from.equals(that.from)
        && this.location.equals(that.location)
        && this.query.equals(that.query)
        && this.offset.equals(that.offset);
  }

  @Override
  public String toString() {
    return "InlineQuery{"
        + "id="
        + '\''
        + this.id
        + '\''
        + ','
        + "from="
        + this.from
        + ','
        + "location="
        + this.location
        + ','
        + "query="
        + '\''
        + this.query
        + '\''
        + ','
        + "offset="
        + '\''
        + this.offset
        + '\''
        + '}';
  }
}
