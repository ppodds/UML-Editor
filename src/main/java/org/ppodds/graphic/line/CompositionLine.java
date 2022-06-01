package org.ppodds.graphic.line;

import org.ppodds.core.math.Vector2D;
import org.ppodds.graphic.object.UMLBasicObject;

import java.awt.*;
import java.awt.geom.Path2D;

public class CompositionLine extends ConnectionLine {

    private Vector2D h;
    private Vector2D lineEndPoint;

    public CompositionLine(UMLBasicObject.ConnectionPortDirection fromConnectionPort, UMLBasicObject.ConnectionPortDirection toConnectionPort, UMLBasicObject fromObject, UMLBasicObject toObject) {
        super(fromConnectionPort, toConnectionPort, fromObject, toObject);
    }

    @Override
    protected void paintLine(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Vector2D v = arrowEndPoint.subtract(arrowStartPoint);
        h = v.unitVector().multiply(8 * Math.sqrt(2));
        lineEndPoint = arrowStartPoint.add(v.subtract(h));
        g2.drawLine((int) arrowStartPoint.x(), (int) arrowStartPoint.y(), (int) lineEndPoint.x(), (int) lineEndPoint.y());
    }

    @Override
    protected void paintArrow(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Vector2D t = h.multiply(0.5);
        Vector2D p1 = lineEndPoint.add(t.add(t.normalVector().reverse()));
        Vector2D p2 = lineEndPoint.add(h);
        Vector2D p3 = lineEndPoint.add(t.add(t.normalVector()));
        Path2D.Double path = new Path2D.Double();
        path.moveTo(lineEndPoint.x(), lineEndPoint.y());
        path.lineTo(p1.x(), p1.y());
        path.lineTo(p2.x(), p2.y());
        path.lineTo(p3.x(), p3.y());
        path.closePath();
        g2.draw(path);
    }
}
