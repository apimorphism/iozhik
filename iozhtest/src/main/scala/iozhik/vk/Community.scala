package iozhik.vk

final case class Community(
                           /** Идентификатор сообщества*/
                           id: Integer,
                           /** Название сообщества*/
                           name: String,
                           /** короткий адрес, например, apiclub*/
                           screenName: String,
                           /** является ли сообщество закрытым 0 - открытое, 1 - закрытое, 2 - частное*/
                           isClosed: Integer,
                           /**
                             * * возвращается в случае, если сообщество удалено или заблокировано. Возможные значения:
                             * * deleted — сообщество удалено;
                             * * banned — сообщество заблокировано;
                             * */
                           deactivated: Option[String] = Option.empty,
                           /** информация о том, является ли текущий пользователь руководителем.*/
                           isAdmin: Boolean,
                           /** уровень полномочий текущего пользователя (если is_admin = 1):
                             * * 1 — модератор;
                             * * 2 — редактор;
                             * * 3 — администратор.
                             * */
                           adminLevel: Integer,
                           /** информация о том, является ли текущий пользователь участником.*/
                           isMember: Boolean,
                           /** информация о том, является ли текущий пользователь рекламодателем*/
                           isAdvertiser: Boolean,
                           /** идентификатор пользователя, который отправил приглашение в сообщество.*/
                           invitedBy: Integer,
                           /**
                             * * тип сообщества:
                             * * group — группа;
                             * * page — публичная страница;
                             * * event — мероприятие.
                             * */
                           `type`: String,
                           photo50: String,
                           photo100: String,
                           photo200: String,
                           /** строка тематики паблика. У групп возвращается строковое значение, открыта ли группа или нет, а у событий дата начала.*/
                           activity: Option[String] = Option.empty,
                           /** возрастное ограничение.
                             * * 1 — нет;
                             * * 2 — 16+;
                             * * 3 — 18+.*/
                           ageLimits: Int,
                           /** информация о занесении в черный список сообщества (поле возвращается только при запросе информации об одном сообществе).*/
                           banInfo: Option[BanInfo] = Option.empty,
                           /** информация о том, может ли текущий пользователь создать новое обсуждение в группе*/
                           canCreateTopic: Option[Boolean] = Option.empty,
                           /** информация о том, может ли текущий пользователь написать сообщение сообществу.*/
                           canMessage: Option[Boolean] = Option.empty,
                           /** информация о том, может ли текущий пользователь оставлять записи на стене сообщества*/
                           canPost: Option[Boolean] = Option.empty,
                           /** информация о том, разрешено ли видеть чужие записи на стене группы.*/
                           canSeeAllPosts: Option[Boolean] = Option.empty,
                           /** информация о том, может ли текущий пользователь загружать документы в группу*/
                           canUploadDoc: Option[Boolean] = Option.empty,
                           /** информация о том, может ли текущий пользователь загружать видеозаписи в группу*/
                           canUploadVideo: Option[Boolean] = Option.empty,
                           /** город, указанный в информации о сообществе.*/
                           city: Option[City] = Option.empty,
                           /** информация из блока контактов публичной страницы.*/
                           contacts: Option[CommunityContacts] = Option.empty,
                           /**
                             * * counters object	объект, содержащий счётчики сообщества, может включать любой набор из следующих полей: photos, albums, audios, videos, topics, docs.
                             * * Поле возвращается только при запросе данных об одном сообществе
                             *
                             * страна, указанная в информации о сообществе.*/
                           country: Option[Country] = Option.empty,
                           /** Обложка сообщества*/
                           cover: Option[Cover] = Option.empty,
                           /** возвращает данные о точках, по которым вырезаны профильная и миниатюрная фотографии сообщества.*/
                           cropPhoto: Option[CropPhoto] = Option.empty,
                           /** текст описания сообщества.*/
                           description: Option[String] = Option.empty,
                           /** идентификатор закрепленной записи. Получить дополнительные данные о записи можно методом wall.getById, передав в поле posts {group_id}_{post_id}.*/
                           fixedPost: Option[Int] = Option.empty,
                           /** информация о том, установлена ли у сообщества главная фотография.*/
                           hasPhoto: Option[Boolean] = Option.empty,
                           /** информация о том, находится ли сообщество в закладках у текущего пользователя*/
                           isFavorite: Option[Boolean] = Option.empty,
                           /** информация о том, скрыто ли сообщество из ленты новостей текущего пользователя*/
                           isHiddenFromFeed: Option[Boolean] = Option.empty,
                           /** информация о том, заблокированы ли сообщения от этого сообщества (для текущего пользователя)*/
                           isMessagesBlocked: Option[Boolean] = Option.empty,
                           /** информация из блока ссылок сообщества.*/
                           links: Vector[CommunityLink],
                           /** идентификатор основного фотоальбота*/
                           mainAlbumId: Option[Int] = Option.empty,
                           /**
                             * * информация о главной секции. Возможные значения:
                             * *  0 — отсутствует;
                             * *  1 — фотографии;
                             * *  2 — обсуждения;
                             * *  3 — аудиозаписи;
                             * *  4 — видеозаписи;
                             * *  5 — товары.
                             * */
                           mainSection: Option[Int] = Option.empty,
                           /** Информация о магазине*/
                           market: Option[Market] = Option.empty,
                           /** статус участника текущего пользователя. Возможные значения:
                             * * 0 — не является участником;
                             * * 1 — является участником;
                             * * 2 — не уверен, что посетит мероприятие;
                             * * 3 — отклонил приглашение;
                             * * 4 — запрос на вступление отправлен;
                             * * 5 — приглашен.
                             * */
                           memberStatus: Option[Int] = Option.empty,
                           /** Количество участников в сообществе*/
                           membersCount: Option[Int] = Option.empty,
                           /** место, указанное в информации о сообществе*/
                           place: Option[Place] = Option.empty,
                           /** возвращается для публичных страниц. Текст описания для поля start_date.*/
                           publicDateLabel: Option[String] = Option.empty,
                           /** адрес сайта из поля «веб-сайт» в описании сообщества.*/
                           site: Option[String] = Option.empty,
                           /** для встреч содержат время начала и окончания встречи в формате unixtime. Для публичных страниц содержит только start_date — дата основания в формате YYYYMMDD.*/
                           startDate: Option[Int] = Option.empty,
                           finishDate: Option[Int] = Option.empty,
                           /** статус сообщества*/
                           status: Option[String] = Option.empty,
                           /** информация о том, есть ли у сообщества «огонёк».*/
                           trending: Option[Boolean] = Option.empty,
                           /** информация о том, верифицировано ли сообщество.*/
                           verified: Option[Boolean] = Option.empty,
                           /** стена. Возможные значения:
                             * * 0 — выключена;
                             * * 1 — открытая;
                             * * 2 — ограниченная;
                             * * 3 — закрытая.
                             * */
                           wall: Option[Int] = Option.empty,
                           /** название главной вики-страницы.*/
                           wikiPage: Option[String] = Option.empty)
