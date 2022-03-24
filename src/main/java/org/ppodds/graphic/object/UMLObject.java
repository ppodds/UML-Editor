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

    private UMLObject topObject() {
        UMLObject topObject = this;
        while (topObject.isGrouped) {
            topObject = (UMLObject) topObject.getParent();
        }
        return topObject;
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
                    UMLObject topObject = o.topObject();
                    topObject.isSelected = !topObject.isSelected;
                    if (topObject.isSelected) {
                        state.setSelectedObjects(new UMLObject[]{topObject});
                        topObject.beforeMovePosition = new Point(topObject.getX(), topObject.getY());
                        topObject.beforeMoveOffset = new Point(e.getX(), e.getY());
                        topObject.movingPreview = new PreviewObject(topObject);
                        Editor.getInstance().getCanvas().showPreviewObject(topObject.movingPreview);
                    } else
                        state.setSelectedObjects(null);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                UMLObject o = (UMLObject) e.getSource();
                UMLObject topObject = o.topObject();
                if (topObject.isSelected) {
                    topObject.setLocation(topObject.movingPreview.getX(), topObject.movingPreview.getY());
                    topObject.setVisible(true);
                    Editor.getInstance().getCanvas().removePreviewObject(topObject.movingPreview);
                    topObject.movingPreview = null;
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
                UMLObject topObject = o.topObject();
                if (topObject.isSelected) {
                    topObject.setVisible(false);
                    topObject.movingPreview.setVisible(true);
                    topObject.movingPreview.setLocation(topObject.beforeMovePosition.getX() + e.getX() - topObject.beforeMoveOffset.getX(),
                            topObject.beforeMovePosition.getY() + e.getY() - topObject.beforeMoveOffset.getY());
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
