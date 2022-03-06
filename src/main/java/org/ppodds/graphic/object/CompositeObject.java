package org.ppodds.graphic.object;

import java.awt.*;

public class CompositeObject extends UMLObject {
    public CompositeObject(UMLObject[] umlObjects) {
        super();
        setOpaque(false);
        // 540 <- default size of canvas
        int x = 540, y = 540, width = 0, height = 0;
        for (var o : umlObjects) {
            add(o);
            o.setGrouped(true);
            x = Math.min(x, o.getX());
            y = Math.min(y, o.getY());
        }
        for (var o : umlObjects) {
            width = Math.max(width, (o.getX() + o.getWidth()) - x);
            height = Math.max(height, (o.getY() + o.getHeight()) - y);
        }
        setBounds(x, y, width, height);
        for (var o : umlObjects) {
            o.setLocation(o.getX() - x, o.getY() - y);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.BLACK);
        paintConnectionPorts(g);
    }
}
