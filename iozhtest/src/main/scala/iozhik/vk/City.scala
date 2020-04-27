package iozhik.vk

final case class City(
                      /** идентификатор города, который можно использовать для получения его названия с помощью метода database.getCitiesById;*/
                      id: Int,
                      /** название города.*/
                      title: String)
