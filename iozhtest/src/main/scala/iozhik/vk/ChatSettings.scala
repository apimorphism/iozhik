package iozhik.vk

final case class ChatSettings(
  membersCount: Int,
  title: String,
  pinnedMessage: PinnedMessage,
  state: String,
  photo: ActionPhoto,
  activeIds: Vector[Int],
  isGroupChannel: Boolean
)
