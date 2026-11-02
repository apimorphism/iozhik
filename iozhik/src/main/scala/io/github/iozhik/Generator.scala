package io.github.iozhik

import cats.MonadError
import cats.implicits._

object Generator {
  object Model {
    final case class Code(
      body: String = "",
      name: String = "",
      path: String = "",
      packageObject: String = "",
      imports: List[String] = List.empty,
      // a test source: written to the test output directory, not the main one
      test: Boolean = false,
    )

    final case class Version(inclusive: Boolean, version: String)
    final case class Kind(name: String, params: List[Kind] = List.empty[Kind], path: List[String] = List.empty[String]) {
      def flatten: List[Kind] = {
        this :: params
      }
      def unresolvedIn(symt: Symtable): List[Kind] = {
        flatten.filter(symt.resolve(_).isEmpty)
      }
    }

    sealed trait Sym extends Product with Serializable
    sealed trait StrucItem extends Product with Serializable
    sealed trait SpaceItem extends Product with Serializable
    sealed trait ServcItem extends Product with Serializable

    final case class Mixin(doc: String, kind: Kind) extends StrucItem
    final case class Using(doc: String, kind: Kind) extends StrucItem
    final case class Embed(doc: String, name: String, struc: Struc) extends StrucItem
    final case class Field(doc: String, name: String, opts: List[String], kind: Kind) extends StrucItem
    final case class Typet(doc: String, name: String, opts: List[String], tag: String, id: Option[Int]) extends StrucItem
    // A union's variant: a struct of its own (a leaf), a wrapped value or a string item, in the order they're generated.
    sealed trait Variant extends StrucItem

    // How a struct is generated.
    sealed trait Shape
    object Shape {
      // A case class, or a case object when it has no fields (Struc.isCaseObject).
      case object Record extends Shape
      // A trait over its variants: sealed for an `enum`, open for an `abstract` struct.
      final case class Union(isSealed: Boolean, variants: List[Variant]) extends Shape
    }

    final case class EnumStr(doc: String, name: Kind, opts: List[String], target: String) extends Variant {
      def asStruc(parent: Struc): Struc = {
        Struc(
          doc = doc,
          kind = Some(name),
          path = parent.path,
          typets = List(
            Typet(
              doc = "",
              name = "_type_",
              opts = List.empty[String],
              tag = target,
              id = Option.empty[Int]
            )
          ),
        )
      }
    }
    final case class Wrapp(doc: String, name: Kind, opts: List[String], target: Kind, fname: String) extends Variant {
      def asStruc(parent: Struc): Struc = {
        Struc(
          doc = doc,
          kind = Some(name),
          path = parent.path,
          fields = List(
            Field(
              doc = "",
              name = fname,
              opts = List.empty[String],
              kind = target,
            )
          ),
          typets = List(
            Typet(
              doc = "",
              name = "_type_",
              opts = List.empty[String],
              tag = s"${target.name}",
              id = Option.empty[Int]
            )
          ),
        )
      }
    }

    final case class Exter(doc: String, name: Kind, path: List[String]) extends SpaceItem with StrucItem with Sym
    final case class Impor(doc: String, name: Kind, path: List[String]) extends SpaceItem with StrucItem
    final case class Alias(doc: String, alias: Kind, name: Kind, path: List[String]) extends SpaceItem with StrucItem with Sym

