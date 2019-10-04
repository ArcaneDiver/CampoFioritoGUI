package main.containers;

import main.components.DoneGame;

import javax.swing.*;
import java.awt.*;

public class DialogContainer extends JPanel {

    private Color bgColor = new Color(0, 0, 0, 125);

    public DoneGame label;

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

    public void setDialog(boolean isOpen, boolean isLoosed) {
        setVisible(isOpen);

        System.out.println("Called");
        label.setText(null);
        label.setText(isLoosed ? "Hai perso" : "Hai perso");
        label.setBackground(new Color(1, 255 ,1));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
