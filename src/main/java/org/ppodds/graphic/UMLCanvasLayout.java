package org.ppodds.graphic;

import java.awt.*;

public class UMLCanvasLayout implements LayoutManager {
    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return parent.getSize();
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return parent.getSize();
    }

    @Override
    public void layoutContainer(Container parent) {
//        for (var c : parent.getComponents()) {
//            c.setBounds(c.);
//        }
    }
}
