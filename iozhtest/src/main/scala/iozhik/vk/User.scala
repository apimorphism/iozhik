package iozhik.vk

/** @param id
  *   идентификатор пользователя.
  * @param firstName
  *   имя
  * @param lastName
  *   фамилия.
  * @param deactivated
  *   поле возвращается, если страница пользователя удалена или заблокирована, содержит значение deleted или banned. В
  *   этом случае опциональные поля не возвращаются.
  * @param isClosed
  *   скрыт ли профиль пользователя настройками приватности.
  * @param canAccessClosed
  *   может ли текущий пользователь видеть профиль при is_closed = 1 (например, он есть в друзьях).
  * @param universities
  *   список вузов, в которых учился пользователь.
  * @param about
  *   содержимое поля «О себе» из профиля.
  * @param activities
  *   содержимое поля «Деятельность» из профиля.
  * @param bdate
  *   дата рождения. Возвращается в формате D.M.YYYY или D.M (если год рождения скрыт). Если дата рождения скрыта
  *   целиком, поле отсутствует в ответе.
  * @param blacklisted
  *   [0,1] информация о том, находится ли текущий пользователь в черном списке. Возможные значения: Option[
  * @param blacklistedByMe
  *   [0,1] информация о том, находится ли пользователь в черном списке у текущего пользователя. Возможные значения:
  *   Option[
  * @param books
  *   содержимое поля «Любимые книги» из профиля пользователя.
  * @param canPost
  *   [0,1] информация о том, может ли текущий пользователь оставлять записи на стене.
  * @param canSeeAllPosts
  *   [0,1] информация о том, может ли текущий пользователь видеть чужие записи на стене.
  * @param canSeeAudio
  *   [0,1] информация о том, может ли текущий пользователь видеть аудиозаписи. Возможные значения: Option[
  * @param canSendFriendRequest
  *   [0,1] информация о том, будет ли отправлено уведомление пользователю о заявке в друзья от текущего пользователя.
  *   Возможные значения: Option[
  * @param can_write_private_message
  *   [0,1] информация о том, может ли текущий пользователь отправить личное сообщение. Возможные значения: Option[
  * @param career
  *   информация о карьере пользователя. Объект, содержащий следующие поля:
  * @param city
  *   информация о городе, указанном на странице пользователя в разделе «Контакты». Возвращаются следующие поля:
  * @param commonCount
  *   количество общих друзей с текущим пользователем.
  * @param skype
  *   возвращает данные об указанных в профиле сервисах пользователя, таких как: skype, facebook, twitter, livejournal,
  *   instagram. Для каждого сервиса возвращается отдельное поле с типом string, содержащее никнейм пользователя.
  *   Например, "instagram": "username".
  * @param contacts
  *   информация о телефонных номерах пользователя
  * @param counters
  *   количество различных объектов у пользователя. Поле возвращается только в методе users.get при запросе информации
  *   об одном пользователе, с передачей пользовательского access_token.
  * @param country
  *   информация о стране, указанной на странице пользователя в разделе «Контакты».
  * @param cropPhoto
  *   возвращает данные о точках, по которым вырезаны профильная и миниатюрная фотографии пользователя, при наличии.
  * @param domain
  *   короткий адрес страницы. Возвращается строка, содержащая короткий адрес страницы (например, andrew). Если он не
  *   назначен, возвращается "id"+user_id, например, id35828305.
  * @param education
  *   информация о высшем учебном заведении пользователя.
  * @param firstNameNom
  *   имя в заданном падеже.
  * @param followersCount
  *   количество подписчиков пользователя.
  * @param friendStatus
  *   статус дружбы с пользователем. Возможные значения: 0 — не является другом, 1 — отправлена заявка/подписка
  *   пользователю, 2 — имеется входящая заявка/подписка от пользователя, 3 — является другом.
  * @param games
  *   содержимое поля «Любимые игры» из профиля.
  * @param hasMobile
  *   информация о том, известен ли номер мобильного телефона пользователя. Возвращаемые значения: 1 — известен, 0 — не
  *   известен.
  * @param lastNameNom
  *   фамилия в заданном падеже.
  * @param lastSeen
  *   время последнего посещения. Объект, содержащий следующие поля:
  * @param lists
  *   разделенные запятой идентификаторы списков друзей, в которых состоит пользователь. Поле доступно только для метода
  *   friends.get.
  * @param maidenName
  *   девичья фамилия
  * @param military
  *   информация о военной службе пользователя
  * @param movies
  *   содержимое поля «Любимые фильмы» из профиля пользователя.
  * @param music
  *   содержимое поля «Любимая музыка» из профиля пользователя.
  * @param nickname
  *   никнейм (отчество) пользователя.
  * @param occupation
  *   информация о текущем роде занятия пользователя.
  * @param online
  *   информация о том, находится ли пользователь сейчас на сайте. Если пользователь использует мобильное приложение
  *   либо мобильную версию, возвращается дополнительное поле online_mobile, содержащее 1. При этом, если используется
  *   именно приложение, дополнительно возвращается поле online_app, содержащее его идентификатор.
  * @param personal
  *   информация о полях из раздела «Жизненная позиция».
  * @param photo50
  *   url квадратной фотографии пользователя, имеющей ширину 50 пикселей. В случае отсутствия у пользователя фотографии
  *   возвращается https://vk.com/images/camera_50.png.
  * @param photoId
  *   строковый идентификатор главной фотографии профиля пользователя в формате {user_id}_{photo_id}, например,
  *   6492_192164258. Обратите внимание, это поле может отсутствовать в ответе.
  * @param quotes
  *   любимые цитаты
  * @param relatives
  *   список родственников.
  * @param relation
  *   семейное положение
  * @param schools
  *   список школ, в которых учился пользователь.
  * @param screenName
  *   короткое имя страницы.
  * @param sex
  *   пол
  * @param trending
  *   информация о том, есть ли на странице пользователя «огонёк».
  * @param tv
  *   любимые телешоу
  * @param verified
  *   возвращается 1, если страница пользователя верифицирована, 0 — если нет.
  * @param wallDefault
  *   режим стены по умолчанию. Возможные значения: owner, all.
  */
