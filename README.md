# Turtle Bridge - Java Swing Game

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=flat-square&logo=java&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-blue.svg?style=flat-square)

**Turtle Bridge** is a 2D arcade game built with Java Swing and AWT, inspired by classic Game & Watch handhelds. The player carries packages across a bridge of turtles that occasionally submerge to eat fish. Don't fall in.

## Technical Details

* **Custom Rendering:** Uses `Graphics2D` to draw 7-segment score displays and render entities over background plates.
* **Progressive Difficulty:** The game loop relies on a `javax.swing.Timer`. The tick interval decreases as you play, speeding up the game over time.
* **Event System:** Uses the Observer pattern (`StartEvent`, `PlusOneEvent`, `ResetEvent`, `DigitEventListener`) to handle game state updates.
* **Grid-Based Rendering Hack:** Combines a standard `JPanel` (for `ImageIO` backgrounds) with a transparent, non-focusable `JTable` and a custom `TableCellRenderer` to handle the spatial rendering of the Player, Turtles, and Fish.

## How to Play

* **S** - Start / Restart
* **A** - Move Left
* **D** - Move Right

**Objective:** Pick up packages from the left base (x=0) and deliver them to the right base (x=11). If a turtle dives for a fish while you're standing on it, it's game over. Reach a score of `999` to win.

## Tech Stack

* **Language:** Java 17+
* **Framework:** Java Swing, AWT

## Getting Started

### Prerequisites
* JDK 17 or higher.
* Ensure the following graphical assets are in your classpath (e.g., `src/` or `resources/` folder):
  * `Backdrop.png`
  * `Background.png`
  * `Fix.png`
  * `Gradient.png`
  * `Unit.png`

### Running locally
1. Compile the project files.
2. Run the `Main` class:
```bash
java p02.Main
```

## Author

* **Jan Myziak** (Index: s31003)
* [GitHub Profile](https://github.com/lopez9090)
