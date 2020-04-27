package iozhik.vk

final case class Career(
                        /** идентификатор сообщества (если доступно, иначе company);*/
                        groupId: Int,
                        /** название компании (если доступно, иначе group_id);*/
                        company: String,
                        /** идентификатор страны;*/
                        countryId: Int,
                        /** идентификатор города (если доступно, иначе city_name);*/
                        cityId: Int,
                        /** название города (если доступно, иначе city_id);*/
                        cityName: String,
                        /** год начала работы;*/
                        from: Int,
                        /** год окончания работы;*/
                        until: Int,
                        /** должность.*/
                        position: String)
