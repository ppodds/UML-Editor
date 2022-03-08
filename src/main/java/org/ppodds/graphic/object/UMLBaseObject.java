package org.ppodds.graphic.object;

import org.ppodds.graphic.Editor;

import javax.swing.*;
import java.awt.*;

public abstract class UMLBaseObject extends JComponent {
    protected final Editor editor;
    protected int padding;

    public UMLBaseObject() {
        editor = Editor.getInstance();
    }


    public int getPadding() {
        return padding;
    }

    @Override
    public abstract void paintComponent(Graphics g);
}
