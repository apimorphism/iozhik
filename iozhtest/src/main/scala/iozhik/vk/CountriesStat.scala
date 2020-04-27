package iozhik.vk

final case class CountriesStat(
                               /** название страны;*/
                               name: String,
                               /** двухбуквенный код страны (например, "RU");*/
                               code: String,
                               /** идентификатор страны;*/
                               countryId: Int,
                               /** число посетителей.*/
                               count: Int)
