package iozhik.vk

sealed trait Attachment {}

final case class AttachmentPhoto(photo: Photo) extends Attachment

final case class AttachmentVideo(video: Video) extends Attachment
