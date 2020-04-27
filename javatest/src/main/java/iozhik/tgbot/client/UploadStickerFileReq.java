package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UploadStickerFileReq {

  private int userId;

  private IFile pngSticker;

  public UploadStickerFileReq() {}

  public UploadStickerFileReq(int userId, IFile pngSticker) {
    this.userId = userId;
    this.pngSticker = pngSticker;
  }

  public int getUserId() {
    return this.userId;
  }

  public UploadStickerFileReq setUserId(int v) {
    this.userId = v;
    return this;
  }

  public IFile getPngSticker() {
    return this.pngSticker;
  }

  public UploadStickerFileReq setPngSticker(IFile v) {
    this.pngSticker = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof UploadStickerFileReq)) return false;

    UploadStickerFileReq that = (UploadStickerFileReq) thatObj;

    return this.userId == that.userId && this.pngSticker.equals(that.pngSticker);
  }

  @Override
  public String toString() {
    return "UploadStickerFileReq{"
        + "userId="
        + this.userId
        + ','
        + "pngSticker="
        + this.pngSticker
        + '}';
  }
}
