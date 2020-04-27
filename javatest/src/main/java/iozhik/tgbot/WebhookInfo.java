package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WebhookInfo {

  private String url;

  private boolean hasCustomCertificate;

  private int pendingUpdateCount;

  private Integer lastErrorDate;

  private String lastErrorMessage;

  private Integer maxConnections;

  private List<String> allowedUpdates;

  public WebhookInfo() {}

  public WebhookInfo(
      String url,
      boolean hasCustomCertificate,
      int pendingUpdateCount,
      Integer lastErrorDate,
      String lastErrorMessage,
      Integer maxConnections,
      List<String> allowedUpdates) {
    this.url = url;
    this.hasCustomCertificate = hasCustomCertificate;
    this.pendingUpdateCount = pendingUpdateCount;
    this.lastErrorDate = lastErrorDate;
    this.lastErrorMessage = lastErrorMessage;
    this.maxConnections = maxConnections;
    this.allowedUpdates = allowedUpdates;
  }

  public String getUrl() {
    return this.url;
  }

  public WebhookInfo setUrl(String v) {
    this.url = v;
    return this;
  }

  public boolean getHasCustomCertificate() {
    return this.hasCustomCertificate;
  }

  public WebhookInfo setHasCustomCertificate(boolean v) {
    this.hasCustomCertificate = v;
    return this;
  }

  public int getPendingUpdateCount() {
    return this.pendingUpdateCount;
  }

  public WebhookInfo setPendingUpdateCount(int v) {
    this.pendingUpdateCount = v;
    return this;
  }

  public Optional<Integer> getLastErrorDate() {
    return Optional.ofNullable(lastErrorDate);
  }

  public WebhookInfo setLastErrorDate(Integer v) {
    this.lastErrorDate = v;
    return this;
  }

  public Optional<String> getLastErrorMessage() {
    return Optional.ofNullable(lastErrorMessage);
  }

  public WebhookInfo setLastErrorMessage(String v) {
    this.lastErrorMessage = v;
    return this;
  }

  public Optional<Integer> getMaxConnections() {
    return Optional.ofNullable(maxConnections);
  }

  public WebhookInfo setMaxConnections(Integer v) {
    this.maxConnections = v;
    return this;
  }

  public List<String> getAllowedUpdates() {
    return this.allowedUpdates;
  }

  public WebhookInfo setAllowedUpdates(List<String> v) {
    this.allowedUpdates = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof WebhookInfo)) return false;

    WebhookInfo that = (WebhookInfo) thatObj;

    return this.url.equals(that.url)
        && this.hasCustomCertificate == that.hasCustomCertificate
        && this.pendingUpdateCount == that.pendingUpdateCount
        && this.lastErrorDate.equals(that.lastErrorDate)
        && this.lastErrorMessage.equals(that.lastErrorMessage)
        && this.maxConnections.equals(that.maxConnections)
        && this.allowedUpdates.equals(that.allowedUpdates);
  }

  @Override
  public String toString() {
    return "WebhookInfo{"
        + "url="
        + '\''
        + this.url
        + '\''
        + ','
        + "hasCustomCertificate="
        + this.hasCustomCertificate
        + ','
        + "pendingUpdateCount="
        + this.pendingUpdateCount
        + ','
        + "lastErrorDate="
        + this.lastErrorDate
        + ','
        + "lastErrorMessage="
        + '\''
        + this.lastErrorMessage
        + '\''
        + ','
        + "maxConnections="
        + this.maxConnections
        + ','
        + "allowedUpdates="
        + this.allowedUpdates
        + '}';
  }
}
