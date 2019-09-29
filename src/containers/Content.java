package containers;

import javax.swing.*;
import java.awt.*;

public class Content extends JPanel {

    public Content(int maxWidth, int maxHeight, int sideSize) {
        setLayout(new GridLayout(sideSize, sideSize, 0, 0));

        setBorder(BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(maxWidth, maxHeight));
        setMaximumSize(new Dimension(maxWidth, maxHeight));
        setMinimumSize(new Dimension(maxWidth, maxHeight));
    }

}
