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

public class InputMessageContentDeserializer extends StdDeserializer<InputMessageContent> {

  ObjectMapper objectMapper = new ObjectMapper();

  public InputMessageContentDeserializer() {
    this(null);
    objectMapper.registerModule(new Jdk8Module());
  }

  public InputMessageContentDeserializer(Class<InputMessageContent> t) {
    super(t);
    objectMapper.registerModule(new Jdk8Module());
  }

  @Override
  public InputMessageContent deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);

    String type = node.get("type").asText();

    switch (type) {
      case "none":
        return new InputVenueMessageContent(
            objectMapper.readValue(node.get("latitude").traverse(), Float.class),
            objectMapper.readValue(node.get("longitude").traverse(), Float.class),
            objectMapper.readValue(node.get("title").traverse(), String.class),
            objectMapper.readValue(node.get("address").traverse(), String.class),
            objectMapper.readValue(
                node.get("foursquareId").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("foursquareType").traverse(), new TypeReference<String>() {}));

      case "none1":
        return new InputContactMessageContent(
            objectMapper.readValue(node.get("phoneNumber").traverse(), String.class),
            objectMapper.readValue(node.get("firstName").traverse(), String.class),
            objectMapper.readValue(node.get("lastName").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(node.get("vcard").traverse(), new TypeReference<String>() {}));

      case "none2":
        return new InputLocationMessageContent(
            objectMapper.readValue(node.get("latitude").traverse(), Float.class),
            objectMapper.readValue(node.get("longitude").traverse(), Float.class),
            objectMapper.readValue(
                node.get("livePeriod").traverse(), new TypeReference<Integer>() {}));

      case "none3":
        return new InputTextMessageContent(
            objectMapper.readValue(node.get("messageText").traverse(), String.class),
            objectMapper.readValue(
                node.get("parseMode").traverse(), new TypeReference<String>() {}),
            objectMapper.readValue(
                node.get("disableWebPagePreview").traverse(), new TypeReference<Boolean>() {}));

      default:
        throw ctxt.weirdStringException("type", InputMessageContent.class, "Unknown discriminator");
    }
  }
}
