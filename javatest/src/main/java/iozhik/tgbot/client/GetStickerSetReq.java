package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetStickerSetReq {

  private String name;

  public GetStickerSetReq() {}

  public GetStickerSetReq(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public GetStickerSetReq setName(String v) {
    this.name = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof GetStickerSetReq)) return false;

    GetStickerSetReq that = (GetStickerSetReq) thatObj;

    return this.name.equals(that.name);
  }

  @Override
  public String toString() {
    return "GetStickerSetReq{" + "name=" + '\'' + this.name + '\'' + '}';
  }
}
