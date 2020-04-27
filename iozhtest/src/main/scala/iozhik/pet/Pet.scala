package iozhik.pet

sealed trait Pet {
  def name: String
}

final case class Dog(name: String) extends Pet

final case class Cat(name: String) extends Pet
