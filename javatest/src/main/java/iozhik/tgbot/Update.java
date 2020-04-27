package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Update {

  private int updateId;

  private Message message;

  private Message editedMessage;

  private Message channelPost;

  private Message editedChannelPost;

  private InlineQuery inlineQuery;

  private ChosenInlineResult chosenInlineResult;

  private CallbackQuery callbackQuery;

  private ShippingQuery shippingQuery;

  private PreCheckoutQuery preCheckoutQuery;

  private Poll poll;

  public Update() {}

  public Update(
      int updateId,
      Message message,
      Message editedMessage,
      Message channelPost,
      Message editedChannelPost,
      InlineQuery inlineQuery,
      ChosenInlineResult chosenInlineResult,
      CallbackQuery callbackQuery,
      ShippingQuery shippingQuery,
      PreCheckoutQuery preCheckoutQuery,
      Poll poll) {
    this.updateId = updateId;
    this.message = message;
    this.editedMessage = editedMessage;
    this.channelPost = channelPost;
    this.editedChannelPost = editedChannelPost;
    this.inlineQuery = inlineQuery;
    this.chosenInlineResult = chosenInlineResult;
    this.callbackQuery = callbackQuery;
    this.shippingQuery = shippingQuery;
    this.preCheckoutQuery = preCheckoutQuery;
    this.poll = poll;
  }

  public int getUpdateId() {
    return this.updateId;
  }

  public Update setUpdateId(int v) {
    this.updateId = v;
    return this;
  }

  public Optional<Message> getMessage() {
    return Optional.ofNullable(message);
  }

  public Update setMessage(Message v) {
    this.message = v;
    return this;
  }

  public Optional<Message> getEditedMessage() {
    return Optional.ofNullable(editedMessage);
  }

  public Update setEditedMessage(Message v) {
    this.editedMessage = v;
    return this;
  }

  public Optional<Message> getChannelPost() {
    return Optional.ofNullable(channelPost);
  }

  public Update setChannelPost(Message v) {
    this.channelPost = v;
    return this;
  }

  public Optional<Message> getEditedChannelPost() {
    return Optional.ofNullable(editedChannelPost);
  }

  public Update setEditedChannelPost(Message v) {
    this.editedChannelPost = v;
    return this;
  }

  public Optional<InlineQuery> getInlineQuery() {
    return Optional.ofNullable(inlineQuery);
  }

  public Update setInlineQuery(InlineQuery v) {
    this.inlineQuery = v;
    return this;
  }

  public Optional<ChosenInlineResult> getChosenInlineResult() {
    return Optional.ofNullable(chosenInlineResult);
  }

  public Update setChosenInlineResult(ChosenInlineResult v) {
    this.chosenInlineResult = v;
    return this;
  }

  public Optional<CallbackQuery> getCallbackQuery() {
    return Optional.ofNullable(callbackQuery);
  }

  public Update setCallbackQuery(CallbackQuery v) {
    this.callbackQuery = v;
    return this;
  }

  public Optional<ShippingQuery> getShippingQuery() {
    return Optional.ofNullable(shippingQuery);
  }

  public Update setShippingQuery(ShippingQuery v) {
    this.shippingQuery = v;
    return this;
  }

  public Optional<PreCheckoutQuery> getPreCheckoutQuery() {
    return Optional.ofNullable(preCheckoutQuery);
  }

  public Update setPreCheckoutQuery(PreCheckoutQuery v) {
    this.preCheckoutQuery = v;
    return this;
  }

  public Optional<Poll> getPoll() {
    return Optional.ofNullable(poll);
  }

  public Update setPoll(Poll v) {
    this.poll = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Update)) return false;

    Update that = (Update) thatObj;

    return this.updateId == that.updateId
        && this.message.equals(that.message)
        && this.editedMessage.equals(that.editedMessage)
        && this.channelPost.equals(that.channelPost)
        && this.editedChannelPost.equals(that.editedChannelPost)
        && this.inlineQuery.equals(that.inlineQuery)
        && this.chosenInlineResult.equals(that.chosenInlineResult)
        && this.callbackQuery.equals(that.callbackQuery)
        && this.shippingQuery.equals(that.shippingQuery)
        && this.preCheckoutQuery.equals(that.preCheckoutQuery)
        && this.poll.equals(that.poll);
  }

  @Override
  public String toString() {
    return "Update{"
        + "updateId="
        + this.updateId
        + ','
        + "message="
        + this.message
        + ','
        + "editedMessage="
        + this.editedMessage
        + ','
        + "channelPost="
        + this.channelPost
        + ','
        + "editedChannelPost="
        + this.editedChannelPost
        + ','
        + "inlineQuery="
        + this.inlineQuery
        + ','
        + "chosenInlineResult="
        + this.chosenInlineResult
        + ','
        + "callbackQuery="
        + this.callbackQuery
        + ','
        + "shippingQuery="
        + this.shippingQuery
        + ','
        + "preCheckoutQuery="
        + this.preCheckoutQuery
        + ','
        + "poll="
        + this.poll
        + '}';
  }
}