    final case class Struc(
      doc: String,
      kind: Option[Kind],
      path: List[String],
      shape: Shape = Shape.Record,
      minVersion: Option[Version] = Option.empty[Version],
      maxVersion: Option[Version] = Option.empty[Version],
      typets: List[Typet] = List.empty[Typet],
      opts: List[String] = List.empty[String],
      fields: List[Field] = List.empty[Field],
      mixins: List[Kind] = List.empty[Kind],
      usings: List[Using] = List.empty[Using],
      // The fields of the structs named by `usings`, resolved by translate.
      usingFields: List[Field] = List.empty[Field],
      embeds: List[Embed] = List.empty[Embed],
    ) extends SpaceItem with StrucItem with Sym with Variant {
      def typet(): Option[Typet] = typets.headOption

      def variants: List[Variant] = shape match {
        case Shape.Union(_, variants) => variants
        case Shape.Record             => Nil
      }
      def leaves: List[Struc] = variants.collect { case s: Struc => s }
      def wrapps: List[Wrapp] = variants.collect { case w: Wrapp => w }
      def enumstrs: List[EnumStr] = variants.collect { case e: EnumStr => e }
      def isEnum: Boolean = shape match {
        case Shape.Union(isSealed, _) => isSealed
        case Shape.Record             => false
      }
      def isAbstract: Boolean = shape match {
        case Shape.Union(isSealed, _) => !isSealed
        case Shape.Record             => false
      }

      // Every field the generated class has, the used structs' first.
      def allFields: List[Field] = usingFields ++ fields

      // A record with no fields is generated as a case object.
      def isCaseObject: Boolean = shape == Shape.Record && allFields.isEmpty

      def typetForBins: Option[Typet] = {
        typet().orElse {
          Some(Typet(
            doc = "",
            name = "_type_",
            opts = List.empty[String],
            tag = "",
            id = Option.empty[Int]
          ))
        }
      }
      def leavesForBins: List[Struc] = {
        leaves.map{ s =>
          if (s.typets.isEmpty) {
            val tag = s.kind.map(_.name).getOrElse("$$")
            s.copy(
              typets = List(
                Typet(
                  doc = "",
                  name = "_type_",
                  opts = List.empty[String],
                  tag = tag,
                  id = Option.empty[Int]
                )
              ),
            )
          } else {
            s
          }
        } ++ wrapps.map(_.asStruc(this)) ++ enumstrs.map(_.asStruc(this))
      }
    }

    final case class Servc(
      doc: String,
      kind: Kind,
      path: List[String]  = List.empty[String],
      minVersion: Option[Version] = Option.empty[Version],
      maxVersion: Option[Version] = Option.empty[Version],
      opts: List[String] = List.empty[String],
      items: List[ServcItem],
    ) extends SpaceItem

    final case class Defun(
      doc: String,
      kind: Kind,
      path: List[String],
      minVersion: Option[Version] = Option.empty[Version],
      maxVersion: Option[Version] = Option.empty[Version],
      opts: List[String] = List.empty[String],
      dom: Either[Kind, Struc],
      cod: Either[Kind, Struc],
    ) extends SpaceItem with ServcItem with Sym

    final case class Space(
      name: String,
      opts: List[String],
      path: List[String]  = List.empty[String],
      exters: List[Exter] = List.empty[Exter],
      impors: List[Impor] = List.empty[Impor],
      aliass: List[Alias] = List.empty[Alias],
      strucs: List[Struc] = List.empty[Struc],
      servcs: List[Servc] = List.empty[Servc],
      spaces: List[Space] = List.empty[Space],
      symbol: Symtable = Symtable()
    ) extends SpaceItem {
      def ++(other: Space): Space = {
        if (this.name == "") {
          if (other.name == "") {
            Space(
              name = "",
              opts = this.opts ++ other.opts,
              path = this.path,
              exters = this.exters ++ other.exters,
              impors = this.impors ++ other.impors,
              aliass = this.aliass ++ other.aliass,
              strucs = this.strucs ++ other.strucs,
              spaces = this.spaces ++ other.spaces,
              symbol = this.symbol ++ other.symbol
            )
          } else {
            this.copy(spaces = this.spaces :+ other)
          }
        } else {
          other.copy(spaces = other.spaces :+ this)
        }
      }
      def find(k: Kind, orIn: Option[Kind]): Option[Sym] = symbol.find(k, orIn)
    }

    val P = Parser.Model

