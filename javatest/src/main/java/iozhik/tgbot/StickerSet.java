package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StickerSet {

  private String name;

  private String title;

  private boolean containsMasks;

  private List<Sticker> stickers;

  public StickerSet() {}

  public StickerSet(String name, String title, boolean containsMasks, List<Sticker> stickers) {
    this.name = name;
    this.title = title;
    this.containsMasks = containsMasks;
    this.stickers = stickers;
  }

  public String getName() {
    return this.name;
  }

  public StickerSet setName(String v) {
    this.name = v;
    return this;
  }

  public String getTitle() {
    return this.title;
  }

  public StickerSet setTitle(String v) {
    this.title = v;
    return this;
  }

  public boolean getContainsMasks() {
    return this.containsMasks;
  }

  public StickerSet setContainsMasks(boolean v) {
    this.containsMasks = v;
    return this;
  }

  public List<Sticker> getStickers() {
    return this.stickers;
  }

  public StickerSet setStickers(List<Sticker> v) {
    this.stickers = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof StickerSet)) return false;

    StickerSet that = (StickerSet) thatObj;

    return this.name.equals(that.name)
        && this.title.equals(that.title)
        && this.containsMasks == that.containsMasks
        && this.stickers.equals(that.stickers);
  }

  @Override
  public String toString() {
    return "StickerSet{"
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
        + "containsMasks="
        + this.containsMasks
        + ','
        + "stickers="
        + this.stickers
        + '}';
  }
}
