package iozhik.vk

final case class Audio(id: Int,
                       ownerId: Int,
                       artist: String,
                       title: String,
                       duration: Int,
                       url: String,
                       lyricsId: Int,
                       albumId: Int,
                       genreId: Int,
                       date: Int,
                       noSearch: Option[Int] = Option.empty,
                       isHq: Option[Int] = Option.empty)