    case class Symtable(
      spaceMap: Map[List[String], Space] = Map.empty[List[String], Space],
      strucMap: Map[List[String], Struc] = Map.empty[List[String], Struc],
      exterMap: Map[List[String], Exter] = Map.empty[List[String], Exter],
      servcMap: Map[List[String], Servc] = Map.empty[List[String], Servc],
    ) {
      def ++(other: Symtable): Symtable = {
        copy(
          spaceMap ++ other.spaceMap,
          strucMap ++ other.strucMap,
          exterMap ++ other.exterMap,
          servcMap ++ other.servcMap,
        )
      }
      def +(s: Struc): Symtable = {
        s.kind.map{ k =>
          val key = k.name :: k.path
          copy(strucMap = this.strucMap + (key -> s))
        }.getOrElse(this)
      }
      def +(s: Servc): Symtable = {
        copy(servcMap = this.servcMap + ((s.kind.name :: s.path) -> s))
      }
      def +(s: Space): Symtable = {
        copy(spaceMap = this.spaceMap + ((s.name :: s.path) -> s))
      }
      def +(e: Exter): Symtable = {
        copy(exterMap = this.exterMap + ((e.name.name :: e.path) -> e))
      }

      def resolve(k: Kind): Option[Sym] = {
        if (k.path.isEmpty) {
          val key = k.name :: Nil
          strucMap.get(k.name :: Nil)
            .orElse(exterMap.get(key))
        } else {
          val key = k.name :: k.path
          strucMap.get(key).orElse(
            spaceMap.get(k.path).flatMap { space =>
              space.symbol.strucMap.get(key)
                .orElse(space.symbol.exterMap.get(key))
            }.orElse(resolve(Kind(name = k.name, path = k.path.drop(1))))
          ).orElse(exterMap.get(key))
        }
      }
      def find(k: Kind, orIn: Option[Kind] = Option.empty): Option[Sym] = {
        resolve(k).orElse(resolve(k.copy(path = orIn.map(_.path).getOrElse(List.empty[String]))))
      }
    }

