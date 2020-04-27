package iozhik.vk

final case class Message(id: Int,
                         date: Long,
                         peerId: Int,
                         fromId: Int,
                         text: String,
                         randomId: Int,
                         ref: String,
                         refSource: String,
                         attachments: Vector[Attachment],
                         important: Boolean,
                         geo: Geo,
                         payload: String,
                         fwdMessages: Vector[Message],
                         replyMessage: Vector[Message],
                         action: Action)
