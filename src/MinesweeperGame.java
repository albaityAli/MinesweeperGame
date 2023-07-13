import javax.swing.*;

public class MinesweeperGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameBoard gameBoard = new GameBoard();
            MinesweeperUI ui = new MinesweeperUI(gameBoard);
            gameBoard.setUI(ui);
            ui.startGame();
        });
    }
}
