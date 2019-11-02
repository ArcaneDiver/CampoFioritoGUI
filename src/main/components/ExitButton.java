package main.components;

import main.interfaces.Callback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ExitButton extends JButton {

    /**
     * Represents the icon that the button will display
     * @see ImageIcon
     */
    private ImageIcon icon;

    /**
     * Callback used for call the function {@link JFrame#dispose()}
     */
    private Callback cb;

    /**
     * @param cb callback instance
     * @param buttonSize size
     * @param windowSize size of the window used for calculate the position of the button in the screen
     */
    public ExitButton(Callback cb, int buttonSize, int windowSize) {

        icon = new ImageIcon( new ImageIcon( this.getClass().getResource("../res/XIcon.png") ).getImage().getScaledInstance( buttonSize, buttonSize, Image.SCALE_SMOOTH ) );

        this.cb = cb;

        setIcon(icon);

        addMouseListener(mouseListener);

        setMargin( new Insets(0, 0, 0, 0));

        setBackground(new Color(74, 117, 44));
        setBorder(BorderFactory.createEmptyBorder());

        setOpaque(true);
        setBounds(windowSize - buttonSize, 0, buttonSize, buttonSize);

    }

    /**
     * Handle click and dispose the window
     * @see MouseAdapter
     */
    private MouseAdapter mouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            cb.exitFromFrame();
        }
    };

}
