package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ChatMember {

  private User user;

  private String status;

  private Integer untilDate;

  private Boolean canBeEdited;

  private Boolean canChangeInfo;

  private Boolean canPostMessages;

  private Boolean canEditMessages;

  private Boolean canDeleteMessages;

  private Boolean canInviteUsers;

  private Boolean canRestrictMembers;

  private Boolean canPinMessages;

  private Boolean canPromoteMembers;

  private Boolean isMember;

  private Boolean canSendMessages;

  private Boolean canSendMediaMessages;

  private Boolean canSendOtherMessages;

  private Boolean canAddWebPagePreviews;

  public ChatMember() {}

  public ChatMember(
      User user,
      String status,
      Integer untilDate,
      Boolean canBeEdited,
      Boolean canChangeInfo,
      Boolean canPostMessages,
      Boolean canEditMessages,
      Boolean canDeleteMessages,
      Boolean canInviteUsers,
      Boolean canRestrictMembers,
      Boolean canPinMessages,
      Boolean canPromoteMembers,
      Boolean isMember,
      Boolean canSendMessages,
      Boolean canSendMediaMessages,
      Boolean canSendOtherMessages,
      Boolean canAddWebPagePreviews) {
    this.user = user;
    this.status = status;
    this.untilDate = untilDate;
    this.canBeEdited = canBeEdited;
    this.canChangeInfo = canChangeInfo;
    this.canPostMessages = canPostMessages;
    this.canEditMessages = canEditMessages;
    this.canDeleteMessages = canDeleteMessages;
    this.canInviteUsers = canInviteUsers;
    this.canRestrictMembers = canRestrictMembers;
    this.canPinMessages = canPinMessages;
    this.canPromoteMembers = canPromoteMembers;
    this.isMember = isMember;
    this.canSendMessages = canSendMessages;
    this.canSendMediaMessages = canSendMediaMessages;
    this.canSendOtherMessages = canSendOtherMessages;
    this.canAddWebPagePreviews = canAddWebPagePreviews;
  }

  public User getUser() {
    return this.user;
  }

  public ChatMember setUser(User v) {
    this.user = v;
    return this;
  }

  public String getStatus() {
    return this.status;
  }

  public ChatMember setStatus(String v) {
    this.status = v;
    return this;
  }

  public Optional<Integer> getUntilDate() {
    return Optional.ofNullable(untilDate);
  }

  public ChatMember setUntilDate(Integer v) {
    this.untilDate = v;
    return this;
  }

  public Optional<Boolean> getCanBeEdited() {
    return Optional.ofNullable(canBeEdited);
  }

  public ChatMember setCanBeEdited(Boolean v) {
    this.canBeEdited = v;
    return this;
  }

  public Optional<Boolean> getCanChangeInfo() {
    return Optional.ofNullable(canChangeInfo);
  }

  public ChatMember setCanChangeInfo(Boolean v) {
    this.canChangeInfo = v;
    return this;
  }

  public Optional<Boolean> getCanPostMessages() {
    return Optional.ofNullable(canPostMessages);
  }

  public ChatMember setCanPostMessages(Boolean v) {
    this.canPostMessages = v;
    return this;
  }

  public Optional<Boolean> getCanEditMessages() {
    return Optional.ofNullable(canEditMessages);
  }

  public ChatMember setCanEditMessages(Boolean v) {
    this.canEditMessages = v;
    return this;
  }

  public Optional<Boolean> getCanDeleteMessages() {
    return Optional.ofNullable(canDeleteMessages);
  }

  public ChatMember setCanDeleteMessages(Boolean v) {
    this.canDeleteMessages = v;
    return this;
  }

  public Optional<Boolean> getCanInviteUsers() {
    return Optional.ofNullable(canInviteUsers);
  }

  public ChatMember setCanInviteUsers(Boolean v) {
    this.canInviteUsers = v;
    return this;
  }

  public Optional<Boolean> getCanRestrictMembers() {
    return Optional.ofNullable(canRestrictMembers);
  }

  public ChatMember setCanRestrictMembers(Boolean v) {
    this.canRestrictMembers = v;
    return this;
  }

  public Optional<Boolean> getCanPinMessages() {
    return Optional.ofNullable(canPinMessages);
  }

  public ChatMember setCanPinMessages(Boolean v) {
    this.canPinMessages = v;
    return this;
  }

  public Optional<Boolean> getCanPromoteMembers() {
    return Optional.ofNullable(canPromoteMembers);
  }

  public ChatMember setCanPromoteMembers(Boolean v) {
    this.canPromoteMembers = v;
    return this;
  }

  public Optional<Boolean> getIsMember() {
    return Optional.ofNullable(isMember);
  }

  public ChatMember setIsMember(Boolean v) {
    this.isMember = v;
    return this;
  }

  public Optional<Boolean> getCanSendMessages() {
    return Optional.ofNullable(canSendMessages);
  }

  public ChatMember setCanSendMessages(Boolean v) {
    this.canSendMessages = v;
    return this;
  }

  public Optional<Boolean> getCanSendMediaMessages() {
    return Optional.ofNullable(canSendMediaMessages);
  }

  public ChatMember setCanSendMediaMessages(Boolean v) {
    this.canSendMediaMessages = v;
    return this;
  }

  public Optional<Boolean> getCanSendOtherMessages() {
    return Optional.ofNullable(canSendOtherMessages);
  }

  public ChatMember setCanSendOtherMessages(Boolean v) {
    this.canSendOtherMessages = v;
    return this;
  }

  public Optional<Boolean> getCanAddWebPagePreviews() {
    return Optional.ofNullable(canAddWebPagePreviews);
  }

  public ChatMember setCanAddWebPagePreviews(Boolean v) {
    this.canAddWebPagePreviews = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof ChatMember)) return false;

    ChatMember that = (ChatMember) thatObj;

    return this.user.equals(that.user)
        && this.status.equals(that.status)
        && this.untilDate.equals(that.untilDate)
        && this.canBeEdited.equals(that.canBeEdited)
        && this.canChangeInfo.equals(that.canChangeInfo)
        && this.canPostMessages.equals(that.canPostMessages)
        && this.canEditMessages.equals(that.canEditMessages)
        && this.canDeleteMessages.equals(that.canDeleteMessages)
        && this.canInviteUsers.equals(that.canInviteUsers)
        && this.canRestrictMembers.equals(that.canRestrictMembers)
        && this.canPinMessages.equals(that.canPinMessages)
        && this.canPromoteMembers.equals(that.canPromoteMembers)
        && this.isMember.equals(that.isMember)
        && this.canSendMessages.equals(that.canSendMessages)
        && this.canSendMediaMessages.equals(that.canSendMediaMessages)
        && this.canSendOtherMessages.equals(that.canSendOtherMessages)
        && this.canAddWebPagePreviews.equals(that.canAddWebPagePreviews);
  }

  @Override
  public String toString() {
    return "ChatMember{"
        + "user="
        + this.user
        + ','
        + "status="
        + '\''
        + this.status
        + '\''
        + ','
        + "untilDate="
        + this.untilDate
        + ','
        + "canBeEdited="
        + this.canBeEdited
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
        + ','
        + "isMember="
        + this.isMember
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
