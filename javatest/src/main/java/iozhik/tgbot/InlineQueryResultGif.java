package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InlineQueryResultGif implements InlineQueryResult {

  private String id;

  private String gifUrl;

  private Integer gifWidth;

  private Integer gifHeight;

  private Integer gifDuration;

  private String thumbUrl;

  private String title;

  private String caption;

  private String parseMode;

  private InlineKeyboardMarkup replyMarkup;

  private InputMessageContent inputMessageContent;

  public InlineQueryResults what() {
    return InlineQueryResults.INLINE_QUERY_RESULT_GIF;
  }

  public String getDiscriminator() {
    return "gif";
  }

  public InlineQueryResultGif() {}

  public InlineQueryResultGif(
      String id,
      String gifUrl,
      Integer gifWidth,
      Integer gifHeight,
      Integer gifDuration,
      String thumbUrl,
      String title,
      String caption,
      String parseMode,
      InlineKeyboardMarkup replyMarkup,
      InputMessageContent inputMessageContent) {
    this.id = id;
    this.gifUrl = gifUrl;
    this.gifWidth = gifWidth;
    this.gifHeight = gifHeight;
    this.gifDuration = gifDuration;
    this.thumbUrl = thumbUrl;
    this.title = title;
    this.caption = caption;
    this.parseMode = parseMode;
    this.replyMarkup = replyMarkup;
    this.inputMessageContent = inputMessageContent;
  }

  public String getId() {
    return this.id;
  }

  public InlineQueryResultGif setId(String v) {
    this.id = v;
    return this;
  }

  public String getGifUrl() {
    return this.gifUrl;
  }

  public InlineQueryResultGif setGifUrl(String v) {
    this.gifUrl = v;
    return this;
  }

  public Optional<Integer> getGifWidth() {
    return Optional.ofNullable(gifWidth);
  }

  public InlineQueryResultGif setGifWidth(Integer v) {
    this.gifWidth = v;
    return this;
  }

  public Optional<Integer> getGifHeight() {
    return Optional.ofNullable(gifHeight);
  }

  public InlineQueryResultGif setGifHeight(Integer v) {
    this.gifHeight = v;
    return this;
  }

  public Optional<Integer> getGifDuration() {
    return Optional.ofNullable(gifDuration);
  }

  public InlineQueryResultGif setGifDuration(Integer v) {
    this.gifDuration = v;
    return this;
  }

  public String getThumbUrl() {
    return this.thumbUrl;
  }

  public InlineQueryResultGif setThumbUrl(String v) {
    this.thumbUrl = v;
    return this;
  }

  public Optional<String> getTitle() {
    return Optional.ofNullable(title);
  }

  public InlineQueryResultGif setTitle(String v) {
    this.title = v;
    return this;
  }

  public Optional<String> getCaption() {
    return Optional.ofNullable(caption);
  }

  public InlineQueryResultGif setCaption(String v) {
    this.caption = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public InlineQueryResultGif setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public Optional<InlineKeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public InlineQueryResultGif setReplyMarkup(InlineKeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  public Optional<InputMessageContent> getInputMessageContent() {
    return Optional.ofNullable(inputMessageContent);
  }

  public InlineQueryResultGif setInputMessageContent(InputMessageContent v) {
    this.inputMessageContent = v;
    return this;
  }

  public boolean isInlineQueryResultGif() {
    return true;
  }

  public InlineQueryResultGif asInlineQueryResultGif() {
    return this;
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

    if (!(thatObj instanceof InlineQueryResultGif)) return false;

    InlineQueryResultGif that = (InlineQueryResultGif) thatObj;

    return this.id.equals(that.id)
        && this.gifUrl.equals(that.gifUrl)
        && this.gifWidth.equals(that.gifWidth)
        && this.gifHeight.equals(that.gifHeight)
        && this.gifDuration.equals(that.gifDuration)
        && this.thumbUrl.equals(that.thumbUrl)
        && this.title.equals(that.title)
        && this.caption.equals(that.caption)
        && this.parseMode.equals(that.parseMode)
        && this.replyMarkup.equals(that.replyMarkup)
        && this.inputMessageContent.equals(that.inputMessageContent);
  }

  @Override
  public String toString() {
    return "InlineQueryResultGif{"
        + "id="
        + '\''
        + this.id
        + '\''
        + ','
        + "gifUrl="
        + '\''
        + this.gifUrl
        + '\''
        + ','
        + "gifWidth="
        + this.gifWidth
        + ','
        + "gifHeight="
        + this.gifHeight
        + ','
        + "gifDuration="
        + this.gifDuration
        + ','
        + "thumbUrl="
        + '\''
        + this.thumbUrl
        + '\''
        + ','
        + "title="
        + '\''
        + this.title
        + '\''
        + ','
        + "caption="
        + '\''
        + this.caption
        + '\''
        + ','
        + "parseMode="
        + '\''
        + this.parseMode
        + '\''
        + ','
        + "replyMarkup="
        + this.replyMarkup
        + ','
        + "inputMessageContent="
        + this.inputMessageContent
        + '}';
  }
}
