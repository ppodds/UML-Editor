package org.ppodds.graphic.editor.operation.behavior;

import org.ppodds.graphic.editor.operation.Behavior;
import org.ppodds.graphic.object.UMLBasicObject;
import org.ppodds.graphic.object.UMLObject;

import java.awt.event.MouseEvent;

public abstract class CreateLineBehavior extends Behavior {

    private UMLBasicObject originObject;
    private UMLBasicObject.ConnectionPortDirection fromConnectionPort;

    public void setLineStart(UMLBasicObject originObject,
                             UMLBasicObject.ConnectionPortDirection fromConnectionPort) {
        this.originObject = originObject;
        this.fromConnectionPort = fromConnectionPort;
    }

    public void unsetLineStart() {
        this.originObject = null;
        this.fromConnectionPort = null;
    }

    public boolean isLineStartSet() {
        return originObject != null && fromConnectionPort != null;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!(e.getSource() instanceof UMLObject o))
            return;

        if (o.isLinkable() && !o.isGrouped()) {
            setLineStart(((UMLBasicObject) o), ((UMLBasicObject) o).getConnectionPortDirection(e.getX(), e.getY()));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!(e.getSource() instanceof UMLObject o))
            return;

        if (o.isLinkable() && isLineStartSet() && !o.isGrouped()) {
            int x = o.getX() + e.getX();
            int y = o.getY() + e.getY();
            UMLBasicObject toObject = UMLBasicObject.getLinkableObjectOn(x, y);
            if (toObject != null && originObject != toObject) {
                addConnectionLine(
                        fromConnectionPort,
                        toObject.getConnectionPortDirection(
                                x - toObject.getX(),
                                y - toObject.getY()),
                        (UMLBasicObject) o, toObject);
            }
        }
        unsetLineStart();
    }

    public abstract void addConnectionLine(UMLBasicObject.ConnectionPortDirection fromConnectionPort, UMLBasicObject.ConnectionPortDirection toConnectionPort, UMLBasicObject fromObject, UMLBasicObject toObject);
}
