package iozhik.vk

/** @param count число ответов;
  * @param neww
  * * число новых ответов. Возвращается только для историй текущего пользователя.
  * * Получить истории-ответы можно методом stories.getReplies.
  *  */
final case class HistoryReplies(count: Integer, neww: Integer)
