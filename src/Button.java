import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


class Button extends JButton {
    private int row;
    private int col;
    private boolean mine;
    private boolean revealed;
    private int neighborMines;

    public Button(int row, int col) {
        //  initialise the button
        this.row = row;
        this.col = col;
        this.mine = false;
        this.revealed = false;
        this.neighborMines = 0;

        setPreferredSize(new Dimension(40, 40));
        setFocusPainted(false);
        setIcon(new ImageIcon("src/images/faceDown.png"));
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean state) {
        mine = state;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public int getNeighborMines() {
        return neighborMines;
    }

    public void setNeighborMines(int neighborMines) {
        this.neighborMines = neighborMines;
    }

    public void reveal() {
        if (revealed) {
            return;
        }

        setEnabled(false);
        revealed = true;

        //  changes the image of a button to reveal, either displays nothing, a bomb or the number of nearby bombs

        if (mine) {
            try {
                Image image = ImageIO.read(new File("src/images/bomb.png"));
                Image resizedImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(resizedImage);

                setIcon(icon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (neighborMines == 0) {
                setIcon(null);
            } else {
                try {
                    Image image = ImageIO.read(new File("src/images/" + neighborMines + ".png"));
                    Image resizedImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(resizedImage);

                    setIcon(icon);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
