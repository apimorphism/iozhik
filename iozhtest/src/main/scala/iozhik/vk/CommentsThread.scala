package iozhik.vk

final case class CommentsThread(count: Int,
                                items: Vector[Comment],
                                canPost: Boolean,
                                showReplyButton: Boolean,
                                groupsCanPost: Boolean)
