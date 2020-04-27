package iozhik.vk

final case class Military(
                          /** номер части;*/
                          unit: String,
                          /** идентификатор части в базе данных;*/
                          unitId: Integer,
                          /** идентификатор страны, в которой находится часть;*/
                          countryId: Integer,
                          /** год начала службы;*/
                          from: Integer,
                          /** год окончания службы.*/
                          until: Integer)
