package main.components;

import javax.swing.*;
import java.awt.*;


public class Button extends JButton {

    /**
     * Represents the color that the button will assume if it`s an empty cell.
     * @see #showItsRealNature()
     * @see Color
     */
    private static final Color emptyColor = new Color(138, 138, 138);

    /**
     * Represents the color that the button will assume if it`s a bomb cell.
     * @see #showItsRealNature()
     * @see Color
     */
    private static final Color bombColor = new Color(255, 0, 21);

    /**
     * Represents the color that the button will assume if it`s a even cell.
     * @see Color
     */
    private final Color evenColor =  new Color(170, 215, 81);

    /**
     * Represents the color that the button will assume if it`s a odd cell.
     * @see Color
     */
    private final Color oddColor = new Color(162, 209, 73);

    // Todo: da sistemare le costanti
    private final int ButtonSize = 900 / 25;

    /**
     * Stores the instance of flag icon
     */
    private final ImageIcon flagIcon;

    /**
     * Value that represent if it`s a bomb or not
     */
    private byte status;

    /**
     * Store the text before replace it with a flag
     * @see #setFlag(boolean)
     */
    private String tempText;

    /**
     * Use for store if this button has been already checked
     * <p>Used in recursive function of {@link main.CampoFiorito}</p>
     */
    private boolean checked;

    /**
     * Stores if the function {@link #showItsRealNature()} has been already called
     */
    private boolean isShowingItsRealNature = false;


    /**
     * Get flag icon.
     * <p>Set font style.</p>
     * <p>Remove margin and border.</p>
     * <p>Set background color based on even </p>
     * @param even if his color has to be brighter or not
     */
    public Button(boolean even){

        checked = false;

        flagIcon = new ImageIcon( new ImageIcon(this.getClass().getResource("../res/flag.png")).getImage()
                .getScaledInstance(ButtonSize, ButtonSize, Image.SCALE_SMOOTH));


        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        setMargin(new Insets(0, 0, 0, 0));
        setBorder(BorderFactory.createEmptyBorder());


        setBackground(even ? evenColor : oddColor);
    }

    /**
     * His background became {@link #emptyColor} if its cell is empty.
     * <p>Otherwise its background became {@link #bombColor}</p>
     */
    public void showItsRealNature() {

        setIcon(null);
        setText(null);

        if(status == 0) setBackground(emptyColor);
        else setBackground(bombColor);
        isShowingItsRealNature = true;
    }

    /**
     * @return {@link #isShowingItsRealNature}
     * @see #showItsRealNature()
     */
    public boolean getShowingStatus() {
        return isShowingItsRealNature;
    }

    /**
     * Override the method {@link JLabel#setText(String)}
     * <p> Method used for assign text while keeping icons </p>
     * @param text the string to be assigned
     */
    @Override
    public void setText(String text) {

        super.setText(null);
        setIcon(null);

        setBackground(emptyColor);
        super.setText(text);
    }

    /**
     * Setter for {@link #status}
     * @param status value that represent if this cell is a bomb or not
     */
    public void setStatus(byte status) { this.status = status; }

    /**
     * @return {@link #status}
     */
    public byte getStatus() {
        return status;
    }

    /**
     * Setter for {@link #checked}
     * @param value the value that will assume checked
     */
    public void setChecked(boolean value) {
        checked = value;
    }

    /**
     * @return {@link #checked}
     */
    public boolean getChecked() {
        return checked;
    }

    /**
     * Show the flag on this cell
     * <p>Store in {@link #tempText} the actual text</p>
     * @param status true to set the flag and false to remove it
     */
    public void setFlag(boolean status) {
        if(status) {
            setIcon(flagIcon);
            tempText = getText();
            super.setText(null);
        }
        else {
            setIcon(null);
            super.setText(tempText);
            tempText = null;
        }
    }

    /**
     * @return if button is showing a flag
     */
    public boolean isFlagged() {
        return getIcon() != null;
    }


}

