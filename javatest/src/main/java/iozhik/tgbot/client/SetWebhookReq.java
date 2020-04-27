package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SetWebhookReq {

  private String url;

  private IFile certificate;

  private Integer maxConnections;

  private List<String> allowedUpdates;

  public SetWebhookReq() {}

  public SetWebhookReq(
      String url, IFile certificate, Integer maxConnections, List<String> allowedUpdates) {
    this.url = url;
    this.certificate = certificate;
    this.maxConnections = maxConnections;
    this.allowedUpdates = allowedUpdates;
  }

  public String getUrl() {
    return this.url;
  }

  public SetWebhookReq setUrl(String v) {
    this.url = v;
    return this;
  }

  public Optional<IFile> getCertificate() {
    return Optional.ofNullable(certificate);
  }

  public SetWebhookReq setCertificate(IFile v) {
    this.certificate = v;
    return this;
  }

  public Optional<Integer> getMaxConnections() {
    return Optional.ofNullable(maxConnections);
  }

  public SetWebhookReq setMaxConnections(Integer v) {
    this.maxConnections = v;
    return this;
  }

  public List<String> getAllowedUpdates() {
    return this.allowedUpdates;
  }

  public SetWebhookReq setAllowedUpdates(List<String> v) {
    this.allowedUpdates = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SetWebhookReq)) return false;

    SetWebhookReq that = (SetWebhookReq) thatObj;

    return this.url.equals(that.url)
        && this.certificate.equals(that.certificate)
        && this.maxConnections.equals(that.maxConnections)
        && this.allowedUpdates.equals(that.allowedUpdates);
  }

  @Override
  public String toString() {
    return "SetWebhookReq{"
        + "url="
        + '\''
        + this.url
        + '\''
        + ','
        + "certificate="
        + this.certificate
        + ','
        + "maxConnections="
        + this.maxConnections
        + ','
        + "allowedUpdates="
        + this.allowedUpdates
        + '}';
  }
}
