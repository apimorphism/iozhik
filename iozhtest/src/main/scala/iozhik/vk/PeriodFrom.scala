package iozhik.vk

final case class PeriodFrom(
                            /** период начала отсчёта в формате YYYY-MM-DD.*/
                            periodFrom: String,
                            /** период окончания отсчёта в формате YYYY-MM-DD.*/
                            periodTo: String,
                            /** данные о посетителях и просмотрах*/
                            visitors: Visitors,
                            /** данные об охвате*/
                            reach: Reach)
