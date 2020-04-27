package io.github.iozhik

import atto._
import Atto._
import cats.syntax.apply._
import cats.syntax.functor._
import cats.syntax.foldable._
import cats.instances.list._
import cats.instances.string._

import scala.annotation.unchecked.uncheckedVariance

object Parser {

  object Model {
    final case class Version(inclusive: Boolean, version: String)

    sealed trait StrucItem extends Product with Serializable
    sealed trait SpaceItem extends Product with Serializable
    sealed trait ServcItem extends Product with Serializable

    final case class Exter(doc: String, name: Kind) extends SpaceItem with StrucItem
    final case class Impor(doc: String, name: Kind) extends SpaceItem with StrucItem
    final case class Alias(doc: String, alias: Kind, name: Kind) extends SpaceItem with StrucItem

    /** Represents a type, type constructor or a higher kind.
      *
      * Kind with empty params is a type.
      * Kind with non-empty params but with no nested kinds in them is a type constructor.
      * Any other kind is a real higher kind.
      */
    final case class Kind(name: String, params: List[Kind] = List.empty[Kind])

    final case class Mixin(doc: String, kind: Kind) extends StrucItem
    final case class Using(doc: String, kind: Kind) extends StrucItem
    final case class Embed(doc: String, name: String, struc: Struc) extends StrucItem
    final case class Field(doc: String, name: String, opts: List[String], kind: Kind) extends StrucItem
    final case class Typet(doc: String, name: String, opts: List[String], tag: String) extends StrucItem
    final case class Wrapp(doc: String, name: Kind, opts: List[String], target: Kind) extends StrucItem
    final case class EnumStr(doc: String, name: Kind, opts: List[String], target: String) extends StrucItem

    final case class Struc(
      doc: String,
      kind: Option[Kind],
      isAbstract: Boolean = false,
      isEnum: Boolean = false,
      minVersion: Option[Version] = Option.empty[Version],
      maxVersion: Option[Version] = Option.empty[Version],
      opts: List[String] = List.empty[String],
      items: List[StrucItem] = List.empty[StrucItem]
    ) extends SpaceItem with StrucItem

    final case class Defun(
      doc: String,
      kind: Kind,
      minVersion: Option[Version] = Option.empty[Version],
      maxVersion: Option[Version] = Option.empty[Version],
      opts: List[String] = List.empty[String],
      dom: Either[Kind, Struc],
      cod: Either[Kind, Struc],
    ) extends ServcItem

    final case class Servc(
      doc: String,
      kind: Kind,
      minVersion: Option[Version] = Option.empty[Version],
      maxVersion: Option[Version] = Option.empty[Version],
      opts: List[String] = List.empty[String],
      items: List[ServcItem],
    ) extends SpaceItem

    final case class Space(
      name: String,
      opts: List[String] = List.empty[String],
      items: List[SpaceItem] = List.empty[SpaceItem],
    ) extends SpaceItem
  }

  object Grammar {

    import Model._

    lazy val ws: Parser[Unit]             = skipWhitespace
    lazy val idenstart: Parser[Char]      = letter | char('_')
    lazy val identifier: Parser[String]   = (many1(idenstart), many(idenstart | digit)).mapN(_ ++ _).map(_.toList.mkString)
    lazy val pathTail: Parser[String]     = many((char('.'), identifier).mapN(_ +: _)).map(_.mkString)
    lazy val path: Parser[String]         = (identifier, pathTail).mapN(_ ++ _)
    lazy val integer: Parser[Int]         = many1(digit).map(_.toList.mkString.toInt)
    lazy val version: Parser[String]      = many1(oneOf(".v") | digit).map(_.toList.mkString)
    lazy val stringLit: Parser[String]    = string("\"") ~> manyUntil(anyChar, string("\"")).map(_.mkString)

    lazy val mComment: Parser[String] = string("/*") ~> manyUntil(anyChar, string("*/")).map(_.mkString)
    lazy val lComment: Parser[String] = string("--") ~> manyUntil(anyChar, char('\n') or endOfInput).map(_.mkString)
    lazy val comments: Parser[String] = many(ws ~> (mComment | lComment)).map(_.intercalate("\n"))

    lazy val minVer: Parser[Version] = (
      ws ~> string(">=").as(true) | string(">").as(false),
      version
    ).mapN(Version)

    lazy val maxVer: Parser[Version] = (
      ws ~> (string("<=").as(true) | string("<").as(false)),
      version
    ).mapN(Version)

    lazy val kind: Parser[Kind] = (
      ws ~> path,
      opt( (
        char('[') ~> kind,
        many(ws ~> char(',') ~> kind) <~ char(']')
      ).mapN(_ +: _) ).map(_.getOrElse(List.empty[Kind]))
    ).mapN(Kind)

    lazy val alias: Parser[Alias] = (
      comments,
      ws ~> string("type") ~> ws ~> kind,
      ws ~> char('=') ~> ws ~> kind
    ).mapN(Alias)

