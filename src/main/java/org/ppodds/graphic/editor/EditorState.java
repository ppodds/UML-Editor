package org.ppodds.graphic.editor;

import org.ppodds.graphic.editor.operation.EditorOperation;

public class EditorState {
    private final EditorOperation operation;

    public EditorState() {
        operation = new EditorOperation();
    }

    public EditorOperation getOperation() {
        return operation;
    }

//    public void setOperation(EditorOperation operation) {
//        this.operation = operation;
//        if (operation != EditorOperation.SELECT)
//            selectedObjects = null;
//        publishEvent();
//    }


}
