package iozhik.vk

/** @param id
  *   идентификатор города, который можно использовать для получения его названия с помощью метода
  *   database.getCitiesById;
  * @param title
  *   название города.
  */
final case class City(id: Int, title: String)
