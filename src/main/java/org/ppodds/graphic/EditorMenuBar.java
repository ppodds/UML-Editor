package org.ppodds.graphic;

import javax.swing.*;

public class EditorMenuBar extends JMenuBar {
    private final JMenu fileMenu;
    private final JMenu editMenu;

    public EditorMenuBar() {
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        JMenuItem group = new JMenuItem("group");
        JMenuItem ungroup = new JMenuItem("ungroup");
        JMenuItem changeObjectName = new JMenuItem("change object name");
        editMenu.add(group);
        editMenu.add(ungroup);
        editMenu.add(changeObjectName);

        add(fileMenu);
        add(editMenu);
    }
}
