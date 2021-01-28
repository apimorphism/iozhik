package iozhik.vk

/** @param id идентификатор документа.
  * @param ownerId идентификатор пользователя, загрузившего документ.
  * @param title название документа.
  * @param size размер документа в байтах.
  * @param ext расширение документа.
  * @param url адрес документа, по которому его можно загрузить.
  * @param date дата добавления в формате Unixtime.
  * @param type
  * * тип документа. Возможные значения:
  * * 1 — текстовые документы;
  * * 2 — архивы;
  * * 3 — gif;
  * * 4 — изображения;
  * * 5 — аудио;
  * * 6 — видео;
  * * 7 — электронные книги;
  * * 8 — неизвестно.
  *
  * @param preview информация для предварительного просмотра документа */
final case class Document(id: Integer,
                          ownerId: Integer,
                          title: String,
                          size: Integer,
                          ext: String,
                          url: String,
                          date: Integer,
                          `type`: Int,
                          preview: Preview)
