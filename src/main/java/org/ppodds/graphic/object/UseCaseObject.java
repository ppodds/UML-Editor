package org.ppodds.graphic.object;

import org.ppodds.core.math.Oval;

import java.awt.*;

public class UseCaseObject extends UMLBasicObject {
    public UseCaseObject(int x, int y) {
        super(x, y, 150, 100, 10);
        setName("Use Case Object");
        shape = new Oval(getWidth() - getPadding() * 2, getHeight() - getPadding() * 2);
    }

    @Override
    protected void paintSelf(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int t1 = getWidth() - 2 * getPadding();
        int t2 = getHeight() - 2 * getPadding();
        g2.setPaint(Color.LIGHT_GRAY);
        g2.fillOval(getPadding(), getPadding(), t1, t2);
        g2.setPaint(Color.BLACK);
        g2.drawOval(getPadding(), getPadding(), t1, t2);
        FontMetrics metrics = g2.getFontMetrics();
        int x = getPadding() + (t1 - metrics.stringWidth(getName())) / 2;
        int y = getPadding() + ((t2 - metrics.getHeight()) / 2) + metrics.getAscent();
        g2.drawString(getName(), x, y);
    }
}
