package iozhik.vk

/** @param id идентификатор фона.
  * @param type тип фона. Возможные значения: gradient, tile.
  * @param angle (для type = gradient) угол градиента по оси X.
  * @param color HEX-код замещающего цвета (без #).
  * @param width (для type = tile) ширина плитки паттерна.
  * @param height (для type = tile) высота плитки паттерна.
  * @param images для type = tile) изображение плитки паттерна. Массив объектов изображений. */
final case class Background(id: Integer,
                            `type`: String,
                            angle: Integer,
                            color: String,
                            width: Integer,
                            height: Integer,
                            images: Vector[Photo],
                            points: Vector[Point])
