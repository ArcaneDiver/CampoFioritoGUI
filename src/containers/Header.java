package containers;

import componets.FlagCounter;
import interfaces.Callback;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Header extends JPanel {

    private Color HEADER_BACKGROUND = new Color(74, 117, 44);

    private boolean dragged = false;

    private Callback callback;
    private Point clickLocation;

    public Header(int maxWidth, int maxHeight, int sideSize, Callback callback) {

        this.callback = callback;

        setLayout(new FlowLayout(FlowLayout.LEFT));

        setBorder(BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(maxWidth, maxHeight));
        setMaximumSize(new Dimension(maxWidth, maxHeight));
        setMinimumSize(new Dimension(maxWidth, maxHeight));

        add(new FlagCounter(), FlowLayout.LEFT);
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

    SwingWorker<Object, Object> sw = new SwingWorker<Object, Object>() {
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

}
