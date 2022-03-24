package org.ppodds.graphic.line;

import org.ppodds.core.math.Point;
import org.ppodds.core.math.Vector2D;
import org.ppodds.graphic.object.UMLObject;

import java.awt.*;

public abstract class ConnectionLine {
    private final UMLObject.ConnectionPortDirection fromConnectionPort;
    private final UMLObject.ConnectionPortDirection toConnectionPort;
    private final UMLObject fromObject;
    private final UMLObject toObject;
    protected Vector2D arrowStartPoint;
    protected Vector2D arrowEndPoint;
    private final ConnectionLineType type;

    public ConnectionLine(ConnectionLineType type, UMLObject.ConnectionPortDirection fromConnectionPort, UMLObject.ConnectionPortDirection toConnectionPort, UMLObject fromObject, UMLObject toObject) {
        this.type = type;
        this.fromConnectionPort = fromConnectionPort;
        this.toConnectionPort = toConnectionPort;
        this.fromObject = fromObject;
        this.toObject = toObject;
    }

    public UMLObject.ConnectionPortDirection getFromConnectionPort() {
        return fromConnectionPort;
    }

    public UMLObject.ConnectionPortDirection getToConnectionPort() {
        return toConnectionPort;
    }

    public UMLObject getFromObject() {
        return fromObject;
    }

    public UMLObject getToObject() {
        return toObject;
    }

    public ConnectionLineType getType() {
        return type;
    }

    private void calculateLine() {
        UMLObject fromTopObject = getFromObject();
        Point p1 = fromObject.getConnectionPortOfDirection(getFromConnectionPort());
        UMLObject toTopObject = getToObject();
        Point p2 = toObject.getConnectionPortOfDirection(getToConnectionPort());
        int fromX = fromObject.getX() + fromObject.getPadding() + p1.getX();
        int fromY = fromObject.getY() + fromObject.getPadding() + p1.getY();
        int toX = toObject.getX() + toObject.getPadding() + p2.getX();
        int toY = toObject.getY() + toObject.getPadding() + p2.getY();
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
