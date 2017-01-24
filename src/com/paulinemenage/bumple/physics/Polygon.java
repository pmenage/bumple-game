package com.paulinemenage.bumple.physics;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Polygon {

    private ArrayList<Point> points = new ArrayList<>();
    private ArrayList<Segment> segments = new ArrayList<>();

    /**
     * Defines all the segments of the polygon.
     * @param points An array of points.
     */
    public Polygon(ArrayList<Point> points) {
        for (Point point : points)
            this.points.add(point);
        for (int i = 0; i < this.points.size(); i++)
            this.segments.add(new Segment(
                    this.points.get(i),
                    this.points.get((i + 1) % this.points.size())
            ));
    }

}