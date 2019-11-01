package main;


import main.components.*;
import main.components.Button;
import main.containers.*;
import main.interfaces.Callback;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CampoFiorito extends AbstractCampoFiorito {

    private JLayeredPane container = new JLayeredPane();
    private Header header;
    private Content content;
    private ExitButton exit;
    private DialogContainer dialog;

    private Button[][] buttons = new Button[size][size];

    private boolean boardGenerated;
    private boolean dialogOpened;

    private AudioController audioController;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CampoFiorito::new);
    }

    public CampoFiorito() {
        super("Campo fiorito");

        audioController = new AudioController();

        setUndecorated(true);

        setBounds(20, 20, MAX_WINDOW_SIZE, MAX_WINDOW_SIZE);

        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));


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


        startNewGame();


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


    private void startNewGame() {
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {

                if (buttons[i][k] != null) content.remove(buttons[i][k]);

                Clock clock = header.getClock();
                clock.stopClock();
                clock.startClock();


                boolean even = ((k + 1) % 2 == 0 && (i % 2 == 0)) || ((k + 1) % 2 != 0 && (i % 2 != 0));

                buttons[i][k] = new Button(even);
                buttons[i][k].addMouseListener(mouseListenerContent);

                dialogOpened = false;
                boardGenerated = false;

                content.add(buttons[i][k]);

            }
        }
    }

    private MouseAdapter mouseListenerContent = new MouseAdapter () {

        private Color lastBackground;

        public void mouseClicked (MouseEvent e) {

            if(dialogOpened) return;

            Button btClicked = (Button) e.getSource();


            if(SwingUtilities.isRightMouseButton(e)) {

                FlagCounter fCounter = header.getFCounter();

                fCounter.setNumberOfFlags(!btClicked.isFlagged() ? fCounter.getNumberOfFlags() + 1 : fCounter.getNumberOfFlags() - 1);

                btClicked.setFlag( !btClicked.isFlagged() );

            } else if ( !btClicked.isFlagged() ){

                if (btClicked.getStatus() == 0) {
                    if(!boardGenerated) {
                        generateBoard(indexOf2D(btClicked));
                        //openBoard();
                    }

                    audioController.play("empty");

                    recursiveExpansion(btClicked, true);
                    cleanCheckedButtons();


                } else {
                    audioController.play("bomb");
                    btClicked.showItsRealNature();
                    loose();
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

    private void generateBoard(int[] index) {

        recGenBoardWhereClick(index, (int) (Math.random() * 10 + 5));
        fillTheRestOfTheBoard();
        cleanCheckedButtons();
        boardGenerated = true;
    }

    private void recGenBoardWhereClick(int[] index, int remaining) {
        if(remaining == 0) return;

        Button bt = buttons[index[0]][index[1]];
        bt.setStatus((byte) 0);

        if(Math.random() * 100 < 20 && remaining < 3) return;

        ArrayList<Button> listNeighbors = getNeighbors(index);

        for (Button neighbor : listNeighbors) {
            neighbor.setChecked(true);
            //bt.setStatus((byte) 0);
            recGenBoardWhereClick(indexOf2D(neighbor), remaining - 1);
        }
    }

    private void fillTheRestOfTheBoard() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(!buttons[i][j].getChecked()) {
                    byte status = Math.random() * 100 < 20 ? (byte) 1 : (byte) 0;
                    buttons[i][j].setStatus(status);
                }
            }
        }
    }

    private ArrayList<Button> getNeighbors(int[] index) {
        ArrayList<Button> list = new ArrayList<>();

        for ( int i = 0; i < 3; i++ ) {
            for (int j = 0; j < 3; j++) {


                if( (i == 0 && j == 0) || (i == 0 && j == 2) || (i == 2 && j == 0) || (i == 2 && j == 2)) continue;
                if(i == j && i == 1) continue;

                int x = i + index[0] - 1;
                int y = j + index[1] - 1;
                if(!(x < 0 || x >= size || y < 0 || y >= size)) {
                    if(!buttons[x][y].getChecked()) list.add(buttons[x][y]);
                }
            }
        }

        return list;

    }

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
        int numberOfNearBombs = getNumberOfNearBombs(bt);

        int[] index = indexOf2D(bt);

        if( expandible ) bt.setChecked(true);

        if(numberOfNearBombs == 0 && expandible) {
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
            if(numberOfNearBombs > 0) bt.setText(Integer.toString(numberOfNearBombs));
        }


    }

    private void cleanCheckedButtons() {
        for( int i = 0; i < size; i++ ) {
            for( int k = 0; k < size; k++ ) {
                buttons[i][k].setChecked(false);
            }
        }
    }

    private void loose() {
        setButtonsStatus(false);
        openDialog(true);
    }

    private void openDialog(boolean isLoosed) {

        header.getClock().stopClock();

        dialog.setDialog(true, isLoosed);

        dialogOpened = isLoosed;
    }

    private void setButtonsStatus(boolean status) {
        for(Button[] arr : buttons)
            for(Button e : arr)
                e.setDisabled(status);
    }

    /**
     * Debug utility
     */
    private void openBoard() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                buttons[i][j].showItsRealNature();
            }
        }
    }
}