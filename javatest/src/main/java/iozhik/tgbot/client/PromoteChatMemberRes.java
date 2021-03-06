package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PromoteChatMemberRes {

  private Boolean result;

  private boolean ok;

  private String description;

  public PromoteChatMemberRes() {}

  public PromoteChatMemberRes(Boolean result, boolean ok, String description) {
    this.result = result;
    this.ok = ok;
    this.description = description;
  }

  public Optional<Boolean> getResult() {
    return Optional.ofNullable(result);
  }

  public PromoteChatMemberRes setResult(Boolean v) {
    this.result = v;
    return this;
  }

  public boolean getOk() {
    return this.ok;
  }

  public PromoteChatMemberRes setOk(boolean v) {
    this.ok = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public PromoteChatMemberRes setDescription(String v) {
    this.description = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof PromoteChatMemberRes)) return false;

    PromoteChatMemberRes that = (PromoteChatMemberRes) thatObj;

    return this.result.equals(that.result)
        && this.ok == that.ok
        && this.description.equals(that.description);
  }

  @Override
  public String toString() {
    return "PromoteChatMemberRes{"
        + "result="
        + this.result
        + ','
        + "ok="
        + this.ok
        + ','
        + "description="
        + '\''
        + this.description
        + '\''
        + '}';
  }
}
