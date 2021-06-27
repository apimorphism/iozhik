package iozhik.mat

sealed trait Material {
  def density: Double
}

final case class Plastic(plasticity: Double, density: Double, color: String) extends Material

final case class Metal(conductivity: Double, density: Double, valency: Int, strength: Double) extends Material

final case class Wood(colors: Vector[String], density: Double) extends Material
