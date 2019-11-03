package main.components;

import javax.swing.*;
import java.awt.*;

public class RetryButton extends JButton {

    /**
     * Set background color and remove border
     * <p>Change font style, color and size</p>
     */
    public RetryButton() {
        super("Retry Game");
        setOpaque(false);

        setBackground(new Color(0, 0, 0, 0));
        setForeground(new Color(255, 255, 255));

        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

        setBorder(BorderFactory.createEmptyBorder());

    }
}
