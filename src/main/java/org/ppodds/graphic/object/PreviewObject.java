package org.ppodds.graphic.object;

import java.awt.*;

public class PreviewObject extends UMLObject {
    public PreviewObject(UMLObject umlObject) {
        super(false, false);
        padding = umlObject.padding;
        setBounds(umlObject.getX(), umlObject.getY(), umlObject.getWidth(), umlObject.getHeight());
        shape = umlObject.shape;
    }

    @Override
    public void paintSelf(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int t1 = getWidth() - 2 * padding;
        int t2 = getHeight() - 2 * padding;
        g2.setPaint(Color.BLACK);
        g2.drawRect(padding, padding, t1, t2);
    }
}
