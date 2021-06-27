package iozhik.vk

/** @param id
  *   Идентификатор сообщества
  * @param name
  *   Название сообщества
  * @param screenName
  *   короткий адрес, например, apiclub
  * @param isClosed
  *   является ли сообщество закрытым 0 - открытое, 1 - закрытое, 2 - частное
  * @param deactivated
  *   * возвращается в случае, если сообщество удалено или заблокировано. Возможные значения: * deleted — сообщество
  *   удалено; * banned — сообщество заблокировано;
  *
  * @param isAdmin
  *   информация о том, является ли текущий пользователь руководителем.
  * @param adminLevel
  *   уровень полномочий текущего пользователя (если is_admin = 1): * 1 — модератор; * 2 — редактор; * 3 —
  *   администратор.
  *
  * @param isMember
  *   информация о том, является ли текущий пользователь участником.
  * @param isAdvertiser
  *   информация о том, является ли текущий пользователь рекламодателем
  * @param invitedBy
  *   идентификатор пользователя, который отправил приглашение в сообщество.
  * @param type
  *   * тип сообщества: * group — группа; * page — публичная страница; * event — мероприятие.
  *
  * @param activity
  *   строка тематики паблика. У групп возвращается строковое значение, открыта ли группа или нет, а у событий дата
  *   начала.
  * @param ageLimits
  *   возрастное ограничение. * 1 — нет; * 2 — 16+; * 3 — 18+.
  * @param banInfo
  *   информация о занесении в черный список сообщества (поле возвращается только при запросе информации об одном
  *   сообществе).
  * @param canCreateTopic
  *   информация о том, может ли текущий пользователь создать новое обсуждение в группе
  * @param canMessage
  *   информация о том, может ли текущий пользователь написать сообщение сообществу.
  * @param canPost
  *   информация о том, может ли текущий пользователь оставлять записи на стене сообщества
  * @param canSeeAllPosts
  *   информация о том, разрешено ли видеть чужие записи на стене группы.
  * @param canUploadDoc
  *   информация о том, может ли текущий пользователь загружать документы в группу
  * @param canUploadVideo
  *   информация о том, может ли текущий пользователь загружать видеозаписи в группу
  * @param city
  *   город, указанный в информации о сообществе.
  * @param contacts
  *   информация из блока контактов публичной страницы.
  * @param country
  *   * counters object объект, содержащий счётчики сообщества, может включать любой набор из следующих полей: photos,
  *   albums, audios, videos, topics, docs. * Поле возвращается только при запросе данных об одном сообществе
  *
  * страна, указанная в информации о сообществе.
  * @param cover
  *   Обложка сообщества
  * @param cropPhoto
  *   возвращает данные о точках, по которым вырезаны профильная и миниатюрная фотографии сообщества.
  * @param description
  *   текст описания сообщества.
  * @param fixedPost
  *   идентификатор закрепленной записи. Получить дополнительные данные о записи можно методом wall.getById, передав в
  *   поле posts {group_id}_{post_id}.
  * @param hasPhoto
  *   информация о том, установлена ли у сообщества главная фотография.
  * @param isFavorite
  *   информация о том, находится ли сообщество в закладках у текущего пользователя
  * @param isHiddenFromFeed
  *   информация о том, скрыто ли сообщество из ленты новостей текущего пользователя
  * @param isMessagesBlocked
  *   информация о том, заблокированы ли сообщения от этого сообщества (для текущего пользователя)
  * @param links
  *   информация из блока ссылок сообщества.
  * @param mainAlbumId
  *   идентификатор основного фотоальбота
  * @param mainSection
  *   * информация о главной секции. Возможные значения: * 0 — отсутствует; * 1 — фотографии; * 2 — обсуждения; * 3 —
  *   аудиозаписи; * 4 — видеозаписи; * 5 — товары.
  *
  * @param market
  *   Информация о магазине
  * @param memberStatus
  *   статус участника текущего пользователя. Возможные значения: * 0 — не является участником; * 1 — является
  *   участником; * 2 — не уверен, что посетит мероприятие; * 3 — отклонил приглашение; * 4 — запрос на вступление
  *   отправлен; * 5 — приглашен.
  *
  * @param membersCount
  *   Количество участников в сообществе
  * @param place
  *   место, указанное в информации о сообществе
  * @param publicDateLabel
  *   возвращается для публичных страниц. Текст описания для поля start_date.
  * @param site
  *   адрес сайта из поля «веб-сайт» в описании сообщества.
  * @param startDate
  *   для встреч содержат время начала и окончания встречи в формате unixtime. Для публичных страниц содержит только
  *   start_date — дата основания в формате YYYYMMDD.
  * @param status
  *   статус сообщества
  * @param trending
  *   информация о том, есть ли у сообщества «огонёк».
  * @param verified
  *   информация о том, верифицировано ли сообщество.
  * @param wall
  *   стена. Возможные значения: * 0 — выключена; * 1 — открытая; * 2 — ограниченная; * 3 — закрытая.
  *
  * @param wikiPage
  *   название главной вики-страницы.
  */
final case class Community(
  id: Integer,
  name: String,
  screenName: String,
  isClosed: Integer,
  deactivated: Option[String] = Option.empty,
  isAdmin: Boolean,
  adminLevel: Integer,
  isMember: Boolean,
  isAdvertiser: Boolean,
  invitedBy: Integer,
  `type`: String,
  photo50: String,
  photo100: String,
  photo200: String,
  activity: Option[String] = Option.empty,
  ageLimits: Int,
  banInfo: Option[BanInfo] = Option.empty,
  canCreateTopic: Option[Boolean] = Option.empty,
  canMessage: Option[Boolean] = Option.empty,
  canPost: Option[Boolean] = Option.empty,
  canSeeAllPosts: Option[Boolean] = Option.empty,
  canUploadDoc: Option[Boolean] = Option.empty,
  canUploadVideo: Option[Boolean] = Option.empty,
  city: Option[City] = Option.empty,
  contacts: Option[CommunityContacts] = Option.empty,
  country: Option[Country] = Option.empty,
  cover: Option[Cover] = Option.empty,
  cropPhoto: Option[CropPhoto] = Option.empty,
  description: Option[String] = Option.empty,
  fixedPost: Option[Int] = Option.empty,
  hasPhoto: Option[Boolean] = Option.empty,
  isFavorite: Option[Boolean] = Option.empty,
  isHiddenFromFeed: Option[Boolean] = Option.empty,
  isMessagesBlocked: Option[Boolean] = Option.empty,
  links: Vector[CommunityLink],
  mainAlbumId: Option[Int] = Option.empty,
  mainSection: Option[Int] = Option.empty,
  market: Option[Market] = Option.empty,
  memberStatus: Option[Int] = Option.empty,
  membersCount: Option[Int] = Option.empty,
  place: Option[Place] = Option.empty,
  publicDateLabel: Option[String] = Option.empty,
  site: Option[String] = Option.empty,
  startDate: Option[Int] = Option.empty,
  finishDate: Option[Int] = Option.empty,
  status: Option[String] = Option.empty,
  trending: Option[Boolean] = Option.empty,
  verified: Option[Boolean] = Option.empty,
  wall: Option[Int] = Option.empty,
  wikiPage: Option[String] = Option.empty
)
