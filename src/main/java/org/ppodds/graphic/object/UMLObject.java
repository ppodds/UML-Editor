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

public abstract class UMLObject extends UMLBaseObject {
    protected boolean isSelected = false;

    public UMLObject(int x, int y) {
        super();
        setLocation(x, y);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                UMLObject o = (UMLObject) e.getSource();
                EditorState state = editor.getState();
                if (state.getOperation() == EditorState.EditorOperation.SELECT) {
                    o.isSelected = !isSelected;
                    state.setSelectedObjects(new UMLObject[]{o});
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
            if (this instanceof ClassObject)
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
