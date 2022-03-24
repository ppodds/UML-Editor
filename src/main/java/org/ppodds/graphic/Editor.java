package org.ppodds.graphic;

import org.ppodds.core.ResourceManager;
import org.ppodds.core.event.ChangeListener;

import javax.swing.*;

public class Editor extends JPanel {
    private final UMLCanvas canvas;

    private static Editor instance = null;
    private final EditorState state;

    public Editor() {
        instance = this;
        state = new EditorState();

        JButton selectBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/arrow-pointer-solid.png")), EditorState.EditorOperation.SELECT);
        JButton associationLineBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/association-line.png")), EditorState.EditorOperation.ASSOCIATION_LINE);
        JButton generalizationLineBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/generalization-line.png")), EditorState.EditorOperation.GENERALIZATION_LINE);
        JButton compositionLineBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/composition-line.png")), EditorState.EditorOperation.COMPOSITION_LINE);
        JButton classBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/class.png")), EditorState.EditorOperation.CLASS);
        JButton useCaseBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/use-case.png")), EditorState.EditorOperation.USE_CASE);

        canvas = new UMLCanvas();
        add(canvas);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        initLayout(selectBtn, associationLineBtn, generalizationLineBtn, compositionLineBtn, classBtn, useCaseBtn, layout);
    }

    private void initLayout(JButton selectBtn, JButton associationLineBtn, JButton generalizationLineBtn, JButton compositionLineBtn, JButton classBtn, JButton useCaseBtn, GroupLayout layout) {
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

    public static Editor getInstance() {
        return instance;
    }

    public EditorState getState() {
        return state;
    }

    public void addChangeListener(ChangeListener l) {
        listenerList.add(ChangeListener.class, l);
    }

    public ChangeListener[] getChangeListeners() {
        return getListeners(ChangeListener.class);
    }

    public UMLCanvas getCanvas() {
        return canvas;
    }
}
