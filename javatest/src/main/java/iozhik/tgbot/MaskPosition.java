package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MaskPosition {

  private String point;

  private Float xShift;

  private Float yShift;

  private Float scale;

  public MaskPosition() {}

  public MaskPosition(String point, Float xShift, Float yShift, Float scale) {
    this.point = point;
    this.xShift = xShift;
    this.yShift = yShift;
    this.scale = scale;
  }

  public String getPoint() {
    return this.point;
  }

  public MaskPosition setPoint(String v) {
    this.point = v;
    return this;
  }

  public Float getXShift() {
    return this.xShift;
  }

  public MaskPosition setXShift(Float v) {
    this.xShift = v;
    return this;
  }

  public Float getYShift() {
    return this.yShift;
  }

  public MaskPosition setYShift(Float v) {
    this.yShift = v;
    return this;
  }

  public Float getScale() {
    return this.scale;
  }

  public MaskPosition setScale(Float v) {
    this.scale = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof MaskPosition)) return false;

    MaskPosition that = (MaskPosition) thatObj;

    return this.point.equals(that.point)
        && this.xShift.equals(that.xShift)
        && this.yShift.equals(that.yShift)
        && this.scale.equals(that.scale);
  }

  @Override
  public String toString() {
    return "MaskPosition{"
        + "point="
        + '\''
        + this.point
        + '\''
        + ','
        + "xShift="
        + this.xShift
        + ','
        + "yShift="
        + this.yShift
        + ','
        + "scale="
        + this.scale
        + '}';
  }
}
