package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AnswerCallbackQueryReq {

  private String callbackQueryId;

  private String text;

  private Boolean showAlert;

  private String url;

  private Integer cacheTime;

  public AnswerCallbackQueryReq() {}

  public AnswerCallbackQueryReq(
      String callbackQueryId, String text, Boolean showAlert, String url, Integer cacheTime) {
    this.callbackQueryId = callbackQueryId;
    this.text = text;
    this.showAlert = showAlert;
    this.url = url;
    this.cacheTime = cacheTime;
  }

  public String getCallbackQueryId() {
    return this.callbackQueryId;
  }

  public AnswerCallbackQueryReq setCallbackQueryId(String v) {
    this.callbackQueryId = v;
    return this;
  }

  public Optional<String> getText() {
    return Optional.ofNullable(text);
  }

  public AnswerCallbackQueryReq setText(String v) {
    this.text = v;
    return this;
  }

  public Optional<Boolean> getShowAlert() {
    return Optional.ofNullable(showAlert);
  }

  public AnswerCallbackQueryReq setShowAlert(Boolean v) {
    this.showAlert = v;
    return this;
  }

  public Optional<String> getUrl() {
    return Optional.ofNullable(url);
  }

  public AnswerCallbackQueryReq setUrl(String v) {
    this.url = v;
    return this;
  }

  public Optional<Integer> getCacheTime() {
    return Optional.ofNullable(cacheTime);
  }

  public AnswerCallbackQueryReq setCacheTime(Integer v) {
    this.cacheTime = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof AnswerCallbackQueryReq)) return false;

    AnswerCallbackQueryReq that = (AnswerCallbackQueryReq) thatObj;

    return this.callbackQueryId.equals(that.callbackQueryId)
        && this.text.equals(that.text)
        && this.showAlert.equals(that.showAlert)
        && this.url.equals(that.url)
        && this.cacheTime.equals(that.cacheTime);
  }

  @Override
  public String toString() {
    return "AnswerCallbackQueryReq{"
        + "callbackQueryId="
        + '\''
        + this.callbackQueryId
        + '\''
        + ','
        + "text="
        + '\''
        + this.text
        + '\''
        + ','
        + "showAlert="
        + this.showAlert
        + ','
        + "url="
        + '\''
        + this.url
        + '\''
        + ','
        + "cacheTime="
        + this.cacheTime
        + '}';
  }
}
