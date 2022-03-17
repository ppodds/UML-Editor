package org.ppodds.graphic;

import org.ppodds.core.math.Point;
import org.ppodds.core.math.Vector2D;
import org.ppodds.graphic.object.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Path2D;
import java.util.LinkedList;
import java.util.List;

/**
 * This class paint UML objects on canvas.
 * The objects should be painted
 */
public class UMLCanvas extends JPanel {
    private final Editor editor;
    private SelectedArea selectedArea;
    private final List<ConnectionLine> connectionLineList;

    public UMLCanvas() {
        super(new UMLCanvasLayout());
        editor = Editor.getInstance();
        connectionLineList = new LinkedList<>();
        setPreferredSize(new Dimension(540, 540));
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                switch (editor.getState().getOperation()) {
                    case CLASS: {
                        createClassObject(e.getX(), e.getY());
                        break;
                    }
                    case USE_CASE: {
                        createUseCaseObject(e.getX(), e.getY());
                        break;
                    }
                    default:
                        break;
                }

                // select null detect
                if (editor.getState().getOperation() == EditorState.EditorOperation.SELECT) {
                    editor.getState().setSelectedObjects(null);
                    // create select area
                    selectedArea = new SelectedArea(e.getX(), e.getY());
                    add(selectedArea);
                    setComponentZOrder(selectedArea, 0);
                    selectedArea.repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (editor.getState().getOperation() == EditorState.EditorOperation.SELECT && selectedArea != null) {
                    // select UMLObjects in selected area
                    selectedArea.selectUMLObjects(getComponents());

                    remove(selectedArea);
                    selectedArea = null;
                    repaint();
                }
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
                if (editor.getState().getOperation() == EditorState.EditorOperation.SELECT && selectedArea != null) {
                    selectedArea.selecting(Math.max(e.getX(), 0), Math.max(e.getY(), 0));
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
    }

    public void createCompositeObject() {
        UMLObject[] selectedObjects = editor.getState().getSelectedObjects();
        if (selectedObjects != null && selectedObjects.length > 1) {
            for (var o : selectedObjects)
                remove(o);
            CompositeObject obj = new CompositeObject(selectedObjects);
            add(obj);
            setComponentZOrder(obj, 0);
            repaint();
        }
    }

    public void ungroupCompositeObject() {
        UMLObject[] selectedObjects = editor.getState().getSelectedObjects();
        if (selectedObjects != null && selectedObjects.length == 1
                && selectedObjects[0] instanceof CompositeObject) {
            CompositeObject o = (CompositeObject) selectedObjects[0];
            remove(o);
            int x = o.getX(), y = o.getY();
            for (var child : o.getComponents()) {
                o.remove(child);
                add(child);
                setComponentZOrder(child, 0);
                child.setLocation(x + child.getX(), y + child.getY());
                ((UMLObject) child).setGrouped(false);
            }
            repaint();
        }
    }

    public void changeObjectName(UMLObject obj, String name) {
        if (obj instanceof CompositeObject)
            return;
        obj.setName(name);
        setComponentZOrder(obj, 0);
        repaint();
    }

    public void createClassObject(int x, int y) {
        ClassObject obj = new ClassObject(x, y);
        add(obj);
        setComponentZOrder(obj, 0);
        repaint();
    }

    public void createUseCaseObject(int x, int y) {
        UseCaseObject obj = new UseCaseObject(x, y);
        add(obj);
        setComponentZOrder(obj, 0);
        repaint();
    }

    public void showPreviewObject(UMLObject previewObject) {
        add(previewObject);
        setComponentZOrder(previewObject, 0);
        repaint();
    }

    public void removePreviewObject(UMLObject previewObject) {
        remove(previewObject);
        repaint();
    }

    public void createConnectionLine(ConnectionLine.ConnectionLineType type, UMLObject.ConnectionPortDirection fromConnectionPort, UMLObject.ConnectionPortDirection toConnectionPort, UMLObject fromObject, UMLObject toObject) {
        connectionLineList.add(new ConnectionLine(type, fromConnectionPort, toConnectionPort, fromObject, toObject));
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintChildren(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.BLACK);
        g2.drawRect(0, 0, getWidth(), getHeight());
        // draw connection lines
        for (var c : connectionLineList) {
            UMLObject fromObject = c.getFromObject();
            org.ppodds.core.math.Point p1 = fromObject.getConnectionPortOfDirection(c.getFromConnectionPort());
            UMLObject toObject = c.getToObject();
            Point p2 = toObject.getConnectionPortOfDirection(c.getToConnectionPort());
            int fromObjectX = fromObject.getX() + fromObject.getPadding() + p1.getX();
            int fromObjectY = fromObject.getY() + fromObject.getPadding() + p1.getY();
            int toObjectX = toObject.getX() + toObject.getPadding() + p2.getX();
            int toObjectY = toObject.getY() + toObject.getPadding() + p2.getY();
            while (fromObject.isGrouped()) {
                fromObject = (UMLObject) fromObject.getParent();
                fromObjectX += fromObject.getX() + fromObject.getPadding();
                fromObjectY += fromObject.getY() + fromObject.getPadding();
            }
            while (toObject.isGrouped()) {
                toObject = (UMLObject) toObject.getParent();
                toObjectX += toObject.getX() + toObject.getPadding();
                toObjectY += toObject.getY() + toObject.getPadding();
            }
            Vector2D start = new Vector2D(fromObjectX, fromObjectY);
            Vector2D end = new Vector2D(toObjectX, toObjectY);
            switch (c.getType()) {
                case ASSOCIATION_LINE -> {
                    Vector2D v = end.subtract(start);
                    Vector2D base = v.normalVector().unitVector().multiply(7 * 2);
                    Vector2D lineEnd = start.add(v.subtract(v.unitVector().multiply(7 * Math.sqrt(3))));
                    g2.drawLine((int) start.x, (int) start.y, (int) end.x, (int) end.y);
                    g2.draw(new Arrow(lineEnd, base));
                }
                case GENERALIZATION_LINE -> {
                    Vector2D v = end.subtract(start);
                    Vector2D base = v.normalVector().unitVector().multiply(7 * 2);
                    Vector2D lineEnd = start.add(v.subtract(v.unitVector().multiply(7 * Math.sqrt(3))));
                    g2.drawLine((int) start.x, (int) start.y, (int) lineEnd.x, (int) lineEnd.y);
                    g2.draw(new Triangle(lineEnd, base));
                }
                case COMPOSITION_LINE -> {
                    Vector2D v = end.subtract(start);
                    Vector2D h = v.unitVector().multiply(8 * Math.sqrt(2));
                    Vector2D lineEnd = start.add(v.subtract(h));
                    g2.drawLine((int) start.x, (int) start.y, (int) lineEnd.x, (int) lineEnd.y);
                    g2.draw(new Diamond(lineEnd, h));
                }
            }
        }
        g.dispose();
    }

    private class Arrow extends Path2D.Double {
        public Arrow(Vector2D basePoint, Vector2D base) {
            Vector2D p1 = basePoint.add(base.multiply(0.5));
            Vector2D p2 = basePoint.add(base.normalVector().reverse());
            Vector2D p3 = basePoint.add(base.multiply(0.5).reverse());
            moveTo(p1.x, p1.y);
            lineTo(p2.x, p2.y);
            lineTo(p3.x, p3.y);
        }
    }

    private class Triangle extends Path2D.Double {
        public Triangle(Vector2D basePoint, Vector2D base) {
            Vector2D p1 = basePoint.add(base.multiply(0.5));
            Vector2D p2 = basePoint.add(base.normalVector().reverse());
            Vector2D p3 = basePoint.add(base.multiply(0.5).reverse());
            moveTo(p1.x, p1.y);
            lineTo(p2.x, p2.y);
            lineTo(p3.x, p3.y);
            closePath();
        }
    }

    private class Diamond extends Path2D.Double {
        public Diamond(Vector2D basePoint, Vector2D hypotenuse) {
            Vector2D t = hypotenuse.multiply(0.5);
            Vector2D p1 = basePoint.add(t.add(t.normalVector().reverse()));
            Vector2D p2 = basePoint.add(hypotenuse);
            Vector2D p3 = basePoint.add(t.add(t.normalVector()));
            moveTo(basePoint.x, basePoint.y);
            lineTo(p1.x, p1.y);
            lineTo(p2.x, p2.y);
            lineTo(p3.x, p3.y);
            closePath();
        }
    }
}
