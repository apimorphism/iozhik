package iozhik.geometry.deserializators;

import java.util.*;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import iozhik.geometry.*;

import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class FigureDeserializer extends StdDeserializer<Figure> {

  ObjectMapper objectMapper = new ObjectMapper();

  public FigureDeserializer() {
    this(null);
    objectMapper.registerModule(new Jdk8Module());
  }

  public FigureDeserializer(Class<Figure> t) {
    super(t);
    objectMapper.registerModule(new Jdk8Module());
  }

  @Override
  public Figure deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);

    String type = node.get("type").asText();

    switch (type) {
      case "square":
        return new Square(
            node.get("area").asInt(),
            node.get("width").asInt(),
            node.get("height").asInt(),
            node.get("x").asInt(),
            node.get("y").asInt());

      case "circle":
        return new Circle(
            node.get("area").asInt(),
            node.get("radius").asInt(),
            node.get("x").asInt(),
            node.get("y").asInt());

      case "triangle":
        return new Triangle(
            node.get("area").asInt(),
            objectMapper.readValue(node.get("a").traverse(), Point.class),
            objectMapper.readValue(node.get("b").traverse(), Point.class),
            objectMapper.readValue(node.get("c").traverse(), Point.class));

      default:
        throw ctxt.weirdStringException("type", Figure.class, "Unknown discriminator");
    }
  }
}
