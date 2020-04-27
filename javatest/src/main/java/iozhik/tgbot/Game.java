package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import iozhik.tgbot.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Game {

  private String title;

  private String description;

  private List<PhotoSize> photo;

  private String text;

  private List<MessageEntity> textEntities;

  private Animation animation;

  public Game() {}

  public Game(
      String title,
      String description,
      List<PhotoSize> photo,
      String text,
      List<MessageEntity> textEntities,
      Animation animation) {
    this.title = title;
    this.description = description;
    this.photo = photo;
    this.text = text;
    this.textEntities = textEntities;
    this.animation = animation;
  }

  public String getTitle() {
    return this.title;
  }

  public Game setTitle(String v) {
    this.title = v;
    return this;
  }

  public String getDescription() {
    return this.description;
  }

  public Game setDescription(String v) {
    this.description = v;
    return this;
  }

  public List<PhotoSize> getPhoto() {
    return this.photo;
  }

  public Game setPhoto(List<PhotoSize> v) {
    this.photo = v;
    return this;
  }

  public Optional<String> getText() {
    return Optional.ofNullable(text);
  }

  public Game setText(String v) {
    this.text = v;
    return this;
  }

  public List<MessageEntity> getTextEntities() {
    return this.textEntities;
  }

  public Game setTextEntities(List<MessageEntity> v) {
    this.textEntities = v;
    return this;
  }

  public Optional<Animation> getAnimation() {
    return Optional.ofNullable(animation);
  }

  public Game setAnimation(Animation v) {
    this.animation = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof Game)) return false;

    Game that = (Game) thatObj;

    return this.title.equals(that.title)
        && this.description.equals(that.description)
        && this.photo.equals(that.photo)
        && this.text.equals(that.text)
        && this.textEntities.equals(that.textEntities)
        && this.animation.equals(that.animation);
  }

  @Override
  public String toString() {
    return "Game{"
        + "title="
        + '\''
        + this.title
        + '\''
        + ','
        + "description="
        + '\''
        + this.description
        + '\''
        + ','
        + "photo="
        + this.photo
        + ','
        + "text="
        + '\''
        + this.text
        + '\''
        + ','
        + "textEntities="
        + this.textEntities
        + ','
        + "animation="
        + this.animation
        + '}';
  }
}
