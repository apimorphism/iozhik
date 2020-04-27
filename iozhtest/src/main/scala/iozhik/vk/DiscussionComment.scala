package iozhik.vk

final case class DiscussionComment(id: Int,
                                   fromId: Int,
                                   date: Int,
                                   text: String,
                                   attachments: Vector[Attachment],
                                   likes: Likes)
