package main.containers;

import javax.swing.*;
import java.awt.*;


public class Content extends JPanel {

    /**
     * Container used to store the buttons
     * @param width the absolute width of the container
     * @param height the absolute height of the container
     * @param sideSize size of a border of the grid {@link GridLayout}
     */
    public Content(int width, int height, int sideSize) {

        setLayout(new GridLayout(sideSize, sideSize, 0, 0));

        setBorder(BorderFactory.createEmptyBorder());

        setBounds(0, width - height, width, height);

    }

}
