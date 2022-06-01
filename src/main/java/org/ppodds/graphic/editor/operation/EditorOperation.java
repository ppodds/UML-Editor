package org.ppodds.graphic.editor.operation;

import org.ppodds.core.event.ChangeEvent;
import org.ppodds.core.event.ChangeListener;

import java.util.ArrayList;
import java.util.List;

public class EditorOperation {
    private final List<ChangeListener> listenerList = new ArrayList<>();
    private Behavior usingBehavior;

    public Behavior getUsingBehavior() {
        return usingBehavior;
    }

    public void setUsingBehavior(Behavior usingBehavior) {
        this.usingBehavior = usingBehavior;
        publishEvent();
    }

    public void addChangeListener(ChangeListener listener) {
        listenerList.add(listener);
    }

    private void publishEvent() {
        for (var l : listenerList) {
            l.stateChanged(new ChangeEvent(this));
        }
    }
}
