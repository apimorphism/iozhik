package iozhik.vk

final case class SexAgeStat(
                            /** пол и возрастной интервал (например, "f;12-18");*/
                            value: String,
                            /** число посетителей.*/
                            count: Integer)
