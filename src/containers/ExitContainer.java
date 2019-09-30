package containers;

import components.ExitButton;
import interfaces.Callback;

import javax.swing.*;
import java.awt.*;

public class ExitContainer extends JPanel{

    private Point position;

    private int size = 30;

    public ExitContainer(int maxWindowSize, Callback cb) {

        Dimension d = new Dimension(size, size);

        position = new Point(maxWindowSize - size, 0);

        setSize(d);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);

        setBounds(position.x, position.y, size, size);

        setLayout(null);

        setBackground(new Color(74, 117, 44));
        add(new ExitButton(cb, size));
    }


}
