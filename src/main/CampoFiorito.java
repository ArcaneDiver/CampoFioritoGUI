package main;

import main.components.Button;

import main.components.ExitButton;
import main.components.FlagCounter;
import main.containers.Header;
import main.containers.Content;
import main.containers.DialogContainer;
import main.interfaces.Callback;

import javax.swing.*;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CampoFiorito extends AbstractCampoFiorito {


   
    private JLayeredPane container = new JLayeredPane();
    private Header header;
    private Content content;
    private ExitButton exit;
    private DialogContainer dialog;

    private Button[][] buttons = new Button[size][size];

    private boolean dialogOpened = false;

    public static void main(String[] args) {
        new CampoFiorito();
        
    }

    public CampoFiorito() {
        super("Campo fiorito");

        setUndecorated(true);

        setBounds(20, 20, MAX_WINDOW_SIZE, MAX_WINDOW_SIZE);

        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        //container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        header = new Header(MAX_WINDOW_SIZE, HEADER_HEIGHT, size, new Callback() {

            @Override
            public void moveScreen(Point locationInTheScreen, Point locationInTheFrame) {
                setBounds(locationInTheScreen.x - locationInTheFrame.x, locationInTheScreen.y - locationInTheFrame.y,
                        MAX_WINDOW_SIZE, MAX_WINDOW_SIZE);
            }

            @Override
            public void exitFromFrame() {
            }
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

        exit = new ExitButton(new Callback() {
            @Override
            public void moveScreen(Point locationInTheScreen, Point locationInTheFrame) {
            }

            @Override
            public void exitFromFrame() {
                dispose();
            }

        }, 40, MAX_WINDOW_SIZE);

        dialog = new DialogContainer(MAX_WINDOW_SIZE);

        container.add(header, JLayeredPane.DEFAULT_LAYER);
        container.add(content, JLayeredPane.DEFAULT_LAYER);
        container.add(exit, JLayeredPane.MODAL_LAYER);
        container.add(dialog, JLayeredPane.MODAL_LAYER);

        add(container);
        setVisible(true);

        
    }

  

    private MouseAdapter mouseListenerContent = new MouseAdapter () {

        private Color lastBackground;

        public void mouseClicked (MouseEvent e) {

            if(dialogOpened) return;

            Button btClicked = (Button) e.getSource();


            if(SwingUtilities.isRightMouseButton(e)) {

                final FlagCounter fCounter = header.fCounter;

                fCounter.setNumberOfFlags(!btClicked.isFlagged() ? fCounter.getNumberOfFlags() + 1 : fCounter.getNumberOfFlags() - 1);

                btClicked.setFlag( !btClicked.isFlagged() );

            } else if ( !btClicked.isFlagged() ){

                if (btClicked.getStatus() == 0) {

                    recursiveExpansion(btClicked, true);
                    cleanCheckedButtons();

                } else {

                    int status = btClicked.showItsRealNature();

                    if(status == 1) {

                        setButtonsStatus(false);
                        setDialogOpened(true);

                    }
                }
            }

        }

        public void mouseEntered(MouseEvent e) {

            if(dialogOpened) return;

            Button btClicked = (Button) e.getSource();

            lastBackground = btClicked.getBackground();

            btClicked.setBackground(btClicked.getBackground().brighter());
        }

        public void mouseExited (MouseEvent e) {
            if(dialogOpened) return;

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
                Le * sono celle in cui non si espande ma posso comunque scrivere

                |  -  |  -  |  -  |
                |  *  |  O  |  *  |
                |  -  |  -  |  -  |
                |  O  |  §  |  O  |
                |  -  |  -  |  -  |
                |  *  |  O  |  *  |
                |  -  |  -  |  -  |

     **/
    private void recursiveExpansion(Button bt, boolean expandible) {
        Integer number = getNumberOfNearBombs(bt);

        int[] index = indexOf2D(bt);

        if( expandible ) bt.setChecked(true);

        if(number == 0 && expandible) {
            bt.showItsRealNature();


            for( int i = index[0] - 1; i < index[0] + 2; i++) {

                for( int k = index[1] - 1; k < index[1] + 2; k++) {


                    if( k == index[1] && i == index[0] || ( i == -1 || k == -1 || k == size || i == size ) || buttons[i][k].getChecked()) continue;

                    if(
                            (i == index[0] - 1 && k == index[1] - 1)
                            ||
                            (i == index[0] + 1 && k == index[1] - 1)
                            ||
                            (i == index[0] - 1 && k == index[1] + 1)
                            ||
                            (i == index[0] + 1 && k == index[1] + 1)
                    ) {
                        recursiveExpansion(buttons[i][k], false);
                    } else {
                        recursiveExpansion(buttons[i][k], true);
                    }

                }
            }

        } else {
            if( number > 0) bt.setSafeText(number.toString());

        }


    }

    private void cleanCheckedButtons() {
        for( int i = 0; i < size; i++ ) {
            for( int k = 0; k < size; k++ ) {
                buttons[i][k].setChecked(false);
            }
        }
    }

    private void setDialogOpened(boolean isLoosed) {
        dialog.setDialog(true, isLoosed);

        dialogOpened = isLoosed;
    }

    private void setButtonsStatus(boolean status) {
        for(Button[] arr : buttons)
            for(Button e : arr)
                e.setDisabled(status);
    }
}