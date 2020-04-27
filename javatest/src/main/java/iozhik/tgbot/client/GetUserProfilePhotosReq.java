package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetUserProfilePhotosReq {

  private int userId;

  private Integer offset;

  private Integer limit;

  public GetUserProfilePhotosReq() {}

  public GetUserProfilePhotosReq(int userId, Integer offset, Integer limit) {
    this.userId = userId;
    this.offset = offset;
    this.limit = limit;
  }

  public int getUserId() {
    return this.userId;
  }

  public GetUserProfilePhotosReq setUserId(int v) {
    this.userId = v;
    return this;
  }

  public Optional<Integer> getOffset() {
    return Optional.ofNullable(offset);
  }

  public GetUserProfilePhotosReq setOffset(Integer v) {
    this.offset = v;
    return this;
  }

  public Optional<Integer> getLimit() {
    return Optional.ofNullable(limit);
  }

  public GetUserProfilePhotosReq setLimit(Integer v) {
    this.limit = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof GetUserProfilePhotosReq)) return false;

    GetUserProfilePhotosReq that = (GetUserProfilePhotosReq) thatObj;

    return this.userId == that.userId
        && this.offset.equals(that.offset)
        && this.limit.equals(that.limit);
  }

  @Override
  public String toString() {
    return "GetUserProfilePhotosReq{"
        + "userId="
        + this.userId
        + ','
        + "offset="
        + this.offset
        + ','
        + "limit="
        + this.limit
        + '}';
  }
}
