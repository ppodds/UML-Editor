package org.ppodds.graphic;

import org.ppodds.core.event.ChangeEvent;
import org.ppodds.graphic.object.ConnectionLine;
import org.ppodds.graphic.object.UMLObject;

public class EditorState {
    private final Editor editor;
    private EditorOperation operation = EditorOperation.SELECT;
    private UMLObject[] selectedObjects = null;
    private CreatingConnectionLine creatingConnectionLine = null;

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

    public CreatingConnectionLine getCreatingConnectionLine() {
        return creatingConnectionLine;
    }

    public void createCreatingConnectionLine(UMLObject.ConnectionPortDirection fromConnectionPort) {
        ConnectionLine.ConnectionLineType type;
        switch (operation) {
            case ASSOCIATION_LINE -> type = ConnectionLine.ConnectionLineType.ASSOCIATION_LINE;
            case GENERALIZATION_LINE -> type = ConnectionLine.ConnectionLineType.GENERALIZATION_LINE;
            case COMPOSITION_LINE -> type = ConnectionLine.ConnectionLineType.COMPOSITION_LINE;
            default -> throw new IllegalStateException("Unexpected value: " + operation);
        }
        System.out.println("AA");
        creatingConnectionLine = new CreatingConnectionLine(type, fromConnectionPort);
        publishEvent();
    }

    public void removeCreatingConnectionLine() {
        creatingConnectionLine = null;
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

    public class CreatingConnectionLine {
        public final ConnectionLine.ConnectionLineType type;
        public final UMLObject.ConnectionPortDirection fromConnectionPort;

        public CreatingConnectionLine(ConnectionLine.ConnectionLineType type,
                                      UMLObject.ConnectionPortDirection fromConnectionPort) {
            this.type = type;
            this.fromConnectionPort = fromConnectionPort;
        }
    }
}
