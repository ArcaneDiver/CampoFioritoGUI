package main.interfaces;

import java.awt.*;

public interface Callback {

    void moveScreen(Point locationInTheScreen, Point locationInTheFrame);
    void exitFromFrame();
    void retryGame();
}
