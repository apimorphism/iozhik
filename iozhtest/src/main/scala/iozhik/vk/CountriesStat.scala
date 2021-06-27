package iozhik.vk

/** @param name
  *   название страны;
  * @param code
  *   двухбуквенный код страны (например, "RU");
  * @param countryId
  *   идентификатор страны;
  * @param count
  *   число посетителей.
  */
final case class CountriesStat(name: String, code: String, countryId: Int, count: Int)
