package iozhik.vk

/** @param duration
  *   длительность аудиосообщения в секундах;
  * @param waveform
  *   массив значений (integer) для визуального отображения звука;
  * @param linkOgg
  *   URL .ogg-файла;
  * @param linkMp3
  *   URL .mp3-файла.
  */
final case class AudioMessage(duration: Integer, waveform: Vector[Int], linkOgg: String, linkMp3: String)
