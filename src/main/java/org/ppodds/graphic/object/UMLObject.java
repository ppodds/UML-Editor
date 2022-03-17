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
    private static CreatingConnectionLine creatingConnectionLine = null;
    protected final boolean linkable;
    protected final boolean nameCustomizable;
    private final Editor editor;
    protected int padding;
    private boolean isSelected = false;
    private boolean isGrouped = false;
    protected Shape shape;
    private int beforeMoveX;
    private int beforeMoveY;
    private int beforeMoveXOffset;
    private int beforeMoveYOffset;
    private PreviewObject movingPreview = null;

    public UMLObject(boolean linkable, boolean nameCustomizable) {
        super();
        editor = Editor.getInstance();
        this.linkable = linkable;
        this.nameCustomizable = nameCustomizable;
        init();
    }

    public UMLObject(boolean linkable, boolean nameCustomizable, int x, int y) {
        super();
        editor = Editor.getInstance();
        this.linkable = linkable;
        this.nameCustomizable = nameCustomizable;
        setLocation(x, y);
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
                EditorState state = editor.getState();
                if (state.getOperation() == EditorState.EditorOperation.SELECT) {
                    o = topObject(o);
                    o.isSelected = !o.isSelected;
                    if (o.isSelected) {
                        state.setSelectedObjects(new UMLObject[]{o});
                        beforeMoveX = o.getX();
                        beforeMoveY = o.getY();
                        beforeMoveXOffset = e.getX();
                        beforeMoveYOffset = e.getY();
                        movingPreview = new PreviewObject(o);
                        editor.getCanvas().showPreviewObject(movingPreview);
                    } else
                        state.setSelectedObjects(null);
                } else if ((state.getOperation() == EditorState.EditorOperation.ASSOCIATION_LINE
                        || state.getOperation() == EditorState.EditorOperation.GENERALIZATION_LINE
                        || state.getOperation() == EditorState.EditorOperation.COMPOSITION_LINE)
                        && !o.isGrouped && linkable) {
                    var t = new CreatingConnectionLine();
                    switch (state.getOperation()) {
                        case ASSOCIATION_LINE -> t.type = ConnectionLine.ConnectionLineType.ASSOCIATION_LINE;
                        case GENERALIZATION_LINE -> t.type = ConnectionLine.ConnectionLineType.GENERALIZATION_LINE;
                        case COMPOSITION_LINE -> t.type = ConnectionLine.ConnectionLineType.COMPOSITION_LINE;
                    }
                    t.fromConnectionPort = getConnectionPortDirection(e.getX(), e.getY());
                    UMLObject.creatingConnectionLine = t;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                UMLObject o = (UMLObject) e.getSource();
                EditorState state = editor.getState();
                if (UMLObject.creatingConnectionLine != null
                        && (state.getOperation() == EditorState.EditorOperation.ASSOCIATION_LINE
                        || state.getOperation() == EditorState.EditorOperation.GENERALIZATION_LINE
                        || state.getOperation() == EditorState.EditorOperation.COMPOSITION_LINE)
                        && !o.isGrouped) {
                    int x = o.getX() + e.getX();
                    int y = o.getY() + e.getY();

                    UMLObject toObject = null;
                    for (var c : editor.getCanvas().getComponents()) {
                        if (((UMLObject) c).linkable) {
                            // check if the mouse in the object
                            if (x > c.getX() && x < c.getX() + c.getWidth()
                                    && y > c.getY() && y < c.getY() + c.getHeight()) {
                                if (toObject == null) {
                                    toObject = (UMLObject) c;
                                }
                            }
                        }
                    }
                    if (toObject != null) {
                        var t = UMLObject.creatingConnectionLine;
                        editor.getCanvas().createConnectionLine(t.type,
                                t.fromConnectionPort,
                                toObject.getConnectionPortDirection(
                                        x - toObject.getX(),
                                        y - toObject.getY()),
                                o, toObject);
                    }
                    UMLObject.creatingConnectionLine = null;
                }
                o = topObject(o);
                if (o.isSelected) {
                    o.setLocation(movingPreview.getX(), movingPreview.getY());
                    o.setVisible(true);
                    editor.getCanvas().removePreviewObject(movingPreview);
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
                    movingPreview.setLocation(o.beforeMoveX + e.getX() - o.beforeMoveXOffset,
                            o.beforeMoveY + e.getY() - o.beforeMoveYOffset);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

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

    public boolean isGrouped() {
        return isGrouped;
    }

    public void setGrouped(boolean grouped) {
        isGrouped = grouped;
    }

    public Point getConnectionPortOfDirection(ConnectionPortDirection direction) {
        return switch (direction) {
            case TOP -> shape.getPointOfDirection(Shape.Direction.TOP);
            case RIGHT -> shape.getPointOfDirection(Shape.Direction.RIGHT);
            case BOTTOM -> shape.getPointOfDirection(Shape.Direction.BOTTOM);
            case LEFT -> shape.getPointOfDirection(Shape.Direction.LEFT);
        };
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
        paintConnectionPorts(g);
        g.dispose();
    }

    protected abstract void paintSelf(Graphics g);

    protected void paintConnectionPorts(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (isSelected) {
            // connection ports
            Point p1 = shape.getPointOfDirection(Shape.Direction.LEFT_TOP);
            Point p2 = shape.getPointOfDirection(Shape.Direction.RIGHT_BOTTOM);
            Point p3 = shape.getPointOfDirection(Shape.Direction.RIGHT_TOP);
            Point p4 = shape.getPointOfDirection(Shape.Direction.LEFT_BOTTOM);

            g2.drawLine(p1.getX() + padding,
                    p1.getY() + padding,
                    p2.getX() + padding,
                    p2.getY() + padding);
            g2.drawLine(p3.getX() + padding,
                    p3.getY() + padding,
                    p4.getX() + padding,
                    p4.getY() + padding);

            if (linkable) {
                int width = (getWidth() - padding * 2) / 20;
                int height = (getHeight() - padding * 2) / 20;

                Point p5 = shape.getPointOfDirection(Shape.Direction.TOP);
                Point p6 = shape.getPointOfDirection(Shape.Direction.BOTTOM);
                Point p7 = shape.getPointOfDirection(Shape.Direction.LEFT);
                Point p8 = shape.getPointOfDirection(Shape.Direction.RIGHT);

                g2.fillRect(p5.getX() + padding - width / 2, p5.getY() + padding - height, width, height);
                g2.fillRect(p6.getX() + padding - width / 2, p6.getY() + padding, width, height);
                g2.fillRect(p7.getX() + padding - width, p7.getY() + padding - height / 2, width, height);
                g2.fillRect(p8.getX() + padding, p8.getY() + padding - height / 2, width, height);
            }
        }
    }

    public ConnectionPortDirection getConnectionPortDirection(int x, int y) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        double degree = Math.toDegrees(
                Math.acos((x - centerX) / Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2))));
        if (degree >= 45 && degree <= 135) {
            if (centerY < y)
                return ConnectionPortDirection.BOTTOM;
            else
                return ConnectionPortDirection.TOP;
        } else if (degree >= 0 && degree <= 45)
            return ConnectionPortDirection.RIGHT;
        else
            return ConnectionPortDirection.LEFT;
    }

    public int getPadding() {
        return padding;
    }

    public enum ConnectionPortDirection {
        TOP, RIGHT, BOTTOM, LEFT
    }

    private class CreatingConnectionLine {
        public ConnectionLine.ConnectionLineType type;
        public UMLObject.ConnectionPortDirection fromConnectionPort;
    }
}
