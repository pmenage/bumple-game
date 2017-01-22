package com.paulinemenage.bumple.physics;

public class Utils {

    /**
     * Detect if there is an intersection between a segment and a circle.
     * @param segment The segment.
     * @param circle The circle.
     * @return Whether there is an intersection.
     */
    public static boolean detectSegmentCircleIntersection(Segment segment, Circle circle) {
        Point I = projectOrthogonally(segment, circle.center);
        Point AB = segment.getDirectionVector();
        Point AI = I.subtract(segment.a);
        Point BI = I.subtract(segment.b);
        float normSqAB = AB.normSq();
        boolean isOnSegment = isPointInCircle(I, circle) && AI.normSq() < normSqAB && BI.normSq() < normSqAB;
        return isOnSegment || isPointInCircle(segment.a, circle) || isPointInCircle(segment.b, circle);
    }

    /**
     * Detect if a point is in a circle.
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
    public static Point projectOrthogonally(Segment line, Point P) {
        Point AB = line.getDirectionVector();
        return findIntersection(line.a, AB, P, AB.normal());
    }

    /**
     * Find the intersection between two lines.
     * @param P1 A point of the first line.
     * @param V1 A direction vector of the first line.
     * @param P2 A point of the second line.
     * @param V2 A direction vector of the second line.
     * @return The single point of intersection or null.
     */
    public static Point findIntersection(Point P1, Point V1, Point P2, Point V2) {
        float detA1 = (P2.x - P1.x) * (-V2.y) - (P2.y - P1.y) * (-V2.x);
        float detA = V1.x * (-V2.y) - V1.y * (-V2.x);
        float t1 = detA1 / detA;
        return new Point(P1.x + t1 * V1.x, P1.y + t1 * V1.y);
    }

}
