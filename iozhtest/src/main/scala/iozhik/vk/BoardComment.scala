package iozhik.vk

final case class BoardComment(id: Int, fromId: Int, date: Long, text: String, attachments: Vector[Attachment])
