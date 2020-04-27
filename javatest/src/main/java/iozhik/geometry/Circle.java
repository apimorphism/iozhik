package iozhik.geometry;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Circle implements Figure {

  private int area;

  private int radius;

  private int x;

  private int y;

  public Figures what() {
    return Figures.CIRCLE;
  }

  public String getDiscriminator() {
    return "circle";
  }

  public Circle() {}

  public Circle(int area, int radius, int x, int y) {
    this.area = area;
    this.radius = radius;
    this.x = x;
    this.y = y;
  }

  public int getArea() {
    return this.area;
  }

  public Circle setArea(int v) {
    this.area = v;
    return this;
  }

  public int getRadius() {
    return this.radius;
  }

  public Circle setRadius(int v) {
    this.radius = v;
    return this;
  }

  public int getX() {
    return this.x;
  }

  public Circle setX(int v) {
    this.x = v;
    return this;
  }

  public int getY() {
    return this.y;
  }

  public Circle setY(int v) {
    this.y = v;
    return this;
  }

  public boolean isSquare() {
    return false;
  }

  public Square asSquare() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isCircle() {
    return true;
  }

  public Circle asCircle() {
    return this;
  }

  public boolean isTriangle() {
    return false;
  }

  public Triangle asTriangle() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Circle)) return false;

    Circle that = (Circle) thatObj;

    return this.area == that.area
        && this.radius == that.radius
        && this.x == that.x
        && this.y == that.y;
  }

  @Override
  public String toString() {
    return "Circle{"
        + "area="
        + this.area
        + ','
        + "radius="
        + this.radius
        + ','
        + "x="
        + this.x
        + ','
        + "y="
        + this.y
        + '}';
  }
}
