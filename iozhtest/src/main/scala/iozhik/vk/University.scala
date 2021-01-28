package iozhik.vk

/** @param id идентификатор университета;
  * @param country идентификатор страны, в которой расположен университет;
  * @param city идентификатор города, в котором расположен университет;
  * @param name наименование университета;
  * @param faculty идентификатор факультета;
  * @param facultyName наименование факультета;
  * @param chair идентификатор кафедры;
  * @param chairName наименование кафедры;
  * @param graduation год окончания обучения;
  * @param educationForm форма обучения;
  * @param educationStatus статус (например, «Выпускник (специалист)»). */
final case class University(id: Integer,
                            country: Integer,
                            city: Integer,
                            name: String,
                            faculty: Integer,
                            facultyName: String,
                            chair: Integer,
                            chairName: String,
                            graduation: Integer,
                            educationForm: String,
                            educationStatus: String)
