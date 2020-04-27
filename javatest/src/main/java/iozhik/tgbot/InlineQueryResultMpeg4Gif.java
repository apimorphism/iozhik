package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InlineQueryResultMpeg4Gif implements InlineQueryResult {

  private String id;

  private String mpeg4Url;

  private Integer mpeg4Width;

  private Integer mpeg4Height;

  private Integer mpeg4Duration;

  private String thumbUrl;

  private String title;

  private String caption;

  private String parseMode;

  private InlineKeyboardMarkup replyMarkup;

  private InputMessageContent inputMessageContent;

  public InlineQueryResults what() {
    return InlineQueryResults.INLINE_QUERY_RESULT_MPEG4_GIF;
  }

  public String getDiscriminator() {
    return "mpeg4_gif";
  }

  public InlineQueryResultMpeg4Gif() {}

  public InlineQueryResultMpeg4Gif(
      String id,
      String mpeg4Url,
      Integer mpeg4Width,
      Integer mpeg4Height,
      Integer mpeg4Duration,
      String thumbUrl,
      String title,
      String caption,
      String parseMode,
      InlineKeyboardMarkup replyMarkup,
      InputMessageContent inputMessageContent) {
    this.id = id;
    this.mpeg4Url = mpeg4Url;
    this.mpeg4Width = mpeg4Width;
    this.mpeg4Height = mpeg4Height;
    this.mpeg4Duration = mpeg4Duration;
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

  public InlineQueryResultMpeg4Gif setId(String v) {
    this.id = v;
    return this;
  }

  public String getMpeg4Url() {
    return this.mpeg4Url;
  }

  public InlineQueryResultMpeg4Gif setMpeg4Url(String v) {
    this.mpeg4Url = v;
    return this;
  }

  public Optional<Integer> getMpeg4Width() {
    return Optional.ofNullable(mpeg4Width);
  }

  public InlineQueryResultMpeg4Gif setMpeg4Width(Integer v) {
    this.mpeg4Width = v;
    return this;
  }

  public Optional<Integer> getMpeg4Height() {
    return Optional.ofNullable(mpeg4Height);
  }

  public InlineQueryResultMpeg4Gif setMpeg4Height(Integer v) {
    this.mpeg4Height = v;
    return this;
  }

  public Optional<Integer> getMpeg4Duration() {
    return Optional.ofNullable(mpeg4Duration);
  }

  public InlineQueryResultMpeg4Gif setMpeg4Duration(Integer v) {
    this.mpeg4Duration = v;
    return this;
  }

  public String getThumbUrl() {
    return this.thumbUrl;
  }

  public InlineQueryResultMpeg4Gif setThumbUrl(String v) {
    this.thumbUrl = v;
    return this;
  }

  public Optional<String> getTitle() {
    return Optional.ofNullable(title);
  }

  public InlineQueryResultMpeg4Gif setTitle(String v) {
    this.title = v;
    return this;
  }

  public Optional<String> getCaption() {
    return Optional.ofNullable(caption);
  }

  public InlineQueryResultMpeg4Gif setCaption(String v) {
    this.caption = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public InlineQueryResultMpeg4Gif setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public Optional<InlineKeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public InlineQueryResultMpeg4Gif setReplyMarkup(InlineKeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  public Optional<InputMessageContent> getInputMessageContent() {
    return Optional.ofNullable(inputMessageContent);
  }

  public InlineQueryResultMpeg4Gif setInputMessageContent(InputMessageContent v) {
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
    return true;
  }

  public InlineQueryResultMpeg4Gif asInlineQueryResultMpeg4Gif() {
    return this;
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

    if (!(thatObj instanceof InlineQueryResultMpeg4Gif)) return false;

    InlineQueryResultMpeg4Gif that = (InlineQueryResultMpeg4Gif) thatObj;

    return this.id.equals(that.id)
        && this.mpeg4Url.equals(that.mpeg4Url)
        && this.mpeg4Width.equals(that.mpeg4Width)
        && this.mpeg4Height.equals(that.mpeg4Height)
        && this.mpeg4Duration.equals(that.mpeg4Duration)
        && this.thumbUrl.equals(that.thumbUrl)
        && this.title.equals(that.title)
        && this.caption.equals(that.caption)
        && this.parseMode.equals(that.parseMode)
        && this.replyMarkup.equals(that.replyMarkup)
        && this.inputMessageContent.equals(that.inputMessageContent);
  }

  @Override
  public String toString() {
    return "InlineQueryResultMpeg4Gif{"
        + "id="
        + '\''
        + this.id
        + '\''
        + ','
        + "mpeg4Url="
        + '\''
        + this.mpeg4Url
        + '\''
        + ','
        + "mpeg4Width="
        + this.mpeg4Width
        + ','
        + "mpeg4Height="
        + this.mpeg4Height
        + ','
        + "mpeg4Duration="
        + this.mpeg4Duration
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
