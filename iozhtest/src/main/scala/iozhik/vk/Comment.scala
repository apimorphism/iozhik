package iozhik.vk

final case class Comment(
  id: Int,
  fromId: Int,
  date: Long,
  text: String,
  replyToUser: Int,
  replyToComment: Int,
  attachments: Vector[Attachment],
  parentsStack: Vector[Int],
  thread: CommentsThread
)
