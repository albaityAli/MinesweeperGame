import javax.swing.*;
import java.awt.*;

public class MinesweeperUI extends JFrame {
    private static final int BUTTON_SIZE = 40; //  Dimensions of each cell
    private static final int TIMER_DELAY = 1000;

    private GameBoard gameBoard;
    private Timer gameTimer;
    private JPanel timerPanel;
    private JLabel timerLabel;

    public MinesweeperUI(GameBoard gameBoard) {
        // initialise UI

        this.gameBoard = gameBoard;

        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(gameBoard.getBoardSize() * BUTTON_SIZE, gameBoard.getBoardSize() * BUTTON_SIZE);
        setLocationRelativeTo(null);

        // Set window icon
        String iconFilePath = "src/images/bomb.png"; // Replace "y" with the actual file path of your icon image
        ImageIcon iconImage = new ImageIcon(iconFilePath);
        setIconImage(iconImage.getImage());

        setLayout(new BorderLayout());

        pack();
        setVisible(true);
    }

    public void startGame() {
        gameBoard.initializeGame();

        createTimerPanel();
        createButtons();

        pack();
        setVisible(true);
        startTimer();
    }

    private void resetGame() {
        //resets the game; used if game won or lost
        getContentPane().removeAll();

        gameBoard = new GameBoard();
        gameBoard.setUI(this);

        startGame();
        revalidate();
    }

    private void createButtons() {
        // creates the ui for all the cells

        int boardSize = gameBoard.getBoardSize();
        JPanel buttonsPanel = new JPanel(new GridLayout(boardSize, boardSize));
        buttonsPanel.setPreferredSize(new Dimension(boardSize * BUTTON_SIZE, boardSize * BUTTON_SIZE));

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Button button = gameBoard.getButton(row, col);
                buttonsPanel.add(button);
            }
        }

        add(buttonsPanel, BorderLayout.CENTER);
    }

    private void createTimerPanel() {
        // creates the ui for the timer


        timerPanel = new JPanel();
        timerPanel.setLayout(new FlowLayout());
        timerPanel.setBackground(Color.WHITE);

        timerLabel = new JLabel("Time: 0");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        timerPanel.add(timerLabel);

        timerPanel.setPreferredSize(new Dimension(getWidth(), BUTTON_SIZE));

        add(timerPanel, BorderLayout.NORTH);
    }

    public void showGameOverMessage() {
        JOptionPane.showMessageDialog(this, "Game Over! You stepped on a mine!", "Game Over", JOptionPane.ERROR_MESSAGE);
        resetGame();
    }

    public void showWinMessage() {
        JOptionPane.showMessageDialog(this, "Congratulations! You won the game!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
        resetGame();
    }

    private void startTimer() {
        gameTimer = new Timer(TIMER_DELAY, e -> updateTimer());
        gameTimer.start();
    }


    private void updateTimer() {
        long elapsedTime = gameBoard.getElapsedTime();
        long seconds = elapsedTime / 1000;

        timerLabel.setText("Time: " + seconds);
    }
}