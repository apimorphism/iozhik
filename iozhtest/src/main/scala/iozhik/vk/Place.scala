package iozhik.vk

final case class Place(
  id: Int,
  title: String,
  latitude: Double,
  longitude: Double,
  created: Int,
  icon: String,
  country: String,
  city: String,
  `type`: Option[Int] = Option.empty,
  groupId: Option[Int] = Option.empty,
  groupPhoto: Option[String] = Option.empty,
  checkins: Option[Int] = Option.empty,
  updated: Option[Int] = Option.empty,
  address: Option[Int] = Option.empty
)
