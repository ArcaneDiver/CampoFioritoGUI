package main.components;

import javax.swing.*;
import java.awt.*;

public class Clock extends JLabel {

    private ImageIcon clockIcon;

    private SwingWorker<Object, Object> clock;

    /**
     * Init clockIcon object
     * <p>Display the icon and also default text</p>
     * <p>Change the font color, size and format</p>
     */
    public Clock() {

        clockIcon = new ImageIcon(getClass().getResource("../res/clock.png"));

        setIcon(clockIcon);
        setText(" 0");

        setForeground(Color.WHITE);

        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
    }

    /**
     * Create a new thread that display the time that has passed from the start of the game and start it
     * @see SwingWorker
     */
    public void startClock() {
        clock = new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() throws Exception {

                int counter = 0;
                while(true) {

                    counter++;

                    setText(Integer.toString(counter));

                    Thread.sleep(1000);
                }
            }
        };

        clock.execute();
    }

    /**
     * Stop the clock and dismiss the thread
     * @see SwingWorker
     */
    public void stopClock() {
        if(clock != null) clock.cancel(true);
    }

    /**
     * Override the method {@link JLabel#setText(String)}
     * <p> Method used for assign text keeping icons </p>
     * @param text
     */
    @Override
    public void setText(String text) {
        setIcon(null);
        super.setText(null);

        setIcon(clockIcon);
        super.setText(text);
    }
}
