package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SetStickerPositionInSetReq {

  private String sticker;

  private int position;

  public SetStickerPositionInSetReq() {}

  public SetStickerPositionInSetReq(String sticker, int position) {
    this.sticker = sticker;
    this.position = position;
  }

  public String getSticker() {
    return this.sticker;
  }

  public SetStickerPositionInSetReq setSticker(String v) {
    this.sticker = v;
    return this;
  }

  public int getPosition() {
    return this.position;
  }

  public SetStickerPositionInSetReq setPosition(int v) {
    this.position = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SetStickerPositionInSetReq)) return false;

    SetStickerPositionInSetReq that = (SetStickerPositionInSetReq) thatObj;

    return this.sticker.equals(that.sticker) && this.position == that.position;
  }

  @Override
  public String toString() {
    return "SetStickerPositionInSetReq{"
        + "sticker="
        + '\''
        + this.sticker
        + '\''
        + ','
        + "position="
        + this.position
        + '}';
  }
}
