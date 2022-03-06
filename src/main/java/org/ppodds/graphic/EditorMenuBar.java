package org.ppodds.graphic;

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
        JMenuItem changeObjectName = new JMenuItem("change object name");
        editMenu.add(group);
        editMenu.add(ungroup);
        editMenu.add(changeObjectName);

        add(fileMenu);
        add(editMenu);
    }
}
