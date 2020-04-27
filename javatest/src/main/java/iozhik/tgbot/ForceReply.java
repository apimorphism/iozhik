package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ForceReply {

  private boolean forceReply;

  private Boolean selective;

  public ForceReply() {}

  public ForceReply(boolean forceReply, Boolean selective) {
    this.forceReply = forceReply;
    this.selective = selective;
  }

  public boolean getForceReply() {
    return this.forceReply;
  }

  public ForceReply setForceReply(boolean v) {
    this.forceReply = v;
    return this;
  }

  public Optional<Boolean> getSelective() {
    return Optional.ofNullable(selective);
  }

  public ForceReply setSelective(Boolean v) {
    this.selective = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof ForceReply)) return false;

    ForceReply that = (ForceReply) thatObj;

    return this.forceReply == that.forceReply && this.selective.equals(that.selective);
  }

  @Override
  public String toString() {
    return "ForceReply{"
        + "forceReply="
        + this.forceReply
        + ','
        + "selective="
        + this.selective
        + '}';
  }
}
