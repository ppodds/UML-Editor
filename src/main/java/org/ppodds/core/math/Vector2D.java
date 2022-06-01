package org.ppodds.core.math;

public record Vector2D(double x, double y) {

    public Vector2D normalVector() {
        return new Vector2D(-this.y, this.x);
    }

    public Vector2D unitVector() {
        double t = length();
        return new Vector2D(this.x / t, this.y / t);
    }

    public Vector2D reverse() {
        return new Vector2D(-this.x, -this.y);
    }

    public double length() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public Vector2D multiply(double multiplier) {
        return new Vector2D(this.x * multiplier, this.y * multiplier);
    }

    public Vector2D add(Vector2D v) {
        return new Vector2D(this.x + v.x, this.y + v.y);
    }

    public Vector2D subtract(Vector2D v) {
        return new Vector2D(this.x - v.x, this.y - v.y);
    }

    public Vector2D rotate(double degree) {
        double radians = Math.toRadians(degree);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        return new Vector2D(cos * x - sin * y, sin * x + cos * y);
    }
}
