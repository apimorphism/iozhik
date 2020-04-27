package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GameHighScore {

  private int position;

  private User user;

  private int score;

  public GameHighScore() {}

  public GameHighScore(int position, User user, int score) {
    this.position = position;
    this.user = user;
    this.score = score;
  }

  public int getPosition() {
    return this.position;
  }

  public GameHighScore setPosition(int v) {
    this.position = v;
    return this;
  }

  public User getUser() {
    return this.user;
  }

  public GameHighScore setUser(User v) {
    this.user = v;
    return this;
  }

  public int getScore() {
    return this.score;
  }

  public GameHighScore setScore(int v) {
    this.score = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof GameHighScore)) return false;

    GameHighScore that = (GameHighScore) thatObj;

    return this.position == that.position
        && this.user.equals(that.user)
        && this.score == that.score;
  }

  @Override
  public String toString() {
    return "GameHighScore{"
        + "position="
        + this.position
        + ','
        + "user="
        + this.user
        + ','
        + "score="
        + this.score
        + '}';
  }
}
