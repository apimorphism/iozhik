package iozhik.vk

/** @param x координата X левого верхнего угла в процентах;
  * @param y координата Y левого верхнего угла в процентах;
  * @param x2 координата X правого нижнего угла в процентах;
  * @param y2 координата Y правого нижнего угла в процентах. */
final case class Crop(x: Integer, y: Integer, x2: Integer, y2: Integer)
