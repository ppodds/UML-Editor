package org.ppodds.graphic;

import org.ppodds.core.ResourceManager;

import javax.swing.*;
import java.awt.*;

public class Editor {
    private final JPanel panel;
    private final JButton selectBtn;
    private final JButton associationLineBtn;
    private final JButton generalizationLineBtn;
    private final JButton compositionLineBtn;
    private final JButton classBtn;
    private final JButton useCaseBtn;
    private final UMLCanvas canvas;

    private final EditorState state;

    public Editor() {
        state = new EditorState();

        panel = new JPanel();
        selectBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/arrow-pointer-solid.png")));
        associationLineBtn = new ToolButton("btn");
        generalizationLineBtn = new ToolButton("btn");
        compositionLineBtn = new ToolButton("btn");
        classBtn = new ToolButton("btn");
        useCaseBtn = new ToolButton("btn");

        canvas = new UMLCanvas(state);
        canvas.setPreferredSize(new Dimension(540, 540));
        panel.add(canvas);

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
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

    public JPanel getPanel() {
        return panel;
    }
}
