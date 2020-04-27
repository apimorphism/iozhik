package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PassportData {

  private List<EncryptedPassportElement> data;

  private EncryptedCredentials credentials;

  public PassportData() {}

  public PassportData(List<EncryptedPassportElement> data, EncryptedCredentials credentials) {
    this.data = data;
    this.credentials = credentials;
  }

  public List<EncryptedPassportElement> getData() {
    return this.data;
  }

  public PassportData setData(List<EncryptedPassportElement> v) {
    this.data = v;
    return this;
  }

  public EncryptedCredentials getCredentials() {
    return this.credentials;
  }

  public PassportData setCredentials(EncryptedCredentials v) {
    this.credentials = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof PassportData)) return false;

    PassportData that = (PassportData) thatObj;

    return this.data.equals(that.data) && this.credentials.equals(that.credentials);
  }

  @Override
  public String toString() {
    return "PassportData{" + "data=" + this.data + ',' + "credentials=" + this.credentials + '}';
  }
}
