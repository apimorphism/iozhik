{
  description = "A Nix-flake-based Scala development environment";

  inputs.nixpkgs.url = "https://flakehub.com/f/NixOS/nixpkgs/0.1";

  outputs =
    { self, ... }@inputs:

    let
      javaVersion = 17; # Change this value to update the whole stack

      supportedSystems = [
        "x86_64-linux"
        "aarch64-linux"
        "aarch64-darwin"
      ];
      forEachSupportedSystem =
        f:
        inputs.nixpkgs.lib.genAttrs supportedSystems (
          system:
          f {
            inherit system;
            pkgs = import inputs.nixpkgs {
              inherit system;
              overlays = [ inputs.self.overlays.default ];
            };
          }
        );
    in
    {
      overlays.default =
        final: prev:
        let
          jdk = prev."jdk${toString javaVersion}";
        in
        {
          sbt = prev.sbt.override { jre = jdk; };
          scala = prev.scala_2_13.override { jre = jdk; };
        };

      devShells = forEachSupportedSystem (
        { pkgs, system }:
        {
          # Aliases below implement the generation flow documented in
          # CONTRIBUTING.md. They assume a sibling checkout of
          # apimorphism/telegramium; set TELEGRAMIUM_DIR if it isn't at
          # ../telegramium.
          default = pkgs.mkShellNoCC {
            packages = with pkgs; [
              scala
              sbt
              coursier
              gh
            ];

            shellHook = ''
              export TELEGRAMIUM_DIR="''${TELEGRAMIUM_DIR:-../telegramium}"

              tgbot-scrape() {
                IOZHDIR="$PWD" sbt "iozh-api-scraper/runMain TgBotApiScrapper"
              }

              tgbot-regen-core() {
                if [ ! -d "$TELEGRAMIUM_DIR/telegramium-core" ]; then
                  echo "TELEGRAMIUM_DIR ($TELEGRAMIUM_DIR) is not a telegramium checkout. Set TELEGRAMIUM_DIR to its path." >&2
                  return 1
                fi
                sbt "iozhik/run iozhik/src/main/resources/tgbotapi.conf $TELEGRAMIUM_DIR/telegramium-core/src/main/scala"
              }

              tgbot-refresh() {
                tgbot-scrape && tgbot-regen-core
              }

              export -f tgbot-scrape tgbot-regen-core tgbot-refresh

              echo "iozhik dev shell: tgbot-scrape, tgbot-regen-core, tgbot-refresh"
              echo "TELEGRAMIUM_DIR=$TELEGRAMIUM_DIR"
            '';
          };
        }
      );
    };
}
