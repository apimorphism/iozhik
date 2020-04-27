package iozhik.vk

final case class PreviewPhoto(
    /** массив копий изображения в разных размерах.*/
    sizes: Vector[SizedPhoto])
