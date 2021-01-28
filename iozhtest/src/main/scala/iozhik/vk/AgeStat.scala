package iozhik.vk

/** @param value возрастной интервал (например, "12-18"). Возможные значения: 12-18, 18-21, 21-24, 24-27, 27-30, 30-35, 35-45, 45-100.
  * @param count число посетителей; */
final case class AgeStat(value: String, count: Int)
