package iozhik.vk

final case class Graffiti(
                          /** URL документа с граффити;*/
                          src: String,
                          /** ширина изображения в px;*/
                          width: Integer,
                          /** высота изображения в px.*/
                          height: Integer)
