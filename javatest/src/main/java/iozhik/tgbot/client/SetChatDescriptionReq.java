package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SetChatDescriptionReq {

  private ChatId chatId;

  private String description;

  public SetChatDescriptionReq() {}

  public SetChatDescriptionReq(ChatId chatId, String description) {
    this.chatId = chatId;
    this.description = description;
  }

  public ChatId getChatId() {
    return this.chatId;
  }

  public SetChatDescriptionReq setChatId(ChatId v) {
    this.chatId = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public SetChatDescriptionReq setDescription(String v) {
    this.description = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SetChatDescriptionReq)) return false;

    SetChatDescriptionReq that = (SetChatDescriptionReq) thatObj;

    return this.chatId.equals(that.chatId) && this.description.equals(that.description);
  }

  @Override
  public String toString() {
    return "SetChatDescriptionReq{"
        + "chatId="
        + this.chatId
        + ','
        + "description="
        + '\''
        + this.description
        + '\''
        + '}';
  }
}
