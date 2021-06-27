package iozhik.vk

/** @param id
  *   идентификатор страны, который можно использовать для получения ее названия с помощью метода
  *   database.getCountriesById;
  * @param title
  *   название страны.
  */
final case class Country(id: Int, title: String)
