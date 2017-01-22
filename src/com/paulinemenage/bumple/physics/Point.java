package com.paulinemenage.bumple.physics;

public class Point {

    public float x;
    public float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point subtract(Point other) {
        return new Point(other.x - x, other.y - y);
    }

    public Point normal() {
        return new Point(-y, x);
    }

    /**
     * @return Square of this vector's norm.
     */
    public float normSq() {
        return x * x + y * y;
    }

}
