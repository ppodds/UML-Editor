package org.ppodds.graphic;

import javax.swing.*;
import java.awt.*;

public class ToolButton extends JButton {
    private final EditorState.EditorOperation operation;
    private final Editor editor;

    public ToolButton(Icon icon, EditorState.EditorOperation operation) {
        super(icon);
        editor = Editor.getInstance();
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
            if (editor.getState().getOperation() == operation)
                setBackground(Color.BLACK);
            else
                setBackground(Color.WHITE);
        });
    }
}
