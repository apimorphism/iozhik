package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SetChatStickerSetReq {

  private ChatId chatId;

  private String stickerSetName;

  public SetChatStickerSetReq() {}

  public SetChatStickerSetReq(ChatId chatId, String stickerSetName) {
    this.chatId = chatId;
    this.stickerSetName = stickerSetName;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public SetChatStickerSetReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public String getStickerSetName() {
    return this.stickerSetName;
  }

  public SetChatStickerSetReq setStickerSetName(String v) {
    this.stickerSetName = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SetChatStickerSetReq)) return false;

    SetChatStickerSetReq that = (SetChatStickerSetReq) thatObj;

    return this.chatId.equals(that.chatId) && this.stickerSetName.equals(that.stickerSetName);
  }

  @Override
  public String toString() {
    return "SetChatStickerSetReq{"
        + "chatId="
        + this.chatId
        + ','
        + "stickerSetName="
        + '\''
        + this.stickerSetName
        + '\''
        + '}';
  }
}
