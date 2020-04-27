package iozhik.tgbot;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import iozhik.tgbot.deserializators.InputMediaDeserializer;

import java.util.*;

@JsonDeserialize(using = InputMediaDeserializer.class)
public interface InputMedia {

  enum InputMedias {
    INPUT_MEDIA_ANIMATION,
    INPUT_MEDIA_PHOTO,
    INPUT_MEDIA_VIDEO,
    INPUT_MEDIA_DOCUMENT,
    INPUT_MEDIA_AUDIO
  }

  InputMedias what();

  boolean isInputMediaAnimation();

  InputMediaAnimation asInputMediaAnimation();

  boolean isInputMediaPhoto();

  InputMediaPhoto asInputMediaPhoto();

  boolean isInputMediaVideo();

  InputMediaVideo asInputMediaVideo();

  boolean isInputMediaDocument();

  InputMediaDocument asInputMediaDocument();

  boolean isInputMediaAudio();

  InputMediaAudio asInputMediaAudio();
}
