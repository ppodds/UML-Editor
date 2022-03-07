package org.ppodds.graphic;

import org.ppodds.graphic.object.ClassObject;
import org.ppodds.graphic.object.CompositeObject;
import org.ppodds.graphic.object.UMLObject;
import org.ppodds.graphic.object.UseCaseObject;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This class paint UML objects on canvas.
 * The objects should be painted
 */
public class UMLCanvas extends JPanel {
    private final Editor editor;
    private SelectedArea selectedArea;

    public UMLCanvas() {
        super(new UMLCanvasLayout());
        editor = Editor.getInstance();
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
}
