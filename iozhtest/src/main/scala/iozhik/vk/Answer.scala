package iozhik.vk

/** @param id
  *   идентификатор ответа;
  * @param text
  *   текст ответа;
  * @param votes
  *   число проголосовавших за этот ответ;
  * @param rate
  *   рейтинг ответа.
  */
final case class Answer(id: Integer, text: String, votes: Integer, rate: Int)
