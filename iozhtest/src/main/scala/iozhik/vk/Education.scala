package iozhik.vk

/** @param university
  *   идентификатор университета;
  * @param universityName
  *   название университета;
  * @param faculty
  *   идентификатор факультета;
  * @param facultyName
  *   название факультета;
  * @param graduation
  *   год окончания.
  */
final case class Education(
  university: Integer,
  universityName: String,
  faculty: Integer,
  facultyName: String,
  graduation: Integer
)
