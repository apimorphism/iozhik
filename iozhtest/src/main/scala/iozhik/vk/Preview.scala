package iozhik.vk

final case class Preview(
                         /** изображения для предпросмотра*/
                         photo: PreviewPhoto,
                         /** данные о граффити*/
                         graffiti: Graffiti,
                         /** данные об аудиосообщении*/
                         audioMessage: AudioMessage)
