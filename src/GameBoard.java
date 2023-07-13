import java.awt.*;

class GameBoard {
    private static final int BOARD_SIZE = 10;  // dimensions of the board
    private static final int NUM_MINES = 10;  // number of mines

    private Button[][] buttons;  // grid of buttons
    private MinesweeperUI ui;
    private long startTime;  // timer for each game


    public GameBoard() {
        buttons = new Button[BOARD_SIZE][BOARD_SIZE];
    }

    public void setUI(MinesweeperUI ui) {
        this.ui = ui;
    }

    public void initializeGame() {
        // Initialize the game board with buttons and randomly place mines
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Button button = new Button(row, col);
                buttons[row][col] = button;
                buttons[row][col].addActionListener(e -> revealCell(button.getRow(), button.getCol()));
            }
        }
        placeMines();
        calculateNeighbourMines();

        startTime = System.currentTimeMillis();

    }

    private void placeMines() {
        // Place mines randomly on the board
        int minesToPlace = NUM_MINES;
        while (minesToPlace > 0) {
            int row = (int) (Math.random() * BOARD_SIZE);
            int col = (int) (Math.random() * BOARD_SIZE);

            if (! buttons[row][col].isMine()) {
                buttons[row][col].setMine(true);
                minesToPlace--;
            }
        }
    }

    private void calculateNeighbourMines() {
        //  calculates neighbour mines for each cell
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                int count = 0;

                // check upward
                if (y > 0 && buttons[y - 1][x].isMine())
                    count++;
                // check downward
                if (y < BOARD_SIZE - 1 && buttons[y + 1][x].isMine())
                    count++;
                // check left
                if (x > 0 && buttons[y][x - 1].isMine())
                    count++;
                // check right
                if (x < BOARD_SIZE - 1 && buttons[y][x + 1].isMine())
                    count++;
                // check top left
                if (x > 0 && y > 0 && buttons[y - 1][x - 1].isMine())
                    count++;
                // check top right
                if (x < BOARD_SIZE - 1 && y > 0 && buttons[y - 1][x + 1].isMine())
                    count++;
                // check down left
                if (x > 0 && y < BOARD_SIZE - 1 && buttons[y + 1][x - 1].isMine())
                    count++;
                // check down right
                if (x < BOARD_SIZE - 1 && y < BOARD_SIZE - 1 && buttons[y + 1][x + 1].isMine())
                    count++;

                buttons[y][x].setNeighborMines(count);

            }
        }
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    public void revealCell(int row, int col) {
        Button button = buttons[row][col];
        if (button.isMine()) {
            // Reveal all mines and end the game
            revealAllMines();
            button.setBackground(Color.RED);
            ui.showGameOverMessage();
        } else {
            button.reveal();
            if (button.getNeighborMines() == 0) {
                // If the revealed cell has no neighboring mines, recursively reveal adjacent cells
                revealAdjacentCells(row, col);
            }
            if (checkWin()) {
                // Check if the player has won the game
                ui.showWinMessage();
            }
        }
    }

    private void revealAdjacentCells(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (isValidCell(i, j) && !buttons[i][j].isRevealed()) {
                    revealCell(i, j);
                }
            }
        }
    }

    private void revealAllMines() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (buttons[row][col].isMine()) {
                    buttons[row][col].reveal();
                }
            }
        }
    }

    private boolean checkWin() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (!buttons[row][col].isMine() && !buttons[row][col].isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidCell(int row, int col) {
        //  checks the cell with x,y dimensions is valid
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }

    public Button getButton(int row, int col) {
        return buttons[row][col];
    }
}
