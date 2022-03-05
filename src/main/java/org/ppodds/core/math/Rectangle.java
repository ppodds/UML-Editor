package org.ppodds.core.math;

public class Rectangle extends Shape {
    private final int width;
    private final int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public Point getPointOfDirection(Direction direction) {
        return switch (direction) {
            case TOP -> new Point(width / 2, 0);
            case RIGHT_TOP -> new Point(width, 0);
            case RIGHT -> new Point(width, height / 2);
            case RIGHT_BOTTOM -> new Point(width, height);
            case BOTTOM -> new Point(width / 2, height);
            case LEFT_BOTTOM -> new Point(0, height);
            case LEFT -> new Point(0, height / 2);
            case LEFT_TOP -> new Point(0, 0);
        };
    }
}
