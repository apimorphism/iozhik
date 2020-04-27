package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExportChatInviteLinkReq {

  private ChatId chatId;

  public ExportChatInviteLinkReq() {}

  public ExportChatInviteLinkReq(ChatId chatId) {
    this.chatId = chatId;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public ExportChatInviteLinkReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof ExportChatInviteLinkReq)) return false;

    ExportChatInviteLinkReq that = (ExportChatInviteLinkReq) thatObj;

    return this.chatId.equals(that.chatId);
  }

  @Override
  public String toString() {
    return "ExportChatInviteLinkReq{" + "chatId=" + this.chatId + '}';
  }
}
