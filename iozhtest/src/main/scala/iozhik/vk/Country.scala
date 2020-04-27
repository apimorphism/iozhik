package iozhik.vk

final case class Country(
                         /** идентификатор страны, который можно использовать для получения ее названия с помощью метода database.getCountriesById;*/
                         id: Int,
                         /** название страны.*/
                         title: String)
