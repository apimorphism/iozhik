package iozhik.vk

/** @param unit номер части;
  * @param unitId идентификатор части в базе данных;
  * @param countryId идентификатор страны, в которой находится часть;
  * @param from год начала службы;
  * @param until год окончания службы. */
final case class Military(unit: String,
                          unitId: Integer,
                          countryId: Integer,
                          from: Integer,
                          until: Integer)
