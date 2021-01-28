package iozhik.vk

/** @param id идентификатор адреса.
  * @param countryId идентификатор страны.
  * @param cityId идентификатор города.
  * @param title заголовок адреса.
  * @param address строка адреса.
  * @param additionalAddress описание адреса.
  * @param latitude географическая широта отметки, заданная в градусах (от -90 до 90).
  * @param longitude географическая долгота отметки, заданная в градусах (от -180 до 180).
  * @param metroStationId идентификатор станции метрополитена.
  * @param workInfoStatus тип расписания
  * * noInformation — нет информации о расписании;
  * * temporarily_closed — временно закрыто;
  * * always_opened — открыто круглосуточно;
  * * forever_closed — закрыто навсегда;
  * * timetable — открыто в указанные часы работы. Для этого типа расписания необходимо передать параметр timetable;
  *
  * @param timetable для типа расписания timetable можно передать расписание в формате json. Время передается в минутах от 0 часов. Ключ по дню означает, что день рабочий. open_time, close_time — начало и конец рабочего дня. break_open_time, break_close_time - время перерыва. */
final case class Address(id: Integer,
                         countryId: Int,
                         cityId: Int,
                         title: String,
                         address: String,
                         additionalAddress: String,
                         latitude: Double,
                         longitude: Double,
                         metroStationId: Int,
                         workInfoStatus: String,
                         timetable: String)