final case class User(
  id: Int,
  firstName: String,
  lastName: String,
  deactivated: String,
  isClosed: Boolean,
  canAccessClosed: Boolean,
  universities: Vector[University],
  about: Option[String] = Option.empty,
  activities: Option[String] = Option.empty,
  bdate: Option[String] = Option.empty,
  blacklisted: Option[Boolean] = Option.empty,
  blacklistedByMe: Option[Boolean] = Option.empty,
  books: Option[String] = Option.empty,
  canPost: Option[Boolean] = Option.empty,
  canSeeAllPosts: Option[Boolean] = Option.empty,
  canSeeAudio: Option[Boolean] = Option.empty,
  canSendFriendRequest: Option[Boolean] = Option.empty,
  can_write_private_message: Option[Boolean] = Option.empty,
  career: Option[Career] = Option.empty,
  city: Option[City] = Option.empty,
  commonCount: Option[Int] = Option.empty,
  skype: Option[String] = Option.empty,
  facebook: Option[String] = Option.empty,
  twitter: Option[String] = Option.empty,
  livejournal: Option[String] = Option.empty,
  instagram: Option[String] = Option.empty,
  contacts: Option[Contacts] = Option.empty,
  counters: Option[Counters] = Option.empty,
  country: Option[Country] = Option.empty,
  cropPhoto: Option[CropPhoto] = Option.empty,
  domain: Option[String] = Option.empty,
  education: Option[Education] = Option.empty,
  firstNameNom: Option[String] = Option.empty,
  firstNameGen: Option[String] = Option.empty,
  firstNameDat: Option[String] = Option.empty,
  firstNameAcc: Option[String] = Option.empty,
  firstNameIns: Option[String] = Option.empty,
  firstNameAbl: Option[String] = Option.empty,
  followersCount: Option[Int] = Option.empty,
  friendStatus: Option[Int] = Option.empty,
  games: Option[String] = Option.empty,
  hasMobile: Option[Boolean] = Option.empty,
  hasPhoto: Option[Boolean] = Option.empty,
  homeTown: Option[String] = Option.empty,
  interests: Option[String] = Option.empty,
  isFavorite: Option[Boolean] = Option.empty,
  isFriend: Option[Boolean] = Option.empty,
  isHiddenFromFeed: Option[Boolean] = Option.empty,
  lastNameNom: Option[String] = Option.empty,
  lastNameGen: Option[String] = Option.empty,
  lastNameDat: Option[String] = Option.empty,
  lastNameAcc: Option[String] = Option.empty,
  lastNameIns: Option[String] = Option.empty,
  lastNameAbl: Option[String] = Option.empty,
  lastSeen: Option[LastSeen] = Option.empty,
  lists: Option[String] = Option.empty,
  maidenName: Option[String] = Option.empty,
  military: Option[Military] = Option.empty,
  movies: Option[String] = Option.empty,
  music: Option[String] = Option.empty,
  nickname: Option[String] = Option.empty,
  occupation: Option[Occupation] = Option.empty,
  online: Option[Boolean] = Option.empty,
  onlineMobile: Option[Boolean] = Option.empty,
  onlineApp: Option[String] = Option.empty,
  personal: Option[Personal] = Option.empty,
  photo50: Option[String] = Option.empty,
  photo100: Option[String] = Option.empty,
  photo200orig: Option[String] = Option.empty,
  photo200: Option[String] = Option.empty,
  photo400orig: Option[String] = Option.empty,
  photoId: Option[String] = Option.empty,
  photoMax: Option[String] = Option.empty,
  photoMaxOrig: Option[String] = Option.empty,
  quotes: Option[String] = Option.empty,
  relatives: Option[Relative] = Option.empty,
  relation: Option[Int] = Option.empty,
  schools: Option[School] = Option.empty,
  screenName: Option[String] = Option.empty,
  sex: Option[Int] = Option.empty,
  site: Option[String] = Option.empty,
  status: Option[String] = Option.empty,
  timezone: Option[Int] = Option.empty,
  trending: Option[Boolean] = Option.empty,
  tv: Option[String] = Option.empty,
  verified: Option[Boolean] = Option.empty,
  wallDefault: Option[String] = Option.empty
)
