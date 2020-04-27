package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetGameHighScoresRes {

  private List<GameHighScore> result;

  private boolean ok;

  private String description;

  public GetGameHighScoresRes() {}

  public GetGameHighScoresRes(List<GameHighScore> result, boolean ok, String description) {
    this.result = result;
    this.ok = ok;
    this.description = description;
  }

  public List<GameHighScore> getResult() {
    return this.result;
  }

  public GetGameHighScoresRes setResult(List<GameHighScore> v) {
    this.result = v;
    return this;
  }

  public boolean getOk() {
    return this.ok;
  }

  public GetGameHighScoresRes setOk(boolean v) {
    this.ok = v;
    return this;
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public GetGameHighScoresRes setDescription(String v) {
    this.description = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof GetGameHighScoresRes)) return false;

    GetGameHighScoresRes that = (GetGameHighScoresRes) thatObj;

    return this.result.equals(that.result)
        && this.ok == that.ok
        && this.description.equals(that.description);
  }

  @Override
  public String toString() {
    return "GetGameHighScoresRes{"
        + "result="
        + this.result
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
