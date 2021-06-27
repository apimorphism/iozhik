package iozhik.vk

final case class Note(
  id: Int,
  ownerId: Int,
  title: String,
  text: String,
  date: Int,
  comments: Int,
  readComments: Int,
  viewUrl: String
)
