package iozhik.vk

final case class CommunityContacts(
                                   /** идентификатор пользователя*/
                                   userId: Int,
                                   /** должность*/
                                   desc: String,
                                   /** номер телефона*/
                                   phone: String,
                                   email: String)
