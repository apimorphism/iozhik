package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PromoteChatMemberReq {

  private ChatId chatId;

  private int userId;

  private Boolean canChangeInfo;

  private Boolean canPostMessages;

  private Boolean canEditMessages;

  private Boolean canDeleteMessages;

  private Boolean canInviteUsers;

  private Boolean canRestrictMembers;

  private Boolean canPinMessages;

  private Boolean canPromoteMembers;

  public PromoteChatMemberReq() {}

  public PromoteChatMemberReq(
      ChatId chatId,
      int userId,
      Boolean canChangeInfo,
      Boolean canPostMessages,
      Boolean canEditMessages,
      Boolean canDeleteMessages,
      Boolean canInviteUsers,
      Boolean canRestrictMembers,
      Boolean canPinMessages,
      Boolean canPromoteMembers) {
    this.chatId = chatId;
    this.userId = userId;
    this.canChangeInfo = canChangeInfo;
    this.canPostMessages = canPostMessages;
    this.canEditMessages = canEditMessages;
    this.canDeleteMessages = canDeleteMessages;
    this.canInviteUsers = canInviteUsers;
    this.canRestrictMembers = canRestrictMembers;
    this.canPinMessages = canPinMessages;
    this.canPromoteMembers = canPromoteMembers;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public PromoteChatMemberReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public int getUserId() {
    return this.userId;
  }

  public PromoteChatMemberReq setUserId(int v) {
    this.userId = v;
    return this;
  }

  public Optional<Boolean> getCanChangeInfo() {
    return Optional.ofNullable(canChangeInfo);
  }

  public PromoteChatMemberReq setCanChangeInfo(Boolean v) {
    this.canChangeInfo = v;
    return this;
  }

  public Optional<Boolean> getCanPostMessages() {
    return Optional.ofNullable(canPostMessages);
  }

  public PromoteChatMemberReq setCanPostMessages(Boolean v) {
    this.canPostMessages = v;
    return this;
  }

  public Optional<Boolean> getCanEditMessages() {
    return Optional.ofNullable(canEditMessages);
  }

  public PromoteChatMemberReq setCanEditMessages(Boolean v) {
    this.canEditMessages = v;
    return this;
  }

  public Optional<Boolean> getCanDeleteMessages() {
    return Optional.ofNullable(canDeleteMessages);
  }

  public PromoteChatMemberReq setCanDeleteMessages(Boolean v) {
    this.canDeleteMessages = v;
    return this;
  }

  public Optional<Boolean> getCanInviteUsers() {
    return Optional.ofNullable(canInviteUsers);
  }

  public PromoteChatMemberReq setCanInviteUsers(Boolean v) {
    this.canInviteUsers = v;
    return this;
  }

  public Optional<Boolean> getCanRestrictMembers() {
    return Optional.ofNullable(canRestrictMembers);
  }

  public PromoteChatMemberReq setCanRestrictMembers(Boolean v) {
    this.canRestrictMembers = v;
    return this;
  }

  public Optional<Boolean> getCanPinMessages() {
    return Optional.ofNullable(canPinMessages);
  }

  public PromoteChatMemberReq setCanPinMessages(Boolean v) {
    this.canPinMessages = v;
    return this;
  }

  public Optional<Boolean> getCanPromoteMembers() {
    return Optional.ofNullable(canPromoteMembers);
  }

  public PromoteChatMemberReq setCanPromoteMembers(Boolean v) {
    this.canPromoteMembers = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof PromoteChatMemberReq)) return false;

    PromoteChatMemberReq that = (PromoteChatMemberReq) thatObj;

    return this.chatId.equals(that.chatId)
        && this.userId == that.userId
        && this.canChangeInfo.equals(that.canChangeInfo)
        && this.canPostMessages.equals(that.canPostMessages)
        && this.canEditMessages.equals(that.canEditMessages)
        && this.canDeleteMessages.equals(that.canDeleteMessages)
        && this.canInviteUsers.equals(that.canInviteUsers)
        && this.canRestrictMembers.equals(that.canRestrictMembers)
        && this.canPinMessages.equals(that.canPinMessages)
        && this.canPromoteMembers.equals(that.canPromoteMembers);
  }

  @Override
  public String toString() {
    return "PromoteChatMemberReq{"
        + "chatId="
        + this.chatId
        + ','
        + "userId="
        + this.userId
        + ','
        + "canChangeInfo="
        + this.canChangeInfo
        + ','
        + "canPostMessages="
        + this.canPostMessages
        + ','
        + "canEditMessages="
        + this.canEditMessages
        + ','
        + "canDeleteMessages="
        + this.canDeleteMessages
        + ','
        + "canInviteUsers="
        + this.canInviteUsers
        + ','
        + "canRestrictMembers="
        + this.canRestrictMembers
        + ','
        + "canPinMessages="
        + this.canPinMessages
        + ','
        + "canPromoteMembers="
        + this.canPromoteMembers
        + '}';
  }
}