    lazy val exter: Parser[Exter] = (
      comments,
      ws ~> string("provided") ~> ws ~> kind
    ).mapN(Exter)

    lazy val impor: Parser[Impor] = (
      comments,
      ws ~> string("import") ~> ws ~> kind
    ).mapN(Impor)

    lazy val options: Parser[List[String]] =
      ws ~> char('(') ~> many(ws ~> identifier) <~ ws <~ char(')')

    lazy val ditems: Parser[List[StrucItem]] =
      ws ~> char('{') ~> many(ws ~> (enumItem or wrapp or typet or field or embed or mixin or using or struc)) <~ ws <~ char('}')

    lazy val fname: Parser[String] = ws ~> identifier <~ ws <~ char(':')

    lazy val field: Parser[Field] = (
      comments,
      fname,
      opt(options).map(_.getOrElse(List.empty[String])),
      kind,
    ).mapN(Field)

    lazy val typet: Parser[Typet] = (
      comments,
      fname,
      opt(options).map(_.getOrElse(List.empty[String])),
      ws ~> stringLit,
    ).mapN(Typet)

    lazy val embed: Parser[Embed] = (
      comments,
      fname,
      anonymousStruc,
    ).mapN(Embed)

    lazy val mixin: Parser[Mixin] = (
      comments,
      ws ~> string("mix") ~> kind,
    ).mapN(Mixin)

    lazy val using: Parser[Using] = (
      comments,
      ws ~> string("use") ~> kind,
    ).mapN(Using)

    lazy val enumItem: Parser[EnumStr] = (
      comments,
      ws ~> string("item") ~> ws ~> kind,
      ws ~> opt(options).map(_.getOrElse(List.empty[String])),
      ws ~> string("=") ~> ws ~> stringLit
    ).mapN(EnumStr)

    lazy val wrapp: Parser[Wrapp] = (
      comments,
      ws ~> string("wrap") ~> ws ~> kind,
      ws ~> opt(options).map(_.getOrElse(List.empty[String])),
      ws ~> string("=") ~> ws ~> kind
    ).mapN(Wrapp)

    lazy val strucBody: Parser[Struc] = (
      opt(minVer),
      opt(maxVer),
      opt(options).map(_.getOrElse(List.empty[String])),
      ditems
    ).mapN(Struc("", Option.empty[Kind], false, false, _, _, _, _))

    lazy val anonymousStruc: Parser[Struc] = (
      comments,
      opt(kind),
      strucBody
    ).mapN {
      (doc, kind, body) => body.copy(doc = doc, kind = kind)
    }

    lazy val struc: Parser[Struc] = (
      comments,
      ws ~> opt(string("abstract")),
      ws ~> opt(string("enum")),
      kind,
      opt(strucBody)
    ).mapN {
      (doc, abstractOpt, enumOpt, kind, body) => body
        .map(_.copy(doc = doc, kind = Some(kind), isAbstract = abstractOpt.nonEmpty, isEnum = enumOpt.nonEmpty))
        .getOrElse(Struc(doc, Some(kind), abstractOpt.nonEmpty, enumOpt.nonEmpty))
    }

    lazy val defxy: Parser[Either[Kind, Struc]] =
      kind.map(Left(_)) or anonymousStruc.map(Right(_))

    lazy val defun: Parser[Defun] = (
      comments,
      ws ~> string("def") ~> kind,
      opt(minVer),
      opt(maxVer),
      opt(options).map(_.getOrElse(List.empty[String])),
      defxy,
      ws ~> string("=>") ~> ws ~> defxy,
    ).mapN(Defun)

    lazy val servc: Parser[Servc] = (
      comments,
      kind,
      opt(minVer),
      opt(maxVer),
      opt(options).map(_.getOrElse(List.empty[String])),
      ws ~> char('{') ~> many1(ws ~> defun).map(_.toList) <~ ws <~ char('}')
    ).mapN(Servc)

    lazy val space: Parser[Space] = (
      ws ~> string("namespace") ~> ws ~> path,
      ws ~> opt(options).map(_.getOrElse(List.empty[String])),
      ws ~> char('{') ~> many(space or alias or exter or impor or servc or struc) <~ ws <~ char('}')
    ).mapN(Space)

    lazy val parser: Parser[List[Space]] = many1(space).map(_.toList)

    def parse(code: String): Either[String, Space] = {
      parser.parse(code).done.either.map{ spaceList =>
        Space(
          name = "",
          items = spaceList.widen
        )
      }
    }

    private implicit class CovariantParser[+A](val inv: Parser[A] @uncheckedVariance) {
      def or[C >: A](other: CovariantParser[C]): CovariantParser[C] = {
        inv | other.inv
      }
    }

    private implicit def covariantParserToInvariant[A](cp: CovariantParser[A]): Parser[A] = cp.inv
  }
}
