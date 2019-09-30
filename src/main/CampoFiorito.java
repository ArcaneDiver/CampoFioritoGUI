package main;

import components.Button;

import components.FlagCounter;
import containers.ExitContainer;
import containers.Header;
import containers.Content;
import interfaces.Callback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CampoFiorito extends AbstractCampoFiorito {


    private JLayeredPane container = new JLayeredPane();
    private Header header;
    private Content content;
    private ExitContainer exit;

    private Button[][] buttons = new Button[size][size];

    public static void main(String[] args) {
        new CampoFiorito();
    }

    public CampoFiorito() {
        super("Campo fiorito");

        setSize(MAX_WINDOW_SIZE, MAX_WINDOW_SIZE);

        setUndecorated(true);

        setBounds(20, 20, MAX_WINDOW_SIZE, MAX_WINDOW_SIZE);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        header = new Header(MAX_WINDOW_SIZE, HEADER_HEIGHT, size, new Callback() {
            @Override
            public void moveScreen(Point locationInTheScreen, Point locationInTheFrame) {
                setBounds(locationInTheScreen.x - locationInTheFrame.x, locationInTheScreen.y - locationInTheFrame.y, MAX_WINDOW_SIZE, MAX_WINDOW_SIZE);
            }

            @Override
            public void exitFromFrame() {}
        });

        content = new Content(MAX_WINDOW_SIZE, CONTENT_HEIGHT, size);


        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {

                boolean even = ((k + 1) % 2 == 0 && (i % 2 == 0)) || ((k + 1) % 2 != 0 && (i % 2 != 0));

                buttons[i][k] = new Button(even);
                buttons[i][k].addMouseListener(mouseListenerContent);

                content.add(buttons[i][k]);
            }
        }

        exit = new ExitContainer(MAX_WINDOW_SIZE, new Callback() {
            @Override
            public void moveScreen(Point locationInTheScreen, Point locationInTheFrame) {

            }

            @Override
            public void exitFromFrame() {
                dispose();
            }
        });

        container.add(header, 2);
        container.add(content, 1);
        container.add(exit, 0);

        /*container.setLayer(header, 0);
        container.setLayer(content, 1);
        container.setLayer(exit, 2);*/


        add(container);
        setVisible(true);
    }

    private MouseAdapter mouseListenerContent = new MouseAdapter () {

        private Color lastBackground;

        public void mouseClicked (MouseEvent e) {
            Button btClicked = (Button) e.getSource();


            if(SwingUtilities.isRightMouseButton(e)) {

                final FlagCounter fCounter = header.fCounter;

                fCounter.setNumberOfFlags(!btClicked.isFlagged() ? fCounter.getNumberOfFlags() + 1 : fCounter.getNumberOfFlags() - 1);

                btClicked.setFlag( !btClicked.isFlagged() );

            } else if ( !btClicked.isFlagged() ){
                if (btClicked.getStatus() == 0) {
                    recursiveExpansion(btClicked);
                    cleanCheckedButtons();
                } else {
                    btClicked.showItsRealNature();
                }
            }

        }

        public void mouseEntered(MouseEvent e) {
            Button btClicked = (Button) e.getSource();

            lastBackground = btClicked.getBackground();

            btClicked.setBackground(btClicked.getBackground().brighter());
        }

        public void mouseExited (MouseEvent e) {
            Button btClicked = (Button) e.getSource();
            if( lastBackground.brighter().getRGB() == btClicked.getBackground().getRGB() ) btClicked.setBackground(lastBackground);
        }


    };



    private int[] indexOf2D(Button toSearch) {
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {

                if (buttons[i][k] == toSearch) return new int[]{i, k};

            }
        }
        return new int[]{-1, -1};
    }

    private int getNumberOfNearBombs(Button bt) {
        int[] index = indexOf2D(bt);


        int count = 0;

        for( int i = index[0] - 1; i < index[0] + 2; i++) {

            for( int k = index[1] - 1; k < index[1] + 2; k++) {

                if( k == index[1] && i == index[0] || ( i == -1 || k == -1 || k == size || i == size ) ) continue;


                if(buttons[i][k].getStatus() == 1) count++;
            }
        }

        return count;
    }

    /**
                Le O sono le celle dove esegui l`espansione

                | - | - | - |
                | * | O | * |
                | - | - | - |
                | O | * | O |
                | - | - | - |
                | * | O | * |
                | - | - | - |
     **/
    private void recursiveExpansion(Button bt) {
        Integer number = getNumberOfNearBombs(bt);

        int[] index = indexOf2D(bt);

        bt.setChecked(true);

        if(number == 0) {
            bt.showItsRealNature();


            for( int i = index[0] - 1; i < index[0] + 2; i++) {

                for( int k = index[1] - 1; k < index[1] + 2; k++) {

                    if( (i == index[0] - 1 && k == index[1] - 1) || (i == index[0] + 1 && k == index[1] - 1) || (i == index[0] - 1 && k == index[1] + 1) || (i == index[0] + 1 && k == index[1] + 1)) continue;
                    if( k == index[1] && i == index[0] || ( i == -1 || k == -1 || k == size || i == size ) || buttons[i][k].getChecked()) continue;

                    recursiveExpansion(buttons[i][k]);

                }
            }

        } else {
            bt.setText(number.toString());
        }


    }

    private void cleanCheckedButtons() {
        for( int i = 0; i < size; i++ ) {
            for( int k = 0; k < size; k++ ) {
                buttons[i][k].setChecked(false);
            }
        }
    }
}