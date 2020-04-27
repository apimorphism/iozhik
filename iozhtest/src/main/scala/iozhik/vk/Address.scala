package iozhik.vk

final case class Address(
                         /** идентификатор адреса.*/
                         id: Integer,
                         /** идентификатор страны.*/
                         countryId: Int,
                         /** идентификатор города.*/
                         cityId: Int,
                         /** заголовок адреса.*/
                         title: String,
                         /** строка адреса.*/
                         address: String,
                         /** описание адреса.*/
                         additionalAddress: String,
                         /** географическая широта отметки, заданная в градусах (от -90 до 90).*/
                         latitude: Double,
                         /** географическая долгота отметки, заданная в градусах (от -180 до 180).*/
                         longitude: Double,
                         /** идентификатор станции метрополитена.*/
                         metroStationId: Int,
                         /** тип расписания
                           * * noInformation — нет информации о расписании;
                           * * temporarily_closed — временно закрыто;
                           * * always_opened — открыто круглосуточно;
                           * * forever_closed — закрыто навсегда;
                           * * timetable — открыто в указанные часы работы. Для этого типа расписания необходимо передать параметр timetable;
                           * */
                         workInfoStatus: String,
                         /** для типа расписания timetable можно передать расписание в формате json. Время передается в минутах от 0 часов. Ключ по дню означает, что день рабочий. open_time, close_time — начало и конец рабочего дня. break_open_time, break_close_time - время перерыва.*/
                         timetable: String)
