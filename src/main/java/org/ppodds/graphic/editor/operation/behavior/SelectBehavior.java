package org.ppodds.graphic.editor.operation.behavior;

import org.ppodds.core.math.Point;
import org.ppodds.graphic.editor.ChangeObjectNameFrame;
import org.ppodds.graphic.editor.Editor;
import org.ppodds.graphic.editor.operation.Behavior;
import org.ppodds.graphic.editor.operation.SelectedArea;
import org.ppodds.graphic.object.CompositeObject;
import org.ppodds.graphic.object.UMLObject;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class SelectBehavior extends Behavior {
    private UMLObject[] selectedObjects;
    private Point beforeMovePosition;
    private Point beforeMoveOffset;
    private UMLObject movingPreview;
    private SelectedArea selectedArea;

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() instanceof UMLObject o) {
            UMLObject topObject = o.topObject();
            topObject.setSelected(!topObject.isSelected());
            if (topObject.isSelected()) {
                setSelectedObjects(new UMLObject[]{topObject});
                beforeMovePosition = new Point(topObject.getX(), topObject.getY());
                beforeMoveOffset = new Point(e.getX(), e.getY());
                movingPreview = topObject.clone();
                Editor.getInstance().getEditorContentPane().getCanvas().addObject(movingPreview);
            } else setSelectedObjects(null);
        } else {
            // select null detect
            setSelectedObjects(null);
            // create select area
            selectedArea = new SelectedArea(e.getX(), e.getY());
            Editor.getInstance().getEditorContentPane().getCanvas().add(selectedArea);
            Editor.getInstance().getEditorContentPane().getCanvas().setComponentZOrder(selectedArea, 0);
            selectedArea.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() instanceof UMLObject o) {
            UMLObject topObject = o.topObject();
            if (topObject.isSelected()) {
                topObject.setLocation(movingPreview.getX(), movingPreview.getY());
                topObject.setVisible(true);
                Editor.getInstance().getEditorContentPane().getCanvas().removePreviewObject(movingPreview);
                movingPreview = null;
            }
        } else {
            if (selectedArea != null) {
                // select UMLObjects in selected area
                selectObjectsInArea(Editor.getInstance().getEditorContentPane().getCanvas().getObjects());

                Editor.getInstance().getEditorContentPane().getCanvas().remove(selectedArea);
                selectedArea = null;
                Editor.getInstance().getEditorContentPane().getCanvas().repaint();
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() instanceof UMLObject o) {
            UMLObject topObject = o.topObject();
            if (topObject.isSelected()) {
                topObject.setVisible(false);
                movingPreview.setVisible(true);
                movingPreview.setLocation(beforeMovePosition.x() + e.getX() - beforeMoveOffset.x(),
                        beforeMovePosition.y() + e.getY() - beforeMoveOffset.y());
            }
        } else {
            if (selectedArea != null) {
                selectedArea.selecting(Math.max(e.getX(), 0), Math.max(e.getY(), 0));
            }
        }
    }

    private void setSelectedObjects(UMLObject[] objects) {
        for (var o : Editor.getInstance().getEditorContentPane().getCanvas().getObjects()) {
            o.setSelected(false);
        }
        if (objects != null)
            for (var o : objects)
                o.setSelected(true);
        selectedObjects = objects;
    }

    public void createCompositeObject() {
        if (selectedObjects != null && selectedObjects.length > 1) {
            for (var o : selectedObjects)
                Editor.getInstance().getEditorContentPane().getCanvas().remove(o);

            CompositeObject obj = new CompositeObject(selectedObjects);
            Editor.getInstance().getEditorContentPane().getCanvas().addObject(obj);
        }
        setSelectedObjects(null);
    }

    public void ungroupCompositeObject() {
        if (selectedObjects != null && selectedObjects.length == 1
                && selectedObjects[0] instanceof CompositeObject o) {
            Editor.getInstance().getEditorContentPane().getCanvas().remove(o);
            int x = o.getX();
            int y = o.getY();
            for (var child : o.getComponents()) {
                o.remove(child);
                Editor.getInstance().getEditorContentPane().getCanvas().add(child);
                Editor.getInstance().getEditorContentPane().getCanvas().setComponentZOrder(child, 0);
                child.setLocation(x + child.getX(), y + child.getY());
                ((UMLObject) child).setGrouped(false);
            }
            Editor.getInstance().getEditorContentPane().getCanvas().repaint();
        }
        setSelectedObjects(null);
    }

    public void changeObjectName() {
        if (selectedObjects != null
                && selectedObjects.length == 1
                && selectedObjects[0].isNameCustomizable()) {
            new ChangeObjectNameFrame(selectedObjects[0]);
        }
    }

    private void selectObjectsInArea(Component[] components) {
        LinkedList<UMLObject> selectedObjectList = new LinkedList<>();
        for (var c : components) {
            if (c instanceof UMLObject o) {
                // check if the object in the selected area
                if (o.getX() > selectedArea.getX() && o.getX() + o.getWidth() < selectedArea.getX() + selectedArea.getWidth()
                        && o.getY() > selectedArea.getY() && o.getY() + o.getHeight() < selectedArea.getY() + selectedArea.getHeight()
                        && !o.isGrouped()) {
                    o.setSelected(true);
                    selectedObjectList.add(o);
                }
            }
        }
        UMLObject[] selectedObjects = new UMLObject[selectedObjectList.size()];
        setSelectedObjects(selectedObjectList.toArray(selectedObjects));
    }
}
