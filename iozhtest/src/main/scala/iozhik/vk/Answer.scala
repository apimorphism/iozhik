package iozhik.vk

final case class Answer(
                        /** идентификатор ответа;*/
                        id: Integer,
                        /** текст ответа;*/
                        text: String,
                        /** число проголосовавших за этот ответ;*/
                        votes: Integer,
                        /** рейтинг ответа.*/
                        rate: Int)
