package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExportChatInviteLinkRes {

  private String result;

  private boolean ok;

  private String description;

  public ExportChatInviteLinkRes() {}

  public ExportChatInviteLinkRes(String result, boolean ok, String description) {
    this.result = result;
    this.ok = ok;
    this.description = description;
  }

  public Optional<String> getResult() {
    return Optional.ofNullable(result);
  }

  public ExportChatInviteLinkRes setResult(String v) {
    this.result = v;
    return this;
  }

  public boolean getOk() {
    return this.ok;
  }

  public ExportChatInviteLinkRes setOk(boolean v) {
    this.ok = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public ExportChatInviteLinkRes setDescription(String v) {
    this.description = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof ExportChatInviteLinkRes)) return false;

    ExportChatInviteLinkRes that = (ExportChatInviteLinkRes) thatObj;

    return this.result.equals(that.result)
        && this.ok == that.ok
        && this.description.equals(that.description);
  }

  @Override
  public String toString() {
    return "ExportChatInviteLinkRes{"
        + "result="
        + '\''
        + this.result
        + '\''
        + ','
        + "ok="
        + this.ok
        + ','
        + "description="
        + '\''
        + this.description
        + '\''
        + '}';
  }
}
