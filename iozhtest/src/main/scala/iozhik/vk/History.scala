package iozhik.vk

/** @param id
  *   идентификатор истории.
  * @param ownerId
  *   идентификатор владельца истории.
  * @param date
  *   дата добавления в Unixtime.
  * @param expiresAt
  *   дата в Unixtime, когда история истечёт.
  * @param isExpired
  *   true, если срок хранения истории истёк. * В этом случае объект истории содержит только поля id, owner_id, date,
  *   expires_at, is_expired.
  *
  * @param isDeleted
  *   true, если история удалена или не существует. * В этом случае объект истории содержит только поля id, owner_id,
  *   is_deleted.
  *
  * @param canSee
  *   информация о том, может ли пользователь просмотреть историю (0 — нет, 1 — да). * Если can_see = 0, объект истории
  *   содержит только поля id, owner_id, date, expires_at, can_see, type.
  *
  * @param type
  *   * тип истории. Возможные значения: * photo — фотография; * video — видеозапись.
  *
  * @param link
  *   ссылка для перехода из истории.
  * @param parentStoryOwnerId
  *   идентификатор пользователя, загрузившего историю, ответом на которую является текущая.
  * @param parentStoryId
  *   идентификатор истории, ответом на которую является текущая.
  * @param parentStory
  *   объект родительской истории.
  * @param replies
  *   информация об ответах на текущую историю
  * @param canReply
  *   информация о том, может ли пользователь ответить на историю (0 — нет, 1 — да).
  * @param canShare
  *   информация о том, может ли пользователь расшарить историю (0 — нет, 1 — да).
  * @param canComment
  *   информация о том, может ли пользователь комментировать историю (0 — нет, 1 — да).
  * @param views
  *   число просмотров.
  * @param accessKey
  *   ключ доступа для приватного объекта.
  * @param seen
  *   [1] 1, если история просмотрена текущим пользователем.
  * @param photo
  *   (для type = photo). Фотография из истории. Поля объекта описаны на отдельной странице.
  * @param video
  *   (для type = video). Видео из истории. Поля объекта описаны на отдельной странице.
  */
final case class History(
  id: Integer,
  ownerId: Integer,
  date: Integer,
  expiresAt: Integer,
  isExpired: Boolean,
  isDeleted: Boolean,
  canSee: Boolean,
  `type`: String,
  link: Link,
  parentStoryOwnerId: Integer,
  parentStoryId: Integer,
  parentStory: History,
  replies: HistoryReplies,
  canReply: Boolean,
  canShare: Boolean,
  canComment: Boolean,
  views: Integer,
  accessKey: String,
  seen: Option[Integer] = Option.empty,
  photo: Option[Photo] = Option.empty,
  video: Option[HistoryVideo] = Option.empty
)
