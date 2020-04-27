package iozhik.vk

final case class ProductCollection(id: Int,
                                   ownerId: Int,
                                   title: String,
                                   photo: Photo,
                                   count: Int,
                                   updatedTime: Int)
