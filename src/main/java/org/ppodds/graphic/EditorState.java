package org.ppodds.graphic;

import org.ppodds.core.event.ChangeEvent;

public class EditorState {
    private final Editor editor;
    private EditorOperation operation = EditorOperation.SELECT;
    public EditorState(Editor editor) {
        this.editor = editor;
    }

    public EditorOperation getOperation() {
        return operation;
    }

    public void setOperation(EditorOperation operation) {
        this.operation = operation;
        publishEvent();
    }

    private void publishEvent() {
        for (var l : editor.getChangeListeners()) {
            l.stateChanged(new ChangeEvent(editor));
        }
    }

    public enum EditorOperation {
        SELECT, ASSOCIATION_LINE, GENERALIZATION_LINE, COMPOSITION_LINE, CLASS, USE_CASE
    }
}
