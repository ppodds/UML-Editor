package org.ppodds.graphic.line;

import org.ppodds.core.math.Point;
import org.ppodds.core.math.Vector2D;
import org.ppodds.graphic.object.UMLBasicObject;
import org.ppodds.graphic.object.UMLObject;

import java.awt.*;

public abstract class ConnectionLine {
    private final UMLBasicObject.ConnectionPortDirection fromConnectionPort;
    private final UMLBasicObject.ConnectionPortDirection toConnectionPort;
    private final UMLBasicObject fromObject;
    private final UMLBasicObject toObject;
    protected Vector2D arrowStartPoint;
    protected Vector2D arrowEndPoint;

    public ConnectionLine(UMLBasicObject.ConnectionPortDirection fromConnectionPort, UMLBasicObject.ConnectionPortDirection toConnectionPort, UMLBasicObject fromObject, UMLBasicObject toObject) {
        this.fromConnectionPort = fromConnectionPort;
        this.toConnectionPort = toConnectionPort;
        this.fromObject = fromObject;
        this.toObject = toObject;
    }

    public UMLBasicObject.ConnectionPortDirection getFromConnectionPort() {
        return fromConnectionPort;
    }

    public UMLBasicObject.ConnectionPortDirection getToConnectionPort() {
        return toConnectionPort;
    }

    public UMLBasicObject getFromObject() {
        return fromObject;
    }

    public UMLBasicObject getToObject() {
        return toObject;
    }

    private void calculateLine() {
        UMLObject fromTopObject = getFromObject();
        Point p1 = fromObject.getConnectionPortOfDirection(getFromConnectionPort());
        UMLObject toTopObject = getToObject();
        Point p2 = toObject.getConnectionPortOfDirection(getToConnectionPort());
        int fromX = fromObject.getX() + fromObject.getPadding() + p1.x();
        int fromY = fromObject.getY() + fromObject.getPadding() + p1.y();
        int toX = toObject.getX() + toObject.getPadding() + p2.x();
        int toY = toObject.getY() + toObject.getPadding() + p2.y();
        while (fromTopObject.isGrouped()) {
            fromTopObject = (UMLObject) fromTopObject.getParent();
            fromX += fromTopObject.getX() + fromTopObject.getPadding();
            fromY += fromTopObject.getY() + fromTopObject.getPadding();
        }
        while (toTopObject.isGrouped()) {
            toTopObject = (UMLObject) toTopObject.getParent();
            toX += toTopObject.getX() + toTopObject.getPadding();
            toY += toTopObject.getY() + toTopObject.getPadding();
        }
        arrowStartPoint = new Vector2D(fromX, fromY);
        arrowEndPoint = new Vector2D(toX, toY);
    }

    public void paint(Graphics g) {
        calculateLine();
        paintLine(g);
        paintArrow(g);
    }

    protected abstract void paintLine(Graphics g);

    protected abstract void paintArrow(Graphics g);
}
