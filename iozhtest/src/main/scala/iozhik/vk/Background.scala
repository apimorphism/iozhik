package iozhik.vk

final case class Background(
                            /** идентификатор фона.*/
                            id: Integer,
                            /** тип фона. Возможные значения: gradient, tile.*/
                            `type`: String,
                            /** (для type = gradient) угол градиента по оси X.*/
                            angle: Integer,
                            /** HEX-код замещающего цвета (без #).*/
                            color: String,
                            /** (для type = tile) ширина плитки паттерна.*/
                            width: Integer,
                            /** (для type = tile) высота плитки паттерна.*/
                            height: Integer,
                            /** для type = tile) изображение плитки паттерна. Массив объектов изображений.*/
                            images: Vector[Photo],
                            points: Vector[Point])
