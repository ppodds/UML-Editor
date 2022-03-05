package org.ppodds.graphic.object;

import javax.swing.*;
import java.awt.*;

public abstract class UMLBaseObject extends JComponent {
    protected int depth;

    @Override
    public abstract void paintComponent(Graphics g);
}
