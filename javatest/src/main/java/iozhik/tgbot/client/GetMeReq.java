package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetMeReq {

  public GetMeReq() {}

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    return (thatObj instanceof GetMeReq);
  }

  @Override
  public String toString() {
    return "GetMeReq{" + +'}';
  }
}
