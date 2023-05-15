# TicTacToe Using MiniMax Algorithm

Project for school

## Description

This project creates an ASCII TicTacToe interface in the Terminal, and lets you play the game in either

* multiplayer
* against a random (dumb) AI
* or, against a minimax algorithm (smart ai)

## Project structure

The source code can be found in the `src/` dir.

### `Main`

The `Main` file instantiates a new `Engine` instance and attaches that instance to a `TicTacToe` Game instance. After
that, it calls the `engine.runGame` method which starts the tictactoe game.

### `ai`

The AI package contains Three files.
The `BaseAI` class is abstract and defines the common members that all AI's have to share. This allows me to easily add
new AI's without having to change much of
the existing code.
The `RandomAI` file just picks a random empty field, and return that row and col.
The `Minimax` file searches the whole game tree (about 362100 nodes) for the best possible move and returns the row and
col of said move.

### `engine`

Non-game specific code is here. The table `Renderer`, `MenuBuilder`, ConfigHolder (`Preferences`), `Logger` and an
abstract class `Game` which holds common methods that every Game must have.

### `game`
All the files under this package are specific to TicTacToe. The main file is `TicTacToe`

### To run the game:
* clone this github repo
* open the cloned project in intellij or vscode
* open the `Main` file under `src/`
* press the little arrow on the left side of the `public class Main` line.