package org.ppodds.graphic.editor.operation.behavior;

import org.ppodds.graphic.editor.UMLCanvas;
import org.ppodds.graphic.editor.operation.Behavior;

import java.awt.event.MouseEvent;

public abstract class CreateObjectBehavior extends Behavior {

    @Override
    public void mousePressed(MouseEvent e) {
        if (!(e.getSource() instanceof UMLCanvas))
            return;

        addObject(e.getX(), e.getY());
    }

    public abstract void addObject(int x, int y);

}
