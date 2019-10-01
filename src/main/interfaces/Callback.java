package main.interfaces;

import java.awt.*;

public interface Callback {
    public void moveScreen( Point locationInTheScreen, Point locationInTheFrame );
    public void exitFromFrame();
}
