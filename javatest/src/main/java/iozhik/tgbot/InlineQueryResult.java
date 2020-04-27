package iozhik.tgbot;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import iozhik.tgbot.deserializators.InlineQueryResultDeserializer;

import java.util.*;

@JsonDeserialize(using = InlineQueryResultDeserializer.class)
public interface InlineQueryResult {

  enum InlineQueryResults {
    INLINE_QUERY_RESULT_GIF,
    INLINE_QUERY_RESULT_VENUE,
    INLINE_QUERY_RESULT_CONTACT,
    INLINE_QUERY_RESULT_PHOTO,
    INLINE_QUERY_RESULT_DOCUMENT,
    INLINE_QUERY_RESULT_CACHED_VOICE,
    INLINE_QUERY_RESULT_ARTICLE,
    INLINE_QUERY_RESULT_AUDIO,
    INLINE_QUERY_RESULT_MPEG4_GIF,
    INLINE_QUERY_RESULT_CACHED_MPEG4_GIF,
    INLINE_QUERY_RESULT_CACHED_DOCUMENT,
    INLINE_QUERY_RESULT_CACHED_VIDEO,
    INLINE_QUERY_RESULT_GAME,
    INLINE_QUERY_RESULT_CACHED_PHOTO,
    INLINE_QUERY_RESULT_CACHED_STICKER,
    INLINE_QUERY_RESULT_VIDEO,
    INLINE_QUERY_RESULT_CACHED_AUDIO,
    INLINE_QUERY_RESULT_LOCATION,
    INLINE_QUERY_RESULT_CACHED_GIF,
    INLINE_QUERY_RESULT_VOICE
  }

  InlineQueryResults what();

  boolean isInlineQueryResultGif();

  InlineQueryResultGif asInlineQueryResultGif();

  boolean isInlineQueryResultVenue();

  InlineQueryResultVenue asInlineQueryResultVenue();

  boolean isInlineQueryResultContact();

  InlineQueryResultContact asInlineQueryResultContact();

  boolean isInlineQueryResultPhoto();

  InlineQueryResultPhoto asInlineQueryResultPhoto();

  boolean isInlineQueryResultDocument();

  InlineQueryResultDocument asInlineQueryResultDocument();

  boolean isInlineQueryResultCachedVoice();

  InlineQueryResultCachedVoice asInlineQueryResultCachedVoice();

  boolean isInlineQueryResultArticle();

  InlineQueryResultArticle asInlineQueryResultArticle();

  boolean isInlineQueryResultAudio();

  InlineQueryResultAudio asInlineQueryResultAudio();

  boolean isInlineQueryResultMpeg4Gif();

  InlineQueryResultMpeg4Gif asInlineQueryResultMpeg4Gif();

  boolean isInlineQueryResultCachedMpeg4Gif();

  InlineQueryResultCachedMpeg4Gif asInlineQueryResultCachedMpeg4Gif();

  boolean isInlineQueryResultCachedDocument();

  InlineQueryResultCachedDocument asInlineQueryResultCachedDocument();

  boolean isInlineQueryResultCachedVideo();

  InlineQueryResultCachedVideo asInlineQueryResultCachedVideo();

  boolean isInlineQueryResultGame();

  InlineQueryResultGame asInlineQueryResultGame();

  boolean isInlineQueryResultCachedPhoto();

  InlineQueryResultCachedPhoto asInlineQueryResultCachedPhoto();

  boolean isInlineQueryResultCachedSticker();

  InlineQueryResultCachedSticker asInlineQueryResultCachedSticker();

  boolean isInlineQueryResultVideo();

  InlineQueryResultVideo asInlineQueryResultVideo();

  boolean isInlineQueryResultCachedAudio();

  InlineQueryResultCachedAudio asInlineQueryResultCachedAudio();

  boolean isInlineQueryResultLocation();

  InlineQueryResultLocation asInlineQueryResultLocation();

  boolean isInlineQueryResultCachedGif();

  InlineQueryResultCachedGif asInlineQueryResultCachedGif();

  boolean isInlineQueryResultVoice();

  InlineQueryResultVoice asInlineQueryResultVoice();
}
