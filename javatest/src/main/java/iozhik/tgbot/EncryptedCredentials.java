package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EncryptedCredentials {

  private String data;

  private String hash;

  private String secret;

  public EncryptedCredentials() {}

  public EncryptedCredentials(String data, String hash, String secret) {
    this.data = data;
    this.hash = hash;
    this.secret = secret;
  }

  public String getData() {
    return this.data;
  }

  public EncryptedCredentials setData(String v) {
    this.data = v;
    return this;
  }

  public String getHash() {
    return this.hash;
  }

  public EncryptedCredentials setHash(String v) {
    this.hash = v;
    return this;
  }

  public String getSecret() {
    return this.secret;
  }

  public EncryptedCredentials setSecret(String v) {
    this.secret = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof EncryptedCredentials)) return false;

    EncryptedCredentials that = (EncryptedCredentials) thatObj;

    return this.data.equals(that.data)
        && this.hash.equals(that.hash)
        && this.secret.equals(that.secret);
  }

  @Override
  public String toString() {
    return "EncryptedCredentials{"
        + "data="
        + '\''
        + this.data
        + '\''
        + ','
        + "hash="
        + '\''
        + this.hash
        + '\''
        + ','
        + "secret="
        + '\''
        + this.secret
        + '\''
        + '}';
  }
}
