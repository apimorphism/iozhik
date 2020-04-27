package iozhik.tgbot.client;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AnswerInlineQueryReq {

  private String inlineQueryId;

  private List<InlineQueryResult> results;

  private Integer cacheTime;

  private Boolean isPersonal;

  private String nextOffset;

  private String switchPmText;

  private String switchPmParameter;

  public AnswerInlineQueryReq() {}

  public AnswerInlineQueryReq(
      String inlineQueryId,
      List<InlineQueryResult> results,
      Integer cacheTime,
      Boolean isPersonal,
      String nextOffset,
      String switchPmText,
      String switchPmParameter) {
    this.inlineQueryId = inlineQueryId;
    this.results = results;
    this.cacheTime = cacheTime;
    this.isPersonal = isPersonal;
    this.nextOffset = nextOffset;
    this.switchPmText = switchPmText;
    this.switchPmParameter = switchPmParameter;
  }

  public String getInlineQueryId() {
    return this.inlineQueryId;
  }

  public AnswerInlineQueryReq setInlineQueryId(String v) {
    this.inlineQueryId = v;
    return this;
  }

  public List<InlineQueryResult> getResults() {
    return this.results;
  }

  public AnswerInlineQueryReq setResults(List<InlineQueryResult> v) {
    this.results = v;
    return this;
  }

  public Optional<Integer> getCacheTime() {
    return Optional.ofNullable(cacheTime);
  }

  public AnswerInlineQueryReq setCacheTime(Integer v) {
    this.cacheTime = v;
    return this;
  }

  public Optional<Boolean> getIsPersonal() {
    return Optional.ofNullable(isPersonal);
  }

  public AnswerInlineQueryReq setIsPersonal(Boolean v) {
    this.isPersonal = v;
    return this;
  }

  public Optional<String> getNextOffset() {
    return Optional.ofNullable(nextOffset);
  }

  public AnswerInlineQueryReq setNextOffset(String v) {
    this.nextOffset = v;
    return this;
  }

  public Optional<String> getSwitchPmText() {
    return Optional.ofNullable(switchPmText);
  }

  public AnswerInlineQueryReq setSwitchPmText(String v) {
    this.switchPmText = v;
    return this;
  }

  public Optional<String> getSwitchPmParameter() {
    return Optional.ofNullable(switchPmParameter);
  }

  public AnswerInlineQueryReq setSwitchPmParameter(String v) {
    this.switchPmParameter = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof AnswerInlineQueryReq)) return false;

    AnswerInlineQueryReq that = (AnswerInlineQueryReq) thatObj;

    return this.inlineQueryId.equals(that.inlineQueryId)
        && this.results.equals(that.results)
        && this.cacheTime.equals(that.cacheTime)
        && this.isPersonal.equals(that.isPersonal)
        && this.nextOffset.equals(that.nextOffset)
        && this.switchPmText.equals(that.switchPmText)
        && this.switchPmParameter.equals(that.switchPmParameter);
  }

  @Override
  public String toString() {
    return "AnswerInlineQueryReq{"
        + "inlineQueryId="
        + '\''
        + this.inlineQueryId
        + '\''
        + ','
        + "results="
        + this.results
        + ','
        + "cacheTime="
        + this.cacheTime
        + ','
        + "isPersonal="
        + this.isPersonal
        + ','
        + "nextOffset="
        + '\''
        + this.nextOffset
        + '\''
        + ','
        + "switchPmText="
        + '\''
        + this.switchPmText
        + '\''
        + ','
        + "switchPmParameter="
        + '\''
        + this.switchPmParameter
        + '\''
        + '}';
  }
}
