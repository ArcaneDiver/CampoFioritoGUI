package main.containers;

import main.components.Clock;
import main.components.FlagCounter;
import main.interfaces.Callback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Header extends JPanel {

    /**
     * Represents the background of this container
     * @see Color
     */
    private Color HEADER_BACKGROUND = new Color(74, 117, 44);

    /**
     * Stores if the mouse is onClick on this container
     */
    private boolean dragged = false;

    /**
     * Stores {@link Callback} used to follow the mouse while his clicking on this container
     *
     * @see #mouseFollower
     */
    private Callback callback;

    /**
     * Stores the location in the screen of the mouse when he`s clicking on this container
     *
     * @see Point
     */
    private Point clickLocation;

    /**
     * Stores instance of {@link FlagCounter}
     * @see #getFCounter()
     */
    private FlagCounter fCounter;

    /**
     * Stores instance of {@link Clock}
     * @see #getClock()
     */
    private Clock clock;

    /**
     * Set font style
     * <p>Remove border</p>
     * @param width width of container
     * @param height height of container
     * @param callback callback
     */
    public Header(int width, int height, Callback callback) {

        this.callback = callback;

        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBorder(BorderFactory.createEmptyBorder());
        setBounds(0, 0, width, height);


        fCounter = new FlagCounter();
        clock = new Clock();

        add(fCounter);
        add(clock);

        setBackground(HEADER_BACKGROUND);
        addMouseListener(mouseListener);

        mouseFollower.execute();
    }

    /**
     * Stores if this component his clicked and the relative position of the mouse
     *
     * @see #mouseFollower
     */
    private MouseAdapter mouseListener = new MouseAdapter() {

        public void mousePressed(MouseEvent e) {
            dragged = true;
            clickLocation = new Point(e.getX(), e.getY());
        }

        public void mouseReleased(MouseEvent e) {
            dragged = false;
            clickLocation = null;
        }

    };

    /**
     * Thread used to move the Frame in order to follow the mouse
     *
     * @see SwingWorker
     */
    private SwingWorker<Object, Object> mouseFollower = new SwingWorker<Object, Object>() {

        @Override
        protected Object doInBackground() throws Exception {
            while (true) {

                if (dragged) {
                    Point location = MouseInfo.getPointerInfo().getLocation();

                    callback.moveScreen(location, clickLocation);
                }
                Thread.sleep(1000 / 40);
            }
        }

    };

    /**
     * @return the instance of {@link FlagCounter}
     */
    public FlagCounter getFCounter() {
        return fCounter;
    }

    /**
     * @return the instance of {@link Clock}
     */
    public Clock getClock() {
        return clock;
    }
}
