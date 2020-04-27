package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ResponseParameters {

  private Integer migrateToChatId;

  private Integer retryAfter;

  public ResponseParameters() {}

  public ResponseParameters(Integer migrateToChatId, Integer retryAfter) {
    this.migrateToChatId = migrateToChatId;
    this.retryAfter = retryAfter;
  }

  public Optional<Integer> getMigrateToChatId() {
    return Optional.ofNullable(migrateToChatId);
  }

  public ResponseParameters setMigrateToChatId(Integer v) {
    this.migrateToChatId = v;
    return this;
  }

  public Optional<Integer> getRetryAfter() {
    return Optional.ofNullable(retryAfter);
  }

  public ResponseParameters setRetryAfter(Integer v) {
    this.retryAfter = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof ResponseParameters)) return false;

    ResponseParameters that = (ResponseParameters) thatObj;

    return this.migrateToChatId.equals(that.migrateToChatId)
        && this.retryAfter.equals(that.retryAfter);
  }

  @Override
  public String toString() {
    return "ResponseParameters{"
        + "migrateToChatId="
        + this.migrateToChatId
        + ','
        + "retryAfter="
        + this.retryAfter
        + '}';
  }
}
