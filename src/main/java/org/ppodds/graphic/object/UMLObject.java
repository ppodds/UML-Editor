package org.ppodds.graphic.object;

import org.ppodds.core.math.Point;
import org.ppodds.core.math.Shape;
import org.ppodds.graphic.Editor;
import org.ppodds.graphic.EditorState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class UMLObject extends JComponent {
    private final boolean nameCustomizable;
    private final boolean linkable;
    private final int padding;
    protected Shape shape;
    private boolean isSelected = false;
    private boolean isGrouped = false;
    private Point beforeMovePosition;
    private Point beforeMoveOffset;
    private PreviewObject movingPreview = null;

    public UMLObject(boolean linkable, boolean nameCustomizable, int padding) {
        super();
        this.linkable = linkable;
        this.nameCustomizable = nameCustomizable;
        this.padding = padding;
        init();
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

    public boolean isLinkable() {
        return linkable;
    }

    public boolean isNameCustomizable() {
        return nameCustomizable;
    }

    private void init() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                UMLObject o = (UMLObject) e.getSource();
                EditorState state = Editor.getInstance().getState();
                if (state.getOperation() == EditorState.EditorOperation.SELECT) {
                    o = topObject(o);
                    o.isSelected = !o.isSelected;
                    if (o.isSelected) {
                        state.setSelectedObjects(new UMLObject[]{o});
                        beforeMovePosition = new Point(o.getX(), o.getY());
                        beforeMoveOffset = new Point(e.getX(), e.getY());
                        movingPreview = new PreviewObject(o);
                        Editor.getInstance().getCanvas().showPreviewObject(movingPreview);
                    } else
                        state.setSelectedObjects(null);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                UMLObject o = (UMLObject) e.getSource();
                o = topObject(o);
                if (o.isSelected) {
                    o.setLocation(movingPreview.getX(), movingPreview.getY());
                    o.setVisible(true);
                    Editor.getInstance().getCanvas().removePreviewObject(movingPreview);
                    movingPreview = null;
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
                UMLObject o = (UMLObject) e.getSource();
                o = topObject(o);
                if (o.isSelected) {
                    o.setVisible(false);
                    movingPreview.setVisible(true);
                    movingPreview.setLocation(o.beforeMovePosition.getX() + e.getX() - o.beforeMoveOffset.getX(),
                            o.beforeMovePosition.getY() + e.getY() - o.beforeMoveOffset.getY());
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        // set isSelected = false when select other object or null
        Editor.getInstance().addChangeListener(e -> {
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

    public boolean isGrouped() {
        return isGrouped;
    }

    public void setGrouped(boolean grouped) {
        isGrouped = grouped;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public void paintComponent(Graphics g) {
        paintChildren(g);
        super.paintComponent(g);
        paintSelf(g);
    }

    public int getPadding() {
        return padding;
    }

    protected abstract void paintSelf(Graphics g);
}
