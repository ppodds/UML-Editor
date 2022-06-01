package org.ppodds.graphic.editor.button;

import org.ppodds.graphic.editor.Editor;
import org.ppodds.graphic.editor.operation.Behavior;

import javax.swing.*;
import java.awt.*;

public class ToolButton extends JButton {
    public ToolButton(Icon icon, Behavior behavior, boolean isDefault) {
        super(icon);
        setMargin(new Insets(0, 0, 0, 0));
        setBackground(Color.WHITE);
        if (isDefault) {
            setBackground(Color.BLACK);
            Editor.getInstance().getEditorState().getOperation().setUsingBehavior(behavior);
        }
        addActionListener(e -> Editor.getInstance().getEditorState().getOperation().setUsingBehavior(behavior));
        Editor.getInstance().getEditorState().getOperation().addChangeListener(e -> {
            if (Editor.getInstance().getEditorState().getOperation().getUsingBehavior().getName().equals(behavior.getName()))
                setBackground(Color.BLACK);
            else setBackground(Color.WHITE);
        });
    }
}
