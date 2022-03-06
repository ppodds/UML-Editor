package org.ppodds.graphic;

import java.awt.*;

public class SelectedArea extends Canvas {
    private int centerX;
    private int centerY;

    public SelectedArea(int x, int y) {
        super();
        centerX = x;
        centerY = y;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public void selecting(int mouseX, int mouseY) {
        setLocation(Math.min(mouseX, centerX), Math.min(mouseY, centerY));
        setSize(Math.abs(mouseX - centerX), Math.abs(mouseY - centerY));
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(new Color(100, 200, 250, 100));
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}
