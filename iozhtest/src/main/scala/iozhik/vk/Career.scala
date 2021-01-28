package iozhik.vk

/** @param groupId идентификатор сообщества (если доступно, иначе company);
  * @param company название компании (если доступно, иначе group_id);
  * @param countryId идентификатор страны;
  * @param cityId идентификатор города (если доступно, иначе city_name);
  * @param cityName название города (если доступно, иначе city_id);
  * @param from год начала работы;
  * @param until год окончания работы;
  * @param position должность. */
final case class Career(groupId: Int,
                        company: String,
                        countryId: Int,
                        cityId: Int,
                        cityName: String,
                        from: Int,
                        until: Int,
                        position: String)
