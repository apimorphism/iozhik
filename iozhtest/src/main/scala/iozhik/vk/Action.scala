package iozhik.vk

final case class Action(`type`: String,
                        memberId: Int,
                        text: String,
                        email: String,
                        photo: ActionPhoto)
