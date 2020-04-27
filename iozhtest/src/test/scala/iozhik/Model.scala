package iozhik

trait Model {

  import pet.{Cat, Dog}
  import fig.{Circle, Square, Triangle, Point}
  import bio.{Elephant, Dolphin}
  import mat.{Plastic, Metal, Wood}

  val tom = Cat("Tom")
  val pit = Dog("Pit")

  val circle = Circle(area = 10 * 10 * 3.14, perimeter = 2 * 10 * 3.14, sides = 0, radius = 10)
  val square = Square(area = 10 * 10, perimeter = 10 * 4, sides = 4, width = 10, height = 10)
  val triangle = Triangle(area = 100, perimeter = 3, sides = 3, vertices = List(Point(0, 0), Point(1, 1), Point(2, 2)))

  val elephant = Elephant
  val dolphin = Dolphin

  val plastic = Plastic(plasticity = 0.1, density = 0.8, color = "white")
  val metal = Metal(conductivity = 1.0, density = 2.0, valency = 2, strength = 0.1)
  val wood = Wood(colors = Vector("brown", "yellow"), density = 0.7)

}
