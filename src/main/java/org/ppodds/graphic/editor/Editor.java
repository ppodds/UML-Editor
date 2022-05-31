package org.ppodds.graphic.editor;

import org.ppodds.core.event.ChangeListener;

import javax.swing.*;

public class Editor extends JFrame {
    private static Editor instance = null;
    private final EditorState editorState;
    private final EditorContentPane editorContentPane;

    public Editor() {
        instance = this;
        editorState = new EditorState();
        editorContentPane = new EditorContentPane();

        setTitle("UML Editor");
        setContentPane(editorContentPane);
        setJMenuBar(new EditorMenuBar());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public EditorState getEditorState() {
        return editorState;
    }

    public EditorContentPane getEditorContentPane() {
        return editorContentPane;
    }

    public static Editor getInstance() {
        return instance;
    }


    public ChangeListener[] getChangeListeners() {
        return getListeners(ChangeListener.class);
    }


}
