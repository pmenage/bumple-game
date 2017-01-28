package com.paulinemenage.bumple.game;

import com.paulinemenage.bumple.physics.Point;
import com.paulinemenage.bumple.physics.Segment;
import com.paulinemenage.bumple.physics.Utils;
import processing.core.PApplet;
import processing.event.KeyEvent;

public class Bumple extends PApplet {

    private static final int PIXELS_PER_METER = 200;
    private BumpCube bumpCube = new BumpCube();
    private Obstacle[] obstacles = new Obstacle[2];
    private float cameraHeight = 0f;
    private static final float CAMERA_SPEED = 1.8f;

    private Obstacle getGroundObstacle() {
        return obstacles[0];
    }

    private void setGroundObstacle(Obstacle groundObstacle) {
        this.obstacles[0] = groundObstacle;
    }

    private Obstacle getNextObstacle() {
        return obstacles[1];
    }

    private void setNextObstacle(Obstacle nextObstacle) {
        this.obstacles[1] = nextObstacle;
    }

    public static void main(String[] args) {
        Bumple.main(Bumple.class.getName());
    }

    /**
     * Converts values in meters to pixels.
     * @param meters A value in meters.
     * @return This same value in pixels.
     */
    public int metersToPixels(float meters) {
        return (int) (meters * PIXELS_PER_METER);
    }

    /**
     * Converts values in meters to pixels.
     * @param meters A point whose position is in meters.
     * @return This position in pixels.
     */
    public Point metersToPixels(Point meters) {
        return new Point(metersToPixels(meters.x) + 200, - metersToPixels(meters.y) + 500);
    }

    public Obstacle createRandomObstacle(float y) {
        Obstacle.ObstacleType[] obstacleTypes = new Obstacle.ObstacleType[] {
                Obstacle.ObstacleType.Rotating,
                Obstacle.ObstacleType.Sliding
        };
        return new Obstacle(
                obstacleTypes[(int) random(0, obstacleTypes.length)],
                y,
                random(1,2)
        );
    }

    private void cycleObstacles() {
        setGroundObstacle(getNextObstacle());
        setNextObstacle(createRandomObstacle(getGroundObstacle().getPosition().y + 1f));
    }

    /**
     * Binds the jump method to the space bar.
     * @param event An event.
     */
    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKey() == ' ')
            bumpCube.jump();
    }

    /**
     * Gives the dimensions of the window.
     * Creates and adds the ground and the first obstacle to the obstacles array.
     */
    @Override
    public void settings() {
        size(metersToPixels(2), metersToPixels(3));
        setNextObstacle(new Obstacle(Obstacle.ObstacleType.Ground, 0f, 0)) ;
        cycleObstacles();
    }

    /**
     * Clears the window.
     * Calls the Processing update function.
     * Draws the bumpCube and the obstacles.
     */
    @Override
    public void draw() {
        clear();
        update();
        translate(0, metersToPixels(cameraHeight));
        bumpCube.draw(this);
        for (Obstacle obstacle : obstacles)
            obstacle.draw(this);
    }

    /**
     * Calls the cube's and the obstacles' update methods.
     */
    public void update() {
        int fps = 60;
        Obstacle ground = null;
        for (int i = 0, steps = 1; i < steps; ++i) {
            float delta = 1f / (fps * steps);
            bumpCube.setColliding(false);
            for (Obstacle obstacle : obstacles) {
                obstacle.update(); // TODO add delta to Obstacle.update
                if (detectCollision(obstacle, bumpCube)) {
                    ground = obstacle;
                    bumpCube.setColliding(true);
                }
            }
            bumpCube.update(delta);
            if (bumpCube.isOnGround() && ground == getNextObstacle())
                cycleObstacles();
            if (cameraHeight < getGroundObstacle().getPosition().y)
                cameraHeight += CAMERA_SPEED * delta;
        }
    }

    /**
     * Detects if there is a collision between an obstacle and a bumpCube.
     * @param obstacle An obstacle.
     * @param bumpCube A cube.
     * @return Whether there is a collision.
     */
    public static boolean detectCollision(Obstacle obstacle, BumpCube bumpCube) {
        float obstacleX = obstacle.getPosition().x;
        float obstacleY = obstacle.getPosition().y;
        float obstacleAngle = obstacle.getAngle();
        float obstacleWidth = obstacle.getWidth();
        float obstacleHeight = obstacle.getHeight();
        Point LeftUp = new Point(
                obstacleX,
                obstacleY
        );
        Point RightUp = new Point(
                (float) (obstacleX + Math.cos(obstacleAngle) * obstacleWidth),
                (float) (obstacleY + Math.sin(obstacleAngle) * obstacleWidth)
        );
        Point RightDown = new Point(
                (float) (obstacleX + Math.cos(obstacleAngle) * obstacleWidth + Math.sin(obstacleAngle) * obstacleHeight),
                (float) (obstacleY + Math.sin(obstacleAngle) * obstacleWidth + (-Math.cos(obstacleAngle)) * obstacleHeight)
        );
        Point LeftDown = new Point(
                (float) (obstacleX + Math.sin(obstacleAngle) * obstacleHeight),
                (float) (obstacleY + (-Math.cos(obstacleAngle)) * obstacleHeight)
        );
        return Utils.detectSegmentCircleIntersection(new Segment(LeftUp, LeftDown), bumpCube.getCollisionShape()) ||
                Utils.detectSegmentCircleIntersection(new Segment(LeftUp, RightUp), bumpCube.getCollisionShape()) ||
                Utils.detectSegmentCircleIntersection(new Segment(LeftDown, RightDown), bumpCube.getCollisionShape()) ||
                Utils.detectSegmentCircleIntersection(new Segment(RightUp, RightDown), bumpCube.getCollisionShape());
    }

}