package iozhik.vk

final case class Conversation(peer: Peer,
                              inRead: Int,
                              outRead: Int,
                              unreadCount: Int,
                              important: Boolean,
                              unanswered: Boolean,
                              pushSettings: PushSettings,
                              canWrite: CanWrite,
                              chatSettings: ChatSettings)
