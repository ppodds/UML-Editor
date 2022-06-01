package org.ppodds.graphic.editor;

import javax.swing.*;

public class EditorContentPane extends JPanel {
    private final UMLCanvas canvas;
    private final EditorButtonBar editorButtonBar;

    public EditorContentPane() {
        editorButtonBar = new EditorButtonBar();
        canvas = new UMLCanvas();

        add(editorButtonBar);
        add(canvas);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(editorButtonBar)
                        .addComponent(canvas)

        );

        layout.setVerticalGroup(layout.createParallelGroup().addComponent(editorButtonBar).addComponent(canvas));
    }

    public UMLCanvas getCanvas() {
        return canvas;
    }

    public EditorButtonBar getEditorButtonBar() {
        return editorButtonBar;
    }
}
