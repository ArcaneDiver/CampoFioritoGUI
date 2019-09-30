package main;

import javax.swing.*;
import java.awt.*;

abstract class AbstractCampoFiorito extends JFrame {
    public int size = 25;
    public int numberOfButton = size * size;

    public static int MAX_WINDOW_SIZE = 1000;
    public static int HEADER_HEIGHT = 75;
    public static int CONTENT_HEIGHT = MAX_WINDOW_SIZE - HEADER_HEIGHT;



    public AbstractCampoFiorito(String title) {
        super(title);
    }
}
