package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InlineQueryResultArticle implements InlineQueryResult {

  private String id;

  private String title;

  private InputMessageContent inputMessageContent;

  private InlineKeyboardMarkup replyMarkup;

  private String url;

  private Boolean hideUrl;

  private String description;

  private String thumbUrl;

  private Integer thumbWidth;

  private Integer thumbHeight;

  public InlineQueryResults what() {
    return InlineQueryResults.INLINE_QUERY_RESULT_ARTICLE;
  }

  public String getDiscriminator() {
    return "article";
  }

  public InlineQueryResultArticle() {}

  public InlineQueryResultArticle(
      String id,
      String title,
      InputMessageContent inputMessageContent,
      InlineKeyboardMarkup replyMarkup,
      String url,
      Boolean hideUrl,
      String description,
      String thumbUrl,
      Integer thumbWidth,
      Integer thumbHeight) {
    this.id = id;
    this.title = title;
    this.inputMessageContent = inputMessageContent;
    this.replyMarkup = replyMarkup;
    this.url = url;
    this.hideUrl = hideUrl;
    this.description = description;
    this.thumbUrl = thumbUrl;
    this.thumbWidth = thumbWidth;
    this.thumbHeight = thumbHeight;
  }

  public String getId() {
    return this.id;
  }

  public InlineQueryResultArticle setId(String v) {
    this.id = v;
    return this;
  }

  public String getTitle() {
    return this.title;
  }

  public InlineQueryResultArticle setTitle(String v) {
    this.title = v;
    return this;
  }

  public InputMessageContent getInputMessageContent() {
    return this.inputMessageContent;
  }

  public InlineQueryResultArticle setInputMessageContent(InputMessageContent v) {
    this.inputMessageContent = v;
    return this;
  }

  public Optional<InlineKeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public InlineQueryResultArticle setReplyMarkup(InlineKeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  public Optional<String> getUrl() {
    return Optional.ofNullable(url);
  }

  public InlineQueryResultArticle setUrl(String v) {
    this.url = v;
    return this;
  }

  public Optional<Boolean> getHideUrl() {
    return Optional.ofNullable(hideUrl);
  }

  public InlineQueryResultArticle setHideUrl(Boolean v) {
    this.hideUrl = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public InlineQueryResultArticle setDescription(String v) {
    this.description = v;
    return this;
  }

  public Optional<String> getThumbUrl() {
    return Optional.ofNullable(thumbUrl);
  }

  public InlineQueryResultArticle setThumbUrl(String v) {
    this.thumbUrl = v;
    return this;
  }

  public Optional<Integer> getThumbWidth() {
    return Optional.ofNullable(thumbWidth);
  }

  public InlineQueryResultArticle setThumbWidth(Integer v) {
    this.thumbWidth = v;
    return this;
  }

  public Optional<Integer> getThumbHeight() {
    return Optional.ofNullable(thumbHeight);
  }

  public InlineQueryResultArticle setThumbHeight(Integer v) {
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
    return true;
  }

  public InlineQueryResultArticle asInlineQueryResultArticle() {
    return this;
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

    if (!(thatObj instanceof InlineQueryResultArticle)) return false;

    InlineQueryResultArticle that = (InlineQueryResultArticle) thatObj;

    return this.id.equals(that.id)
        && this.title.equals(that.title)
        && this.inputMessageContent.equals(that.inputMessageContent)
        && this.replyMarkup.equals(that.replyMarkup)
        && this.url.equals(that.url)
        && this.hideUrl.equals(that.hideUrl)
        && this.description.equals(that.description)
        && this.thumbUrl.equals(that.thumbUrl)
        && this.thumbWidth.equals(that.thumbWidth)
        && this.thumbHeight.equals(that.thumbHeight);
  }

  @Override
  public String toString() {
    return "InlineQueryResultArticle{"
        + "id="
        + '\''
        + this.id
        + '\''
        + ','
        + "title="
        + '\''
        + this.title
        + '\''
        + ','
        + "inputMessageContent="
        + this.inputMessageContent
        + ','
        + "replyMarkup="
        + this.replyMarkup
        + ','
        + "url="
        + '\''
        + this.url
        + '\''
        + ','
        + "hideUrl="
        + this.hideUrl
        + ','
        + "description="
        + '\''
        + this.description
        + '\''
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
