package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RestrictChatMemberReq {

  private ChatId chatId;

  private int userId;

  private Integer untilDate;

  private Boolean canSendMessages;

  private Boolean canSendMediaMessages;

  private Boolean canSendOtherMessages;

  private Boolean canAddWebPagePreviews;

  public RestrictChatMemberReq() {}

  public RestrictChatMemberReq(
      ChatId chatId,
      int userId,
      Integer untilDate,
      Boolean canSendMessages,
      Boolean canSendMediaMessages,
      Boolean canSendOtherMessages,
      Boolean canAddWebPagePreviews) {
    this.chatId = chatId;
    this.userId = userId;
    this.untilDate = untilDate;
    this.canSendMessages = canSendMessages;
    this.canSendMediaMessages = canSendMediaMessages;
    this.canSendOtherMessages = canSendOtherMessages;
    this.canAddWebPagePreviews = canAddWebPagePreviews;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public RestrictChatMemberReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public int getUserId() {
    return this.userId;
  }

  public RestrictChatMemberReq setUserId(int v) {
    this.userId = v;
    return this;
  }

  public Optional<Integer> getUntilDate() {
    return Optional.ofNullable(untilDate);
  }

  public RestrictChatMemberReq setUntilDate(Integer v) {
    this.untilDate = v;
    return this;
  }

  public Optional<Boolean> getCanSendMessages() {
    return Optional.ofNullable(canSendMessages);
  }

  public RestrictChatMemberReq setCanSendMessages(Boolean v) {
    this.canSendMessages = v;
    return this;
  }

  public Optional<Boolean> getCanSendMediaMessages() {
    return Optional.ofNullable(canSendMediaMessages);
  }

  public RestrictChatMemberReq setCanSendMediaMessages(Boolean v) {
    this.canSendMediaMessages = v;
    return this;
  }

  public Optional<Boolean> getCanSendOtherMessages() {
    return Optional.ofNullable(canSendOtherMessages);
  }

  public RestrictChatMemberReq setCanSendOtherMessages(Boolean v) {
    this.canSendOtherMessages = v;
    return this;
  }

  public Optional<Boolean> getCanAddWebPagePreviews() {
    return Optional.ofNullable(canAddWebPagePreviews);
  }

  public RestrictChatMemberReq setCanAddWebPagePreviews(Boolean v) {
    this.canAddWebPagePreviews = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof RestrictChatMemberReq)) return false;

    RestrictChatMemberReq that = (RestrictChatMemberReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.userId == that.userId
        && this.untilDate.equals(that.untilDate)
        && this.canSendMessages.equals(that.canSendMessages)
        && this.canSendMediaMessages.equals(that.canSendMediaMessages)
        && this.canSendOtherMessages.equals(that.canSendOtherMessages)
        && this.canAddWebPagePreviews.equals(that.canAddWebPagePreviews);
  }

  @Override
  public String toString() {
    return "RestrictChatMemberReq{"
        + "chatId="
        + this.chatId
        + ','
        + "userId="
        + this.userId
        + ','
        + "untilDate="
        + this.untilDate
        + ','
        + "canSendMessages="
        + this.canSendMessages
        + ','
        + "canSendMediaMessages="
        + this.canSendMediaMessages
        + ','
        + "canSendOtherMessages="
        + this.canSendOtherMessages
        + ','
        + "canAddWebPagePreviews="
        + this.canAddWebPagePreviews
        + '}';
  }
}
