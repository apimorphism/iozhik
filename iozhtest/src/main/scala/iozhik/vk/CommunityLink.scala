package iozhik.vk

final case class CommunityLink(
                               /** идентификатор ссылки;*/
                               id: Integer,
                               /** URL;*/
                               url: String,
                               /** название ссылки;*/
                               name: String,
                               /** описание ссылки;*/
                               desc: String,
                               /** URL изображения-превью шириной 50px;*/
                               photo50: String,
                               /** URL изображения-превью шириной 100px.*/
                               photo100: String)
