package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DeleteStickerFromSetReq {

  private String sticker;

  public DeleteStickerFromSetReq() {}

  public DeleteStickerFromSetReq(String sticker) {
    this.sticker = sticker;
  }

  public String getSticker() {
    return this.sticker;
  }

  public DeleteStickerFromSetReq setSticker(String v) {
    this.sticker = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof DeleteStickerFromSetReq)) return false;

    DeleteStickerFromSetReq that = (DeleteStickerFromSetReq) thatObj;

    return this.sticker.equals(that.sticker);
  }

  @Override
  public String toString() {
    return "DeleteStickerFromSetReq{" + "sticker=" + '\'' + this.sticker + '\'' + '}';
  }
}
