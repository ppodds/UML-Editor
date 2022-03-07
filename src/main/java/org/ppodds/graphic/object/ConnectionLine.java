package org.ppodds.graphic.object;

public class ConnectionLine {
    private final UMLObject.ConnectionPortDirection fromConnectionPort;
    private final UMLObject.ConnectionPortDirection toConnectionPort;
    private final UMLObject fromObject;
    private final UMLObject toObject;
    private final ConnectionLineType type;

    public ConnectionLine(ConnectionLineType type, UMLObject.ConnectionPortDirection fromConnectionPort, UMLObject.ConnectionPortDirection toConnectionPort, UMLObject fromObject, UMLObject toObject) {
        this.type = type;
        this.fromConnectionPort = fromConnectionPort;
        this.toConnectionPort = toConnectionPort;
        this.fromObject = fromObject;
        this.toObject = toObject;
    }

    public UMLObject.ConnectionPortDirection getFromConnectionPort() {
        return fromConnectionPort;
    }

    public UMLObject.ConnectionPortDirection getToConnectionPort() {
        return toConnectionPort;
    }

    public UMLObject getFromObject() {
        return fromObject;
    }

    public UMLObject getToObject() {
        return toObject;
    }

    public ConnectionLineType getType() {
        return type;
    }

    public enum ConnectionLineType {
        ASSOCIATION_LINE, COMPOSITION_LINE, GENERALIZATION_LINE
    }
}
