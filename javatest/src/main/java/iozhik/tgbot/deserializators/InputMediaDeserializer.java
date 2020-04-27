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

public class InputMediaDeserializer extends StdDeserializer<InputMedia> {

  ObjectMapper objectMapper = new ObjectMapper();

  public InputMediaDeserializer() {
    this(null);
    objectMapper.registerModule(new Jdk8Module());
  }

  public InputMediaDeserializer(Class<InputMedia> t) {
    super(t);
    objectMapper.registerModule(new Jdk8Module());
  }

  @Override
  public InputMedia deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);

    String type = node.get("type").asText();

    switch (type) {
      case "photo":
        return new InputMediaPhoto(
            objectMapper.readValue(node.get("media").traverse(), String.class),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}));

      case "document":
        return new InputMediaDocument(
            objectMapper.readValue(node.get("media").traverse(), String.class),
            objectMapper.readValue(node.get("thumb").traverse(), new TypeReference<IFile>() {}),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}));

      case "audio":
        return new InputMediaAudio(
            objectMapper.readValue(node.get("media").traverse(), String.class),
            objectMapper.readValue(node.get("thumb").traverse(), new TypeReference<IFile>() {}),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("duration").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("performer").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(node.get("title").traverse(), new TypeReference<String>() {}));

      case "animation":
        return new InputMediaAnimation(
            objectMapper.readValue(node.get("media").traverse(), String.class),
            objectMapper.readValue(node.get("thumb").traverse(), new TypeReference<IFile>() {}),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(node.get("width").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(node.get("height").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("duration").traverse(), new TypeReference<Integer>() {}));

      case "video":
        return new InputMediaVideo(
            objectMapper.readValue(node.get("media").traverse(), String.class),
            objectMapper.readValue(node.get("thumb").traverse(), new TypeReference<IFile>() {}),
            objectMapper.readValue(node.get("caption").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(node.get("width").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(node.get("height").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("duration").traverse(), new TypeReference<Integer>() {}),
            objectMapper.readValue(
                node.get("supportsStreaming").traverse(), new TypeReference<Boolean>() {}));

      default:
        throw ctxt.weirdStringException("type", InputMedia.class, "Unknown discriminator");
    }
  }
}
