package org.ppodds.graphic.object;

import java.awt.*;

public class UseCaseObject extends UMLObject {
    public UseCaseObject(int x, int y) {
        super(x, y);
        setSize(100, 50);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.BLACK);
        g2.drawOval(0, 0, 100, 50);
    }
}
