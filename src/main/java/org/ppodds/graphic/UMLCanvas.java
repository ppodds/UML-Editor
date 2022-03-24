package org.ppodds.graphic;

import org.ppodds.graphic.line.*;
import org.ppodds.graphic.object.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
    public static final int WIDTH = 540;
    public static final int HEIGHT = 540;

    public UMLCanvas() {
        super(null);
        editor = Editor.getInstance();
        connectionLineList = new LinkedList<>();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
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
            int x = o.getX();
            int y = o.getY();
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

    public void createConnectionLine(ConnectionLineType type, UMLBasicObject.ConnectionPortDirection fromConnectionPort, UMLBasicObject.ConnectionPortDirection toConnectionPort, UMLBasicObject fromObject, UMLBasicObject toObject) {
        switch (type) {
            case ASSOCIATION_LINE -> connectionLineList.add(new AssociationLine(fromConnectionPort, toConnectionPort, fromObject, toObject));
            case GENERALIZATION_LINE -> connectionLineList.add(new GeneralizationLine(fromConnectionPort, toConnectionPort, fromObject, toObject));
            case COMPOSITION_LINE -> connectionLineList.add(new CompositionLine(fromConnectionPort, toConnectionPort, fromObject, toObject));
        }
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
        for (var c : connectionLineList)
            c.paint(g);
        g.dispose();
    }
}
