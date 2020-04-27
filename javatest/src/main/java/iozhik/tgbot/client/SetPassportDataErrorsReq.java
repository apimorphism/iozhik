package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SetPassportDataErrorsReq {

  private int userId;

  private List<PassportElementError> errors;

  public SetPassportDataErrorsReq() {}

  public SetPassportDataErrorsReq(int userId, List<PassportElementError> errors) {
    this.userId = userId;
    this.errors = errors;
  }

  public int getUserId() {
    return this.userId;
  }

  public SetPassportDataErrorsReq setUserId(int v) {
    this.userId = v;
    return this;
  }

  public List<PassportElementError> getErrors() {
    return this.errors;
  }

  public SetPassportDataErrorsReq setErrors(List<PassportElementError> v) {
    this.errors = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof SetPassportDataErrorsReq)) return false;

    SetPassportDataErrorsReq that = (SetPassportDataErrorsReq) thatObj;

    return this.userId == that.userId && this.errors.equals(that.errors);
  }

  @Override
  public String toString() {
    return "SetPassportDataErrorsReq{"
        + "userId="
        + this.userId
        + ','
        + "errors="
        + this.errors
        + '}';
  }
}
