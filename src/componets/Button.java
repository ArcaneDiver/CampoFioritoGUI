package componets;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {

    private final Color evenColor =  new Color(170, 215, 81);
    private final Color oddColor = new Color(162, 209, 73);

    private final int ButtonSize = 900 / 25;

    private final ImageIcon flagIcon;

    private boolean even;
    private byte status;

    private String tempText;

    private boolean checked;

    public Button(boolean even){


        status = ( Math.random() * 100) < 90 ? (byte) 0 : (byte) 1;

        checked = false;

        flagIcon = new ImageIcon( new ImageIcon( this.getClass().getResource("../res/flag.png") ).getImage().getScaledInstance( ButtonSize, ButtonSize, Image.SCALE_SMOOTH ) );

        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        setMargin(new Insets(1, 1, 1, 1));
        setBorder(BorderFactory.createEmptyBorder());


        this.even = even;
        setBackground(even ? evenColor : oddColor);


    }

    public void showItsRealNature() {
        if(status == 0) setBackground(new Color(138, 138, 138));
        else setBackground(new Color(255, 0, 0));
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

}