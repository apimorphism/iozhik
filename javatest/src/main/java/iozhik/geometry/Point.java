package iozhik.geometry;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Point {

  private int x;

  private int y;

  public Point() {}

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return this.x;
  }

  public Point setX(int v) {
    this.x = v;
    return this;
  }

  public int getY() {
    return this.y;
  }

  public Point setY(int v) {
    this.y = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Point)) return false;

    Point that = (Point) thatObj;

    return this.x == that.x && this.y == that.y;
  }

  @Override
  public String toString() {
    return "Point{" + "x=" + this.x + ',' + "y=" + this.y + '}';
  }
}
