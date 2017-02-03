package com.paulinemenage.bumple.physics;

public class Utils {

    /**
     * Detects if there is a collision between a polygon and a circle.
     * @param polygon The polygon.
     * @param circle The circle.
     * @return Whether there is a collision.
     */
    public static boolean detectPolygonCircleIntersection(Polygon polygon, Circle circle) {
        for (Segment segment : polygon.getSegments()) {
            if (detectSegmentCircleIntersection(segment, circle))
                return true;
        }
        return false;
    }

    /**
     * Detect if there is an intersection between origin segment and origin circle.
     * @param segment The segment.
     * @param circle The circle.
     * @return Whether there is an intersection.
     */
    public static boolean detectSegmentCircleIntersection(Segment segment, Circle circle) {
        Point I = projectOrthogonally(new Line(segment.a, segment.getDirectionVector()), circle.center);
        Point AB = segment.getDirectionVector();
        Point AI = I.subtract(segment.a);
        Point BI = I.subtract(segment.b);
        float normSqAB = AB.normSq();
        boolean isOnSegment = isPointInCircle(I, circle) && AI.normSq() < normSqAB && BI.normSq() < normSqAB;
        return isOnSegment || isPointInCircle(segment.a, circle) || isPointInCircle(segment.b, circle);
    }

    /**
     * Detect if origin point is in origin circle.
     * @param I The point whose position we're checking.
     * @param circle The circle.
     * @return Whether the point is in the circle.
     */
    public static boolean isPointInCircle(Point I, Circle circle) {
        Point PI = I.subtract(circle.center);
        return PI.normSq() < circle.radius * circle.radius;
    }

    /**
     * Compute the orthogonal projection of P on (AB).
     * @param line The line.
     * @param P The point to project.
     * @return The projected point.
     */
    public static Point projectOrthogonally(Line line, Point P) {
        return findIntersection(line, new Line(P, line.direction.normal()));
    }

    /**
     * Find the intersection between two lines.
     * @param l1 The first line.
     * @param l2 The second line.
     * @return The single point of intersection or null.
     */
    public static Point findIntersection(Line l1, Line l2) {
        float detA1 = (l2.origin.x - l1.origin.x) * (-l2.direction.y) - (l2.origin.y - l1.origin.y) * (-l2.direction.x);
        float detA = l1.direction.x * (-l2.direction.y) - l1.direction.y * (-l2.direction.x);
        float t1 = detA1 / detA;
        return new Point(l1.origin.x + t1 * l1.direction.x, l1.origin.y + t1 * l1.direction.y);
    }

}
