package main.components;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {

    public static Color emptyColor = new Color(138, 138, 138);
    public static Color bombColor = new Color(255, 0, 21);

    private final Color evenColor =  new Color(170, 215, 81);
    private final Color oddColor = new Color(162, 209, 73);

    private final int ButtonSize = 900 / 25;

    private final ImageIcon flagIcon;

    private boolean even;
    private byte status;

    private String tempText;

    private boolean checked;

    private boolean isDisabled = false;

    public Button(boolean even){


        status = ( Math.random() * 100) < 2 ? (byte) 0 : (byte) 1;

        checked = false;

        flagIcon = new ImageIcon( new ImageIcon( this.getClass().getResource("../res/flag.png") ).getImage().getScaledInstance( ButtonSize, ButtonSize, Image.SCALE_SMOOTH ) );

        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        setMargin(new Insets(0, 0, 0, 0));
        setBorder(BorderFactory.createEmptyBorder());


        this.even = even;
        setBackground(even ? evenColor : oddColor);


    }

    public int showItsRealNature() {

        setIcon(null);
        setText(null);

        System.out.println(status + " " + bombColor.toString());
        if(status == 0) setBackground(emptyColor);
        else setBackground(bombColor);

        return status;
    }


    public void setSafeText(String text) {
        setText(null);
        setIcon(null);

        setBackground(emptyColor);
        setText(text);
    }
    public byte getStatus() {
        return status;
    }

    public void setChecked(boolean value) {
        checked = value;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setFlag(boolean status) {
        if(status) {
            setIcon(flagIcon);
            tempText = getText();
            setText(null);
        }
        else {
            setIcon(null);
            setText(tempText);
            tempText = null;
        }
    }

    public boolean isFlagged() {
        return getIcon() != null;
    }

    public Color getColor() {
        return even ? evenColor : oddColor;
    }

    public void setDisabled(boolean toSet) {
        isDisabled = !toSet;
        setEnabled(toSet);
    }

}