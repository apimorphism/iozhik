package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AddStickerToSetReq {

  private int userId;

  private String name;

  private IFile pngSticker;

  private String emojis;

  private MaskPosition maskPosition;

  public AddStickerToSetReq() {}

  public AddStickerToSetReq(
      int userId, String name, IFile pngSticker, String emojis, MaskPosition maskPosition) {
    this.userId = userId;
    this.name = name;
    this.pngSticker = pngSticker;
    this.emojis = emojis;
    this.maskPosition = maskPosition;
  }

  public int getUserId() {
    return this.userId;
  }

  public AddStickerToSetReq setUserId(int v) {
    this.userId = v;
    return this;
  }

  public String getName() {
    return this.name;
  }

  public AddStickerToSetReq setName(String v) {
    this.name = v;
    return this;
  }

  public IFile getPngSticker() {
    return this.pngSticker;
  }

  public AddStickerToSetReq setPngSticker(IFile v) {
    this.pngSticker = v;
    return this;
  }

  public String getEmojis() {
    return this.emojis;
  }

  public AddStickerToSetReq setEmojis(String v) {
    this.emojis = v;
    return this;
  }

  public Optional<MaskPosition> getMaskPosition() {
    return Optional.ofNullable(maskPosition);
  }

  public AddStickerToSetReq setMaskPosition(MaskPosition v) {
    this.maskPosition = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof AddStickerToSetReq)) return false;

    AddStickerToSetReq that = (AddStickerToSetReq) thatObj;

    return this.userId == that.userId
        && this.name.equals(that.name)
        && this.pngSticker.equals(that.pngSticker)
        && this.emojis.equals(that.emojis)
        && this.maskPosition.equals(that.maskPosition);
  }

  @Override
  public String toString() {
    return "AddStickerToSetReq{"
        + "userId="
        + this.userId
        + ','
        + "name="
        + '\''
        + this.name
        + '\''
        + ','
        + "pngSticker="
        + this.pngSticker
        + ','
        + "emojis="
        + '\''
        + this.emojis
        + '\''
        + ','
        + "maskPosition="
        + this.maskPosition
        + '}';
  }
}
