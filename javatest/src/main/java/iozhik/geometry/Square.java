package iozhik.geometry;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Square implements Figure {

  private int area;

  private int width;

  private int height;

  private int x;

  private int y;

  public Figures what() {
    return Figures.SQUARE;
  }

  public String getDiscriminator() {
    return "square";
  }

  public Square() {}

  public Square(int area, int width, int height, int x, int y) {
    this.area = area;
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
  }

  public int getArea() {
    return this.area;
  }

  public Square setArea(int v) {
    this.area = v;
    return this;
  }

  public int getWidth() {
    return this.width;
  }

  public Square setWidth(int v) {
    this.width = v;
    return this;
  }

  public int getHeight() {
    return this.height;
  }

  public Square setHeight(int v) {
    this.height = v;
    return this;
  }

  public int getX() {
    return this.x;
  }

  public Square setX(int v) {
    this.x = v;
    return this;
  }

  public int getY() {
    return this.y;
  }

  public Square setY(int v) {
    this.y = v;
    return this;
  }

  public boolean isSquare() {
    return true;
  }

  public Square asSquare() {
    return this;
  }

  public boolean isCircle() {
    return false;
  }

  public Circle asCircle() {
    throw new IllegalStateException("Not a $stName: " + this);
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

    if (!(thatObj instanceof Square)) return false;

    Square that = (Square) thatObj;

    return this.area == that.area
        && this.width == that.width
        && this.height == that.height
        && this.x == that.x
        && this.y == that.y;
  }

  @Override
  public String toString() {
    return "Square{"
        + "area="
        + this.area
        + ','
        + "width="
        + this.width
        + ','
        + "height="
        + this.height
        + ','
        + "x="
        + this.x
        + ','
        + "y="
        + this.y
        + '}';
  }
}
