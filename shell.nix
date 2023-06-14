let
  sources = import ./nix/sources.nix;
  nixpkgs = import sources.nixpkgs {};
in
with nixpkgs;
stdenv.mkDerivation {
  name = "scala-env";
  buildInputs = [ temurin-bin-8 ];
}
