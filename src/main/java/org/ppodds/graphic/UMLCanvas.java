package org.ppodds.graphic;

import org.ppodds.graphic.object.ClassObject;
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
                if (editor.getState().getOperation() == EditorState.EditorOperation.SELECT)
                    editor.getState().setSelected(null);
                System.out.println("pressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
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
                System.out.println("dragged");
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                System.out.println("moved");
            }
        });
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
