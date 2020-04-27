package iozhik.tgbot.deserializators;

import java.util.*;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import iozhik.tgbot.*;

import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class InlineQueryResultDeserializer extends StdDeserializer<InlineQueryResult> {

  ObjectMapper objectMapper = new ObjectMapper();

  public InlineQueryResultDeserializer() {
    this(null);
    objectMapper.registerModule(new Jdk8Module());
  }

  public InlineQueryResultDeserializer(Class<InlineQueryResult> t) {
    super(t);
    objectMapper.registerModule(new Jdk8Module());
  }

  @Override
  public InlineQueryResult deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);

    String type = node.get("type").asText();

    switch (type) {
      case "mpeg4_gif":
        return new InlineQueryResultMpeg4Gif(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("mpeg4Url").traverse(), String.class),
            objectMapper.readValue(
                node.get("mpeg4Width").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("mpeg4Height").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("mpeg4Duration").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(node.get("thumbUrl").traverse(), String.class),
            objectMapper.readValue(node.get("title").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}));

      case "mpeg4_gif1":
        return new InlineQueryResultCachedMpeg4Gif(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("mpeg4FileId").traverse(), String.class),
            objectMapper.readValue(node.get("title").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}));

      case "location":
        return new InlineQueryResultLocation(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("latitude").traverse(), Float.class),
            objectMapper.readValue(node.get("longitude").traverse(), Float.class),
            objectMapper.readValue(node.get("title").traverse(), String.class),
            objectMapper.readValue(
                node.get("livePeriod").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}),
            objectMapper.readValue(node.get("thumbUrl").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("thumbWidth").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("thumbHeight").traverse(), new TypeReference<Integer>() {}));

      case "photo":
        return new InlineQueryResultPhoto(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("photoUrl").traverse(), String.class),
            objectMapper.readValue(node.get("thumbUrl").traverse(), String.class),
            objectMapper.readValue(
                node.get("photoWidth").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("photoHeight").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(node.get("title").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("description").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}));

      case "photo1":
        return new InlineQueryResultCachedPhoto(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("photoFileId").traverse(), String.class),
            objectMapper.readValue(node.get("title").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("description").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}));

      case "document":
        return new InlineQueryResultDocument(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("title").traverse(), String.class),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(node.get("documentUrl").traverse(), String.class),
            objectMapper.readValue(node.get("mimeType").traverse(), String.class),
            objectMapper.readValue(
                node.get("description").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}),
            objectMapper.readValue(node.get("thumbUrl").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("thumbWidth").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("thumbHeight").traverse(), new TypeReference<Integer>() {}));

      case "document1":
        return new InlineQueryResultCachedDocument(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("title").traverse(), String.class),
            objectMapper.readValue(node.get("documentFileId").traverse(), String.class),
            objectMapper.readValue(
                node.get("description").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}));

      case "audio":
        return new InlineQueryResultAudio(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("audioUrl").traverse(), String.class),
            objectMapper.readValue(node.get("title").traverse(), String.class),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("performer").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("audioDuration").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}));

      case "audio1":
        return new InlineQueryResultCachedAudio(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("audioFileId").traverse(), String.class),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}));

      case "voice":
        return new InlineQueryResultCachedVoice(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("voiceFileId").traverse(), String.class),
            objectMapper.readValue(node.get("title").traverse(), String.class),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}));

      case "voice1":
        return new InlineQueryResultVoice(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("voiceUrl").traverse(), String.class),
            objectMapper.readValue(node.get("title").traverse(), String.class),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("voiceDuration").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}));

      case "article":
        return new InlineQueryResultArticle(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("title").traverse(), String.class),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(), InputMessageContent.class),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(node.get("url").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(node.get("hideUrl").traverse(), new TypeReference<Boolean>() {}),
            objectMapper.readValue(
                node.get("description").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(node.get("thumbUrl").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("thumbWidth").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("thumbHeight").traverse(), new TypeReference<Integer>() {}));

      case "contact":
        return new InlineQueryResultContact(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("phoneNumber").traverse(), String.class),
            objectMapper.readValue(node.get("firstName").traverse(), String.class),
            objectMapper.readValue(node.get("lastName").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(node.get("vcard").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}),
            objectMapper.readValue(node.get("thumbUrl").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("thumbWidth").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("thumbHeight").traverse(), new TypeReference<Integer>() {}));

      case "video":
        return new InlineQueryResultCachedVideo(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("videoFileId").traverse(), String.class),
            objectMapper.readValue(node.get("title").traverse(), String.class),
            objectMapper.readValue(
                node.get("description").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}));

      case "video1":
        return new InlineQueryResultVideo(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("videoUrl").traverse(), String.class),
            objectMapper.readValue(node.get("mimeType").traverse(), String.class),
            objectMapper.readValue(node.get("thumbUrl").traverse(), String.class),
            objectMapper.readValue(node.get("title").traverse(), String.class),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("videoWidth").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("videoHeight").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("videoDuration").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("description").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}));

      case "gif":
        return new InlineQueryResultGif(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("gifUrl").traverse(), String.class),
            objectMapper.readValue(
                node.get("gifWidth").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("gifHeight").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("gifDuration").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(node.get("thumbUrl").traverse(), String.class),
            objectMapper.readValue(node.get("title").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}));

      case "gif1":
        return new InlineQueryResultCachedGif(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("gifFileId").traverse(), String.class),
            objectMapper.readValue(node.get("title").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}));

      case "sticker":
        return new InlineQueryResultCachedSticker(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("stickerFileId").traverse(), String.class),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}));

      case "game":
        return new InlineQueryResultGame(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("gameShortName").traverse(), String.class),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}));

      case "venue":
        return new InlineQueryResultVenue(
            objectMapper.readValue(node.get("id").traverse(), String.class),
            objectMapper.readValue(node.get("latitude").traverse(), Float.class),
            objectMapper.readValue(node.get("longitude").traverse(), Float.class),
            objectMapper.readValue(node.get("title").traverse(), String.class),
            objectMapper.readValue(node.get("address").traverse(), String.class),
            objectMapper.readValue(
                node.get("foursquareId").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("foursquareType").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("replyMarkup").traverse(), new TypeReference<InlineKeyboardMarkup>() {}),
            objectMapper.readValue(
                node.get("inputMessageContent").traverse(),
                new TypeReference<InputMessageContent>() {}),
            objectMapper.readValue(node.get("thumbUrl").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("thumbWidth").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("thumbHeight").traverse(), new TypeReference<Integer>() {}));

      default:
        throw ctxt.weirdStringException("type", InlineQueryResult.class, "Unknown discriminator");
    }
  }
}
