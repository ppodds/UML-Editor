package org.ppodds.graphic;

import org.ppodds.graphic.object.UMLObject;

import javax.swing.*;

public class EditorMenuBar extends JMenuBar {
    private final Editor editor;
    private final JMenu fileMenu;
    private final JMenu editMenu;

    public EditorMenuBar() {
        editor = Editor.getInstance();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        JMenuItem group = new JMenuItem("group");
        group.addActionListener(e -> {
            editor.getCanvas().createCompositeObject();
            editor.getState().setSelectedObjects(null);
        });
        JMenuItem ungroup = new JMenuItem("ungroup");
        ungroup.addActionListener(e -> {
            editor.getCanvas().ungroupCompositeObject();
            editor.getState().setSelectedObjects(null);
        });
        JMenuItem changeObjectName = new JMenuItem("change object name");
        changeObjectName.addActionListener(e -> {
            UMLObject[] selectedObjects = editor.getState().getSelectedObjects();
            if (selectedObjects != null
                    && selectedObjects.length == 1
                    && selectedObjects[0].isNameCustomizable()) {
                new ChangeObjectNameFrame(selectedObjects[0]);
            }
        });
        editMenu.add(group);
        editMenu.add(ungroup);
        editMenu.add(changeObjectName);

        add(fileMenu);
        add(editMenu);
    }
}
