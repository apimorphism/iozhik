package iozhik.vk

final case class Photo(
  id: Int,
  albumId: Int,
  ownerId: Int,
  userId: Int,
  text: String,
  date: Long,
  sizes: Vector[SizedPhoto],
  width: Option[Int] = Option.empty,
  height: Option[Int] = Option.empty
)
