package org.ppodds.graphic;

public class EditorState {
    enum EditorOperation {
        SELECT, ASSOCIATION_LINE, GENERALIZATION_LINE, COMPOSITION_LINE, CLASS, USE_CASE
    }

    private EditorOperation operation;

    public void setOperation(EditorOperation operation) {
        this.operation = operation;
    }

    public EditorOperation getOperation() {
        return operation;
    }
}
