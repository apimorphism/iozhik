package iozhik.vk

/** @param value
  *   пол и возрастной интервал (например, "f;12-18");
  * @param count
  *   число посетителей.
  */
final case class SexAgeStat(value: String, count: Integer)
