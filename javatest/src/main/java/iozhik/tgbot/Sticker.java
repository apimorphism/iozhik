package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Sticker {

  private String fileId;

  private int width;

  private int height;

  private PhotoSize thumb;

  private String emoji;

  private String setName;

  private MaskPosition maskPosition;

  private Integer fileSize;

  public Sticker() {}

  public Sticker(
      String fileId,
      int width,
      int height,
      PhotoSize thumb,
      String emoji,
      String setName,
      MaskPosition maskPosition,
      Integer fileSize) {
    this.fileId = fileId;
    this.width = width;
    this.height = height;
    this.thumb = thumb;
    this.emoji = emoji;
    this.setName = setName;
    this.maskPosition = maskPosition;
    this.fileSize = fileSize;
  }

  public String getFileId() {
    return this.fileId;
  }

  public Sticker setFileId(String v) {
    this.fileId = v;
    return this;
  }

  public int getWidth() {
    return this.width;
  }

  public Sticker setWidth(int v) {
    this.width = v;
    return this;
  }

  public int getHeight() {
    return this.height;
  }

  public Sticker setHeight(int v) {
    this.height = v;
    return this;
  }

  public Optional<PhotoSize> getThumb() {
    return Optional.ofNullable(thumb);
  }

  public Sticker setThumb(PhotoSize v) {
    this.thumb = v;
    return this;
  }

  public Optional<String> getEmoji() {
    return Optional.ofNullable(emoji);
  }

  public Sticker setEmoji(String v) {
    this.emoji = v;
    return this;
  }

  public Optional<String> getSetName() {
    return Optional.ofNullable(setName);
  }

  public Sticker setSetName(String v) {
    this.setName = v;
    return this;
  }

  public Optional<MaskPosition> getMaskPosition() {
    return Optional.ofNullable(maskPosition);
  }

  public Sticker setMaskPosition(MaskPosition v) {
    this.maskPosition = v;
    return this;
  }

  public Optional<Integer> getFileSize() {
    return Optional.ofNullable(fileSize);
  }

  public Sticker setFileSize(Integer v) {
    this.fileSize = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Sticker)) return false;

    Sticker that = (Sticker) thatObj;

    return this.fileId.equals(that.fileId)
        && this.width == that.width
        && this.height == that.height
        && this.thumb.equals(that.thumb)
        && this.emoji.equals(that.emoji)
        && this.setName.equals(that.setName)
        && this.maskPosition.equals(that.maskPosition)
        && this.fileSize.equals(that.fileSize);
  }

  @Override
  public String toString() {
    return "Sticker{"
        + "fileId="
        + '\''
        + this.fileId
        + '\''
        + ','
        + "width="
        + this.width
        + ','
        + "height="
        + this.height
        + ','
        + "thumb="
        + this.thumb
        + ','
        + "emoji="
        + '\''
        + this.emoji
        + '\''
        + ','
        + "setName="
        + '\''
        + this.setName
        + '\''
        + ','
        + "maskPosition="
        + this.maskPosition
        + ','
        + "fileSize="
        + this.fileSize
        + '}';
  }
}
