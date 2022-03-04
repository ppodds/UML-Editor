package org.ppodds.graphic;

import javax.swing.*;
import java.awt.*;

public class ToolButton extends JButton {
    public ToolButton(String text) {
        super(text);
    }

    public ToolButton(Icon icon) {
        super(icon);
        setMargin(new Insets(0, 0, 0, 0));
    }
}
