package iozhik.vk

final case class User(
                      /** идентификатор пользователя.*/
                      id: Int,
                      /** имя*/
                      firstName: String,
                      /** фамилия.*/
                      lastName: String,
                      /** поле возвращается, если страница пользователя удалена или заблокирована, содержит значение deleted или banned. В этом случае опциональные поля не возвращаются.*/
                      deactivated: String,
                      /** скрыт ли профиль пользователя настройками приватности.*/
                      isClosed: Boolean,
                      /** может ли текущий пользователь видеть профиль при is_closed = 1 (например, он есть в друзьях).*/
                      canAccessClosed: Boolean,
                      /** содержимое поля «О себе» из профиля.*/
                      about: Option[String] = Option.empty,
                      /** содержимое поля «Деятельность» из профиля.*/
                      activities: Option[String] = Option.empty,
                      /** дата рождения. Возвращается в формате D.M.YYYY или D.M (если год рождения скрыт). Если дата рождения скрыта целиком, поле отсутствует в ответе.*/
                      bdate: Option[String] = Option.empty,
                      /** [0,1]	информация о том, находится ли текущий пользователь в черном списке. Возможные значения: Option[*/
                      blacklisted: Option[Boolean] = Option.empty,
                      /** [0,1]	информация о том, находится ли пользователь в черном списке у текущего пользователя. Возможные значения: Option[*/
                      blacklistedByMe: Option[Boolean] = Option.empty,
                      /** содержимое поля «Любимые книги» из профиля пользователя.*/
                      books: Option[String] = Option.empty,
                      /** [0,1]	информация о том, может ли текущий пользователь оставлять записи на стене.*/
                      canPost: Option[Boolean] = Option.empty,
                      /** [0,1]	информация о том, может ли текущий пользователь видеть чужие записи на стене.*/
                      canSeeAllPosts: Option[Boolean] = Option.empty,
                      /** [0,1]	информация о том, может ли текущий пользователь видеть аудиозаписи. Возможные значения: Option[*/
                      canSeeAudio: Option[Boolean] = Option.empty,
                      /** [0,1]	информация о том, будет ли отправлено уведомление пользователю о заявке в друзья от текущего пользователя. Возможные значения: Option[*/
                      canSendFriendRequest: Option[Boolean] = Option.empty,
                      /** [0,1]	информация о том, может ли текущий пользователь отправить личное сообщение. Возможные значения: Option[*/
                      can_write_private_message: Option[Boolean] = Option.empty,
                      /** информация о карьере пользователя. Объект, содержащий следующие поля:*/
                      career: Option[Career] = Option.empty,
                      /** информация о городе, указанном на странице пользователя в разделе «Контакты». Возвращаются следующие поля:*/
                      city: Option[City] = Option.empty,
                      /** количество общих друзей с текущим пользователем.*/
                      commonCount: Option[Int] = Option.empty,
                      /** возвращает данные об указанных в профиле сервисах пользователя, таких как: skype, facebook, twitter, livejournal, instagram. Для каждого сервиса возвращается отдельное поле с типом string, содержащее никнейм пользователя. Например, "instagram": "username".*/
                      skype: Option[String] = Option.empty,
                      facebook: Option[String] = Option.empty,
                      twitter: Option[String] = Option.empty,
                      livejournal: Option[String] = Option.empty,
                      instagram: Option[String] = Option.empty,
                      /** информация о телефонных номерах пользователя*/
                      contacts: Option[Contacts] = Option.empty,
                      /** количество различных объектов у пользователя. Поле возвращается только в методе users.get при запросе информации об одном пользователе, с передачей пользовательского access_token.*/
                      counters: Option[Counters] = Option.empty,
                      /** информация о стране, указанной на странице пользователя в разделе «Контакты».*/
                      country: Option[Country] = Option.empty,
                      /** возвращает данные о точках, по которым вырезаны профильная и миниатюрная фотографии пользователя, при наличии.*/
                      cropPhoto: Option[CropPhoto] = Option.empty,
                      /** короткий адрес страницы. Возвращается строка, содержащая короткий адрес страницы (например, andrew). Если он не назначен, возвращается "id"+user_id, например, id35828305.*/
                      domain: Option[String] = Option.empty,
                      /** информация о высшем учебном заведении пользователя.*/
                      education: Option[Education] = Option.empty,
                      /** имя в заданном падеже.*/
                      firstNameNom: Option[String] = Option.empty,
                      firstNameGen: Option[String] = Option.empty,
                      firstNameDat: Option[String] = Option.empty,
                      firstNameAcc: Option[String] = Option.empty,
                      firstNameIns: Option[String] = Option.empty,
                      firstNameAbl: Option[String] = Option.empty,
                      /** количество подписчиков пользователя.*/
                      followersCount: Option[Int] = Option.empty,
                      /** статус дружбы с пользователем. Возможные значения:
                        * 0 — не является другом,
                        * 1 — отправлена заявка/подписка пользователю,
                        * 2 — имеется входящая заявка/подписка от пользователя,
                        * 3 — является другом.*/
                      friendStatus: Option[Int] = Option.empty,
                      /** содержимое поля «Любимые игры» из профиля.*/
                      games: Option[String] = Option.empty,
                      /** информация о том, известен ли номер мобильного телефона пользователя. Возвращаемые значения: 1 — известен, 0 — не известен.*/
                      hasMobile: Option[Boolean] = Option.empty,
                      hasPhoto: Option[Boolean] = Option.empty,
                      homeTown: Option[String] = Option.empty,
                      interests: Option[String] = Option.empty,
                      isFavorite: Option[Boolean] = Option.empty,
                      isFriend: Option[Boolean] = Option.empty,
                      isHiddenFromFeed: Option[Boolean] = Option.empty,
                      /** фамилия в заданном падеже.*/
                      lastNameNom: Option[String] = Option.empty,
                      lastNameGen: Option[String] = Option.empty,
                      lastNameDat: Option[String] = Option.empty,
                      lastNameAcc: Option[String] = Option.empty,
                      lastNameIns: Option[String] = Option.empty,
                      lastNameAbl: Option[String] = Option.empty,
                      /** время последнего посещения. Объект, содержащий следующие поля:*/
                      lastSeen: Option[LastSeen] = Option.empty,
                      /** разделенные запятой идентификаторы списков друзей, в которых состоит пользователь. Поле доступно только для метода friends.get.*/
                      lists: Option[String] = Option.empty,
                      /** девичья фамилия*/
                      maidenName: Option[String] = Option.empty,
                      /** информация о военной службе пользователя*/
                      military: Option[Military] = Option.empty,
                      /** содержимое поля «Любимые фильмы» из профиля пользователя.*/
                      movies: Option[String] = Option.empty,
                      /** содержимое поля «Любимая музыка» из профиля пользователя.*/
                      music: Option[String] = Option.empty,
                      /** никнейм (отчество) пользователя.*/
                      nickname: Option[String] = Option.empty,
                      /** информация о текущем роде занятия пользователя.*/
                      occupation: Option[Occupation] = Option.empty,
                      /** информация о том, находится ли пользователь сейчас на сайте. Если пользователь использует мобильное приложение либо мобильную версию, возвращается дополнительное поле online_mobile, содержащее 1. При этом, если используется именно приложение, дополнительно возвращается поле online_app, содержащее его идентификатор.*/
                      online: Option[Boolean] = Option.empty,
                      onlineMobile: Option[Boolean] = Option.empty,
                      onlineApp: Option[String] = Option.empty,
                      /** информация о полях из раздела «Жизненная позиция».*/
                      personal: Option[Personal] = Option.empty,
                      /** url квадратной фотографии пользователя, имеющей ширину 50 пикселей. В случае отсутствия у пользователя фотографии возвращается https://vk.com/images/camera_50.png.*/
                      photo50: Option[String] = Option.empty,
                      photo100: Option[String] = Option.empty,
                      photo200orig: Option[String] = Option.empty,
                      photo200: Option[String] = Option.empty,
                      photo400orig: Option[String] = Option.empty,
                      /** строковый идентификатор главной фотографии профиля пользователя в формате {user_id}_{photo_id}, например, 6492_192164258. Обратите внимание, это поле может отсутствовать в ответе.*/
                      photoId: Option[String] = Option.empty,
                      photoMax: Option[String] = Option.empty,
                      photoMaxOrig: Option[String] = Option.empty,
                      /** любимые цитаты*/
                      quotes: Option[String] = Option.empty,
                      /** список родственников.*/
                      relatives: Option[Relative] = Option.empty,
                      /** семейное положение*/
                      relation: Option[Int] = Option.empty,
                      /** список школ, в которых учился пользователь.*/
                      schools: Option[School] = Option.empty,
                      /** короткое имя страницы.*/
                      screenName: Option[String] = Option.empty,
                      /** пол*/
                      sex: Option[Int] = Option.empty,
                      site: Option[String] = Option.empty,
                      status: Option[String] = Option.empty,
                      timezone: Option[Int] = Option.empty,
                      /** информация о том, есть ли на странице пользователя «огонёк».*/
                      trending: Option[Boolean] = Option.empty,
                      /** любимые телешоу*/
                      tv: Option[String] = Option.empty,
                      /** список вузов, в которых учился пользователь.*/
                      universities: Vector[University],
                      /** возвращается 1, если страница пользователя верифицирована, 0 — если нет.*/
                      verified: Option[Boolean] = Option.empty,
                      /** режим стены по умолчанию. Возможные значения: owner, all.*/
                      wallDefault: Option[String] = Option.empty)
