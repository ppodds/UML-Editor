package org.ppodds.graphic.object;

import java.awt.*;

public class ClassObject extends UMLObject {
    public ClassObject(int x, int y) {
        super(x, y);
        setSize(100, 100);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.BLACK);
        g2.drawRect(0, 0, 100, 100);
    }
}
