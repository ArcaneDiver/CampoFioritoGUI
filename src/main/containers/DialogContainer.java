package main.containers;

import main.components.DoneGame;

import javax.swing.*;
import java.awt.*;

public class DialogContainer extends JPanel {

    private Color bgColor = new Color(0, 0, 0, 125);

    private DoneGame label;

    public DialogContainer(int windowSize) {

        setBorder(BorderFactory.createEmptyBorder());
        setLayout(new GridBagLayout());
        setBounds(0, 0, windowSize, windowSize);

        setOpaque(true);

        setBackground(bgColor);
        setVisible(false);

        label = new DoneGame();


        add(label);
    }

    public void setDialog(boolean isOpen, boolean looseOrWin) {
        setVisible(isOpen);
        label.setText(null);
        label.setText(looseOrWin ? "Hai perso" : "Hai perso");
    }
}
