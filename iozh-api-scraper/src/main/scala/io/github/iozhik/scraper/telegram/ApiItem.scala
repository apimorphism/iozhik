package io.github.iozhik.scraper.telegram

sealed trait ApiItem
case class Entity(name: String, desc: String, table: List[EntityParam]) extends ApiItem
case class Method(name: String, desc: String, table: List[MethodParam], returns: String) extends ApiItem
case class Sumtyp(name: String, desc: String, items: List[String], table: List[EntityParam] = List.empty) extends ApiItem

case class EntityParam(name: String, kind: String, desc: String)
case class EntityParamTag(name: String, value: String)
case class MethodParam(name: String, kind: String, required: String, desc: String)
