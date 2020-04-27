package iozhik.vk

final case class History(
                         /** идентификатор истории.*/
                         id: Integer,
                         /** идентификатор владельца истории.*/
                         ownerId: Integer,
                         /** дата добавления в Unixtime.*/
                         date: Integer,
                         /** дата в Unixtime, когда история истечёт.*/
                         expiresAt: Integer,
                         /** true, если срок хранения истории истёк.
                           * * В этом случае объект истории содержит только поля id, owner_id, date, expires_at, is_expired.
                           * */
                         isExpired: Boolean,
                         /** true, если история удалена или не существует.
                           * * В этом случае объект истории содержит только поля id, owner_id, is_deleted.
                           * */
                         isDeleted: Boolean,
                         /** информация о том, может ли пользователь просмотреть историю (0 — нет, 1 — да).
                           * * Если can_see = 0, объект истории содержит только поля id, owner_id, date, expires_at, can_see, type.
                           * */
                         canSee: Boolean,
                         /** [1]	1, если история просмотрена текущим пользователем.*/
                         seen: Option[Integer] = Option.empty,
                         /**
                           * * тип истории. Возможные значения:
                           * * photo — фотография;
                           * * video — видеозапись.
                           * */
                         `type`: String,
                         /** (для type = photo). Фотография из истории. Поля объекта описаны на отдельной странице.*/
                         photo: Option[Photo] = Option.empty,
                         /** (для type = video). Видео из истории. Поля объекта описаны на отдельной странице.*/
                         video: Option[HistoryVideo] = Option.empty,
                         /** ссылка для перехода из истории.*/
                         link: Link,
                         /** идентификатор пользователя, загрузившего историю, ответом на которую является текущая.*/
                         parentStoryOwnerId: Integer,
                         /** идентификатор истории, ответом на которую является текущая.*/
                         parentStoryId: Integer,
                         /** объект родительской истории.*/
                         parentStory: History,
                         /** информация об ответах на текущую историю*/
                         replies: HistoryReplies,
                         /** информация о том, может ли пользователь ответить на историю (0 — нет, 1 — да).*/
                         canReply: Boolean,
                         /** информация о том, может ли пользователь расшарить историю (0 — нет, 1 — да).*/
                         canShare: Boolean,
                         /** информация о том, может ли пользователь комментировать историю (0 — нет, 1 — да).*/
                         canComment: Boolean,
                         /** число просмотров.*/
                         views: Integer,
                         /** ключ доступа для приватного объекта.*/
                         accessKey: String)
