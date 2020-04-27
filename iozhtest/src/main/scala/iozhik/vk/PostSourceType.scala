package iozhik.vk

sealed trait PostSourceType {}

case object Vk extends PostSourceType

case object Widget extends PostSourceType

case object Api extends PostSourceType

case object Rss extends PostSourceType

case object Sms extends PostSourceType
