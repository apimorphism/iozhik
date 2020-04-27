package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InputFile {

  public InputFile() {}

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    return (thatObj instanceof InputFile);
  }

  @Override
  public String toString() {
    return "InputFile{" + +'}';
  }
}
