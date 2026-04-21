# Turtle Bridge - Java Swing Game

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=flat-square&logo=java&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-blue.svg?style=flat-square)

**Turtle Bridge** is a 2D arcade game built entirely with Java Swing and AWT, heavily inspired by the classic Game & Watch handhelds. The player must carry packages across a bridge of turtles that sporadically submerge to eat fish, making timing and resource management crucial.

## 🚀 Key Features

* **Custom Rendering & UI:** Utilizes Java 2D Graphics (`Graphics2D`) for drawing dynamic 7-segment score displays and rendering game entities over custom graphical background plates.
* **Progressive Difficulty:** Features a dynamic game loop utilizing `javax.swing.Timer` where the tick interval decreases over time, actively challenging the player's reflexes.
* **Custom Event System:** Implements a robust Observer design pattern with custom event objects (`StartEvent`, `PlusOneEvent`, `ResetEvent`) and a dedicated `DigitEventListener` interface.
* **Hybrid Grid-Image Architecture:** Ingeniously combines standard Swing components (`JPanel` with `ImageIO` background layers) with a transparent, non-focusable `JTable` and `TableCellRenderer` to manage spatial entity rendering (Player, Turtles, Fish) seamlessly.

## 🎮 How to Play

* **Start / Restart:** Press `S` to start or reset the game.
* **Move Left:** Press `A`.
* **Move Right:** Press `D`.
* **Objective:** Pick up packages from the base on the left (x=0) and deliver them to the base on the right (x=11). Beware of the turtles—if a turtle dives to eat a fish while you are standing on it, you fall, and the game is over! You win when your score reaches `999`.

## 🛠️ Tech Stack

* **Language:** Java 17+
* **Framework:** Java Swing, AWT

## 📦 Getting Started

### Prerequisites
* Java Development Kit (JDK) 17 or higher.
* The following graphical assets must be placed in your classpath (e.g., in the `resources` folder or the root of your `src` directory):
  * `Backdrop.png`
  * `Background.png`
  * `Fix.png`
  * `Gradient.png`
  * `Unit.png`

### Execution
1. Compile the project files.
2. Run the `Main` class located in the `p02` package.
```bash
java p02.Main
```

## 👨‍💻 Author
**Jan Myziak** * Index: s31003
* [GitHub Profile](https://github.com/lopez9090)
