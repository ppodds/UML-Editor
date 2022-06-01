package org.ppodds.graphic.editor;

import org.ppodds.graphic.line.ConnectionLine;
import org.ppodds.graphic.object.CompositeObject;
import org.ppodds.graphic.object.UMLObject;

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

    private final List<ConnectionLine> connectionLineList = new LinkedList<>();
    private final List<UMLObject> umlObjectList = new LinkedList<>();
    public static final int WIDTH = 540;
    public static final int HEIGHT = 540;

    public UMLCanvas() {
        super(null);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Editor.getInstance().getEditorState().getOperation().getUsingBehavior().mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Editor.getInstance().getEditorState().getOperation().getUsingBehavior().mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Editor.getInstance().getEditorState().getOperation().getUsingBehavior().mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Editor.getInstance().getEditorState().getOperation().getUsingBehavior().mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Editor.getInstance().getEditorState().getOperation().getUsingBehavior().mouseExited(e);
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Editor.getInstance().getEditorState().getOperation().getUsingBehavior().mouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Editor.getInstance().getEditorState().getOperation().getUsingBehavior().mouseMoved(e);
            }
        });
    }

    public void changeObjectName(UMLObject obj, String name) {
        if (obj instanceof CompositeObject)
            return;
        obj.setName(name);
        setComponentZOrder(obj, 0);
        repaint();
    }

    public UMLObject[] getObjects() {
        UMLObject[] t = new UMLObject[umlObjectList.size()];
        umlObjectList.toArray(t);
        return t;
    }

    public void addObject(UMLObject object) {
        add(object);
        umlObjectList.add(object);
        setComponentZOrder(object, 0);
        repaint();
    }

    public void removePreviewObject(UMLObject previewObject) {
        remove(previewObject);
        umlObjectList.remove(previewObject);
        repaint();
    }

    public void addConnectionLine(ConnectionLine connectionLine) {
        connectionLineList.add(connectionLine);
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
