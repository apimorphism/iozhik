package iozhik.vk

/** @param albums
  *   количество фотоальбомов;
  * @param videos
  *   количество видеозаписей;
  * @param audios
  *   количество аудиозаписей;
  * @param photos
  *   количество фотографий;
  * @param notes
  *   количество заметок;
  * @param friends
  *   количество друзей;
  * @param groups
  *   количество сообществ;
  * @param onlineFriends
  *   количество друзей онлайн;
  * @param mutualFriends
  *   количество общих друзей;
  * @param userVideos
  *   количество видеозаписей с пользователем;
  * @param followers
  *   количество подписчиков;
  * @param pages
  *   количество объектов в блоке «Интересные страницы».
  */
final case class Counters(
  albums: Integer,
  videos: Integer,
  audios: Integer,
  photos: Integer,
  notes: Integer,
  friends: Integer,
  groups: Integer,
  onlineFriends: Integer,
  mutualFriends: Integer,
  userVideos: Integer,
  followers: Integer,
  pages: Integer
)
