package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SetChatTitleReq {

  private ChatId chatId;

  private String title;

  public SetChatTitleReq() {}

  public SetChatTitleReq(ChatId chatId, String title) {
    this.chatId = chatId;
    this.title = title;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public SetChatTitleReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public String getTitle() {
    return this.title;
  }

  public SetChatTitleReq setTitle(String v) {
    this.title = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SetChatTitleReq)) return false;

    SetChatTitleReq that = (SetChatTitleReq) thatObj;

    return this.chatId.equals(that.chatId) && this.title.equals(that.title);
  }

  @Override
  public String toString() {
    return "SetChatTitleReq{"
        + "chatId="
        + this.chatId
        + ','
        + "title="
        + '\''
        + this.title
        + '\''
        + '}';
  }
}
