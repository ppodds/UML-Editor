package org.ppodds.graphic;

import org.ppodds.core.ResourceManager;
import org.ppodds.core.event.ChangeListener;

import javax.swing.*;
import java.awt.*;

public class Editor extends JPanel {
    private final JButton selectBtn;
    private final JButton associationLineBtn;
    private final JButton generalizationLineBtn;
    private final JButton compositionLineBtn;
    private final JButton classBtn;
    private final JButton useCaseBtn;
    private final UMLCanvas canvas;

    private final EditorState state;

    public EditorState getState() {
        return state;
    }

    public Editor() {
        state = new EditorState(this);

        selectBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/arrow-pointer-solid.png")), this, EditorState.EditorOperation.SELECT);
        associationLineBtn = new ToolButton("btn", this, EditorState.EditorOperation.ASSOCIATION_LINE);
        generalizationLineBtn = new ToolButton("btn", this, EditorState.EditorOperation.GENERALIZATION_LINE);
        compositionLineBtn = new ToolButton("btn", this, EditorState.EditorOperation.COMPOSITION_LINE);
        classBtn = new ToolButton("btn", this, EditorState.EditorOperation.CLASS);
        useCaseBtn = new ToolButton("btn", this, EditorState.EditorOperation.USE_CASE);

        canvas = new UMLCanvas(this);
        canvas.setPreferredSize(new Dimension(540, 540));
        add(canvas);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(selectBtn)
                                .addComponent(associationLineBtn)
                                .addComponent(generalizationLineBtn)
                                .addComponent(compositionLineBtn)
                                .addComponent(classBtn)
                                .addComponent(useCaseBtn)
                        )
                        .addComponent(canvas)

        );

        layout.setVerticalGroup(layout.createParallelGroup().addGroup(layout.createSequentialGroup()
                .addComponent(selectBtn)
                .addComponent(associationLineBtn)
                .addComponent(generalizationLineBtn)
                .addComponent(compositionLineBtn)
                .addComponent(classBtn)
                .addComponent(useCaseBtn)).addComponent(canvas));
    }

    public void addChangeListener(ChangeListener l) {
        listenerList.add(ChangeListener.class, l);
    }

    public ChangeListener[] getChangeListeners() {
        return getListeners(ChangeListener.class);
    }

}
