# MinesweeperGame

Minesweeper is a computer board game that prompts th user to click on tiles of a board. The user must ensure they do not click on a tile containing a mine, otherwise they lose. A revealed tile would display the number of mines in the neighbouring tiles.

This project creates a simple minesweeper game using Java and the JFrame library.

The structure of the project consists of four main classes:
- MinesweeperGame: This initialises the game
- GameBoard: This contains all the logic for the game
- Button: This contains the logic for each tile(aka button, cell) and some UI aspects of the button
- MinesweeperUI: This contains the UI code for the game.


To test the project, I mainly used manual testing and went through all the potential scenarios(worked better since there weren't many to go through, however, would definitely need unit tests if I were to consistently make changes to the project to prevent any bugs from popping up)
