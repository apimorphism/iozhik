package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InlineQueryResultContact implements InlineQueryResult {

  private String id;

  private String phoneNumber;

  private String firstName;

  private String lastName;

  private String vcard;

  private InlineKeyboardMarkup replyMarkup;

  private InputMessageContent inputMessageContent;

  private String thumbUrl;

  private Integer thumbWidth;

  private Integer thumbHeight;

  public InlineQueryResults what() {
    return InlineQueryResults.INLINE_QUERY_RESULT_CONTACT;
  }

  public String getDiscriminator() {
    return "contact";
  }

  public InlineQueryResultContact() {}

  public InlineQueryResultContact(
      String id,
      String phoneNumber,
      String firstName,
      String lastName,
      String vcard,
      InlineKeyboardMarkup replyMarkup,
      InputMessageContent inputMessageContent,
      String thumbUrl,
      Integer thumbWidth,
      Integer thumbHeight) {
    this.id = id;
    this.phoneNumber = phoneNumber;
    this.firstName = firstName;
    this.lastName = lastName;
    this.vcard = vcard;
    this.replyMarkup = replyMarkup;
    this.inputMessageContent = inputMessageContent;
    this.thumbUrl = thumbUrl;
    this.thumbWidth = thumbWidth;
    this.thumbHeight = thumbHeight;
  }

  public String getId() {
    return this.id;
  }

  public InlineQueryResultContact setId(String v) {
    this.id = v;
    return this;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public InlineQueryResultContact setPhoneNumber(String v) {
    this.phoneNumber = v;
    return this;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public InlineQueryResultContact setFirstName(String v) {
    this.firstName = v;
    return this;
  }

  public Optional<String> getLastName() {
    return Optional.ofNullable(lastName);
  }

  public InlineQueryResultContact setLastName(String v) {
    this.lastName = v;
    return this;
  }

  public Optional<String> getVcard() {
    return Optional.ofNullable(vcard);
  }

  public InlineQueryResultContact setVcard(String v) {
    this.vcard = v;
    return this;
  }

  public Optional<InlineKeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public InlineQueryResultContact setReplyMarkup(InlineKeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  public Optional<InputMessageContent> getInputMessageContent() {
    return Optional.ofNullable(inputMessageContent);
  }

  public InlineQueryResultContact setInputMessageContent(InputMessageContent v) {
    this.inputMessageContent = v;
    return this;
  }

  public Optional<String> getThumbUrl() {
    return Optional.ofNullable(thumbUrl);
  }

  public InlineQueryResultContact setThumbUrl(String v) {
    this.thumbUrl = v;
    return this;
  }

  public Optional<Integer> getThumbWidth() {
    return Optional.ofNullable(thumbWidth);
  }

  public InlineQueryResultContact setThumbWidth(Integer v) {
    this.thumbWidth = v;
    return this;
  }

  public Optional<Integer> getThumbHeight() {
    return Optional.ofNullable(thumbHeight);
  }

  public InlineQueryResultContact setThumbHeight(Integer v) {
    this.thumbHeight = v;
    return this;
  }

  public boolean isInlineQueryResultGif() {
    return false;
  }

  public InlineQueryResultGif asInlineQueryResultGif() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultVenue() {
    return false;
  }

  public InlineQueryResultVenue asInlineQueryResultVenue() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultContact() {
    return true;
  }

  public InlineQueryResultContact asInlineQueryResultContact() {
    return this;
  }

  public boolean isInlineQueryResultPhoto() {
    return false;
  }

  public InlineQueryResultPhoto asInlineQueryResultPhoto() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultDocument() {
    return false;
  }

  public InlineQueryResultDocument asInlineQueryResultDocument() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultCachedVoice() {
    return false;
  }

  public InlineQueryResultCachedVoice asInlineQueryResultCachedVoice() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultArticle() {
    return false;
  }

  public InlineQueryResultArticle asInlineQueryResultArticle() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultAudio() {
    return false;
  }

  public InlineQueryResultAudio asInlineQueryResultAudio() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultMpeg4Gif() {
    return false;
  }

  public InlineQueryResultMpeg4Gif asInlineQueryResultMpeg4Gif() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultCachedMpeg4Gif() {
    return false;
  }

  public InlineQueryResultCachedMpeg4Gif asInlineQueryResultCachedMpeg4Gif() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultCachedDocument() {
    return false;
  }

  public InlineQueryResultCachedDocument asInlineQueryResultCachedDocument() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultCachedVideo() {
    return false;
  }

  public InlineQueryResultCachedVideo asInlineQueryResultCachedVideo() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultGame() {
    return false;
  }

  public InlineQueryResultGame asInlineQueryResultGame() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultCachedPhoto() {
    return false;
  }

  public InlineQueryResultCachedPhoto asInlineQueryResultCachedPhoto() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultCachedSticker() {
    return false;
  }

  public InlineQueryResultCachedSticker asInlineQueryResultCachedSticker() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultVideo() {
    return false;
  }

  public InlineQueryResultVideo asInlineQueryResultVideo() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultCachedAudio() {
    return false;
  }

  public InlineQueryResultCachedAudio asInlineQueryResultCachedAudio() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultLocation() {
    return false;
  }

  public InlineQueryResultLocation asInlineQueryResultLocation() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultCachedGif() {
    return false;
  }

  public InlineQueryResultCachedGif asInlineQueryResultCachedGif() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isInlineQueryResultVoice() {
    return false;
  }

  public InlineQueryResultVoice asInlineQueryResultVoice() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof InlineQueryResultContact)) return false;

    InlineQueryResultContact that = (InlineQueryResultContact) thatObj;

    return this.id.equals(that.id)
        && this.phoneNumber.equals(that.phoneNumber)
        && this.firstName.equals(that.firstName)
        && this.lastName.equals(that.lastName)
        && this.vcard.equals(that.vcard)
        && this.replyMarkup.equals(that.replyMarkup)
        && this.inputMessageContent.equals(that.inputMessageContent)
        && this.thumbUrl.equals(that.thumbUrl)
        && this.thumbWidth.equals(that.thumbWidth)
        && this.thumbHeight.equals(that.thumbHeight);
  }

  @Override
  public String toString() {
    return "InlineQueryResultContact{"
        + "id="
        + '\''
        + this.id
        + '\''
        + ','
        + "phoneNumber="
        + '\''
        + this.phoneNumber
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
        + "vcard="
        + '\''
        + this.vcard
        + '\''
        + ','
        + "replyMarkup="
        + this.replyMarkup
        + ','
        + "inputMessageContent="
        + this.inputMessageContent
        + ','
        + "thumbUrl="
        + '\''
        + this.thumbUrl
        + '\''
        + ','
        + "thumbWidth="
        + this.thumbWidth
        + ','
        + "thumbHeight="
        + this.thumbHeight
        + '}';
  }
}
