package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReplyKeyboardRemove {

  private boolean removeKeyboard;

  private Boolean selective;

  public ReplyKeyboardRemove() {}

  public ReplyKeyboardRemove(boolean removeKeyboard, Boolean selective) {
    this.removeKeyboard = removeKeyboard;
    this.selective = selective;
  }

  public boolean getRemoveKeyboard() {
    return this.removeKeyboard;
  }

  public ReplyKeyboardRemove setRemoveKeyboard(boolean v) {
    this.removeKeyboard = v;
    return this;
  }

  public Optional<Boolean> getSelective() {
    return Optional.ofNullable(selective);
  }

  public ReplyKeyboardRemove setSelective(Boolean v) {
    this.selective = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof ReplyKeyboardRemove)) return false;

    ReplyKeyboardRemove that = (ReplyKeyboardRemove) thatObj;

    return this.removeKeyboard == that.removeKeyboard && this.selective.equals(that.selective);
  }

  @Override
  public String toString() {
    return "ReplyKeyboardRemove{"
        + "removeKeyboard="
        + this.removeKeyboard
        + ','
        + "selective="
        + this.selective
        + '}';
  }
}
