package main;

import javax.swing.*;

abstract class AbstractCampoFiorito extends JFrame{
    public final int size = 25;
    public final int numberOfButton = size * size;

    public final static int MAX_WINDOW_SIZE = 1000;
    public final static int HEADER_HEIGHT = 75;
    public final static int CONTENT_HEIGHT = MAX_WINDOW_SIZE - HEADER_HEIGHT;



    public AbstractCampoFiorito(String title) {
        super(title);
        
    }

 
}


