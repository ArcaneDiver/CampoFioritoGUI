package main.components;

import javax.swing.*;
import java.awt.*;

public class DoneGame extends JLabel {

    public DoneGame() {
        super("Hai vinto", SwingConstants.CENTER);

        setFont( new Font(Font.SANS_SERIF, Font.BOLD, 30) );

        setForeground(new Color(255, 255, 255));

    }

}
