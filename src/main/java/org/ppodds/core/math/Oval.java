package org.ppodds.core.math;

public class Oval extends Shape {
    private final int width;
    private final int height;

    public Oval(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Point getPointOfAngle(int degree) {
        return new Point(width / 2 + (int) (width / 2 * Math.cos(Math.toRadians(degree))),
                height / 2 - (int) (height / 2 * Math.sin(Math.toRadians(degree))));
    }

    @Override
    public Point getPointOfDirection(Direction direction) {
        return switch (direction) {
            case TOP -> getPointOfAngle(90);
            case RIGHT_TOP -> getPointOfAngle(45);
            case RIGHT -> getPointOfAngle(0);
            case RIGHT_BOTTOM -> getPointOfAngle(315);
            case BOTTOM -> getPointOfAngle(270);
            case LEFT_BOTTOM -> getPointOfAngle(225);
            case LEFT -> getPointOfAngle(180);
            case LEFT_TOP -> getPointOfAngle(135);
        };
    }
}
