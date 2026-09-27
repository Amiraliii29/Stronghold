# Stronghold

A turn-based medieval strategy game in Java, modeled on *Stronghold Crusader*. Built by a team of three for the Advanced Programming course at Sharif University of Technology (Spring 2023).

The project was developed in three phases, and each phase lives on its own branch:

| Phase | Branch | What it adds |
|---|---|---|
| 1. Game logic | `main` | Full game model and a command-line interface |
| 2. Graphics | `developing` | JavaFX user interface on top of the same model |
| 3. Networking | `Client`, `Server` | Online multiplayer: lobby, game rooms and chat over sockets |

## Features

**Game model**
- Tile-based maps with land types, trees and resources. Maps are stored as JSON and can be edited with a map editor.
- Governments with popularity, taxes, food rations and resource stockpiles.
- Buildings (town buildings, generators, stockpiles, barracks, defences) and units (troops, siege engines, engineers, ladder men, tunnelers), all defined in JSON data files under `src/main/resources`.
- Unit movement and combat, a shop, and a trade system between players.
- Turn handling. Fire and disease events were added in phase 2.

**Accounts**
- Sign-up and login with password strength checks, security questions, and a time penalty after repeated wrong passwords. A captcha was added in phase 2.
- User profiles, a scoreboard, and persistent user data in JSON.

**Graphical client (JavaFX)**
- Menus for login, profile, shop, trade and the map editor; a game view with building placement, unit animation and a mini-map.

**Multiplayer**
- A server (`ServerSocket`, port 8080) that manages users, game rooms and the lobby.
- Clients exchange JSON requests and responses (Gson) with the server.
- Global and private chat.

## Architecture

The code follows **Model–View–Controller**:

```
src/main/java/
  Model/        game state: Map, Square, Government, User, Buildings/, Units/
  Controller/   game rules and menu logic; the View calls into these
  View/         CLI menus (phase 1) or JavaFX screens (phase 2+)
    Enums/      regex command patterns and result messages for each menu
  Main/         entry point; Client / Server classes in phase 3
src/main/resources/
  Buildings/ Units/ Resources/   game data in JSON
  Map/                           saved maps
```

In the command-line version, each menu matches input against regular expressions defined in `View/Enums/Commands`, calls the matching controller method, and prints a message from `View/Enums/Messages`.

## Build and run

Requirements: JDK 17 and Maven.

```bash
git checkout developing      # or main (CLI), Client, Server
mvn compile
```

Then run the entry point from your IDE or with `mvn exec:java -Dexec.mainClass=<class>`. The main class is `Main` on `main`, `View.Main` on `developing`, and `Main.Main` on `Client` and `Server`. The JavaFX branches need the JavaFX modules listed in `pom.xml`.

For the online version, start the server from the `Server` branch first, then run one or more clients from the `Client` branch.

## Team

- **AmirAli Sheikhi** ([@Amiraliii29](https://github.com/Amiraliii29)): shop and trade menus, game menu and turn logic, mini-map, fire and disease events, and client/server chat
- **Kiarash Kiani** ([@kiarashkia138](https://github.com/kiarashkia138))
- **Sorush Vakilzadeh**
