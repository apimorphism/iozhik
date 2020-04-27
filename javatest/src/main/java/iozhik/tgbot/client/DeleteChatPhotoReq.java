package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DeleteChatPhotoReq {

  private ChatId chatId;

  public DeleteChatPhotoReq() {}

  public DeleteChatPhotoReq(ChatId chatId) {
    this.chatId = chatId;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public DeleteChatPhotoReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof DeleteChatPhotoReq)) return false;

    DeleteChatPhotoReq that = (DeleteChatPhotoReq) thatObj;

    return this.chatId.equals(that.chatId);
  }

  @Override
  public String toString() {
    return "DeleteChatPhotoReq{" + "chatId=" + this.chatId + '}';
  }
}