    def translate(x: P.Space): Either[String, Space] = {
      def walk(s: Symtable): SpaceItem => Symtable = {
        case x: Struc =>
          x.kind.map { k =>
            s.copy(strucMap = s.strucMap + ((k.name :: k.path)  -> x)) ++
              x.leaves
                .map(walk(s))
                .fold(Symtable())(_ ++ _)
          }.getOrElse(s)
        case x: Servc =>
          s.copy(servcMap = s.servcMap + ((x.kind.name :: x.path)  -> x))
        case x: Space =>
          s.copy(spaceMap = s.spaceMap + ((x.name :: x.path) -> x)) ++
            x.spaces
              .map(walk(s))
              .fold(Symtable())(_ ++ _)
        case _ => s
      }
      def trKind(x: P.Kind, path: List[String]): Kind = Kind(x.name, x.params.map(trKind(_, path)), path)
      def trTypet(x: P.Typet): Typet = {
        val items = x.tag.split(":")
        val tag = items.headOption.getOrElse("")
        val id = items.drop(1).lastOption.map(_.toInt)
        Typet(x.doc, x.name, x.opts, tag, id)
      }
      def trWrapp(x: P.Wrapp, path: List[String]): Wrapp = {
        val fname = x.opts.headOption.getOrElse("value")
        Wrapp(x.doc, trKind(x.name, path), x.opts, trKind(x.target, path), fname)
      }
      def trEnumStr(x: P.EnumStr, path: List[String]): EnumStr = {
        EnumStr(x.doc, trKind(x.name, path), x.opts, x.target)
      }
      def trVersion(x: P.Version): Version = Version.tupled(P.Version.unapply(x).get)
      def trField[F[_]](x: P.Field, path: List[String])(implicit E: MonadError[F, String]): F[Field] = {
        E.pure(
          Field(x.doc, x.name, x.opts, trKind(x.kind, path))
        )
      }
      def trAlias[F[_]](x: P.Alias, path: List[String])(implicit E: MonadError[F, String]): F[Alias] = {
        E.pure(
          Alias(
            doc = "",
            alias = trKind(x.alias, path),
            name = trKind(x.name, path),
            path = path
          )
        )
      }
      def trExter[F[_]](x: P.Exter, path: List[String])(implicit E: MonadError[F, String]): F[Exter] = {
        E.pure(
          Exter(
            doc = "",
            name = trKind(x.name, path),
            path = path
          )
        )
      }
      def trImpor[F[_]](x: P.Impor, path: List[String])(implicit E: MonadError[F, String]): F[Impor] = {
        E.pure(
          Impor(
            doc = "",
            name = trKind(x.name, path),
            path = path
          )
        )
      }
      def trEmbed[F[_]](x: P.Embed, path: List[String])(implicit E: MonadError[F, String]): F[Embed] = {
        for {
          struc <- trStruc(x.struc, path)(E)
        } yield {
          Embed(
            doc = x.doc,
            name = x.name,
            struc = struc
          )
        }
      }
      def trStruc[F[_]](x: P.Struc, path: List[String])(implicit E: MonadError[F, String]): F[Struc] = {
        for {
          fields <- x.items
            .collect{ case x: P.Field => x }
            .traverse(trField(_, path)(E))
          leaves <- x.items
            .collect{ case x: P.Struc => x }
            .traverse(trStruc(_, path)(E))
          embeds <- x.items
            .collect{ case x: P.Embed => x }
            .traverse(trEmbed(_, path)(E))
          typets = x.items
            .collect{ case y: P.Typet => trTypet(y) }
          wrapps = x.items
            .collect{ case y: P.Wrapp => trWrapp(y, path) }
          enumstrs = x.items
            .collect{ case y: P.EnumStr => trEnumStr(y, path) }
          mixins = x.items
            .collect{ case y: P.Mixin => trKind(y.kind, path) }
          usings = x.items
            .collect{ case y: P.Using => Using(doc = y.doc, kind = trKind(y.kind, path)) }
        } yield {
          val kind: Option[Kind] = x.kind.map(trKind(_, path))
          val children = leaves.map(
            y => y.copy(
              fields = fields ++ y.fields,
              mixins = kind.toList ++ y.mixins
            )
          )
          val flattens = embeds.map(_.struc).filter(_.opts.contains("flatten"))
          val variants: List[Variant] = children ++ wrapps ++ enumstrs
          val name = kind.map(_.name).getOrElse("an anonymous struct")
          val shape: F[Shape] =
            if (x.isEnum && x.isAbstract) E.raiseError(s"$name can't be both abstract and an enum")
            else if (x.isEnum || x.isAbstract) E.pure(Shape.Union(isSealed = x.isEnum, variants))
            else if (variants.nonEmpty) E.raiseError(s"$name has variants but is neither abstract nor an enum")
            else E.pure(Shape.Record)
          shape.map(shape => Struc(
            doc = x.doc,
            path = path,
            kind = x.kind.map(trKind(_, path)),
            shape = shape,
            minVersion = x.minVersion.map(trVersion),
            maxVersion = x.maxVersion.map(trVersion),
            typets = if (x.isEnum) leaves.flatMap(_.typets) else typets,
            opts = x.opts,
            fields = fields ++ flattens.flatMap(_.fields),
            mixins = mixins,
            usings = usings ++ flattens.flatMap(_.usings),
            embeds = embeds,
          ))
        }
      }.flatten
      def trDefun[F[_]](x: P.Defun, path: List[String])(implicit E: MonadError[F, String]): F[Defun] = {
        for {
          dom <- x.dom.bimap(trKind(_, path), trStruc(_, path)(E)).sequence
          cod <- x.cod.bimap(trKind(_, path), trStruc(_, path)(E)).sequence
        } yield {
          Defun(
            doc = x.doc,
            path = path,
            kind = trKind(x.kind, path),
            minVersion = x.minVersion.map(trVersion),
            maxVersion = x.maxVersion.map(trVersion),
            opts = x.opts,
            dom = dom,
            cod = cod,
          )
        }
      }
      def trServc[F[_]](x: P.Servc, path: List[String])(implicit E: MonadError[F, String]): F[Servc] = {
        for {
          defuns <- x.items
            .collect{ case x: P.Defun => x }
            .traverse(trDefun(_, path)(E))
        } yield {
          Servc(
            doc = x.doc,
            kind = trKind(x.kind, path),
            path = path,
            minVersion = x.minVersion.map(trVersion),
            maxVersion = x.maxVersion.map(trVersion),
            opts = x.opts,
            items = defuns
          )
        }
      }
      def trSpace[F[_]](x: P.Space, path: List[String])(implicit E: MonadError[F, String]): F[Space] = {
        for {
          exters <- x.items.collect{ case y: P.Exter => trExter(y, x.name :: path)(E) }.sequence
          impors <- x.items.collect{ case y: P.Impor => trImpor(y, x.name :: path)(E) }.sequence
          aliass <- x.items.collect{ case y: P.Alias => trAlias(y, x.name :: path)(E) }.sequence
          strucs <- x.items.collect{ case y: P.Struc => trStruc(y, x.name :: path)(E) }.sequence
          servcs <- x.items.collect{ case y: P.Servc => trServc(y, x.name :: path)(E) }.sequence
          spaces <- x.items.collect{ case y: P.Space => trSpace(y, x.name :: path)(E) }.sequence
        } yield {
          index(Space(
            x.name,
            x.opts,
            path = path,
            exters = exters,
            impors = impors,
            aliass = aliass,
            strucs = strucs,
            servcs = servcs,
            spaces = spaces.map(y => y.copy(opts = y.opts ++ x.opts)),
          ))
        }
      }
      // A space's symbol table, built from its items (nested spaces carry their own).
      def index(x: Space): Space = {
        val exterMap = x.exters.map(walk(Symtable())).fold(Symtable())(_ ++ _)
        val strucMap = x.strucs.map(walk(Symtable())).fold(Symtable())(_ ++ _)
        val spaceMap = x.spaces.map(walk(Symtable())).fold(Symtable())(_ ++ _)
        val servcMap = x.spaces.map(walk(Symtable())).fold(Symtable())(_ ++ _)
        x.copy(symbol = exterMap ++ strucMap ++ spaceMap ++ servcMap)
      }
      // Resolves every struct's `use`s to the used structs' fields, against the whole API's symbol table, and
      // re-indexes the spaces so lookups return the resolved structs.
      def resolveUsings(root: Space): Either[String, Space] = {
        // An embed's `use`s resolve in the scope of the struct holding it.
        def struc(x: Struc, scope: Option[Kind]): Either[String, Struc] = for {
          usingFields <- x.usings.flatTraverse { u =>
            root.symbol.find(u.kind, scope).collect { case s: Struc => s.fields }
              .toRight(s"can't resolve `use ${u.kind.name}` in ${x.kind.fold("an anonymous struct")(_.name)}")
          }
          shape <- x.shape match {
            case Shape.Record => Right(Shape.Record)
            case Shape.Union(isSealed, variants) =>
              variants.traverse {
                case s: Struc => struc(s, s.kind).map(s => s: Variant)
                case other    => Right(other)
              }.map(Shape.Union(isSealed, _))
          }
          embeds <- x.embeds.traverse(e => struc(e.struc, x.kind).map(s => e.copy(struc = s)))
        } yield x.copy(usingFields = usingFields, shape = shape, embeds = embeds)
        def servc(x: Servc): Either[String, Servc] =
          x.items.traverse {
            case d: Defun =>
              for {
                dom <- d.dom.traverse(s => struc(s, s.kind))
                cod <- d.cod.traverse(s => struc(s, s.kind))
              } yield d.copy(dom = dom, cod = cod): ServcItem
          }.map(items => x.copy(items = items))
        def space(x: Space): Either[String, Space] = for {
          strucs <- x.strucs.traverse(s => struc(s, s.kind))
          servcs <- x.servcs.traverse(servc)
          spaces <- x.spaces.traverse(space)
        } yield index(x.copy(strucs = strucs, servcs = servcs, spaces = spaces))
        space(root)
      }
      trSpace[Either[String, *]](x, List()).flatMap(resolveUsings)
    }

  }
}

trait Generator {
  def delimiter: String
  def gen(x: Generator.Model.Space): Either[String, List[Generator.Model.Code]]
}

