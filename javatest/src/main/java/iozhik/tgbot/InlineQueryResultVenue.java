package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InlineQueryResultVenue implements InlineQueryResult {

  private String id;

  private Float latitude;

  private Float longitude;

  private String title;

  private String address;

  private String foursquareId;

  private String foursquareType;

  private InlineKeyboardMarkup replyMarkup;

  private InputMessageContent inputMessageContent;

  private String thumbUrl;

  private Integer thumbWidth;

  private Integer thumbHeight;

  public InlineQueryResults what() {
    return InlineQueryResults.INLINE_QUERY_RESULT_VENUE;
  }

  public String getDiscriminator() {
    return "venue";
  }

  public InlineQueryResultVenue() {}

  public InlineQueryResultVenue(
      String id,
      Float latitude,
      Float longitude,
      String title,
      String address,
      String foursquareId,
      String foursquareType,
      InlineKeyboardMarkup replyMarkup,
      InputMessageContent inputMessageContent,
      String thumbUrl,
      Integer thumbWidth,
      Integer thumbHeight) {
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
    this.title = title;
    this.address = address;
    this.foursquareId = foursquareId;
    this.foursquareType = foursquareType;
    this.replyMarkup = replyMarkup;
    this.inputMessageContent = inputMessageContent;
    this.thumbUrl = thumbUrl;
    this.thumbWidth = thumbWidth;
    this.thumbHeight = thumbHeight;
  }

  public String getId() {
    return this.id;
  }

  public InlineQueryResultVenue setId(String v) {
    this.id = v;
    return this;
  }

  public Float getLatitude() {
    return this.latitude;
  }

  public InlineQueryResultVenue setLatitude(Float v) {
    this.latitude = v;
    return this;
  }

  public Float getLongitude() {
    return this.longitude;
  }

  public InlineQueryResultVenue setLongitude(Float v) {
    this.longitude = v;
    return this;
  }

  public String getTitle() {
    return this.title;
  }

  public InlineQueryResultVenue setTitle(String v) {
    this.title = v;
    return this;
  }

  public String getAddress() {
    return this.address;
  }

  public InlineQueryResultVenue setAddress(String v) {
    this.address = v;
    return this;
  }

  public Optional<String> getFoursquareId() {
    return Optional.ofNullable(foursquareId);
  }

  public InlineQueryResultVenue setFoursquareId(String v) {
    this.foursquareId = v;
    return this;
  }

  public Optional<String> getFoursquareType() {
    return Optional.ofNullable(foursquareType);
  }

  public InlineQueryResultVenue setFoursquareType(String v) {
    this.foursquareType = v;
    return this;
  }

  public Optional<InlineKeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public InlineQueryResultVenue setReplyMarkup(InlineKeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  public Optional<InputMessageContent> getInputMessageContent() {
    return Optional.ofNullable(inputMessageContent);
  }

  public InlineQueryResultVenue setInputMessageContent(InputMessageContent v) {
    this.inputMessageContent = v;
    return this;
  }

  public Optional<String> getThumbUrl() {
    return Optional.ofNullable(thumbUrl);
  }

  public InlineQueryResultVenue setThumbUrl(String v) {
    this.thumbUrl = v;
    return this;
  }

  public Optional<Integer> getThumbWidth() {
    return Optional.ofNullable(thumbWidth);
  }

  public InlineQueryResultVenue setThumbWidth(Integer v) {
    this.thumbWidth = v;
    return this;
  }

  public Optional<Integer> getThumbHeight() {
    return Optional.ofNullable(thumbHeight);
  }

  public InlineQueryResultVenue setThumbHeight(Integer v) {
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
    return true;
  }

  public InlineQueryResultVenue asInlineQueryResultVenue() {
    return this;
  }

  public boolean isInlineQueryResultContact() {
    return false;
  }

  public InlineQueryResultContact asInlineQueryResultContact() {
    throw new IllegalStateException("Not a $stName: " + this);
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

    if (!(thatObj instanceof InlineQueryResultVenue)) return false;

    InlineQueryResultVenue that = (InlineQueryResultVenue) thatObj;

    return this.id.equals(that.id)
        && this.latitude.equals(that.latitude)
        && this.longitude.equals(that.longitude)
        && this.title.equals(that.title)
        && this.address.equals(that.address)
        && this.foursquareId.equals(that.foursquareId)
        && this.foursquareType.equals(that.foursquareType)
        && this.replyMarkup.equals(that.replyMarkup)
        && this.inputMessageContent.equals(that.inputMessageContent)
        && this.thumbUrl.equals(that.thumbUrl)
        && this.thumbWidth.equals(that.thumbWidth)
        && this.thumbHeight.equals(that.thumbHeight);
  }

  @Override
  public String toString() {
    return "InlineQueryResultVenue{"
        + "id="
        + '\''
        + this.id
        + '\''
        + ','
        + "latitude="
        + this.latitude
        + ','
        + "longitude="
        + this.longitude
        + ','
        + "title="
        + '\''
        + this.title
        + '\''
        + ','
        + "address="
        + '\''
        + this.address
        + '\''
        + ','
        + "foursquareId="
        + '\''
        + this.foursquareId
        + '\''
        + ','
        + "foursquareType="
        + '\''
        + this.foursquareType
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
