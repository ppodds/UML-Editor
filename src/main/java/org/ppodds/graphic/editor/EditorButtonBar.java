package org.ppodds.graphic.editor;

import org.ppodds.core.ResourceManager;
import org.ppodds.graphic.editor.button.ToolButton;
import org.ppodds.graphic.editor.operation.behavior.CreateLineBehavior;
import org.ppodds.graphic.editor.operation.behavior.CreateObjectBehavior;
import org.ppodds.graphic.editor.operation.behavior.SelectBehavior;
import org.ppodds.graphic.line.AssociationLine;
import org.ppodds.graphic.line.CompositionLine;
import org.ppodds.graphic.line.GeneralizationLine;
import org.ppodds.graphic.object.ClassObject;
import org.ppodds.graphic.object.UMLBasicObject;
import org.ppodds.graphic.object.UseCaseObject;

import javax.swing.*;

public class EditorButtonBar extends JPanel {
    public EditorButtonBar() {
        JButton selectBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/arrow-pointer-solid.png")), new SelectBehavior(), true);
        JButton associationLineBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/association-line.png")), new CreateLineBehavior() {
            @Override
            public void addConnectionLine(UMLBasicObject.ConnectionPortDirection fromConnectionPort, UMLBasicObject.ConnectionPortDirection toConnectionPort, UMLBasicObject fromObject, UMLBasicObject toObject) {
                Editor.getInstance().getEditorContentPane().getCanvas().addConnectionLine(new AssociationLine(fromConnectionPort, toConnectionPort, fromObject, toObject));
            }
        }, false);
        JButton generalizationLineBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/generalization-line.png")), new CreateLineBehavior() {
            @Override
            public void addConnectionLine(UMLBasicObject.ConnectionPortDirection fromConnectionPort, UMLBasicObject.ConnectionPortDirection toConnectionPort, UMLBasicObject fromObject, UMLBasicObject toObject) {
                Editor.getInstance().getEditorContentPane().getCanvas().addConnectionLine(new GeneralizationLine(fromConnectionPort, toConnectionPort, fromObject, toObject));
            }
        }, false);
        JButton compositionLineBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/composition-line.png")), new CreateLineBehavior() {
            @Override
            public void addConnectionLine(UMLBasicObject.ConnectionPortDirection fromConnectionPort, UMLBasicObject.ConnectionPortDirection toConnectionPort, UMLBasicObject fromObject, UMLBasicObject toObject) {
                Editor.getInstance().getEditorContentPane().getCanvas().addConnectionLine(new CompositionLine(fromConnectionPort, toConnectionPort, fromObject, toObject));
            }
        }, false);
        JButton classBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/class.png")), new CreateObjectBehavior() {
            @Override
            public void addObject(int x, int y) {
                Editor.getInstance().getEditorContentPane().getCanvas().addObject(new ClassObject(x, y));
            }
        }, false);
        JButton useCaseBtn = new ToolButton(new ImageIcon(ResourceManager.getResource("icons/use-case.png")), new CreateObjectBehavior() {
            @Override
            public void addObject(int x, int y) {
                Editor.getInstance().getEditorContentPane().getCanvas().addObject(new UseCaseObject(x, y));
            }
        }, false);

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
