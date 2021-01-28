package iozhik.vk

/** @param userId идентификатор пользователя
  * @param desc должность
  * @param phone номер телефона */
final case class CommunityContacts(userId: Int, desc: String, phone: String, email: String)
