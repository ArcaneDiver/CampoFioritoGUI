package main.components;

import javax.swing.*;
import java.awt.*;

public class DoneGame extends JLabel {

    public DoneGame() {
        super("Hai vinto", SwingConstants.CENTER);

        setFont( new Font(Font.SANS_SERIF, Font.BOLD, 30) );

        setForeground(new Color(255, 255, 255));

    }
/*
    public void paintComponent(Graphics g) {
        System.out.println("Reprint||!!");
        super.paintComponent(g);
    }*/
}
