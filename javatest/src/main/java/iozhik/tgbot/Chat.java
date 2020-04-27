package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Chat {

  private long id;

  private String type;

  private String title;

  private String username;

  private String firstName;

  private String lastName;

  private Boolean allMembersAreAdministrators;

  private ChatPhoto photo;

  private String description;

  private String inviteLink;

  private Message pinnedMessage;

  private String stickerSetName;

  private Boolean canSetStickerSet;

  public Chat() {}

  public Chat(
      long id,
      String type,
      String title,
      String username,
      String firstName,
      String lastName,
      Boolean allMembersAreAdministrators,
      ChatPhoto photo,
      String description,
      String inviteLink,
      Message pinnedMessage,
      String stickerSetName,
      Boolean canSetStickerSet) {
    this.id = id;
    this.type = type;
    this.title = title;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.allMembersAreAdministrators = allMembersAreAdministrators;
    this.photo = photo;
    this.description = description;
    this.inviteLink = inviteLink;
    this.pinnedMessage = pinnedMessage;
    this.stickerSetName = stickerSetName;
    this.canSetStickerSet = canSetStickerSet;
  }

  public long getId() {
    return this.id;
  }

  public Chat setId(long v) {
    this.id = v;
    return this;
  }

  public String getType() {
    return this.type;
  }

  public Chat setType(String v) {
    this.type = v;
    return this;
  }

  public Optional<String> getTitle() {
    return Optional.ofNullable(title);
  }

  public Chat setTitle(String v) {
    this.title = v;
    return this;
  }

  public Optional<String> getUsername() {
    return Optional.ofNullable(username);
  }

  public Chat setUsername(String v) {
    this.username = v;
    return this;
  }

  public Optional<String> getFirstName() {
    return Optional.ofNullable(firstName);
  }

  public Chat setFirstName(String v) {
    this.firstName = v;
    return this;
  }

  public Optional<String> getLastName() {
    return Optional.ofNullable(lastName);
  }

  public Chat setLastName(String v) {
    this.lastName = v;
    return this;
  }

  public Optional<Boolean> getAllMembersAreAdministrators() {
    return Optional.ofNullable(allMembersAreAdministrators);
  }

  public Chat setAllMembersAreAdministrators(Boolean v) {
    this.allMembersAreAdministrators = v;
    return this;
  }

  public Optional<ChatPhoto> getPhoto() {
    return Optional.ofNullable(photo);
  }

  public Chat setPhoto(ChatPhoto v) {
    this.photo = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public Chat setDescription(String v) {
    this.description = v;
    return this;
  }

  public Optional<String> getInviteLink() {
    return Optional.ofNullable(inviteLink);
  }

  public Chat setInviteLink(String v) {
    this.inviteLink = v;
    return this;
  }

  public Optional<Message> getPinnedMessage() {
    return Optional.ofNullable(pinnedMessage);
  }

  public Chat setPinnedMessage(Message v) {
    this.pinnedMessage = v;
    return this;
  }

  public Optional<String> getStickerSetName() {
    return Optional.ofNullable(stickerSetName);
  }

  public Chat setStickerSetName(String v) {
    this.stickerSetName = v;
    return this;
  }

  public Optional<Boolean> getCanSetStickerSet() {
    return Optional.ofNullable(canSetStickerSet);
  }

  public Chat setCanSetStickerSet(Boolean v) {
    this.canSetStickerSet = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Chat)) return false;

    Chat that = (Chat) thatObj;

    return this.id == that.id
        && this.type.equals(that.type)
        && this.title.equals(that.title)
        && this.username.equals(that.username)
        && this.firstName.equals(that.firstName)
        && this.lastName.equals(that.lastName)
        && this.allMembersAreAdministrators.equals(that.allMembersAreAdministrators)
        && this.photo.equals(that.photo)
        && this.description.equals(that.description)
        && this.inviteLink.equals(that.inviteLink)
        && this.pinnedMessage.equals(that.pinnedMessage)
        && this.stickerSetName.equals(that.stickerSetName)
        && this.canSetStickerSet.equals(that.canSetStickerSet);
  }

  @Override
  public String toString() {
    return "Chat{"
        + "id="
        + this.id
        + ','
        + "type="
        + '\''
        + this.type
        + '\''
        + ','
        + "title="
        + '\''
        + this.title
        + '\''
        + ','
        + "username="
        + '\''
        + this.username
        + '\''
        + ','
        + "firstName="
        + '\''
        + this.firstName
        + '\''
        + ','
        + "lastName="
        + '\''
        + this.lastName
        + '\''
        + ','
        + "allMembersAreAdministrators="
        + this.allMembersAreAdministrators
        + ','
        + "photo="
        + this.photo
        + ','
        + "description="
        + '\''
        + this.description
        + '\''
        + ','
        + "inviteLink="
        + '\''
        + this.inviteLink
        + '\''
        + ','
        + "pinnedMessage="
        + this.pinnedMessage
        + ','
        + "stickerSetName="
        + '\''
        + this.stickerSetName
        + '\''
        + ','
        + "canSetStickerSet="
        + this.canSetStickerSet
        + '}';
  }
}
