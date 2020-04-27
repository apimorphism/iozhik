package iozhik.vk

final case class Reach(
                       /** полный охват;*/
                       reach: Integer,
                       /** охват подписчиков;*/
                       reachSubscribers: Int,
                       /** охват с мобильных устройств;*/
                       mobileReach: Int,
                       /** статистика по полу.*/
                       sex: Vector[SexStat],
                       /** статистика по возрасту*/
                       age: Vector[AgeStat],
                       /** статистика по полу и возрасту.*/
                       sexAge: Vector[SexAgeStat],
                       /** статистика по городам.*/
                       cities: Vector[CitiesStat],
                       /** статистика по странам.*/
                       countries: Vector[CountriesStat])
