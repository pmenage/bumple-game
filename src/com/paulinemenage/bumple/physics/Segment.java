package com.paulinemenage.bumple.physics;

public class Segment {

    public Point a;
    public Point b;

    public Segment(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public Point getDirectionVector() {
        return b.subtract(a);
    }

}
