package org.ppodds.graphic.object;

import org.ppodds.core.math.Point;
import org.ppodds.core.math.Shape;
import org.ppodds.graphic.editor.Editor;

import java.awt.*;

public abstract class UMLBasicObject extends UMLObject {

    public final int width;
    public final int height;

    public UMLBasicObject(int x, int y, int width, int height, int padding) {
        super(true, true, padding);
        this.width = width;
        this.height = height;
        setBounds(x, y, width, height);
    }

    public static UMLBasicObject getLinkableObjectOn(int x, int y) {
        UMLBasicObject o = null;
        for (var c : Editor.getInstance().getEditorContentPane().getCanvas().getComponents()) {
            assert c instanceof UMLObject : "Children of canvas must be instance of UMLObject";
            // check if the mouse in the object
            if (x > c.getX() && x < c.getX() + c.getWidth()
                    && y > c.getY() && y < c.getY() + c.getHeight() && ((UMLObject) c).isLinkable()) {
                assert c instanceof UMLBasicObject : "Linkable UMLObject must be an instance of UMLBasicObject";
                o = (UMLBasicObject) c;
                break;
            }
        }
        return o;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintConnectionPorts(g);
        g.dispose();
    }

    private void paintConnectionPorts(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (!isSelected())
            return;
        // connection ports
        Point p1 = shape.getPointOfDirection(Shape.Direction.LEFT_TOP);
        Point p2 = shape.getPointOfDirection(Shape.Direction.RIGHT_BOTTOM);
        Point p3 = shape.getPointOfDirection(Shape.Direction.RIGHT_TOP);
        Point p4 = shape.getPointOfDirection(Shape.Direction.LEFT_BOTTOM);

        g2.drawLine(p1.x() + getPadding(),
                p1.y() + getPadding(),
                p2.x() + getPadding(),
                p2.y() + getPadding());
        g2.drawLine(p3.x() + getPadding(),
                p3.y() + getPadding(),
                p4.x() + getPadding(),
                p4.y() + getPadding());

        int width = (getWidth() - getPadding() * 2) / 20;
        int height = (getHeight() - getPadding() * 2) / 20;

        Point p5 = shape.getPointOfDirection(Shape.Direction.TOP);
        Point p6 = shape.getPointOfDirection(Shape.Direction.BOTTOM);
        Point p7 = shape.getPointOfDirection(Shape.Direction.LEFT);
        Point p8 = shape.getPointOfDirection(Shape.Direction.RIGHT);

        g2.fillRect(p5.x() + getPadding() - width / 2, p5.y() + getPadding() - height, width, height);
        g2.fillRect(p6.x() + getPadding() - width / 2, p6.y() + getPadding(), width, height);
        g2.fillRect(p7.x() + getPadding() - width, p7.y() + getPadding() - height / 2, width, height);
        g2.fillRect(p8.x() + getPadding(), p8.y() + getPadding() - height / 2, width, height);
    }


    public Point getConnectionPortOfDirection(ConnectionPortDirection direction) {
        return switch (direction) {
            case TOP -> shape.getPointOfDirection(Shape.Direction.TOP);
            case RIGHT -> shape.getPointOfDirection(Shape.Direction.RIGHT);
            case BOTTOM -> shape.getPointOfDirection(Shape.Direction.BOTTOM);
            case LEFT -> shape.getPointOfDirection(Shape.Direction.LEFT);
        };
    }

    public ConnectionPortDirection getConnectionPortDirection(int x, int y) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        double degree = Math.toDegrees(
                Math.acos((x - centerX) / Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2))));
        if (degree >= 45 && degree <= 135) {
            if (centerY < y)
                return ConnectionPortDirection.BOTTOM;
            else
                return ConnectionPortDirection.TOP;
        } else if (degree >= 0 && degree <= 45)
            return ConnectionPortDirection.RIGHT;
        else
            return ConnectionPortDirection.LEFT;
    }


    public enum ConnectionPortDirection {
        TOP, RIGHT, BOTTOM, LEFT
    }
}
