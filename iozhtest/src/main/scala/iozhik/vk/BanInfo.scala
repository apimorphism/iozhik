package iozhik.vk

final case class BanInfo(
                         /** срок окончания блокировки в формате unixtime;*/
                         endDate: Integer,
                         /** комментарий к блокировке.*/
                         comment: String)
