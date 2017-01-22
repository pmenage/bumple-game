public class Utils {

    /**
     * Detect if there is an intersection between a segment and a circle.
     * @param A An end of the segment.
     * @param B The other end of the segment.
     * @param P The center of the circle.
     * @param R The radius of the circle.
     * @return Whether there is an intersection.
     */
    public static boolean detectSegmentCircleIntersection(Point A, Point B, Point P, float R) {
        Point I = projectOrthogonally(A, B, P);
        Point AB = B.subtract(A);
        Point AI = I.subtract(A);
        Point BI = I.subtract(B);
        float normSqAB = AB.normSq();
        boolean isOnSegment = isPointInCircle(I, P, R) && AI.normSq() < normSqAB && BI.normSq() < normSqAB;
        return isOnSegment || isPointInCircle(A, P, R) || isPointInCircle(B, P, R);
    }

    /**
     * Detect if a point is in a circle.
     * @param I The point whose position we're checking.
     * @param P The center of the circle.
     * @param R The radius of the circle.
     * @return Whether the point is in the circle.
     */
    public static boolean isPointInCircle(Point I, Point P, float R) {
        Point PI = I.subtract(P);
        return PI.normSq() < R * R;
    }

    /**
     * Compute the orthogonal projection of P on (AB).
     * @param A A point of the line.
     * @param B A point of the line.
     * @param P The point to project.
     * @return The projected point.
     */
    public static Point projectOrthogonally(Point A, Point B, Point P) {
        Point AB = B.subtract(A);
        return findIntersection(A, AB, P, AB.normal());
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
