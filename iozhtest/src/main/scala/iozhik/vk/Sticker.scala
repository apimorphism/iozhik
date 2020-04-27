package iozhik.vk

final case class Sticker(productId: Int,
                         stickerId: Int,
                         images: Vector[StickerImage],
                         imagesWithBackground: Vector[StickerImage])
