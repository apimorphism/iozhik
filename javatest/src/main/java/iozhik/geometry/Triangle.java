package iozhik.geometry;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.geometry.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Triangle implements Figure {

  private int area;

  private Point a;

  private Point b;

  private Point c;

  public Figures what() {
    return Figures.TRIANGLE;
  }

  public String getDiscriminator() {
    return "triangle";
  }

  public Triangle() {}

  public Triangle(int area, Point a, Point b, Point c) {
    this.area = area;
    this.a = a;
    this.b = b;
    this.c = c;
  }

  public int getArea() {
    return this.area;
  }

  public Triangle setArea(int v) {
    this.area = v;
    return this;
  }

  public Point getA() {
    return this.a;
  }

  public Triangle setA(Point v) {
    this.a = v;
    return this;
  }

  public Point getB() {
    return this.b;
  }

  public Triangle setB(Point v) {
    this.b = v;
    return this;
  }

  public Point getC() {
    return this.c;
  }

  public Triangle setC(Point v) {
    this.c = v;
    return this;
  }

  public boolean isSquare() {
    return false;
  }

  public Square asSquare() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isCircle() {
    return false;
  }

  public Circle asCircle() {
    throw new IllegalStateException("Not a $stName: " + this);
  }

  public boolean isTriangle() {
    return true;
  }

  public Triangle asTriangle() {
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Triangle)) return false;

    Triangle that = (Triangle) thatObj;

    return this.area == that.area
        && this.a.equals(that.a)
        && this.b.equals(that.b)
        && this.c.equals(that.c);
  }

  @Override
  public String toString() {
    return "Triangle{"
        + "area="
        + this.area
        + ','
        + "a="
        + this.a
        + ','
        + "b="
        + this.b
        + ','
        + "c="
        + this.c
        + '}';
  }
}
