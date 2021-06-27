package iozhik.vk

final case class Personal(
  political: Int,
  langs: Vector[String],
  religion: String,
  inspiredBy: String,
  peopleMain: Int,
  lifeMain: Int,
  smoking: Int,
  alcohol: Int
)
