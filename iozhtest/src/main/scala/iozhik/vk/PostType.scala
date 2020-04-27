package iozhik.vk

sealed trait PostType {}

case object Post extends PostType

case object Copy extends PostType

case object Reply extends PostType

case object Postpone extends PostType

case object Suggest extends PostType
