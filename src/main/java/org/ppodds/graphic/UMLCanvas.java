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
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("clicked");
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
                    selectedArea.repaint();
                }
                System.out.println("pressed");
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
                System.out.println("released");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("entered");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("exited");
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (editor.getState().getOperation() == EditorState.EditorOperation.SELECT && selectedArea != null) {
                    selectedArea.selecting(Math.max(e.getX(), 0), Math.max(e.getY(), 0));
                }
                System.out.println("dragged");
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                System.out.println("moved");
            }
        });
    }

    public void createCompositeObject() {
        UMLObject[] selectedObjects = editor.getState().getSelectedObjects();
        if (selectedObjects != null && selectedObjects.length > 1) {
            for (var o : selectedObjects)
                remove(o);
            add(new CompositeObject(selectedObjects));
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
                child.setLocation(x + child.getX(), y + child.getY());
                ((UMLObject) child).setGrouped(false);
            }
            repaint();
        }
    }

    public void createClassObject(int x, int y) {
        add(new ClassObject(x, y));
        repaint();
    }

    public void createUseCaseObject(int x, int y) {
        add(new UseCaseObject(x, y));
        repaint();
    }

    public void createConnectionLine(ConnectionLine.ConnectionLineType type, UMLObject.ConnectionPortDirection fromConnectionPort, UMLObject.ConnectionPortDirection toConnectionPort, UMLObject fromObject, UMLObject toObject) {
        connectionLineList.add(new ConnectionLine(type, fromConnectionPort, toConnectionPort, fromObject, toObject));
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.BLACK);
        for (var c : connectionLineList) {
            UMLObject fromObject = c.getFromObject();
            org.ppodds.core.math.Point p1 = fromObject.getConnectionPortOfDirection(c.getFromConnectionPort());
            UMLObject toObject = c.getToObject();
            Point p2 = toObject.getConnectionPortOfDirection(c.getToConnectionPort());
            switch (c.getType()) {
                case GENERALIZATION_LINE -> {
                    Vector2D start = new Vector2D(fromObject.getX() + fromObject.getPadding() + p1.getX(),
                            fromObject.getY() + fromObject.getPadding() + p1.getY());
                    Vector2D end = new Vector2D(toObject.getX() + toObject.getPadding() + p2.getX(),
                            toObject.getY() + toObject.getPadding() + p2.getY());
                    Vector2D v = end.subtract(start);
                    Vector2D base = v.normalVector().unitVector().multiply(7 * 2);
                    Vector2D lineEnd = start.add(v.subtract(v.unitVector().multiply(7 * Math.sqrt(3))));
                    g2.drawLine((int) start.x, (int) start.y, (int) lineEnd.x, (int) lineEnd.y);
                    g2.draw(new Triangle(lineEnd, base));
                }
            }

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
}
