package iozhik.vk

final case class CitiesStat(
                            /** название города;*/
                            name: String,
                            /** идентификатор города или "other" для раздела «прочие города»;*/
                            cityId: String,
                            /** число посетителей.*/
                            count: Int)
