package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InlineKeyboardMarkup {

  private List<List<InlineKeyboardButton>> inlineKeyboard;

  public InlineKeyboardMarkup() {}

  public InlineKeyboardMarkup(List<List<InlineKeyboardButton>> inlineKeyboard) {
    this.inlineKeyboard = inlineKeyboard;
  }

  public List<List<InlineKeyboardButton>> getInlineKeyboard() {
    return this.inlineKeyboard;
  }

  public InlineKeyboardMarkup setInlineKeyboard(List<List<InlineKeyboardButton>> v) {
    this.inlineKeyboard = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof InlineKeyboardMarkup)) return false;

    InlineKeyboardMarkup that = (InlineKeyboardMarkup) thatObj;

    return this.inlineKeyboard.equals(that.inlineKeyboard);
  }

  @Override
  public String toString() {
    return "InlineKeyboardMarkup{" + "inlineKeyboard=" + this.inlineKeyboard + '}';
  }
}
