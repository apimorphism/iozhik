package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DeleteChatStickerSetReq {

  private ChatId chatId;

  public DeleteChatStickerSetReq() {}

  public DeleteChatStickerSetReq(ChatId chatId) {
    this.chatId = chatId;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public DeleteChatStickerSetReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof DeleteChatStickerSetReq)) return false;

    DeleteChatStickerSetReq that = (DeleteChatStickerSetReq) thatObj;

    return this.chatId.equals(that.chatId);
  }

  @Override
  public String toString() {
    return "DeleteChatStickerSetReq{" + "chatId=" + this.chatId + '}';
  }
}
