package org.ppodds.graphic.object;

import java.awt.*;

public class UseCaseObject extends UMLObject {
    public UseCaseObject(int x, int y) {
        super(x, y);
        padding = 10;
        setSize(150, 100);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.BLACK);
        g2.drawOval(padding, padding, getWidth() - 2 * padding, getHeight() - 2 * padding);
        paintConnectionPorts(g);
    }
}
