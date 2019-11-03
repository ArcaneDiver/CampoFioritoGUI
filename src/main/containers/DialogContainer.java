package main.containers;

import main.components.DoneGame;
import main.components.RetryButton;
import main.interfaces.Callback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
     * @param windowSize size of the window
     */
    public DialogContainer(int windowSize, Callback callback) {

        setBorder(BorderFactory.createEmptyBorder());
        setLayout(new GridBagLayout());
        setBounds(0, 0, windowSize, windowSize);

        setOpaque(false);

        setBackground(bgColor);
        setVisible(false);

        label = new DoneGame();

        RetryButton retry = new RetryButton();
        retry.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                callback.retryGame();
            }
        });

        // To center the two labels
        GridBagConstraints gL = new GridBagConstraints();
        gL.gridx = 2;
        GridBagConstraints gR = new GridBagConstraints();
        gR.gridx = 2;

        add(label, gL);
        add(retry, gR);
    }

    /**
     * Show the dialog that display if you won or not
     * @param isLoosed if you have won or loose the game
     * @param score number of second in which the player complete the game
     */
    public void setDialog(boolean isLoosed, int score) {

        setVisible(true);

        label.setText(null);
        label.setText(isLoosed ? "You loose" : "You won in " + score + " seconds");

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


}
