package componets;

import javax.swing.*;
import java.awt.*;

public class FlagCounter extends JLabel {

    private final ImageIcon flagIcon = new ImageIcon( this.getClass().getResource("../res/flag.png") );;

    public FlagCounter() {
        setIcon(flagIcon);

        setText(" Number of flags: ");

        setForeground(Color.WHITE);

        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
    }

    public void changeNumberOfFlags(int number) {
        setIcon(null);
        setText(null);

        setIcon(flagIcon);
        setText(" Number of flags: " + number);
    }
}
