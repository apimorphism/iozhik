package iozhik.vk

/** @param photo изображения для предпросмотра
  * @param graffiti данные о граффити
  * @param audioMessage данные об аудиосообщении */
final case class Preview(photo: PreviewPhoto, graffiti: Graffiti, audioMessage: AudioMessage)
