package iozhik.vk

final case class SexStat(
                         /** пол ("m" — мужской, "f" — женский);*/
                         value: String,
                         /** число посетителей.*/
                         count: Int)
