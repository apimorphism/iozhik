package iozhik.vk

/** @param reach
  *   полный охват;
  * @param reachSubscribers
  *   охват подписчиков;
  * @param mobileReach
  *   охват с мобильных устройств;
  * @param sex
  *   статистика по полу.
  * @param age
  *   статистика по возрасту
  * @param sexAge
  *   статистика по полу и возрасту.
  * @param cities
  *   статистика по городам.
  * @param countries
  *   статистика по странам.
  */
final case class Reach(
  reach: Integer,
  reachSubscribers: Int,
  mobileReach: Int,
  sex: Vector[SexStat],
  age: Vector[AgeStat],
  sexAge: Vector[SexAgeStat],
  cities: Vector[CitiesStat],
  countries: Vector[CountriesStat]
)
