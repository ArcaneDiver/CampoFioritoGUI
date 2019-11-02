package main;


import main.components.*;
import main.components.Button;
import main.containers.*;
import main.interfaces.Callback;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CampoFiorito extends JFrame {

    // Containers
    private JLayeredPane container;
    private Header header;
    private Content content;
    private ExitButton exit;
    private DialogContainer dialog;

    /**
     * Bi-dimensional array that store the instances of {@link Button}
     */
    private Button[][] buttons = new Button[Game.size][Game.size];

    /**
     * If the board is generated
     * @see #generateBoard(int[] index) 
     */
    private boolean boardGenerated;

    /**
     * If the dialog is opened
     * @see #openDialog(boolean) 
     */
    private boolean dialogOpened;

    /**
     * Instance of {@link AudioController}
     */
    private AudioController audioController;

    /**
     * Handle mouse inputs
     */
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

                    setButtonsStatus(false);
                    openDialog(true);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CampoFiorito::new);
    }

    /**
     * Init game.
     * <p>Init window.</p>
     */
    public CampoFiorito() {
        super("Campo fiorito");

        audioController = new AudioController();

        setUndecorated(true);

        setBounds(20, 20, Game.MAX_WINDOW_SIZE, Game.MAX_WINDOW_SIZE);

        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        container = new JLayeredPane();


        header = new Header(Game.MAX_WINDOW_SIZE, Game.HEADER_HEIGHT, new Callback() {

            @Override
            public void moveScreen(Point locationInTheScreen, Point locationInTheFrame) {
                setBounds(locationInTheScreen.x - locationInTheFrame.x, locationInTheScreen.y - locationInTheFrame.y,
                        Game.MAX_WINDOW_SIZE, Game.MAX_WINDOW_SIZE);
            }

            @Override
            public void exitFromFrame() {}
        });
        
        content = new Content(Game.MAX_WINDOW_SIZE, Game.CONTENT_HEIGHT, Game.size);


        startNewGame();


        exit = new ExitButton(new Callback() {
            @Override
            public void moveScreen(Point locationInTheScreen, Point locationInTheFrame) {
            }

            @Override
            public void exitFromFrame() {
                dispose();
            }

        }, 40, Game.MAX_WINDOW_SIZE);
        dialog = new DialogContainer(Game.MAX_WINDOW_SIZE);

        container.add(header, JLayeredPane.DEFAULT_LAYER);
        container.add(content, JLayeredPane.DEFAULT_LAYER);
        container.add(exit, JLayeredPane.MODAL_LAYER);
        container.add(dialog, JLayeredPane.MODAL_LAYER);

        add(container);
        setVisible(true);

    }

    /**
     * Start new game
     * <p>Remove old {@link Button} if they exists</p>
     * <p>Restart a new {@link Clock}</p>
     * <p>Set to 0 the number of flags of {@link FlagCounter}</p>
     * <p>Create new {@link Button}</p>
     *
     * @see #setButtonsStatus(boolean isEnabled)
     */
    private void startNewGame() {
        for (int i = 0; i < Game.size; i++) {
            for (int k = 0; k < Game.size; k++) {

                if (buttons[i][k] != null) content.remove(buttons[i][k]);

                Clock clock = header.getClock();
                clock.stopClock();
                clock.startClock();

                FlagCounter flagCounter = header.getFCounter();
                flagCounter.setNumberOfFlags(0);


                boolean even = ((k + 1) % 2 == 0 && (i % 2 == 0)) || ((k + 1) % 2 != 0 && (i % 2 != 0));

                buttons[i][k] = new Button(even);
                buttons[i][k].addMouseListener(mouseListenerContent);

                dialogOpened = false;
                boardGenerated = false;


                content.add(buttons[i][k]);
            }
        }

        setButtonsStatus(true);

    }

    /**
     * Generate a new board from the given button index
     * @param index represent the position of the button in {@link #buttons}
     */
    private void generateBoard(int[] index) {

        recGenBoardWhereClick(index, (int) (Math.random() * 10 + 5));
        fillTheRestOfTheBoard();
        cleanCheckedButtons();
        boardGenerated = true;
    }

    /**
     * Recursive function that generate the board from the button that has been clicked
     * @param index represent the position of the button in {@link #buttons}
     * @param remaining remaining calls for branch
     */
    private void recGenBoardWhereClick(int[] index, int remaining) {
        if(remaining == 0) return;

        Button bt = buttons[index[0]][index[1]];
        bt.setStatus((byte) 0);

        ArrayList<Button> listNeighbors = getNeighbors(index);

        for (Button neighbor : listNeighbors) {
            neighbor.setChecked(true);
            recGenBoardWhereClick(indexOf2D(neighbor), remaining - 1);
        }
    }

    /**
     * Randomly fill the rest of the board that has not been filled
     */
    private void fillTheRestOfTheBoard() {
        for(int i = 0; i < Game.size; i++) {
            for(int j = 0; j < Game.size; j++) {
                if(!buttons[i][j].getChecked()) {
                    byte status = Math.random() * 100 < 20 ? (byte) 1 : (byte) 0;
                    buttons[i][j].setStatus(status);
                }
            }
        }
    }

    /**
     * Get the buttons near the given index that has not already been checked
     * @param index position in {@link #buttons}
     * @return an {@link ArrayList} in which the buttons near it are stored
     */
    private ArrayList<Button> getNeighbors(int[] index) {
        ArrayList<Button> list = new ArrayList<>();

        for ( int i = 0; i < 3; i++ ) {
            for (int j = 0; j < 3; j++) {

                if((i == 0 && j == 0) || (i == 0 && j == 2) || (i == 2 && j == 0) || (i == 2 && j == 2)) continue;
                if(i == j && i == 1) continue;

                int x = i + index[0] - 1;
                int y = j + index[1] - 1;

                if(!(x < 0 || x >= Game.size || y < 0 || y >= Game.size))
                    if(!buttons[x][y].getChecked())
                        list.add(buttons[x][y]);
            }
        }

        return list;

    }

    /**
     * From the given button return the index of the in button of the array
     * @param toSearch button that have to be searched
     * @return array that has stored the coordinates of the button in {@link #buttons}
     */
    private int[] indexOf2D(Button toSearch) {
        for (int i = 0; i < Game.size; i++) {
            for (int k = 0; k < Game.size; k++) {

                if (buttons[i][k] == toSearch) return new int[]{i, k};

            }
        }
        return new int[]{-1, -1};
    }

    /**
     * @param index position of the button in the array
     * @return returns the number of bombs next to the given cell
     */
    private int getNumberOfNearBombs(int[] index) {

        int count = 0;

        for( int i = index[0] - 1; i < index[0] + 2; i++) {

            for( int k = index[1] - 1; k < index[1] + 2; k++) {

                if( k == index[1] && i == index[0] || ( i == -1 || k == -1 || k == Game.size || i == Game.size ) ) continue;


                if(buttons[i][k].getStatus() == 1) count++;
            }
        }

        return count;
    }

    /**
     * Recursive function that call his self on near buttons and show the number of near bombs
     * @param bt actual button
     * @param expandible true to continue expanding false to just show the number of near bombs
     */
    private void recursiveExpansion(Button bt, boolean expandible) {

        int[] index = indexOf2D(bt);

        int numberOfNearBombs = getNumberOfNearBombs(index);


        if (expandible) bt.setChecked(true);

        if (numberOfNearBombs == 0 && expandible) {
            bt.showItsRealNature();


            for( int i = index[0] - 1; i < index[0] + 2; i++) {

                for( int k = index[1] - 1; k < index[1] + 2; k++) {


                    if( k == index[1] && i == index[0] || ( i == -1 || k == -1 || k == Game.size || i == Game.size ) || buttons[i][k].getChecked()) continue;

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

    /**
     * Call on every {@link Button} the function {@link Button#setChecked(boolean isChecked)}
     */
    private void cleanCheckedButtons() {
        for(int i = 0; i < Game.size; i++ ) {
            for(int k = 0; k < Game.size; k++ ) {
                buttons[i][k].setChecked(false);
            }
        }
    }


    /**
     * Open the dialog and specify if the play has won or loosed
     * @param isLoosed true if loose false if win
     */
    private void openDialog(boolean isLoosed) {

        header.getClock().stopClock();

        dialog.setDialog(true, isLoosed);

        dialogOpened = isLoosed;
    }

    /**
     * Used for enable of disable every button in {@link #buttons}
     * @param isEnabled true to enable and false to disable
     */
    private void setButtonsStatus(boolean isEnabled) {
        for(Button[] arr : buttons)
            for(Button e : arr)
                e.setEnabled(isEnabled);
    }

    /**
     * Debug utility
     * <p>Call {@link Button#showItsRealNature()} on every button in order to show if the are bomb or not</p>
     */
    private void openBoard() {
        for(int i = 0; i < Game.size; i++) {
            for(int j = 0; j < Game.size; j++) {
                buttons[i][j].showItsRealNature();
            }
        }
    }
}