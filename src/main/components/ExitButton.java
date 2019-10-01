package main.components;

import main.interfaces.Callback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ExitButton extends JButton {

    private ImageIcon icon;

    Callback cb;

    public ExitButton(Callback cb, int s, int windowSize) {

        icon = new ImageIcon( new ImageIcon( this.getClass().getResource("../res/XIcon.png") ).getImage().getScaledInstance( s, s, Image.SCALE_SMOOTH ) );

        this.cb = cb;

        setIcon(icon);

        addMouseListener(mouseListener);

        setMargin( new Insets(0, 0, 0, 0));

        setBackground(new Color(74, 117, 44));
        setBorder(BorderFactory.createEmptyBorder());

        setOpaque(true);
        setBounds(windowSize - s, 0, s, s);

        setSize(s, s);
    }

    private MouseAdapter mouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            cb.exitFromFrame();
        }
    };

}
