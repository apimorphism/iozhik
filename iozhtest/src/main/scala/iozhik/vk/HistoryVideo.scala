package iozhik.vk

/** @param firstFrame800
  *   (для type = video). Видео из истории. Поля объекта описаны на отдельной странице. Содержит дополнительные поля
  * @param isPrivate
  *   информация о том, приватная ли история
  */
final case class HistoryVideo(
  id: Int,
  ownerId: Int,
  title: String,
  description: String,
  duration: Int,
  photo130: String,
  photo320: String,
  photo640: String,
  photo800: String,
  photo1280: String,
  firstFrame130: String,
  firstFrame320: String,
  firstFrame640: String,
  firstFrame800: String,
  firstFrame1280: String,
  date: Long,
  addingDate: Long,
  views: Int,
  comments: Int,
  player: String,
  platform: String,
  canEdit: Option[Int] = Option.empty,
  canAdd: Int,
  isPrivate: Option[Int] = Option.empty,
  accessKey: Int,
  processing: Option[Int] = Option.empty,
  live: Option[Int] = Option.empty,
  upcoming: Option[Int] = Option.empty,
  isFavorite: Boolean,
  firstFrame800: String,
  firstFrame320: String,
  firstFrame160: String,
  firstFrame130: String,
  isPrivate: Boolean
)
