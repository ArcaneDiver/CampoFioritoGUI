package main.containers;

import main.components.DoneGame;

import javax.swing.*;
import java.awt.*;

public class DialogContainer extends JPanel {

    private Color bgColor = new Color(0, 0, 0, 125);

    private DoneGame label;
    private Dialog dialog;

    public DialogContainer(int windowSize) {

        setBorder(BorderFactory.createEmptyBorder());
        setLayout(new GridBagLayout());
        setBounds(0, 0, windowSize, windowSize);

        setOpaque(false);

        setBackground(bgColor);
        setVisible(false);

        dialog = new Dialog();


        label = new DoneGame();

        dialog.add(label);
        add(dialog);
    }

    public void setDialog(boolean isOpen, boolean isLoosed) {
        setVisible(isOpen);

        label.setText(null);
        label.setText(isLoosed ? "Hai perso" : "Hai perso");

    }

    @Override
    public void paintComponent(Graphics g) {

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    public DoneGame getDoneGame() {
        return label;
    }
}
