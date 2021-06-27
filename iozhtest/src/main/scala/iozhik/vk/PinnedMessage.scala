package iozhik.vk

final case class PinnedMessage(
  id: Int,
  date: Int,
  fromId: Int,
  text: String,
  attachments: String,
  geo: Geo,
  fwdMessages: Vector[Message]
)
