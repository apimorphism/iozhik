package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Response {

  private boolean ok;

  private String description;

  public Response() {}

  public Response(boolean ok, String description) {
    this.ok = ok;
    this.description = description;
  }

  public boolean getOk() {
    return this.ok;
  }

  public Response setOk(boolean v) {
    this.ok = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public Response setDescription(String v) {
    this.description = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Response)) return false;

    Response that = (Response) thatObj;

    return this.ok == that.ok && this.description.equals(that.description);
  }

  @Override
  public String toString() {
    return "Response{"
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
