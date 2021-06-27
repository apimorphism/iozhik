package iozhik.vk

/** @param endDate
  *   срок окончания блокировки в формате unixtime;
  * @param comment
  *   комментарий к блокировке.
  */
final case class BanInfo(endDate: Integer, comment: String)
