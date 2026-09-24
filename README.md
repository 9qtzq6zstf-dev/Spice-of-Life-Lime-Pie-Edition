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

## Development

Import this directory as a Gradle project in IntelliJ IDEA with Java 21. Build with `./gradlew build` and run focused in-game tests with `./gradlew runGameTestServer`. The release JAR is written to `build/libs/`.

## Credits and license

- **1.21.1 NeoForge port:** JIA.
- **Spice of Life: Apple Pie Edition:** Vice. [Original repository](https://github.com/txnimc/Spice-of-Life-Apple-Pie).
- **Spice of Life: Potato Edition:** [Kevun1](https://github.com/Kevun1/Spice-of-Life-Potato-Edition).
- **Spice of Life: Carrot Edition:** [Cazsius and contributors](https://github.com/Cazsius/Spice-of-Life-Carrot-Edition).

Licensed under the GNU Lesser General Public License 2.1. See [LICENSE.txt](LICENSE.txt).
