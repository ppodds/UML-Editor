package org.ppodds.graphic.editor;

import javax.swing.*;
import java.awt.*;

public class ToolButton extends JButton {
    private final EditorState.EditorOperation operation;

    public ToolButton(Icon icon, EditorState.EditorOperation operation) {
        super(icon);
        this.operation = operation;
        init(operation);
        setMargin(new Insets(0, 0, 0, 0));
    }

    private void init(EditorState.EditorOperation operation) {
        setBackground(Color.WHITE);
        if (operation == EditorState.EditorOperation.SELECT)
            setBackground(Color.BLACK);

        addActionListener(e -> Editor.getInstance().getEditorState().setOperation(this.operation));
        Editor.getInstance().getEditorState().addChangeListener(e -> {
            if (Editor.getInstance().getEditorState().getOperation() == operation)
                setBackground(Color.BLACK);
            else
                setBackground(Color.WHITE);
        });
    }
}
