package iozhik.vk

/** Объект, описывающий опрос
  *
  * @param id идентификатор опроса для получения информации о нем через метод polls.getById.
  * @param ownerId идентификатор владельца опроса.
  * @param created дата создания в формате Unixtime.
  * @param question текст вопроса.
  * @param votes количество голосов.
  * @param answers массив объектов, которые содержат информацию о вариантах ответа
  * @param anonymous является ли опрос анонимным.
  * @param multiple допускает ли опрос выбор нескольких вариантов ответа.
  * @param answerIds идентификаторы вариантов ответа, выбранных текущим пользователем.
  * @param endDate дата завершения опроса в Unixtime. 0, если опрос бессрочный.
  * @param closed является ли опрос завершенным.
  * @param isBoard прикреплён ли опрос к обсуждению.
  * @param canEdit можно ли отредактировать опрос.
  * @param canVote можно ли проголосовать в опросе.
  * @param canReport можно ли пожаловаться на опрос.
  * @param canShare можно ли поделиться опросом.
  * @param authorId идентификатор автора опроса.
  * @param photo фотография — фон сниппета опроса. Объект фотографии.
  * @param background фон сниппета опроса
  * @param friends идентификаторы 3 друзей, которые проголосовали в опросе. */
final case class Poll(id: Int,
                      ownerId: Int,
                      created: Int,
                      question: String,
                      votes: Int,
                      answers: Vector[Answer],
                      anonymous: Boolean,
                      multiple: Boolean,
                      answerIds: Vector[Int],
                      endDate: Integer,
                      closed: Boolean,
                      isBoard: Boolean,
                      canEdit: Boolean,
                      canVote: Boolean,
                      canReport: Boolean,
                      canShare: Boolean,
                      authorId: Integer,
                      photo: Photo,
                      background: Background,
                      friends: Vector[Int])
