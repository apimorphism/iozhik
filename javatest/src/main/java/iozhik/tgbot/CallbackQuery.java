package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CallbackQuery {

  private String id;

  private User from;

  private Message message;

  private String inlineMessageId;

  private String chatInstance;

  private String data;

  private String gameShortName;

  public CallbackQuery() {}

  public CallbackQuery(
      String id,
      User from,
      Message message,
      String inlineMessageId,
      String chatInstance,
      String data,
      String gameShortName) {
    this.id = id;
    this.from = from;
    this.message = message;
    this.inlineMessageId = inlineMessageId;
    this.chatInstance = chatInstance;
    this.data = data;
    this.gameShortName = gameShortName;
  }

  public String getId() {
    return this.id;
  }

  public CallbackQuery setId(String v) {
    this.id = v;
    return this;
  }

  public User getFrom() {
    return this.from;
  }

  public CallbackQuery setFrom(User v) {
    this.from = v;
    return this;
  }

  public Optional<Message> getMessage() {
    return Optional.ofNullable(message);
  }

  public CallbackQuery setMessage(Message v) {
    this.message = v;
    return this;
  }

  public Optional<String> getInlineMessageId() {
    return Optional.ofNullable(inlineMessageId);
  }

  public CallbackQuery setInlineMessageId(String v) {
    this.inlineMessageId = v;
    return this;
  }

  public String getChatInstance() {
    return this.chatInstance;
  }

  public CallbackQuery setChatInstance(String v) {
    this.chatInstance = v;
    return this;
  }

  public Optional<String> getData() {
    return Optional.ofNullable(data);
  }

  public CallbackQuery setData(String v) {
    this.data = v;
    return this;
  }

  public Optional<String> getGameShortName() {
    return Optional.ofNullable(gameShortName);
  }

  public CallbackQuery setGameShortName(String v) {
    this.gameShortName = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof CallbackQuery)) return false;

    CallbackQuery that = (CallbackQuery) thatObj;

    return this.id.equals(that.id)
        && this.from.equals(that.from)
        && this.message.equals(that.message)
        && this.inlineMessageId.equals(that.inlineMessageId)
        && this.chatInstance.equals(that.chatInstance)
        && this.data.equals(that.data)
        && this.gameShortName.equals(that.gameShortName);
  }

  @Override
  public String toString() {
    return "CallbackQuery{"
        + "id="
        + '\''
        + this.id
        + '\''
        + ','
        + "from="
        + this.from
        + ','
        + "message="
        + this.message
        + ','
        + "inlineMessageId="
        + '\''
        + this.inlineMessageId
        + '\''
        + ','
        + "chatInstance="
        + '\''
        + this.chatInstance
        + '\''
        + ','
        + "data="
        + '\''
        + this.data
        + '\''
        + ','
        + "gameShortName="
        + '\''
        + this.gameShortName
        + '\''
        + '}';
  }
}
