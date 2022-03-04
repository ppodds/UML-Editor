package org.ppodds.graphic;

import org.ppodds.core.event.ChangeEvent;

public class EditorState {
    enum EditorOperation {
        SELECT, ASSOCIATION_LINE, GENERALIZATION_LINE, COMPOSITION_LINE, CLASS, USE_CASE
    }

    private EditorOperation operation = EditorOperation.SELECT;
    private final Editor editor;

    public EditorState(Editor editor) {
        this.editor = editor;
    }

    public void setOperation(EditorOperation operation) {
        this.operation = operation;
        publishEvent();
    }

    public EditorOperation getOperation() {
        return operation;
    }

    private void publishEvent() {
        for (var l : editor.getChangeListeners()) {
            l.stateChanged(new ChangeEvent(editor));
        }
    }
}
