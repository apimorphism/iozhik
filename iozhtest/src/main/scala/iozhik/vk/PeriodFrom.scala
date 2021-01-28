package iozhik.vk

/** @param periodFrom период начала отсчёта в формате YYYY-MM-DD.
  * @param periodTo период окончания отсчёта в формате YYYY-MM-DD.
  * @param visitors данные о посетителях и просмотрах
  * @param reach данные об охвате */
final case class PeriodFrom(periodFrom: String, periodTo: String, visitors: Visitors, reach: Reach)
