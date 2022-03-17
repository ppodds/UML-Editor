package org.ppodds.graphic.object;

import java.awt.*;

public class UseCaseObject extends UMLObject {
    public UseCaseObject(int x, int y) {
        super(x, y);
        linkable = true;
        nameCustomizable = true;
        padding = 10;
        setName("");
        setSize(150, 100);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int t1 = getWidth() - 2 * padding;
        int t2 = getHeight() - 2 * padding;
        g2.setPaint(Color.LIGHT_GRAY);
        g2.fillOval(padding, padding, t1, t2);
        g2.setPaint(Color.BLACK);
        g2.drawOval(padding, padding, t1, t2);
        FontMetrics metrics = g2.getFontMetrics();
        int x = padding + (t1 - metrics.stringWidth(getName())) / 2;
        int y = padding + ((t2 - metrics.getHeight()) / 2) + metrics.getAscent();
        g2.drawString(getName(), x, y);
        paintConnectionPorts(g);
    }
}
