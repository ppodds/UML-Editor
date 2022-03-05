package org.ppodds.core.math;

public abstract class Shape {
    public abstract Point getPointOfDirection(Direction direction);

    public enum Direction {
        TOP, RIGHT_TOP, RIGHT, RIGHT_BOTTOM, BOTTOM, LEFT_BOTTOM, LEFT, LEFT_TOP,
    }
}
