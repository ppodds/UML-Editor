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
//    private Type type;
//    private SelectBehavior selectBehavior;
//    private CreateLineBehavior createLineBehavior;
//    private CreateObjectBehavior createObjectBehavior;
//
//    public Type getType() {
//        return type;
//    }
//
//    public Behavior getUsingBehavior(Type type) {
//        return switch (type) {
//            case SELECT -> getSelectBehavior();
//            case CREATE_LINE -> getCreateLineBehavior();
//            case CREATE_OBJECT -> getCreateObjectBehavior();
//        };
//    }
//
//    public void setUsingBehavior(Type type, Behavior usingBehavior) {
//        switch (type) {
//            case SELECT -> setSelectBehavior((SelectBehavior) usingBehavior);
//            case CREATE_LINE -> setCreateLineBehavior((CreateLineBehavior) usingBehavior);
//            case CREATE_OBJECT -> setCreateObjectBehavior((CreateObjectBehavior) usingBehavior)
//        }
//        this.type = type;
//    }
//
//    public SelectBehavior getSelectBehavior() {
//        return selectBehavior;
//    }
//
//    public void setSelectBehavior(SelectBehavior selectBehavior) {
//        this.selectBehavior = selectBehavior;
//    }
//
//    public CreateLineBehavior getCreateLineBehavior() {
//        return createLineBehavior;
//    }
//
//    public void setCreateLineBehavior(CreateLineBehavior createLineBehavior) {
//        this.createLineBehavior = createLineBehavior;
//    }
//
//    public CreateObjectBehavior getCreateObjectBehavior() {
//        return createObjectBehavior;
//    }
//
//    public void setCreateObjectBehavior(CreateObjectBehavior createObjectBehavior) {
//        this.createObjectBehavior = createObjectBehavior;
//    }
//
//    public enum Type {
//        SELECT, CREATE_LINE, CREATE_OBJECT
//    }
}
