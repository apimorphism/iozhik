package iozhik.vk

/** @param mobilePhone
  *   номер мобильного телефона пользователя (только для Standalone-приложений);
  * @param homePhone
  *   дополнительный номер телефона пользователя.
  */
final case class Contacts(mobilePhone: String, homePhone: String)
