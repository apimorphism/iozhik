package iozhik.vk

sealed trait PlatformType {}

case object Android extends PlatformType

case object Iphone extends PlatformType

case object Wphone extends PlatformType
