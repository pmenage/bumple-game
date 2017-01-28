package com.paulinemenage.bumple.physics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Polygon {

    private ArrayList<Point> points = new ArrayList<>();
    private ArrayList<Segment> segments = new ArrayList<>();

    public List<Segment> getSegments() {
        return Collections.unmodifiableList(segments);
    }

    /**
     * Defines all the segments of the polygon.
     * @param points An array of points.
     */
    public Polygon(List<Point> points) {
        for (Point point : points)
            this.points.add(point);
        for (int i = 0; i < this.points.size(); i++)
            this.segments.add(new Segment(
                    this.points.get(i),
                    this.points.get((i + 1) % this.points.size())
            ));
    }

}