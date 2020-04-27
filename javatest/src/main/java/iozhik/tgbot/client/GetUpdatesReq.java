package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetUpdatesReq {

  private Integer offset;

  private Integer limit;

  private Integer timeout;

  private List<String> allowedUpdates;

  public GetUpdatesReq() {}

  public GetUpdatesReq(
      Integer offset, Integer limit, Integer timeout, List<String> allowedUpdates) {
    this.offset = offset;
    this.limit = limit;
    this.timeout = timeout;
    this.allowedUpdates = allowedUpdates;
  }

  public Optional<Integer> getOffset() {
    return Optional.ofNullable(offset);
  }

  public GetUpdatesReq setOffset(Integer v) {
    this.offset = v;
    return this;
  }

  public Optional<Integer> getLimit() {
    return Optional.ofNullable(limit);
  }

  public GetUpdatesReq setLimit(Integer v) {
    this.limit = v;
    return this;
  }

  public Optional<Integer> getTimeout() {
    return Optional.ofNullable(timeout);
  }

  public GetUpdatesReq setTimeout(Integer v) {
    this.timeout = v;
    return this;
  }

  public List<String> getAllowedUpdates() {
    return this.allowedUpdates;
  }

  public GetUpdatesReq setAllowedUpdates(List<String> v) {
    this.allowedUpdates = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof GetUpdatesReq)) return false;

    GetUpdatesReq that = (GetUpdatesReq) thatObj;

    return this.offset.equals(that.offset)
        && this.limit.equals(that.limit)
        && this.timeout.equals(that.timeout)
        && this.allowedUpdates.equals(that.allowedUpdates);
  }

  @Override
  public String toString() {
    return "GetUpdatesReq{"
        + "offset="
        + this.offset
        + ','
        + "limit="
        + this.limit
        + ','
        + "timeout="
        + this.timeout
        + ','
        + "allowedUpdates="
        + this.allowedUpdates
        + '}';
  }
}
