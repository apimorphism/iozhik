package iozhik.vk

final case class HistoryReplies(
                                /** число ответов;*/
                                count: Integer,
                                /**
                                  * * число новых ответов. Возвращается только для историй текущего пользователя.
                                  * * Получить истории-ответы можно методом stories.getReplies.
                                  * */
                                neww: Integer)
