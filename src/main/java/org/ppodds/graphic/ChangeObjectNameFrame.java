package org.ppodds.graphic;

import org.ppodds.graphic.editor.Editor;
import org.ppodds.graphic.object.UMLObject;

import javax.swing.*;

public class ChangeObjectNameFrame extends JFrame {
    public ChangeObjectNameFrame(UMLObject target) {
        super("Change Object Name");
        JPanel panel = new JPanel();
        setContentPane(panel);
        JLabel label = new JLabel("Name:");
        JTextField textField = new JTextField(target.getName(), 15);
        JButton okBtn = new JButton("OK");
        okBtn.addActionListener(event -> {
            Editor.getInstance().getEditorContentPane().getCanvas().changeObjectName(target, textField.getText());
            dispose();
        });
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(event -> dispose());
        panel.add(label);
        panel.add(textField);
        panel.add(okBtn);
        panel.add(cancelBtn);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
