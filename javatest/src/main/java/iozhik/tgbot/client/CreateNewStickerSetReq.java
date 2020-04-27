package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateNewStickerSetReq {

  private int userId;

  private String name;

  private String title;

  private IFile pngSticker;

  private String emojis;

  private Boolean containsMasks;

  private MaskPosition maskPosition;

  public CreateNewStickerSetReq() {}

  public CreateNewStickerSetReq(
      int userId,
      String name,
      String title,
      IFile pngSticker,
      String emojis,
      Boolean containsMasks,
      MaskPosition maskPosition) {
    this.userId = userId;
    this.name = name;
    this.title = title;
    this.pngSticker = pngSticker;
    this.emojis = emojis;
    this.containsMasks = containsMasks;
    this.maskPosition = maskPosition;
  }

  public int getUserId() {
    return this.userId;
  }

  public CreateNewStickerSetReq setUserId(int v) {
    this.userId = v;
    return this;
  }

  public String getName() {
    return this.name;
  }

  public CreateNewStickerSetReq setName(String v) {
    this.name = v;
    return this;
  }

  public String getTitle() {
    return this.title;
  }

  public CreateNewStickerSetReq setTitle(String v) {
    this.title = v;
    return this;
  }

  public IFile getPngSticker() {
    return this.pngSticker;
  }

  public CreateNewStickerSetReq setPngSticker(IFile v) {
    this.pngSticker = v;
    return this;
  }

  public String getEmojis() {
    return this.emojis;
  }

  public CreateNewStickerSetReq setEmojis(String v) {
    this.emojis = v;
    return this;
  }

  public Optional<Boolean> getContainsMasks() {
    return Optional.ofNullable(containsMasks);
  }

  public CreateNewStickerSetReq setContainsMasks(Boolean v) {
    this.containsMasks = v;
    return this;
  }

  public Optional<MaskPosition> getMaskPosition() {
    return Optional.ofNullable(maskPosition);
  }

  public CreateNewStickerSetReq setMaskPosition(MaskPosition v) {
    this.maskPosition = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof CreateNewStickerSetReq)) return false;

    CreateNewStickerSetReq that = (CreateNewStickerSetReq) thatObj;

    return this.userId == that.userId
        && this.name.equals(that.name)
        && this.title.equals(that.title)
        && this.pngSticker.equals(that.pngSticker)
        && this.emojis.equals(that.emojis)
        && this.containsMasks.equals(that.containsMasks)
        && this.maskPosition.equals(that.maskPosition);
  }

  @Override
  public String toString() {
    return "CreateNewStickerSetReq{"
        + "userId="
        + this.userId
        + ','
        + "name="
        + '\''
        + this.name
        + '\''
        + ','
        + "title="
        + '\''
        + this.title
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
        + "containsMasks="
        + this.containsMasks
        + ','
        + "maskPosition="
        + this.maskPosition
        + '}';
  }
}
