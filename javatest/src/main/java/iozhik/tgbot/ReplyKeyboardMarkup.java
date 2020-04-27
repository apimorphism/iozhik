package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReplyKeyboardMarkup {

  private List<List<KeyboardButton>> keyboard;

  private Boolean resizeKeyboard;

  private Boolean oneTimeKeyboard;

  private Boolean selective;

  public ReplyKeyboardMarkup() {}

  public ReplyKeyboardMarkup(
      List<List<KeyboardButton>> keyboard,
      Boolean resizeKeyboard,
      Boolean oneTimeKeyboard,
      Boolean selective) {
    this.keyboard = keyboard;
    this.resizeKeyboard = resizeKeyboard;
    this.oneTimeKeyboard = oneTimeKeyboard;
    this.selective = selective;
  }

  public List<List<KeyboardButton>> getKeyboard() {
    return this.keyboard;
  }

  public ReplyKeyboardMarkup setKeyboard(List<List<KeyboardButton>> v) {
    this.keyboard = v;
    return this;
  }

  public Optional<Boolean> getResizeKeyboard() {
    return Optional.ofNullable(resizeKeyboard);
  }

  public ReplyKeyboardMarkup setResizeKeyboard(Boolean v) {
    this.resizeKeyboard = v;
    return this;
  }

  public Optional<Boolean> getOneTimeKeyboard() {
    return Optional.ofNullable(oneTimeKeyboard);
  }

  public ReplyKeyboardMarkup setOneTimeKeyboard(Boolean v) {
    this.oneTimeKeyboard = v;
    return this;
  }

  public Optional<Boolean> getSelective() {
    return Optional.ofNullable(selective);
  }

  public ReplyKeyboardMarkup setSelective(Boolean v) {
    this.selective = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof ReplyKeyboardMarkup)) return false;

    ReplyKeyboardMarkup that = (ReplyKeyboardMarkup) thatObj;

    return this.keyboard.equals(that.keyboard)
        && this.resizeKeyboard.equals(that.resizeKeyboard)
        && this.oneTimeKeyboard.equals(that.oneTimeKeyboard)
        && this.selective.equals(that.selective);
  }

  @Override
  public String toString() {
    return "ReplyKeyboardMarkup{"
        + "keyboard="
        + this.keyboard
        + ','
        + "resizeKeyboard="
        + this.resizeKeyboard
        + ','
        + "oneTimeKeyboard="
        + this.oneTimeKeyboard
        + ','
        + "selective="
        + this.selective
        + '}';
  }
}
