package main.containers;

import main.components.DoneGame;

import javax.swing.*;
import java.awt.*;

public class DialogContainer extends JPanel {

    /**
     * Represents the background of this container
     * @see Color
     */
    private Color bgColor = new Color(0, 0, 0, 125);

    /**
     * Stores {@link DoneGame} instance
     */
    private DoneGame label;

    /**
     * Remove border
     * <p>Set {@link GridBagLayout}</p>
     * <p>Set background</p>
     * @param windowSize size of dialog
     */
    public DialogContainer(int windowSize) {

        setBorder(BorderFactory.createEmptyBorder());
        setLayout(new GridBagLayout());
        setBounds(0, 0, windowSize, windowSize);

        setOpaque(false);

        setBackground(bgColor);
        setVisible(false);

        label = new DoneGame();

        add(label);
    }

    /**
     * Show the dialog that display if you won or not
     * @param isOpen if you have to open or close the dialog
     * @param isLoosed if you have won or lose the game
     */
    public void setDialog(boolean isOpen, boolean isLoosed) {
        setVisible(isOpen);

        label.setText(null);
        label.setText(isLoosed ? "Hai perso" : "Hai perso");

    }

    /**
     * Set transparent background
     */
    @Override
    public void paintComponent(Graphics g) {

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    /**
     * @return {@link DoneGame}
     */
    public DoneGame getDoneGame() {
        return label;
    }
}
