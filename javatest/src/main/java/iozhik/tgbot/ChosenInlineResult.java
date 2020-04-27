package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ChosenInlineResult {

  private String resultId;

  private User from;

  private Location location;

  private String inlineMessageId;

  private String query;

  public ChosenInlineResult() {}

  public ChosenInlineResult(
      String resultId, User from, Location location, String inlineMessageId, String query) {
    this.resultId = resultId;
    this.from = from;
    this.location = location;
    this.inlineMessageId = inlineMessageId;
    this.query = query;
  }

  public String getResultId() {
    return this.resultId;
  }

  public ChosenInlineResult setResultId(String v) {
    this.resultId = v;
    return this;
  }

  public User getFrom() {
    return this.from;
  }

  public ChosenInlineResult setFrom(User v) {
    this.from = v;
    return this;
  }

  public Optional<Location> getLocation() {
    return Optional.ofNullable(location);
  }

  public ChosenInlineResult setLocation(Location v) {
    this.location = v;
    return this;
  }

  public Optional<String> getInlineMessageId() {
    return Optional.ofNullable(inlineMessageId);
  }

  public ChosenInlineResult setInlineMessageId(String v) {
    this.inlineMessageId = v;
    return this;
  }

  public String getQuery() {
    return this.query;
  }

  public ChosenInlineResult setQuery(String v) {
    this.query = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof ChosenInlineResult)) return false;

    ChosenInlineResult that = (ChosenInlineResult) thatObj;

    return this.resultId.equals(that.resultId)
        && this.from.equals(that.from)
        && this.location.equals(that.location)
        && this.inlineMessageId.equals(that.inlineMessageId)
        && this.query.equals(that.query);
  }

  @Override
  public String toString() {
    return "ChosenInlineResult{"
        + "resultId="
        + '\''
        + this.resultId
        + '\''
        + ','
        + "from="
        + this.from
        + ','
        + "location="
        + this.location
        + ','
        + "inlineMessageId="
        + '\''
        + this.inlineMessageId
        + '\''
        + ','
        + "query="
        + '\''
        + this.query
        + '\''
        + '}';
  }
}
