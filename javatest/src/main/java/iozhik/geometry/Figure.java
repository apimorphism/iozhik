package iozhik.geometry;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import iozhik.geometry.deserializators.FigureDeserializer;

import java.util.*;

@JsonDeserialize(using = FigureDeserializer.class)
public interface Figure {

  enum Figures {
    SQUARE,
    CIRCLE,
    TRIANGLE
  }

  Figures what();

  String getDiscriminator();

  int getArea();

  Figure setArea(int v);

  boolean isSquare();

  Square asSquare();

  boolean isCircle();

  Circle asCircle();

  boolean isTriangle();

  Triangle asTriangle();
}
