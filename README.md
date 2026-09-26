# Spice of Life: Lime Pie Edition

A Minecraft 1.21.1 NeoForge port of Spice of Life: Apple Pie Edition. This edition is maintained by JIA. The original Apple Pie Edition was created by Vice.

The mod ID and resource namespace are `sollimepie`.

## Requirements and installation

- Minecraft 1.21.1
- NeoForge 21.1.251 or newer for Minecraft 1.21.1
- Java 21

Put the release JAR in your `mods` folder. Install it on the server and on clients that join that server. No additional mods are required.

## Gameplay

The mod tracks your recent foods and calculates a diversity score. Eating a wider variety of foods raises the score and grants configurable benefits. Repeating the same foods lowers it again. By default, benefits include extra maximum health and other attribute or effect bonuses.

- **Food Book:** Shows your current diversity, recent foods, and available benefits. It also has an unbound hotkey that you can assign in Controls.
- **Lunchbag, Lunchbox, and Golden Lunchbox:** Store food and choose the stored food that best improves your diversity when you eat from them. Sneak and use one to open its inventory.
- **Configuration:** Food history length, diversity rules, and benefits can be changed in the generated NeoForge config files. Server config is stored per world under `serverconfig`.

Commands use the `/sollimepie` namespace:

- `/sollimepie diversity` shows the current diversity score.
- `/sollimepie clear` clears food history and resets its benefits.
- `/sollimepie sync` resends food history to the client.

The optional Origins diet integration has not been verified with a compatible NeoForge 1.21.1 Origins build.

## Lime Pie updates

- The Food Book, Lunchbag, Lunchbox, and Golden Lunchbox appear in a dedicated creative inventory tab with the Food Book as its icon.
- English and Simplified Chinese translations cover item names, the Food Book, tooltips, keybinds, and command feedback. Player-facing Food Book text uses translation keys instead of fixed English strings.
- Recent port fixes cover configuration parsing, lunch container edge cases, and Food Book background rendering. Migration GameTests check food tracking and container behavior.

## Development

Import this directory as a Gradle project in IntelliJ IDEA with Java 21. Build with `./gradlew build` and run focused in-game tests with `./gradlew runGameTestServer`. The release JAR is written to `build/libs/`.

## Releasing

After pushing this repository to GitHub, push a `v*` version tag or start the **Release** workflow manually with a version. The workflow builds with Java 21, runs the GameTests, and uploads the sole release JAR to GitHub Releases. A tag push creates a stable release; a manual run defaults to beta. Manual release notes override `CHANGELOG.md`.

The workflow also publishes to CurseForge project `1712324`. Add `CURSEFORGE_TOKEN` as an Actions secret in this repository before releasing. A secret in another repository does not transfer to a new personal repository. The workflow stops before building if the secret is unavailable.

## Credits and license

- **1.21.1 NeoForge port:** JIA.
- **Spice of Life: Apple Pie Edition:** Vice. [Original repository](https://github.com/txnimc/Spice-of-Life-Apple-Pie).
- **Spice of Life: Potato Edition:** [Kevun1](https://github.com/Kevun1/Spice-of-Life-Potato-Edition).
- **Spice of Life: Carrot Edition:** [Cazsius and contributors](https://github.com/Cazsius/Spice-of-Life-Carrot-Edition).

Licensed under the GNU Lesser General Public License 2.1. See [LICENSE.txt](LICENSE.txt).
