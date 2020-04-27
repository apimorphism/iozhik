package iozhik.tgbot;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import iozhik.tgbot.deserializators.InputMessageContentDeserializer;

import java.util.*;

@JsonDeserialize(using = InputMessageContentDeserializer.class)
public interface InputMessageContent {

  enum InputMessageContents {
    INPUT_VENUE_MESSAGE_CONTENT,
    INPUT_CONTACT_MESSAGE_CONTENT,
    INPUT_LOCATION_MESSAGE_CONTENT,
    INPUT_TEXT_MESSAGE_CONTENT
  }

  InputMessageContents what();

  boolean isInputVenueMessageContent();

  InputVenueMessageContent asInputVenueMessageContent();

  boolean isInputContactMessageContent();

  InputContactMessageContent asInputContactMessageContent();

  boolean isInputLocationMessageContent();

  InputLocationMessageContent asInputLocationMessageContent();

  boolean isInputTextMessageContent();

  InputTextMessageContent asInputTextMessageContent();
}
