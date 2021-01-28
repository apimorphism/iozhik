package iozhik.vk

/** @param photo объект photo фотографии пользователя, из которой вырезается главное фото профиля.
  * @param crop вырезанная фотография пользователя
  * @param rect миниатюрная квадратная фотография, вырезанная из фотографии crop. Содержит набор полей, аналогичный объекту crop. */
final case class CropPhoto(photo: Photo, crop: Crop, rect: Crop)
