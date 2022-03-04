package org.ppodds.graphic;

import java.awt.*;

/**
 * This class paint UML objects on canvas.
 * The objects should be painted
 */
public class UMLCanvas extends Canvas {
    private EditorState state;

    public UMLCanvas(EditorState state) {
        super();
        this.state = state;
    }

    @Override
    public void paint(Graphics g) {
        // TODO: paint UML objects
    }
}
