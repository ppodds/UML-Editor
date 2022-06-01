package org.ppodds.graphic.object;

import org.ppodds.core.math.Point;
import org.ppodds.core.math.Shape;
import org.ppodds.graphic.editor.Editor;

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
    private final PreviewObject movingPreview = null;

    public UMLObject(boolean linkable, boolean nameCustomizable, int padding) {
        super();
        this.linkable = linkable;
        this.nameCustomizable = nameCustomizable;
        this.padding = padding;
        init();
    }

    public UMLObject topObject() {
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
        repaint();
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
