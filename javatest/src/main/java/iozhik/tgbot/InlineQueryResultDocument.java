package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InlineQueryResultDocument implements InlineQueryResult {

  private String id;

  private String title;

  private String caption;

  private String parseMode;

  private String documentUrl;

  private String mimeType;

  private String description;

  private InlineKeyboardMarkup replyMarkup;

  private InputMessageContent inputMessageContent;

  private String thumbUrl;

  private Integer thumbWidth;

  private Integer thumbHeight;

  public InlineQueryResults what() {
    return InlineQueryResults.INLINE_QUERY_RESULT_DOCUMENT;
  }

  public String getDiscriminator() {
    return "document";
  }

  public InlineQueryResultDocument() {}

  public InlineQueryResultDocument(
      String id,
      String title,
      String caption,
      String parseMode,
      String documentUrl,
      String mimeType,
      String description,
      InlineKeyboardMarkup replyMarkup,
      InputMessageContent inputMessageContent,
      String thumbUrl,
      Integer thumbWidth,
      Integer thumbHeight) {
    this.id = id;
    this.title = title;
    this.caption = caption;
    this.parseMode = parseMode;
    this.documentUrl = documentUrl;
    this.mimeType = mimeType;
    this.description = description;
    this.replyMarkup = replyMarkup;
    this.inputMessageContent = inputMessageContent;
    this.thumbUrl = thumbUrl;
    this.thumbWidth = thumbWidth;
    this.thumbHeight = thumbHeight;
  }

  public String getId() {
    return this.id;
  }

  public InlineQueryResultDocument setId(String v) {
    this.id = v;
    return this;
  }

  public String getTitle() {
    return this.title;
  }

  public InlineQueryResultDocument setTitle(String v) {
    this.title = v;
    return this;
  }

  public Optional<String> getCaption() {
    return Optional.ofNullable(caption);
  }

  public InlineQueryResultDocument setCaption(String v) {
    this.caption = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public InlineQueryResultDocument setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public String getDocumentUrl() {
    return this.documentUrl;
  }

  public InlineQueryResultDocument setDocumentUrl(String v) {
    this.documentUrl = v;
    return this;
  }

  public String getMimeType() {
    return this.mimeType;
  }

  public InlineQueryResultDocument setMimeType(String v) {
    this.mimeType = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public InlineQueryResultDocument setDescription(String v) {
    this.description = v;
    return this;
  }

  public Optional<InlineKeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public InlineQueryResultDocument setReplyMarkup(InlineKeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  public Optional<InputMessageContent> getInputMessageContent() {
    return Optional.ofNullable(inputMessageContent);
  }

  public InlineQueryResultDocument setInputMessageContent(InputMessageContent v) {
    this.inputMessageContent = v;
    return this;
  }

  public Optional<String> getThumbUrl() {
    return Optional.ofNullable(thumbUrl);
  }

  public InlineQueryResultDocument setThumbUrl(String v) {
    this.thumbUrl = v;
    return this;
  }

  public Optional<Integer> getThumbWidth() {
    return Optional.ofNullable(thumbWidth);
  }

  public InlineQueryResultDocument setThumbWidth(Integer v) {
    this.thumbWidth = v;
    return this;
  }

  public Optional<Integer> getThumbHeight() {
    return Optional.ofNullable(thumbHeight);
  }

  public InlineQueryResultDocument setThumbHeight(Integer v) {
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
    return true;
  }

  public InlineQueryResultDocument asInlineQueryResultDocument() {
    return this;
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

    if (!(thatObj instanceof InlineQueryResultDocument)) return false;

    InlineQueryResultDocument that = (InlineQueryResultDocument) thatObj;

    return this.id.equals(that.id)
        && this.title.equals(that.title)
        && this.caption.equals(that.caption)
        && this.parseMode.equals(that.parseMode)
        && this.documentUrl.equals(that.documentUrl)
        && this.mimeType.equals(that.mimeType)
        && this.description.equals(that.description)
        && this.replyMarkup.equals(that.replyMarkup)
        && this.inputMessageContent.equals(that.inputMessageContent)
        && this.thumbUrl.equals(that.thumbUrl)
        && this.thumbWidth.equals(that.thumbWidth)
        && this.thumbHeight.equals(that.thumbHeight);
  }

  @Override
  public String toString() {
    return "InlineQueryResultDocument{"
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
        + "documentUrl="
        + '\''
        + this.documentUrl
        + '\''
        + ','
        + "mimeType="
        + '\''
        + this.mimeType
        + '\''
        + ','
        + "description="
        + '\''
        + this.description
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
