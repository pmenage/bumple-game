import processing.core.PApplet;
import processing.event.KeyEvent;

public class Bumple extends PApplet {

    private static final int PIXELS_PER_METER = 200;
    private BumpCube bumpCube = new BumpCube();
    private Obstacle obstacle = new Obstacle(Obstacle.ObstacleType.Rotating, 1f, 1.5f);

    public static void main(String[] args) {
        Bumple.main(Bumple.class.getName());
    }

    public int metersToPixels(float meters) {
        return (int) (meters * PIXELS_PER_METER);
    }

    public Point metersToPixels(Point meters) {
        return new Point(metersToPixels(meters.x) + 200, - metersToPixels(meters.y) + 500);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKey() == ' ')
            bumpCube.jump();
    }

    @Override
    public void settings() {
        size(400, 600);
    }

    @Override
    public void draw() {
        clear();
        update();
        bumpCube.draw(this);
        obstacle.draw(this);
    }

    public void update() {
        bumpCube.update();
        obstacle.update();
        if (detectCollision(obstacle, bumpCube))
            rect(0, 0, 50, 50);
    }

    public static boolean detectCollision(Obstacle obstacle, BumpCube bumpCube) {
        float obstacleX = obstacle.getPosition().x;
        float obstacleY = obstacle.getPosition().y;
        float obstacleAngle = obstacle.getAngle();
        float obstacleWidth = obstacle.getWidth();
        float obstacleHeight = obstacle.getHeight();
        Point LeftUp = new Point(obstacleX, obstacleY);
        Point RightUp = new Point(obstacleX + cos(obstacleAngle) * obstacleWidth + sin(obstacleAngle) * obstacleHeight, obstacleY);
        Point RightDown = new Point(obstacleX + cos(obstacleAngle) * obstacleWidth + sin(obstacleAngle) * obstacleHeight, obstacleY - (-sin(obstacleAngle) * obstacleWidth + cos(obstacleAngle) * obstacleHeight));
        Point LeftDown = new Point(obstacleX, obstacleY - (-sin(obstacleAngle) * obstacleWidth + cos(obstacleAngle) * obstacleHeight));
        return Utils.detectSegmentCircleIntersection(LeftUp, LeftDown , bumpCube.getPosition(), bumpCube.getRadius()) ||
                Utils.detectSegmentCircleIntersection(LeftUp, RightUp, bumpCube.getPosition(), bumpCube.getRadius()) ||
                Utils.detectSegmentCircleIntersection(LeftDown, RightDown, bumpCube.getPosition(), bumpCube.getRadius()) ||
                Utils.detectSegmentCircleIntersection(RightUp, RightDown, bumpCube.getPosition(), bumpCube.getRadius());
    }

}