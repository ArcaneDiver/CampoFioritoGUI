package main.components;

import javax.swing.*;
import java.awt.*;

public class Clock extends JLabel {

    private ImageIcon clockIcon;

    public Clock() {

        clockIcon = new ImageIcon(getClass().getResource("../res/clock.png"));

        setIcon(clockIcon);
        setText(" 0");

        setForeground(Color.WHITE);

        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
    }

    private SwingWorker<Object, Object> clock;

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

    public void stopClock() {
        if(clock != null) clock.cancel(true);
    }

    @Override
    public void setText(String text) {
        setIcon(null);
        super.setText(null);

        setIcon(clockIcon);
        super.setText(text);
    }
}
