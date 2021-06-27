package iozhik.vk

final case class Chat(
  id: Int,
  `type`: String,
  title: String,
  adminId: Int,
  users: Vector[User],
  pushSettings: PushSettings,
  photo50: String,
  photo100: String,
  photo200: String,
  left: Boolean,
  kicked: Boolean
)
