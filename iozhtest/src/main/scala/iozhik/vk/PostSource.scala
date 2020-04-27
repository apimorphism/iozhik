package iozhik.vk

final case class PostSource(`type`: PostSourceType,
                            platform: PlatformType,
                            data: DataType,
                            url: String)
