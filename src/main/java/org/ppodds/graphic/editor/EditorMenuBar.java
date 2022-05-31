package org.ppodds.graphic.editor;

import org.ppodds.graphic.object.UMLObject;

import javax.swing.*;

public class EditorMenuBar extends JMenuBar {
    public EditorMenuBar() {
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenuItem group = new JMenuItem("group");
        group.addActionListener(e -> {
            Editor.getInstance().getEditorContentPane().getCanvas().createCompositeObject();
            Editor.getInstance().getEditorState().setSelectedObjects(null);
        });
        JMenuItem ungroup = new JMenuItem("ungroup");
        ungroup.addActionListener(e -> {
            Editor.getInstance().getEditorContentPane().getCanvas().ungroupCompositeObject();
            Editor.getInstance().getEditorState().setSelectedObjects(null);
        });
        JMenuItem changeObjectName = new JMenuItem("change object name");
        changeObjectName.addActionListener(e -> {
            UMLObject[] selectedObjects = Editor.getInstance().getEditorState().getSelectedObjects();
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
