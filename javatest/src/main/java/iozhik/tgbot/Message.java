package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Message {

  private int messageId;

  private User from;

  private int date;

  private Chat chat;

  private User forwardFrom;

  private Chat forwardFromChat;

  private Integer forwardFromMessageId;

  private String forwardSignature;

  private String forwardSenderName;

  private Integer forwardDate;

  private Message replyToMessage;

  private Integer editDate;

  private String mediaGroupId;

  private String authorSignature;

  private String text;

  private List<MessageEntity> entities;

  private List<MessageEntity> captionEntities;

  private Audio audio;

  private Document document;

  private Animation animation;

  private Game game;

  private List<PhotoSize> photo;

  private Sticker sticker;

  private Video video;

  private Voice voice;

  private VideoNote videoNote;

  private String caption;

  private Contact contact;

  private Location location;

  private Venue venue;

  private Poll poll;

  private List<User> newChatMembers;

  private User leftChatMember;

  private String newChatTitle;

  private List<PhotoSize> newChatPhoto;

  private Boolean deleteChatPhoto;

  private Boolean groupChatCreated;

  private Boolean supergroupChatCreated;

  private Boolean channelChatCreated;

  private Integer migrateToChatId;

  private Integer migrateFromChatId;

  private Message pinnedMessage;

  private Invoice invoice;

  private SuccessfulPayment successfulPayment;

  private String connectedWebsite;

  private PassportData passportData;

  public Message() {}

  public Message(
      int messageId,
      User from,
      int date,
      Chat chat,
      User forwardFrom,
      Chat forwardFromChat,
      Integer forwardFromMessageId,
      String forwardSignature,
      String forwardSenderName,
      Integer forwardDate,
      Message replyToMessage,
      Integer editDate,
      String mediaGroupId,
      String authorSignature,
      String text,
      List<MessageEntity> entities,
      List<MessageEntity> captionEntities,
      Audio audio,
      Document document,
      Animation animation,
      Game game,
      List<PhotoSize> photo,
      Sticker sticker,
      Video video,
      Voice voice,
      VideoNote videoNote,
      String caption,
      Contact contact,
      Location location,
      Venue venue,
      Poll poll,
      List<User> newChatMembers,
      User leftChatMember,
      String newChatTitle,
      List<PhotoSize> newChatPhoto,
      Boolean deleteChatPhoto,
      Boolean groupChatCreated,
      Boolean supergroupChatCreated,
      Boolean channelChatCreated,
      Integer migrateToChatId,
      Integer migrateFromChatId,
      Message pinnedMessage,
      Invoice invoice,
      SuccessfulPayment successfulPayment,
      String connectedWebsite,
      PassportData passportData) {
    this.messageId = messageId;
    this.from = from;
    this.date = date;
    this.chat = chat;
    this.forwardFrom = forwardFrom;
    this.forwardFromChat = forwardFromChat;
    this.forwardFromMessageId = forwardFromMessageId;
    this.forwardSignature = forwardSignature;
    this.forwardSenderName = forwardSenderName;
    this.forwardDate = forwardDate;
    this.replyToMessage = replyToMessage;
    this.editDate = editDate;
    this.mediaGroupId = mediaGroupId;
    this.authorSignature = authorSignature;
    this.text = text;
    this.entities = entities;
    this.captionEntities = captionEntities;
    this.audio = audio;
    this.document = document;
    this.animation = animation;
    this.game = game;
    this.photo = photo;
    this.sticker = sticker;
    this.video = video;
    this.voice = voice;
    this.videoNote = videoNote;
    this.caption = caption;
    this.contact = contact;
    this.location = location;
    this.venue = venue;
    this.poll = poll;
    this.newChatMembers = newChatMembers;
    this.leftChatMember = leftChatMember;
    this.newChatTitle = newChatTitle;
    this.newChatPhoto = newChatPhoto;
    this.deleteChatPhoto = deleteChatPhoto;
    this.groupChatCreated = groupChatCreated;
    this.supergroupChatCreated = supergroupChatCreated;
    this.channelChatCreated = channelChatCreated;
    this.migrateToChatId = migrateToChatId;
    this.migrateFromChatId = migrateFromChatId;
    this.pinnedMessage = pinnedMessage;
    this.invoice = invoice;
    this.successfulPayment = successfulPayment;
    this.connectedWebsite = connectedWebsite;
    this.passportData = passportData;
  }

  public int getMessageId() {
    return this.messageId;
  }

  public Message setMessageId(int v) {
    this.messageId = v;
    return this;
  }

  public Optional<User> getFrom() {
    return Optional.ofNullable(from);
  }

  public Message setFrom(User v) {
    this.from = v;
    return this;
  }

  public int getDate() {
    return this.date;
  }

  public Message setDate(int v) {
    this.date = v;
    return this;
  }

  public Chat getChat() {
    return this.chat;
  }

  public Message setChat(Chat v) {
    this.chat = v;
    return this;
  }

  public Optional<User> getForwardFrom() {
    return Optional.ofNullable(forwardFrom);
  }

  public Message setForwardFrom(User v) {
    this.forwardFrom = v;
    return this;
  }

  public Optional<Chat> getForwardFromChat() {
    return Optional.ofNullable(forwardFromChat);
  }

  public Message setForwardFromChat(Chat v) {
    this.forwardFromChat = v;
    return this;
  }

  public Optional<Integer> getForwardFromMessageId() {
    return Optional.ofNullable(forwardFromMessageId);
  }

  public Message setForwardFromMessageId(Integer v) {
    this.forwardFromMessageId = v;
    return this;
  }

  public Optional<String> getForwardSignature() {
    return Optional.ofNullable(forwardSignature);
  }

  public Message setForwardSignature(String v) {
    this.forwardSignature = v;
    return this;
  }

  public Optional<String> getForwardSenderName() {
    return Optional.ofNullable(forwardSenderName);
  }

  public Message setForwardSenderName(String v) {
    this.forwardSenderName = v;
    return this;
  }

  public Optional<Integer> getForwardDate() {
    return Optional.ofNullable(forwardDate);
  }

  public Message setForwardDate(Integer v) {
    this.forwardDate = v;
    return this;
  }

  public Optional<Message> getReplyToMessage() {
    return Optional.ofNullable(replyToMessage);
  }

  public Message setReplyToMessage(Message v) {
    this.replyToMessage = v;
    return this;
  }

  public Optional<Integer> getEditDate() {
    return Optional.ofNullable(editDate);
  }

  public Message setEditDate(Integer v) {
    this.editDate = v;
    return this;
  }

  public Optional<String> getMediaGroupId() {
    return Optional.ofNullable(mediaGroupId);
  }

  public Message setMediaGroupId(String v) {
    this.mediaGroupId = v;
    return this;
  }

  public Optional<String> getAuthorSignature() {
    return Optional.ofNullable(authorSignature);
  }

  public Message setAuthorSignature(String v) {
    this.authorSignature = v;
    return this;
  }

  public Optional<String> getText() {
    return Optional.ofNullable(text);
  }

  public Message setText(String v) {
    this.text = v;
    return this;
  }

  public List<MessageEntity> getEntities() {
    return this.entities;
  }

  public Message setEntities(List<MessageEntity> v) {
    this.entities = v;
    return this;
  }

  public List<MessageEntity> getCaptionEntities() {
    return this.captionEntities;
  }

  public Message setCaptionEntities(List<MessageEntity> v) {
    this.captionEntities = v;
    return this;
  }

  public Optional<Audio> getAudio() {
    return Optional.ofNullable(audio);
  }

  public Message setAudio(Audio v) {
    this.audio = v;
    return this;
  }

  public Optional<Document> getDocument() {
    return Optional.ofNullable(document);
  }

  public Message setDocument(Document v) {
    this.document = v;
    return this;
  }

  public Optional<Animation> getAnimation() {
    return Optional.ofNullable(animation);
  }

  public Message setAnimation(Animation v) {
    this.animation = v;
    return this;
  }

  public Optional<Game> getGame() {
    return Optional.ofNullable(game);
  }

  public Message setGame(Game v) {
    this.game = v;
    return this;
  }

  public List<PhotoSize> getPhoto() {
    return this.photo;
  }

  public Message setPhoto(List<PhotoSize> v) {
    this.photo = v;
    return this;
  }

  public Optional<Sticker> getSticker() {
    return Optional.ofNullable(sticker);
  }

  public Message setSticker(Sticker v) {
    this.sticker = v;
    return this;
  }

  public Optional<Video> getVideo() {
    return Optional.ofNullable(video);
  }

  public Message setVideo(Video v) {
    this.video = v;
    return this;
  }

  public Optional<Voice> getVoice() {
    return Optional.ofNullable(voice);
  }

  public Message setVoice(Voice v) {
    this.voice = v;
    return this;
  }

  public Optional<VideoNote> getVideoNote() {
    return Optional.ofNullable(videoNote);
  }

  public Message setVideoNote(VideoNote v) {
    this.videoNote = v;
    return this;
  }

  public Optional<String> getCaption() {
    return Optional.ofNullable(caption);
  }

  public Message setCaption(String v) {
    this.caption = v;
    return this;
  }

  public Optional<Contact> getContact() {
    return Optional.ofNullable(contact);
  }

  public Message setContact(Contact v) {
    this.contact = v;
    return this;
  }

  public Optional<Location> getLocation() {
    return Optional.ofNullable(location);
  }

  public Message setLocation(Location v) {
    this.location = v;
    return this;
  }

  public Optional<Venue> getVenue() {
    return Optional.ofNullable(venue);
  }

  public Message setVenue(Venue v) {
    this.venue = v;
    return this;
  }

  public Optional<Poll> getPoll() {
    return Optional.ofNullable(poll);
  }

  public Message setPoll(Poll v) {
    this.poll = v;
    return this;
  }

  public List<User> getNewChatMembers() {
    return this.newChatMembers;
  }

  public Message setNewChatMembers(List<User> v) {
    this.newChatMembers = v;
    return this;
  }

  public Optional<User> getLeftChatMember() {
    return Optional.ofNullable(leftChatMember);
  }

  public Message setLeftChatMember(User v) {
    this.leftChatMember = v;
    return this;
  }

  public Optional<String> getNewChatTitle() {
    return Optional.ofNullable(newChatTitle);
  }

  public Message setNewChatTitle(String v) {
    this.newChatTitle = v;
    return this;
  }

  public List<PhotoSize> getNewChatPhoto() {
    return this.newChatPhoto;
  }

  public Message setNewChatPhoto(List<PhotoSize> v) {
    this.newChatPhoto = v;
    return this;
  }

  public Optional<Boolean> getDeleteChatPhoto() {
    return Optional.ofNullable(deleteChatPhoto);
  }

  public Message setDeleteChatPhoto(Boolean v) {
    this.deleteChatPhoto = v;
    return this;
  }

  public Optional<Boolean> getGroupChatCreated() {
    return Optional.ofNullable(groupChatCreated);
  }

  public Message setGroupChatCreated(Boolean v) {
    this.groupChatCreated = v;
    return this;
  }

  public Optional<Boolean> getSupergroupChatCreated() {
    return Optional.ofNullable(supergroupChatCreated);
  }

  public Message setSupergroupChatCreated(Boolean v) {
    this.supergroupChatCreated = v;
    return this;
  }

  public Optional<Boolean> getChannelChatCreated() {
    return Optional.ofNullable(channelChatCreated);
  }

  public Message setChannelChatCreated(Boolean v) {
    this.channelChatCreated = v;
    return this;
  }

  public Optional<Integer> getMigrateToChatId() {
    return Optional.ofNullable(migrateToChatId);
  }

  public Message setMigrateToChatId(Integer v) {
    this.migrateToChatId = v;
    return this;
  }

  public Optional<Integer> getMigrateFromChatId() {
    return Optional.ofNullable(migrateFromChatId);
  }

  public Message setMigrateFromChatId(Integer v) {
    this.migrateFromChatId = v;
    return this;
  }

  public Optional<Message> getPinnedMessage() {
    return Optional.ofNullable(pinnedMessage);
  }

  public Message setPinnedMessage(Message v) {
    this.pinnedMessage = v;
    return this;
  }

  public Optional<Invoice> getInvoice() {
    return Optional.ofNullable(invoice);
  }

  public Message setInvoice(Invoice v) {
    this.invoice = v;
    return this;
  }

  public Optional<SuccessfulPayment> getSuccessfulPayment() {
    return Optional.ofNullable(successfulPayment);
  }

  public Message setSuccessfulPayment(SuccessfulPayment v) {
    this.successfulPayment = v;
    return this;
  }

  public Optional<String> getConnectedWebsite() {
    return Optional.ofNullable(connectedWebsite);
  }

  public Message setConnectedWebsite(String v) {
    this.connectedWebsite = v;
    return this;
  }

  public Optional<PassportData> getPassportData() {
    return Optional.ofNullable(passportData);
  }

  public Message setPassportData(PassportData v) {
    this.passportData = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Message)) return false;

    Message that = (Message) thatObj;

    return this.messageId == that.messageId
        && this.from.equals(that.from)
        && this.date == that.date
        && this.chat.equals(that.chat)
        && this.forwardFrom.equals(that.forwardFrom)
        && this.forwardFromChat.equals(that.forwardFromChat)
        && this.forwardFromMessageId.equals(that.forwardFromMessageId)
        && this.forwardSignature.equals(that.forwardSignature)
        && this.forwardSenderName.equals(that.forwardSenderName)
        && this.forwardDate.equals(that.forwardDate)
        && this.replyToMessage.equals(that.replyToMessage)
        && this.editDate.equals(that.editDate)
        && this.mediaGroupId.equals(that.mediaGroupId)
        && this.authorSignature.equals(that.authorSignature)
        && this.text.equals(that.text)
        && this.entities.equals(that.entities)
        && this.captionEntities.equals(that.captionEntities)
        && this.audio.equals(that.audio)
        && this.document.equals(that.document)
        && this.animation.equals(that.animation)
        && this.game.equals(that.game)
        && this.photo.equals(that.photo)
        && this.sticker.equals(that.sticker)
        && this.video.equals(that.video)
        && this.voice.equals(that.voice)
        && this.videoNote.equals(that.videoNote)
        && this.caption.equals(that.caption)
        && this.contact.equals(that.contact)
        && this.location.equals(that.location)
        && this.venue.equals(that.venue)
        && this.poll.equals(that.poll)
        && this.newChatMembers.equals(that.newChatMembers)
        && this.leftChatMember.equals(that.leftChatMember)
        && this.newChatTitle.equals(that.newChatTitle)
        && this.newChatPhoto.equals(that.newChatPhoto)
        && this.deleteChatPhoto.equals(that.deleteChatPhoto)
        && this.groupChatCreated.equals(that.groupChatCreated)
        && this.supergroupChatCreated.equals(that.supergroupChatCreated)
        && this.channelChatCreated.equals(that.channelChatCreated)
        && this.migrateToChatId.equals(that.migrateToChatId)
        && this.migrateFromChatId.equals(that.migrateFromChatId)
        && this.pinnedMessage.equals(that.pinnedMessage)
        && this.invoice.equals(that.invoice)
        && this.successfulPayment.equals(that.successfulPayment)
        && this.connectedWebsite.equals(that.connectedWebsite)
        && this.passportData.equals(that.passportData);
  }

  @Override
  public String toString() {
    return "Message{"
        + "messageId="
        + this.messageId
        + ','
        + "from="
        + this.from
        + ','
        + "date="
        + this.date
        + ','
        + "chat="
        + this.chat
        + ','
        + "forwardFrom="
        + this.forwardFrom
        + ','
        + "forwardFromChat="
        + this.forwardFromChat
        + ','
        + "forwardFromMessageId="
        + this.forwardFromMessageId
        + ','
        + "forwardSignature="
        + '\''
        + this.forwardSignature
        + '\''
        + ','
        + "forwardSenderName="
        + '\''
        + this.forwardSenderName
        + '\''
        + ','
        + "forwardDate="
        + this.forwardDate
        + ','
        + "replyToMessage="
        + this.replyToMessage
        + ','
        + "editDate="
        + this.editDate
        + ','
        + "mediaGroupId="
        + '\''
        + this.mediaGroupId
        + '\''
        + ','
        + "authorSignature="
        + '\''
        + this.authorSignature
        + '\''
        + ','
        + "text="
        + '\''
        + this.text
        + '\''
        + ','
        + "entities="
        + this.entities
        + ','
        + "captionEntities="
        + this.captionEntities
        + ','
        + "audio="
        + this.audio
        + ','
        + "document="
        + this.document
        + ','
        + "animation="
        + this.animation
        + ','
        + "game="
        + this.game
        + ','
        + "photo="
        + this.photo
        + ','
        + "sticker="
        + this.sticker
        + ','
        + "video="
        + this.video
        + ','
        + "voice="
        + this.voice
        + ','
        + "videoNote="
        + this.videoNote
        + ','
        + "caption="
        + '\''
        + this.caption
        + '\''
        + ','
        + "contact="
        + this.contact
        + ','
        + "location="
        + this.location
        + ','
        + "venue="
        + this.venue
        + ','
        + "poll="
        + this.poll
        + ','
        + "newChatMembers="
        + this.newChatMembers
        + ','
        + "leftChatMember="
        + this.leftChatMember
        + ','
        + "newChatTitle="
        + '\''
        + this.newChatTitle
        + '\''
        + ','
        + "newChatPhoto="
        + this.newChatPhoto
        + ','
        + "deleteChatPhoto="
        + this.deleteChatPhoto
        + ','
        + "groupChatCreated="
        + this.groupChatCreated
        + ','
        + "supergroupChatCreated="
        + this.supergroupChatCreated
        + ','
        + "channelChatCreated="
        + this.channelChatCreated
        + ','
        + "migrateToChatId="
        + this.migrateToChatId
        + ','
        + "migrateFromChatId="
        + this.migrateFromChatId
        + ','
        + "pinnedMessage="
        + this.pinnedMessage
        + ','
        + "invoice="
        + this.invoice
        + ','
        + "successfulPayment="
        + this.successfulPayment
        + ','
        + "connectedWebsite="
        + '\''
        + this.connectedWebsite
        + '\''
        + ','
        + "passportData="
        + this.passportData
        + '}';
  }
}
