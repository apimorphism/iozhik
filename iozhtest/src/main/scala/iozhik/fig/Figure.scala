package iozhik.fig

sealed trait Figure {
  def sides: Int
}

final case class Square(area: Double, perimeter: Double, sides: Int, width: Int, height: Int)
    extends Figure

final case class Circle(area: Double, perimeter: Double, sides: Int, radius: Int) extends Figure

final case class Triangle(area: Double,
                          perimeter: Double,
                          sides: Int,
                          vertices: List[Point] = List.empty)
    extends Figure
