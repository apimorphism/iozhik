package iozhik.vk

final case class AudioMessage(
                              /** длительность аудиосообщения в секундах;*/
                              duration: Integer,
                              /** массив значений (integer) для визуального отображения звука;*/
                              waveform: Vector[Int],
                              /** URL .ogg-файла;*/
                              linkOgg: String,
                              /** URL .mp3-файла.*/
                              linkMp3: String)
