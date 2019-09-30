package components;

import interfaces.Callback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ExitButton extends JButton {

    private ImageIcon icon;

    Callback cb;

    public ExitButton(Callback cb, int s) {

        icon = new ImageIcon( new ImageIcon( this.getClass().getResource("../res/XIcon.png") ).getImage().getScaledInstance( s, s, Image.SCALE_SMOOTH ) );

        this.cb = cb;

        setIcon(icon);
        addMouseListener(mouseListener);

        Dimension d = new Dimension(s, s);

        setSize(d);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);

        setMargin( new Insets(1, 1, 1, 1));

        setBackground(new Color(74, 117, 44));
        setBorder(BorderFactory.createEmptyBorder());

        setOpaque(true);
        //setBounds(1000 - s, 0, s, s);

        setSize(s, s);
    }

    private MouseAdapter mouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            cb.exitFromFrame();
        }
    };

}
