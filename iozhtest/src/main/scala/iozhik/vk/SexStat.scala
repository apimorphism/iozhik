package iozhik.vk

/** @param value
  *   пол ("m" — мужской, "f" — женский);
  * @param count
  *   число посетителей.
  */
final case class SexStat(value: String, count: Int)
