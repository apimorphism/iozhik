package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InlineKeyboardButton {

  private String text;

  private String url;

  private String callbackData;

  private String switchInlineQuery;

  private String switchInlineQueryCurrentChat;

  private String callbackGame;

  private Boolean pay;

  public InlineKeyboardButton() {}

  public InlineKeyboardButton(
      String text,
      String url,
      String callbackData,
      String switchInlineQuery,
      String switchInlineQueryCurrentChat,
      String callbackGame,
      Boolean pay) {
    this.text = text;
    this.url = url;
    this.callbackData = callbackData;
    this.switchInlineQuery = switchInlineQuery;
    this.switchInlineQueryCurrentChat = switchInlineQueryCurrentChat;
    this.callbackGame = callbackGame;
    this.pay = pay;
  }

  public String getText() {
    return this.text;
  }

  public InlineKeyboardButton setText(String v) {
    this.text = v;
    return this;
  }

  public Optional<String> getUrl() {
    return Optional.ofNullable(url);
  }

  public InlineKeyboardButton setUrl(String v) {
    this.url = v;
    return this;
  }

  public Optional<String> getCallbackData() {
    return Optional.ofNullable(callbackData);
  }

  public InlineKeyboardButton setCallbackData(String v) {
    this.callbackData = v;
    return this;
  }

  public Optional<String> getSwitchInlineQuery() {
    return Optional.ofNullable(switchInlineQuery);
  }

  public InlineKeyboardButton setSwitchInlineQuery(String v) {
    this.switchInlineQuery = v;
    return this;
  }

  public Optional<String> getSwitchInlineQueryCurrentChat() {
    return Optional.ofNullable(switchInlineQueryCurrentChat);
  }

  public InlineKeyboardButton setSwitchInlineQueryCurrentChat(String v) {
    this.switchInlineQueryCurrentChat = v;
    return this;
  }

  public Optional<String> getCallbackGame() {
    return Optional.ofNullable(callbackGame);
  }

  public InlineKeyboardButton setCallbackGame(String v) {
    this.callbackGame = v;
    return this;
  }

  public Optional<Boolean> getPay() {
    return Optional.ofNullable(pay);
  }

  public InlineKeyboardButton setPay(Boolean v) {
    this.pay = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof InlineKeyboardButton)) return false;

    InlineKeyboardButton that = (InlineKeyboardButton) thatObj;

    return this.text.equals(that.text)
        && this.url.equals(that.url)
        && this.callbackData.equals(that.callbackData)
        && this.switchInlineQuery.equals(that.switchInlineQuery)
        && this.switchInlineQueryCurrentChat.equals(that.switchInlineQueryCurrentChat)
        && this.callbackGame.equals(that.callbackGame)
        && this.pay.equals(that.pay);
  }

  @Override
  public String toString() {
    return "InlineKeyboardButton{"
        + "text="
        + '\''
        + this.text
        + '\''
        + ','
        + "url="
        + '\''
        + this.url
        + '\''
        + ','
        + "callbackData="
        + '\''
        + this.callbackData
        + '\''
        + ','
        + "switchInlineQuery="
        + '\''
        + this.switchInlineQuery
        + '\''
        + ','
        + "switchInlineQueryCurrentChat="
        + '\''
        + this.switchInlineQueryCurrentChat
        + '\''
        + ','
        + "callbackGame="
        + '\''
        + this.callbackGame
        + '\''
        + ','
        + "pay="
        + this.pay
        + '}';
  }
}
