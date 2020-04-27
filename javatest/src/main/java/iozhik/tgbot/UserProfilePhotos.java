package iozhik.tgbot;

import java.util.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserProfilePhotos {

  private int totalCount;

  private List<List<PhotoSize>> photos;

  public UserProfilePhotos() {}

  public UserProfilePhotos(int totalCount, List<List<PhotoSize>> photos) {
    this.totalCount = totalCount;
    this.photos = photos;
  }

  public int getTotalCount() {
    return this.totalCount;
  }

  public UserProfilePhotos setTotalCount(int v) {
    this.totalCount = v;
    return this;
  }

  public List<List<PhotoSize>> getPhotos() {
    return this.photos;
  }

  public UserProfilePhotos setPhotos(List<List<PhotoSize>> v) {
    this.photos = v;
    return this;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (this == thatObj) return true;

    if (!(thatObj instanceof UserProfilePhotos)) return false;

    UserProfilePhotos that = (UserProfilePhotos) thatObj;

    return this.totalCount == that.totalCount && this.photos.equals(that.photos);
  }

  @Override
  public String toString() {
    return "UserProfilePhotos{"
        + "totalCount="
        + this.totalCount
        + ','
        + "photos="
        + this.photos
        + '}';
  }
}
