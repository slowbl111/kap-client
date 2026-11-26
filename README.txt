== TungMod ==
Minecraft 1.21.1 | Fabric Loader 0.20.1

FEATURES
  AmethystESP  — highlights amethyst geodes within 48 blocks (G)
  PlayerESP    — boxes + name + distance on other players (H)
  AutoTotem    — moves totem of undying to offhand automatically (J)
  Freecam      — fly outside your body (V), WASD + Jump/Sneak to move
  Freelook     — look around without turning your character (C)
  3D Skin      — replaces your model with 3D Tung Tung Tung Sahur (K)
                 bat variant appears when elytra gliding or creative flying

BUILD STEPS
  1. Install Java 21 (adoptium.net or any OpenJDK 21 distro)
  2. Copy gradlew + gradlew.bat + gradle/ folder from:
       https://github.com/FabricMC/fabric-example-mod
     into this project root
  3. Run:
       Windows:  gradlew.bat build
       Mac/Linux: chmod +x gradlew && ./gradlew build
  4. Jar appears at: build/libs/tungmod-1.0.0.jar

INSTALL
  1. Install Fabric for MC 1.21.1 from fabricmc.net/use
  2. Drop tungmod-1.0.0.jar into .minecraft/mods/
  3. Drop Fabric API jar (modrinth.com/mod/fabric-api, 1.21.1 version)
  4. Launch with Fabric profile

TEXTURES (required)
  Paint two 64x64 PNGs in Blockbench and place them at:
    src/main/resources/assets/tungmod/textures/entity/tung_tung.png
    src/main/resources/assets/tungmod/textures/entity/tung_tung_bat.png
  See PLACEHOLDER.txt in that folder for UV guidance.

KEYBINDS
  V  Freecam toggle
  C  Freelook toggle
  G  AmethystESP toggle
  H  PlayerESP toggle
  J  AutoTotem toggle
  K  3D Skin toggle
