package iozhik.vk

/** @param enabled
  *   информация о том, включен ли блок товаров в сообществе
  * @param priceMin
  *   минимальная цена товаров;
  * @param priceMax
  *   максимальная цена товаров;
  * @param mainAlbumId
  *   идентификатор главной подборки товаров;
  * @param contactId
  *   идентификатор контактного лица для связи с продавцом. Возвращается отрицательное значение, если для связи с
  *   продавцом используются сообщения сообщества;
  * @param currency
  *   информация о валюте
  * @param currencyText
  *   строковое обозначение
  */
final case class Market(
  enabled: Boolean,
  priceMin: Int,
  priceMax: Int,
  mainAlbumId: Int,
  contactId: Int,
  currency: Currency,
  currencyText: String
)
