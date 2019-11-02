package main.components;

import javax.swing.*;
import java.awt.*;

public class FlagCounter extends JLabel {

    /**
     * Represents the icon that the counter will display
     * @see ImageIcon
     */
    private final ImageIcon flagIcon = new ImageIcon( this.getClass().getResource("../res/flag.png") );

    /**
     * Actual number of flags
     * @see #setNumberOfFlags(int number)
     * @see #getNumberOfFlags()
     */
    private int numberOfFlags = 0;

    /**
     * Set icon and default text
     * <p>Set font style and color</p>
     */
    public FlagCounter() {
        setIcon(flagIcon);

        setText(" 0");

        setForeground(Color.WHITE);

        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

    }

    /**
     * Display the new number of flag and store it {@link #numberOfFlags}
     * @param number number of flag to set
     */
    public void setNumberOfFlags(int number) {
        setIcon(null);
        setText(null);

        setIcon(flagIcon);
        setText(" " + number);

        numberOfFlags = number;
    }

    /**
     * @return {@link #numberOfFlags}
     */
    public int getNumberOfFlags() {
        return numberOfFlags;
    }
}
