package iozhik.vk

final case class Product(
  id: Int,
  ownerId: Int,
  title: String,
  description: String,
  price: Price,
  category: Category,
  thumbPhoto: String,
  date: Int,
  availability: Int,
  isFavorite: Boolean,
  photos: Vector[Photo],
  canComment: Boolean,
  canRepost: Boolean,
  likes: Likes,
  url: String,
  buttonTitle: String
)
