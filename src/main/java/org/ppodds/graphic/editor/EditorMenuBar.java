package org.ppodds.graphic.editor;

import org.ppodds.graphic.editor.operation.behavior.SelectBehavior;

import javax.swing.*;

public class EditorMenuBar extends JMenuBar {
    public EditorMenuBar() {
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenuItem group = new JMenuItem("group");
        group.addActionListener(e -> {
            if (Editor.getInstance().getEditorState().getOperation().getUsingBehavior() instanceof SelectBehavior b) {
                b.createCompositeObject();
            }
        });
        JMenuItem ungroup = new JMenuItem("ungroup");
        ungroup.addActionListener(e -> {
            if (Editor.getInstance().getEditorState().getOperation().getUsingBehavior() instanceof SelectBehavior b) {
                b.ungroupCompositeObject();
            }
        });
        JMenuItem changeObjectName = new JMenuItem("change object name");
        changeObjectName.addActionListener(e -> {
            if (Editor.getInstance().getEditorState().getOperation().getUsingBehavior() instanceof SelectBehavior b) {
                b.changeObjectName();
            }
        });
        editMenu.add(group);
        editMenu.add(ungroup);
        editMenu.add(changeObjectName);

        add(fileMenu);
        add(editMenu);
    }
}
