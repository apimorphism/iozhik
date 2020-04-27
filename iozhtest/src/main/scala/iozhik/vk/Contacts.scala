package iozhik.vk

final case class Contacts(
                          /** номер мобильного телефона пользователя (только для Standalone-приложений);*/
                          mobilePhone: String,
                          /** дополнительный номер телефона пользователя.*/
                          homePhone: String)
