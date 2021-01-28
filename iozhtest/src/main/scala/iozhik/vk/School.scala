package iozhik.vk

/** @param id идентификатор школы;
  * @param country идентификатор страны, в которой расположена школа;
  * @param city идентификатор города, в котором расположена школа;
  * @param name наименование школы
  * @param yearFrom год начала обучения;
  * @param yearTo год окончания обучения;
  * @param yearGraduated год выпуска;
  * @param class буква класса;
  * @param speciality специализация;
  * @param type идентификатор типа;
  * @param typeStr название типа. Возможные значения для пар type-typeStr: */
final case class School(id: Integer,
                        country: Integer,
                        city: Integer,
                        name: String,
                        yearFrom: Integer,
                        yearTo: Integer,
                        yearGraduated: Integer,
                        `class`: String,
                        speciality: String,
                        `type`: Integer,
                        typeStr: String)
