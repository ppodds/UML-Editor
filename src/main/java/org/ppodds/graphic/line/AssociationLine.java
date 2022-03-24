package org.ppodds.graphic.line;

import org.ppodds.core.math.Vector2D;
import org.ppodds.graphic.object.UMLObject;

import java.awt.*;
import java.awt.geom.Path2D;

public class AssociationLine extends ConnectionLine {

    private Vector2D base;
    private Vector2D lineEndPoint;

    public AssociationLine(UMLObject.ConnectionPortDirection fromConnectionPort, UMLObject.ConnectionPortDirection toConnectionPort, UMLObject fromObject, UMLObject toObject) {
        super(ConnectionLineType.ASSOCIATION_LINE, fromConnectionPort, toConnectionPort, fromObject, toObject);
    }

    @Override
    protected void paintLine(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Vector2D v = arrowEndPoint.subtract(arrowStartPoint);
        base = v.normalVector().unitVector().multiply(7 * 2);
        lineEndPoint = arrowStartPoint.add(v.subtract(v.unitVector().multiply(7 * Math.sqrt(3))));
        g2.drawLine((int) arrowStartPoint.x, (int) arrowStartPoint.y, (int) arrowEndPoint.x, (int) arrowEndPoint.y);
    }

    @Override
    protected void paintArrow(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Vector2D p1 = lineEndPoint.add(base.multiply(0.5));
        Vector2D p2 = lineEndPoint.add(base.normalVector().reverse());
        Vector2D p3 = lineEndPoint.add(base.multiply(0.5).reverse());
        Path2D.Double path = new Path2D.Double();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);
        g2.draw(path);
    }
}
