package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InlineQueryResultCachedGif implements InlineQueryResult {

  private String id;

  private String gifFileId;

  private String title;

  private String caption;

  private String parseMode;

  private InlineKeyboardMarkup replyMarkup;

  private InputMessageContent inputMessageContent;

  public InlineQueryResults what() {
    return InlineQueryResults.INLINE_QUERY_RESULT_CACHED_GIF;
  }

  public String getDiscriminator() {
    return "gif";
  }

  public InlineQueryResultCachedGif() {}

  public InlineQueryResultCachedGif(
      String id,
      String gifFileId,
      String title,
      String caption,
      String parseMode,
      InlineKeyboardMarkup replyMarkup,
      InputMessageContent inputMessageContent) {
    this.id = id;
    this.gifFileId = gifFileId;
    this.title = title;
    this.caption = caption;
    this.parseMode = parseMode;
    this.replyMarkup = replyMarkup;
    this.inputMessageContent = inputMessageContent;
  }

  public String getId() {
    return this.id;
  }

  public InlineQueryResultCachedGif setId(String v) {
    this.id = v;
    return this;
  }

  public String getGifFileId() {
    return this.gifFileId;
  }

  public InlineQueryResultCachedGif setGifFileId(String v) {
    this.gifFileId = v;
    return this;
  }

  public Optional<String> getTitle() {
    return Optional.ofNullable(title);
  }

  public InlineQueryResultCachedGif setTitle(String v) {
    this.title = v;
    return this;
  }

  public Optional<String> getCaption() {
    return Optional.ofNullable(caption);
  }

  public InlineQueryResultCachedGif setCaption(String v) {
    this.caption = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public InlineQueryResultCachedGif setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public Optional<InlineKeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public InlineQueryResultCachedGif setReplyMarkup(InlineKeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  public Optional<InputMessageContent> getInputMessageContent() {
    return Optional.ofNullable(inputMessageContent);
  }

  public InlineQueryResultCachedGif setInputMessageContent(InputMessageContent v) {
    this.inputMessageContent = v;
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
    return true;
  }

  public InlineQueryResultCachedGif asInlineQueryResultCachedGif() {
    return this;
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

    if (!(thatObj instanceof InlineQueryResultCachedGif)) return false;

    InlineQueryResultCachedGif that = (InlineQueryResultCachedGif) thatObj;

    return this.id.equals(that.id)
        && this.gifFileId.equals(that.gifFileId)
        && this.title.equals(that.title)
        && this.caption.equals(that.caption)
        && this.parseMode.equals(that.parseMode)
        && this.replyMarkup.equals(that.replyMarkup)
        && this.inputMessageContent.equals(that.inputMessageContent);
  }

  @Override
  public String toString() {
    return "InlineQueryResultCachedGif{"
        + "id="
        + '\''
        + this.id
        + '\''
        + ','
        + "gifFileId="
        + '\''
        + this.gifFileId
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
