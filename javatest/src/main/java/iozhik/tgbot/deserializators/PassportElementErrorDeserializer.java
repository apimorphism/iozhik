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

public class PassportElementErrorDeserializer extends StdDeserializer<PassportElementError> {

  ObjectMapper objectMapper = new ObjectMapper();

  public PassportElementErrorDeserializer() {
    this(null);
    objectMapper.registerModule(new Jdk8Module());
  }

  public PassportElementErrorDeserializer(Class<PassportElementError> t) {
    super(t);
    objectMapper.registerModule(new Jdk8Module());
  }

  @Override
  public PassportElementError deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);

    String type = node.get("type").asText();

    switch (type) {
      case "translation_file":
        return new PassportElementErrorTranslationFile(
            objectMapper.readValue(node.get("type").traverse(), String.class),
            objectMapper.readValue(node.get("fileHash").traverse(), String.class),
            objectMapper.readValue(node.get("message").traverse(), String.class));

      case "translation_files":
        return new PassportElementErrorTranslationFiles(
            objectMapper.readValue(node.get("type").traverse(), String.class),
            objectMapper.readValue(
                node.get("fileHashes").traverse(), new TypeReference<List<String>>() {}),
            objectMapper.readValue(node.get("message").traverse(), String.class));

      case "reverse_side":
        return new PassportElementErrorReverseSide(
            objectMapper.readValue(node.get("type").traverse(), String.class),
            objectMapper.readValue(node.get("fileHash").traverse(), String.class),
            objectMapper.readValue(node.get("message").traverse(), String.class));

      case "data":
        return new PassportElementErrorDataField(
            objectMapper.readValue(node.get("type").traverse(), String.class),
            objectMapper.readValue(node.get("fieldName").traverse(), String.class),
            objectMapper.readValue(node.get("dataHash").traverse(), String.class),
            objectMapper.readValue(node.get("message").traverse(), String.class));

      case "front_side":
        return new PassportElementErrorFrontSide(
            objectMapper.readValue(node.get("type").traverse(), String.class),
            objectMapper.readValue(node.get("fileHash").traverse(), String.class),
            objectMapper.readValue(node.get("message").traverse(), String.class));

      case "files":
        return new PassportElementErrorFiles(
            objectMapper.readValue(node.get("type").traverse(), String.class),
            objectMapper.readValue(
                node.get("fileHashes").traverse(), new TypeReference<List<String>>() {}),
            objectMapper.readValue(node.get("message").traverse(), String.class));

      case "unspecified":
        return new PassportElementErrorUnspecified(
            objectMapper.readValue(node.get("type").traverse(), String.class),
            objectMapper.readValue(node.get("elementHash").traverse(), String.class),
            objectMapper.readValue(node.get("message").traverse(), String.class));

      case "file":
        return new PassportElementErrorFile(
            objectMapper.readValue(node.get("type").traverse(), String.class),
            objectMapper.readValue(node.get("fileHash").traverse(), String.class),
            objectMapper.readValue(node.get("message").traverse(), String.class));

      case "selfie":
        return new PassportElementErrorSelfie(
            objectMapper.readValue(node.get("type").traverse(), String.class),
            objectMapper.readValue(node.get("fileHash").traverse(), String.class),
            objectMapper.readValue(node.get("message").traverse(), String.class));

      default:
        throw ctxt.weirdStringException(
            "type", PassportElementError.class, "Unknown discriminator");
    }
  }
}
