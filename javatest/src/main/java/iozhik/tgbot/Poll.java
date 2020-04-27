package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Poll {

  private String id;

  private String question;

  private List<PollOption> options;

  private boolean isClosed;

  public Poll() {}

  public Poll(String id, String question, List<PollOption> options, boolean isClosed) {
    this.id = id;
    this.question = question;
    this.options = options;
    this.isClosed = isClosed;
  }

  public String getId() {
    return this.id;
  }

  public Poll setId(String v) {
    this.id = v;
    return this;
  }

  public String getQuestion() {
    return this.question;
  }

  public Poll setQuestion(String v) {
    this.question = v;
    return this;
  }

  public List<PollOption> getOptions() {
    return this.options;
  }

  public Poll setOptions(List<PollOption> v) {
    this.options = v;
    return this;
  }

  public boolean getIsClosed() {
    return this.isClosed;
  }

  public Poll setIsClosed(boolean v) {
    this.isClosed = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Poll)) return false;

    Poll that = (Poll) thatObj;

    return this.id.equals(that.id)
        && this.question.equals(that.question)
        && this.options.equals(that.options)
        && this.isClosed == that.isClosed;
  }

  @Override
  public String toString() {
    return "Poll{"
        + "id="
        + '\''
        + this.id
        + '\''
        + ','
        + "question="
        + '\''
        + this.question
        + '\''
        + ','
        + "options="
        + this.options
        + ','
        + "isClosed="
        + this.isClosed
        + '}';
  }
}
