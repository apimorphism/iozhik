package iozhik.vk

final case class Education(
                           /** идентификатор университета;*/
                           university: Integer,
                           /** название университета;*/
                           universityName: String,
                           /** идентификатор факультета;*/
                           faculty: Integer,
                           /** название факультета;*/
                           facultyName: String,
                           /** год окончания.*/
                           graduation: Integer)
