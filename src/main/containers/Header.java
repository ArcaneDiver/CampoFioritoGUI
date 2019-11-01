package main.containers;

import main.components.Clock;
import main.components.FlagCounter;
import main.interfaces.Callback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Header extends JPanel {

    private Color HEADER_BACKGROUND = new Color(74, 117, 44);

    private boolean dragged = false;

    private Callback callback;
    private Point clickLocation;

    private FlagCounter fCounter;
    private Clock clock;

    public Header(int maxWidth, int maxHeight, int sideSize, Callback callback) {

        this.callback = callback;

        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBorder(BorderFactory.createEmptyBorder());
        setBounds(0, 0, maxWidth, maxHeight);


        fCounter = new FlagCounter();
        clock = new Clock();

        add(fCounter);
        add(clock);

        setBackground(HEADER_BACKGROUND);
        addMouseListener(mouseListener);

        sw.execute();
    }

    private MouseAdapter mouseListener = new MouseAdapter () {

        public void mousePressed(MouseEvent e) {
            dragged = true;
            clickLocation = new Point(e.getX(), e.getY());
        }

        public void mouseReleased (MouseEvent e) {
            dragged = false;
            clickLocation = null;
        }

    };

    private SwingWorker<Object, Object> sw = new SwingWorker<Object, Object>() {

        @Override
        protected Object doInBackground() throws Exception {
            while (true) {

                if(dragged) {
                    Point location = MouseInfo.getPointerInfo().getLocation();

                    callback.moveScreen(location, clickLocation);
                }
                Thread.sleep(1000/40);
            }
        }

    };

    public FlagCounter getFCounter() {
        return fCounter;
    }

    public Clock getClock() {
        return clock;
    }
}
