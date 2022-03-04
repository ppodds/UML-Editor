package org.ppodds.core.event;

import java.util.EventObject;

public class ChangeEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ChangeEvent(Object source) {
        super(source);
    }
}
