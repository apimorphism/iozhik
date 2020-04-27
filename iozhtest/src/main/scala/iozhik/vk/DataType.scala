package iozhik.vk

sealed trait DataType {}

case object ProfileActivity extends DataType

case object ProfilePhoto extends DataType

case object Comments extends DataType

case object Like extends DataType

case object Poll extends DataType
