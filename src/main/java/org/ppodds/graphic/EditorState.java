package org.ppodds.graphic;

import org.ppodds.core.event.ChangeEvent;
import org.ppodds.graphic.object.UMLObject;

public class EditorState {
    private final Editor editor;
    private EditorOperation operation = EditorOperation.SELECT;
    private UMLObject[] selectedObjects = null;

    public EditorState() {
        editor = Editor.getInstance();
    }

    public EditorOperation getOperation() {
        return operation;
    }

    public void setOperation(EditorOperation operation) {
        this.operation = operation;
        if (operation != EditorOperation.SELECT)
            selectedObjects = null;
        publishEvent();
    }

    private void publishEvent() {
        for (var l : editor.getChangeListeners()) {
            l.stateChanged(new ChangeEvent(editor));
        }
    }

    public UMLObject[] getSelectedObjects() {
        return selectedObjects;
    }

    public void setSelectedObjects(UMLObject[] selectedObjects) {
        this.selectedObjects = selectedObjects;
        publishEvent();
    }

    public enum EditorOperation {
        SELECT, ASSOCIATION_LINE, GENERALIZATION_LINE, COMPOSITION_LINE, CLASS, USE_CASE
    }
}
