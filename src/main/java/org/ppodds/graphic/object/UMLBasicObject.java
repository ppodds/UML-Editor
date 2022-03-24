package org.ppodds.graphic.object;

import org.ppodds.core.math.Point;
import org.ppodds.core.math.Shape;
import org.ppodds.graphic.Editor;
import org.ppodds.graphic.EditorState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class UMLBasicObject extends UMLObject {

    public final int width;
    public final int height;

    public UMLBasicObject(int x, int y, int width, int height, int padding) {
        super(true, true, padding);
        this.width = width;
        this.height = height;
        setBounds(x, y, width, height);
        registerEventListeners();
    }

    private void registerEventListeners() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                UMLBasicObject o = (UMLBasicObject) e.getSource();
                EditorState state = Editor.getInstance().getState();
                if ((state.getOperation() == EditorState.EditorOperation.ASSOCIATION_LINE
                        || state.getOperation() == EditorState.EditorOperation.GENERALIZATION_LINE
                        || state.getOperation() == EditorState.EditorOperation.COMPOSITION_LINE)
                        && !o.isGrouped()) {
                    state.createCreatingConnectionLine(o, getConnectionPortDirection(e.getX(), e.getY()));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                UMLBasicObject o = (UMLBasicObject) e.getSource();
                EditorState state = Editor.getInstance().getState();
                if (state.getCreatingConnectionLine() != null
                        && state.getCreatingConnectionLine().originObject != o
                        && (state.getOperation() == EditorState.EditorOperation.ASSOCIATION_LINE
                        || state.getOperation() == EditorState.EditorOperation.GENERALIZATION_LINE
                        || state.getOperation() == EditorState.EditorOperation.COMPOSITION_LINE)
                        && !o.isGrouped()) {
                    int x = o.getX() + e.getX();
                    int y = o.getY() + e.getY();
                    UMLObject toObject = getObjectOn(x, y);
                    if (toObject != null && toObject.isLinkable()) {
                        assert toObject instanceof UMLBasicObject : "Linkable UMLObject must be an instance of UMLBasicObject";
                        var t = state.getCreatingConnectionLine();
                        Editor.getInstance().getCanvas().createConnectionLine(t.type,
                                t.fromConnectionPort,
                                ((UMLBasicObject) toObject).getConnectionPortDirection(
                                        x - toObject.getX(),
                                        y - toObject.getY()),
                                o, (UMLBasicObject) toObject);
                    }
                }
                state.removeCreatingConnectionLine();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private UMLObject getObjectOn(int x, int y) {
        UMLObject o = null;
        for (var c : Editor.getInstance().getCanvas().getComponents()) {
            assert c instanceof UMLObject : "Children of canvas must be instance of UMLObject";
            // check if the mouse in the object
            if (x > c.getX() && x < c.getX() + c.getWidth()
                    && y > c.getY() && y < c.getY() + c.getHeight()) {
                if (o == null) {
                    o = (UMLObject) c;
                }
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

        g2.drawLine(p1.getX() + getPadding(),
                p1.getY() + getPadding(),
                p2.getX() + getPadding(),
                p2.getY() + getPadding());
        g2.drawLine(p3.getX() + getPadding(),
                p3.getY() + getPadding(),
                p4.getX() + getPadding(),
                p4.getY() + getPadding());

        int width = (getWidth() - getPadding() * 2) / 20;
        int height = (getHeight() - getPadding() * 2) / 20;

        Point p5 = shape.getPointOfDirection(Shape.Direction.TOP);
        Point p6 = shape.getPointOfDirection(Shape.Direction.BOTTOM);
        Point p7 = shape.getPointOfDirection(Shape.Direction.LEFT);
        Point p8 = shape.getPointOfDirection(Shape.Direction.RIGHT);

        g2.fillRect(p5.getX() + getPadding() - width / 2, p5.getY() + getPadding() - height, width, height);
        g2.fillRect(p6.getX() + getPadding() - width / 2, p6.getY() + getPadding(), width, height);
        g2.fillRect(p7.getX() + getPadding() - width, p7.getY() + getPadding() - height / 2, width, height);
        g2.fillRect(p8.getX() + getPadding(), p8.getY() + getPadding() - height / 2, width, height);
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
