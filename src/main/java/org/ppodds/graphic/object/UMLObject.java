package org.ppodds.graphic.object;

import org.ppodds.core.math.Oval;
import org.ppodds.core.math.Point;
import org.ppodds.core.math.Rectangle;
import org.ppodds.core.math.Shape;
import org.ppodds.graphic.Editor;
import org.ppodds.graphic.EditorState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class UMLObject extends UMLBaseObject {
    private boolean isSelected = false;
    private boolean isGrouped = false;

    private int beforeMoveX;
    private int beforeMoveY;
    private int beforeMoveXOffset;
    private int beforeMoveYOffset;

    public UMLObject() {
        super();
        init();
    }

    public UMLObject(int x, int y) {
        super();
        setLocation(x, y);
        init();
    }

    public boolean isGrouped() {
        return isGrouped;
    }

    public void setGrouped(boolean grouped) {
        isGrouped = grouped;
    }

    private static UMLObject topObject(UMLObject o) {
        if (o.isGrouped) {
            o = (UMLObject) o.getParent();
            while (o.isGrouped) {
                o = (UMLObject) o.getParent();
            }
        }
        return o;
    }

    private void init() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                UMLObject o = (UMLObject) e.getSource();
                EditorState state = editor.getState();
                if (state.getOperation() == EditorState.EditorOperation.SELECT) {
                    o = topObject(o);
                    o.isSelected = !o.isSelected;
                    if (o.isSelected) {
                        state.setSelectedObjects(new UMLObject[]{o});
                        beforeMoveX = o.getX();
                        beforeMoveY = o.getY();
                        beforeMoveXOffset = e.getX();
                        beforeMoveYOffset = e.getY();
                    } else
                        state.setSelectedObjects(null);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                UMLObject o = (UMLObject) e.getSource();
                o = topObject(o);
                if (o.isSelected) {
                    o.setLocation(o.beforeMoveX + e.getX() - o.beforeMoveXOffset,
                            o.beforeMoveY + e.getY() - o.beforeMoveYOffset);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        // set isSelected = false when select other object or null
        editor.addChangeListener(e -> {
            UMLObject[] selectedObjects = ((Editor) e.getSource()).getState().getSelectedObjects();
            if (selectedObjects == null) {
                isSelected = false;
            } else {
                boolean inSelectedObjects = false;
                for (var o : selectedObjects) {
                    if (o.equals(this)) {
                        inSelectedObjects = true;
                        break;
                    }
                }
                if (!inSelectedObjects)
                    isSelected = false;
            }
            repaint();
        });
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    protected void paintConnectionPorts(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (isSelected) {
            // connection ports
            Shape t;
            if (this instanceof ClassObject || this instanceof CompositeObject)
                t = new Rectangle(getWidth() - padding * 2, getHeight() - padding * 2);
            else
                t = new Oval(getWidth() - padding * 2, getHeight() - padding * 2);

            Point p1 = t.getPointOfDirection(Shape.Direction.LEFT_TOP);
            Point p2 = t.getPointOfDirection(Shape.Direction.RIGHT_BOTTOM);
            Point p3 = t.getPointOfDirection(Shape.Direction.RIGHT_TOP);
            Point p4 = t.getPointOfDirection(Shape.Direction.LEFT_BOTTOM);

            g2.drawLine(p1.getX() + padding,
                    p1.getY() + padding,
                    p2.getX() + padding,
                    p2.getY() + padding);
            g2.drawLine(p3.getX() + padding,
                    p3.getY() + padding,
                    p4.getX() + padding,
                    p4.getY() + padding);

            int width = (getWidth() - padding * 2) / 20;
            int height = (getHeight() - padding * 2) / 20;

            Point p5 = t.getPointOfDirection(Shape.Direction.TOP);
            Point p6 = t.getPointOfDirection(Shape.Direction.BOTTOM);
            Point p7 = t.getPointOfDirection(Shape.Direction.LEFT);
            Point p8 = t.getPointOfDirection(Shape.Direction.RIGHT);

            g2.fillRect(p5.getX() + padding - width / 2, p5.getY() + padding - height, width, height);
            g2.fillRect(p6.getX() + padding - width / 2, p6.getY() + padding, width, height);
            g2.fillRect(p7.getX() + padding - width, p7.getY() + padding - height / 2, width, height);
            g2.fillRect(p8.getX() + padding, p8.getY() + padding - height / 2, width, height);
        }
    }
}
