package org.ppodds.graphic.object;

import org.ppodds.core.math.Rectangle;

import java.awt.*;

public class ClassObject extends UMLBasicObject {
    public ClassObject(int x, int y) {
        super(x, y, 100, 100, 10);
        setName("Class Object");
        shape = new Rectangle(getWidth() - getPadding() * 2, getHeight() - getPadding() * 2);
    }

    @Override
    protected void paintSelf(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.LIGHT_GRAY);
        int t1 = getWidth() - getPadding() * 2;
        int t2 = getHeight() - getPadding() * 2;
        g2.fillRect(getPadding(), getPadding(), t1, t2);
        g2.setPaint(Color.BLACK);
        g2.drawRect(getPadding(), getPadding(), t1, t2);
        g2.drawLine(getPadding(), getPadding() + t2 / 3, getPadding() + t1, getPadding() + t2 / 3);
        g2.drawLine(getPadding(), getPadding() + t2 / 3 * 2, getPadding() + t1, getPadding() + t2 / 3 * 2);
        FontMetrics metrics = g2.getFontMetrics();
        int x = getPadding() + (t1 - metrics.stringWidth(getName())) / 2;
        int y = getPadding() + (((getPadding() + getPadding() + t2 / 3) / 2 - metrics.getHeight()) / 2) + metrics.getAscent();
        g2.drawString(getName(), x, y);
    }
}
