package iozhik.vk

final case class Counters(
                          /** количество фотоальбомов;*/
                          albums: Integer,
                          /** количество видеозаписей;*/
                          videos: Integer,
                          /** количество аудиозаписей;*/
                          audios: Integer,
                          /** количество фотографий;*/
                          photos: Integer,
                          /** количество заметок;*/
                          notes: Integer,
                          /** количество друзей;*/
                          friends: Integer,
                          /** количество сообществ;*/
                          groups: Integer,
                          /** количество друзей онлайн;*/
                          onlineFriends: Integer,
                          /** количество общих друзей;*/
                          mutualFriends: Integer,
                          /** количество видеозаписей с пользователем;*/
                          userVideos: Integer,
                          /** количество подписчиков;*/
                          followers: Integer,
                          /** количество объектов в блоке «Интересные страницы».*/
                          pages: Integer)
