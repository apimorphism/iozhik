package iozhik.vk

final case class University(
                            /** идентификатор университета;*/
                            id: Integer,
                            /** идентификатор страны, в которой расположен университет;*/
                            country: Integer,
                            /** идентификатор города, в котором расположен университет;*/
                            city: Integer,
                            /** наименование университета;*/
                            name: String,
                            /** идентификатор факультета;*/
                            faculty: Integer,
                            /** наименование факультета;*/
                            facultyName: String,
                            /** идентификатор кафедры;*/
                            chair: Integer,
                            /** наименование кафедры;*/
                            chairName: String,
                            /** год окончания обучения;*/
                            graduation: Integer,
                            /** форма обучения;*/
                            educationForm: String,
                            /** статус (например, «Выпускник (специалист)»).*/
                            educationStatus: String)
