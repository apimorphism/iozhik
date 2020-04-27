package iozhik.vk

final case class School(
                        /** идентификатор школы;*/
                        id: Integer,
                        /** идентификатор страны, в которой расположена школа;*/
                        country: Integer,
                        /** идентификатор города, в котором расположена школа;*/
                        city: Integer,
                        /** наименование школы*/
                        name: String,
                        /** год начала обучения;*/
                        yearFrom: Integer,
                        /** год окончания обучения;*/
                        yearTo: Integer,
                        /** год выпуска;*/
                        yearGraduated: Integer,
                        /** буква класса;*/
                        `class`: String,
                        /** специализация;*/
                        speciality: String,
                        /** идентификатор типа;*/
                        `type`: Integer,
                        /** название типа. Возможные значения для пар type-typeStr:*/
                        typeStr: String)
