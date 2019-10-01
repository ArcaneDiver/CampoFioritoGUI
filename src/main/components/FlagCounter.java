package main.components;

import javax.swing.*;
import java.awt.*;

public class FlagCounter extends JLabel {

    private final ImageIcon flagIcon = new ImageIcon( this.getClass().getResource("../res/flag.png") );;

    private int numberOfFlags = 0;
    public FlagCounter() {
        setIcon(flagIcon);

        setText(" 0");

        setForeground(Color.WHITE);

        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

    }

    public void setNumberOfFlags(int number) {
        setIcon(null);
        setText(null);

        setIcon(flagIcon);
        setText(" " + number);

        numberOfFlags = number;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }
}
