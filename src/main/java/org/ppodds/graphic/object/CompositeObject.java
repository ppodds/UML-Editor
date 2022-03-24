package org.ppodds.graphic.object;

import org.ppodds.core.math.Rectangle;
import org.ppodds.graphic.UMLCanvas;

import java.awt.*;

public class CompositeObject extends UMLObject {
    public CompositeObject(UMLObject[] umlObjects) {
        super(false, false, 0);
        setOpaque(false);
        int x = UMLCanvas.WIDTH;
        int y = UMLCanvas.HEIGHT;
        int width = 0;
        int height = 0;
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
        shape = new Rectangle(getWidth(), getHeight());
        for (var o : umlObjects) {
            o.setLocation(o.getX() - x, o.getY() - y);
        }
    }

    @Override
    protected void paintSelf(Graphics g) {
        g.dispose();
    }
}
