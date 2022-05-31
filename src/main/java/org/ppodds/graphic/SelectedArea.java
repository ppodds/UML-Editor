package org.ppodds.graphic;

import org.ppodds.graphic.editor.Editor;
import org.ppodds.graphic.object.UMLObject;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class SelectedArea extends JPanel {
    private int centerX;
    private int centerY;

    public SelectedArea(int x, int y) {
        super();
        this.setOpaque(false);
        centerX = x;
        centerY = y;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public void selecting(int mouseX, int mouseY) {
        setLocation(Math.min(mouseX, centerX), Math.min(mouseY, centerY));
        setSize(Math.abs(mouseX - centerX), Math.abs(mouseY - centerY));
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(new Color(100, 200, 250, 100));
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    public void selectUMLObjects(Component[] components) {
        LinkedList<UMLObject> selectedObjectList = new LinkedList<>();
        for (var c : components) {
            if (c instanceof UMLObject) {
                UMLObject o = (UMLObject) c;
                // check if the object in the selected area
                if (o.getX() > getX() && o.getX() + o.getWidth() < getX() + getWidth()
                        && o.getY() > getY() && o.getY() + o.getHeight() < getY() + getHeight()
                        && !o.isGrouped()) {
                    o.setSelected(true);
                    selectedObjectList.add(o);
                }
            }
        }
        UMLObject[] selectedObjects = new UMLObject[selectedObjectList.size()];
        Editor.getInstance().getEditorState().setSelectedObjects(selectedObjectList.toArray(selectedObjects));
    }
}
