package org.ppodds;

import org.ppodds.graphic.Editor;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("UML Editor");

        frame.setContentPane(new Editor().getPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
