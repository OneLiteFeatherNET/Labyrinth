# Labyrinth
<img src="Labyrinth_logo.png" width="50%" height="50%" alt="A labyrinth">

Labyrinth is a Paper plugin for the OneLiteFeather Survival Server. It defines zones where hostile mobs and bats cannot spawn, reducing the need to light the entire labyrinth.

## Features
- Create zones and manage their center, radius, and mob spawning state.
- Toggle mob spawning per zone.
- Zone name validation: alphanumeric only (`A-Z`, `a-z`, `0-9`).

## Commands
- `/labyrinth create <zone>`
- `/labyrinth center <zone>`
- `/labyrinth setradius <zone>`
- `/labyrinth toggle <zone>`
- `/labyrinth delete <zone>`

## Permissions
- `labyrinth.setup.createzone`
- `labyrinth.setup.center`
- `labyrinth.setup.setradius`
- `labyrinth.toggle.mobspawn`
- `labyrinth.setup.deletezone`

## Configuration
- The plugin stores zones under `zones.<zone>` in `config.yml`.
- Keys used internally:
- `zones.<zone>.centerLocation`
- `zones.<zone>.radius`
- `zones.<zone>.mobspawning`

## Build, Test, and Run (Developers)
- `./gradlew build`: compile and run tests.
- `./gradlew test`: run unit tests.
- `./gradlew shadowJar`: produces `build/libs/labyrinth.jar`.
- `./gradlew runServer`: starts a local Paper server for manual testing (Minecraft `1.21.11`).

## Project Structure
- `src/main/java/net/onelitefeather/labyrinth/`: plugin code.
- `src/main/resources/`: resources (including `config.yml`).
- `src/test/java/net/onelitefeather/labyrinth/`: JUnit tests (MockBukkit).

## CI and Releases
- Pull requests run a matrix build on Ubuntu, Windows, and macOS.
- Releases run on pushes to `main`, `next`, `beta`, or `*.x` using semantic-release.

## Notes
- The plugin entrypoint is `net.onelitefeather.labyrinth.Labyrinth`.
- If you change commands, permissions, or config keys, update this README.
