package org.ppodds.graphic.object;

import org.ppodds.core.math.Rectangle;

import java.awt.*;

public class ClassObject extends UMLObject {
    public ClassObject(int x, int y) {
        super(true, true, x, y);
        padding = 10;
        setName("");
        setSize(100, 100);
        shape = new Rectangle(getWidth() - padding * 2, getHeight() - padding * 2);
    }

    @Override
    protected void paintSelf(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.LIGHT_GRAY);
        int t1 = getWidth() - padding * 2;
        int t2 = getHeight() - padding * 2;
        g2.fillRect(padding, padding, t1, t2);
        g2.setPaint(Color.BLACK);
        g2.drawRect(padding, padding, t1, t2);
        g2.drawLine(padding, padding + t2 / 3, padding + t1, padding + t2 / 3);
        g2.drawLine(padding, padding + t2 / 3 * 2, padding + t1, padding + t2 / 3 * 2);
        FontMetrics metrics = g2.getFontMetrics();
        int x = padding + (t1 - metrics.stringWidth(getName())) / 2;
        int y = padding + (((padding + padding + t2 / 3) / 2 - metrics.getHeight()) / 2) + metrics.getAscent();
        g2.drawString(getName(), x, y);
    }
}
