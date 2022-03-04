package org.ppodds.graphic;

import javax.swing.*;
import java.awt.*;

public class ToolButton extends JButton {
    private final Editor editor;
    private final EditorState.EditorOperation operation;

    public ToolButton(String text, Editor editor, EditorState.EditorOperation operation) {
        super(text);
        this.editor = editor;
        this.operation = operation;
        init(operation);
    }

    public ToolButton(Icon icon, Editor editor, EditorState.EditorOperation operation) {
        super(icon);
        this.editor = editor;
        this.operation = operation;
        init(operation);
        setMargin(new Insets(0, 0, 0, 0));
    }

    private void init(EditorState.EditorOperation operation) {
        setBackground(Color.WHITE);
        if (operation == EditorState.EditorOperation.SELECT)
            setBackground(Color.BLACK);
        addActionListener(e -> editor.getState().setOperation(this.operation));
        editor.addChangeListener(e -> {
            Editor editor = (Editor) e.getSource();
            if (editor.getState().getOperation() == operation)
                setBackground(Color.BLACK);
            else
                setBackground(Color.WHITE);
        });
    }
}
