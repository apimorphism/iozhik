package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InlineQueryResultVideo implements InlineQueryResult {

  private String id;

  private String videoUrl;

  private String mimeType;

  private String thumbUrl;

  private String title;

  private String caption;

  private String parseMode;

  private Integer videoWidth;

  private Integer videoHeight;

  private Integer videoDuration;

  private String description;

  private InlineKeyboardMarkup replyMarkup;

  private InputMessageContent inputMessageContent;

  public InlineQueryResults what() {
    return InlineQueryResults.INLINE_QUERY_RESULT_VIDEO;
  }

  public String getDiscriminator() {
    return "video";
  }

  public InlineQueryResultVideo() {}

  public InlineQueryResultVideo(
      String id,
      String videoUrl,
      String mimeType,
      String thumbUrl,
      String title,
      String caption,
      String parseMode,
      Integer videoWidth,
      Integer videoHeight,
      Integer videoDuration,
      String description,
      InlineKeyboardMarkup replyMarkup,
      InputMessageContent inputMessageContent) {
    this.id = id;
    this.videoUrl = videoUrl;
    this.mimeType = mimeType;
    this.thumbUrl = thumbUrl;
    this.title = title;
    this.caption = caption;
    this.parseMode = parseMode;
    this.videoWidth = videoWidth;
    this.videoHeight = videoHeight;
    this.videoDuration = videoDuration;
    this.description = description;
    this.replyMarkup = replyMarkup;
    this.inputMessageContent = inputMessageContent;
  }

  public String getId() {
    return this.id;
  }

  public InlineQueryResultVideo setId(String v) {
    this.id = v;
    return this;
  }

  public String getVideoUrl() {
    return this.videoUrl;
  }

  public InlineQueryResultVideo setVideoUrl(String v) {
    this.videoUrl = v;
    return this;
  }

  public String getMimeType() {
    return this.mimeType;
  }

  public InlineQueryResultVideo setMimeType(String v) {
    this.mimeType = v;
    return this;
  }

  public String getThumbUrl() {
    return this.thumbUrl;
  }

  public InlineQueryResultVideo setThumbUrl(String v) {
    this.thumbUrl = v;
    return this;
  }

  public String getTitle() {
    return this.title;
  }

  public InlineQueryResultVideo setTitle(String v) {
    this.title = v;
    return this;
  }

  public Optional<String> getCaption() {
    return Optional.ofNullable(caption);
  }

  public InlineQueryResultVideo setCaption(String v) {
    this.caption = v;
    return this;
  }

  public Optional<String> getParseMode() {
    return Optional.ofNullable(parseMode);
  }

  public InlineQueryResultVideo setParseMode(String v) {
    this.parseMode = v;
    return this;
  }

  public Optional<Integer> getVideoWidth() {
    return Optional.ofNullable(videoWidth);
  }

  public InlineQueryResultVideo setVideoWidth(Integer v) {
    this.videoWidth = v;
    return this;
  }

  public Optional<Integer> getVideoHeight() {
    return Optional.ofNullable(videoHeight);
  }

  public InlineQueryResultVideo setVideoHeight(Integer v) {
    this.videoHeight = v;
    return this;
  }

  public Optional<Integer> getVideoDuration() {
    return Optional.ofNullable(videoDuration);
  }

  public InlineQueryResultVideo setVideoDuration(Integer v) {
    this.videoDuration = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public InlineQueryResultVideo setDescription(String v) {
    this.description = v;
    return this;
  }

  public Optional<InlineKeyboardMarkup> getReplyMarkup() {
    return Optional.ofNullable(replyMarkup);
  }

  public InlineQueryResultVideo setReplyMarkup(InlineKeyboardMarkup v) {
    this.replyMarkup = v;
    return this;
  }

  public Optional<InputMessageContent> getInputMessageContent() {
    return Optional.ofNullable(inputMessageContent);
  }

  public InlineQueryResultVideo setInputMessageContent(InputMessageContent v) {
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
    return true;
  }

  public InlineQueryResultVideo asInlineQueryResultVideo() {
    return this;
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

    if (!(thatObj instanceof InlineQueryResultVideo)) return false;

    InlineQueryResultVideo that = (InlineQueryResultVideo) thatObj;

    return this.id.equals(that.id)
        && this.videoUrl.equals(that.videoUrl)
        && this.mimeType.equals(that.mimeType)
        && this.thumbUrl.equals(that.thumbUrl)
        && this.title.equals(that.title)
        && this.caption.equals(that.caption)
        && this.parseMode.equals(that.parseMode)
        && this.videoWidth.equals(that.videoWidth)
        && this.videoHeight.equals(that.videoHeight)
        && this.videoDuration.equals(that.videoDuration)
        && this.description.equals(that.description)
        && this.replyMarkup.equals(that.replyMarkup)
        && this.inputMessageContent.equals(that.inputMessageContent);
  }

  @Override
  public String toString() {
    return "InlineQueryResultVideo{"
        + "id="
        + '\''
        + this.id
        + '\''
        + ','
        + "videoUrl="
        + '\''
        + this.videoUrl
        + '\''
        + ','
        + "mimeType="
        + '\''
        + this.mimeType
        + '\''
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
        + "videoWidth="
        + this.videoWidth
        + ','
        + "videoHeight="
        + this.videoHeight
        + ','
        + "videoDuration="
        + this.videoDuration
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
        + '}';
  }
}
