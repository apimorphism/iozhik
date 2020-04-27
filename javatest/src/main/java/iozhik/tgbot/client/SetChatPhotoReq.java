package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SetChatPhotoReq {

  private ChatId chatId;

  private IFile photo;

  public SetChatPhotoReq() {}

  public SetChatPhotoReq(ChatId chatId, IFile photo) {
    this.chatId = chatId;
    this.photo = photo;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public SetChatPhotoReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public IFile getPhoto() {
    return this.photo;
  }

  public SetChatPhotoReq setPhoto(IFile v) {
    this.photo = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SetChatPhotoReq)) return false;

    SetChatPhotoReq that = (SetChatPhotoReq) thatObj;

    return this.chatId.equals(that.chatId) && this.photo.equals(that.photo);
  }

  @Override
  public String toString() {
    return "SetChatPhotoReq{" + "chatId=" + this.chatId + ',' + "photo=" + this.photo + '}';
  }
}
