package iozhik.vk

/** @param src
  *   URL документа с граффити;
  * @param width
  *   ширина изображения в px;
  * @param height
  *   высота изображения в px.
  */
final case class Graffiti(src: String, width: Integer, height: Integer)
