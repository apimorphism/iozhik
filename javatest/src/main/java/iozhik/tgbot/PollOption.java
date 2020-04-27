package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PollOption {

  private String text;

  private int voterCount;

  public PollOption() {}

  public PollOption(String text, int voterCount) {
    this.text = text;
    this.voterCount = voterCount;
  }

  public String getText() {
    return this.text;
  }

  public PollOption setText(String v) {
    this.text = v;
    return this;
  }

  public int getVoterCount() {
    return this.voterCount;
  }

  public PollOption setVoterCount(int v) {
    this.voterCount = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof PollOption)) return false;

    PollOption that = (PollOption) thatObj;

    return this.text.equals(that.text) && this.voterCount == that.voterCount;
  }

  @Override
  public String toString() {
    return "PollOption{"
        + "text="
        + '\''
        + this.text
        + '\''
        + ','
        + "voterCount="
        + this.voterCount
        + '}';
  }
}
