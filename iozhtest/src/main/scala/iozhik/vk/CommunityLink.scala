package iozhik.vk

/** @param id идентификатор ссылки;
  * @param url URL;
  * @param name название ссылки;
  * @param desc описание ссылки;
  * @param photo50 URL изображения-превью шириной 50px;
  * @param photo100 URL изображения-превью шириной 100px. */
final case class CommunityLink(id: Integer,
                               url: String,
                               name: String,
                               desc: String,
                               photo50: String,
                               photo100: String)
