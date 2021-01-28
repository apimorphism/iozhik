package iozhik.vk

/** @param name название города;
  * @param cityId идентификатор города или "other" для раздела «прочие города»;
  * @param count число посетителей. */
final case class CitiesStat(name: String, cityId: String, count: Int)
