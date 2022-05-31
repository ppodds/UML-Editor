package org.ppodds.graphic.editor;

import org.ppodds.core.ResourceManager;

import javax.swing.*;

public class EditorButtonBar extends JPanel {
    public EditorButtonBar() {
        JButton selectBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/arrow-pointer-solid.png")), EditorState.EditorOperation.SELECT);
        JButton associationLineBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/association-line.png")), EditorState.EditorOperation.ASSOCIATION_LINE);
        JButton generalizationLineBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/generalization-line.png")), EditorState.EditorOperation.GENERALIZATION_LINE);
        JButton compositionLineBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/composition-line.png")), EditorState.EditorOperation.COMPOSITION_LINE);
        JButton classBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/class.png")), EditorState.EditorOperation.CLASS);
        JButton useCaseBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/use-case.png")), EditorState.EditorOperation.USE_CASE);

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
        );

        layout.setVerticalGroup(layout.createParallelGroup().addGroup(layout.createSequentialGroup()
                .addComponent(selectBtn)
                .addComponent(associationLineBtn)
                .addComponent(generalizationLineBtn)
                .addComponent(compositionLineBtn)
                .addComponent(classBtn)
                .addComponent(useCaseBtn)));
    }
}
