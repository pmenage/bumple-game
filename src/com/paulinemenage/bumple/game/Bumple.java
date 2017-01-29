package com.paulinemenage.bumple.game;

import com.paulinemenage.bumple.Screen;
import com.paulinemenage.bumple.physics.Point;
import com.paulinemenage.bumple.physics.Utils;
import processing.core.PApplet;
import processing.event.KeyEvent;

public class Bumple extends Screen {

    private static final int PIXELS_PER_METER = 200;
    private BumpCube bumpCube = new BumpCube();
    private Obstacle[] obstacles = new Obstacle[2];
    private float cameraHeight = 0f;
    private static final float CAMERA_SPEED = 1.8f;
    private int score = 0;

    public Bumple() {
        setNextObstacle(new Obstacle(Obstacle.ObstacleType.Ground, 0f, 0));
        cycleObstacles();
    }

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

    /**
     * Converts values in meters to pixels.
     * @param meters A value in meters.
     * @return This same value in pixels.
     */
    public static int metersToPixels(float meters) {
        return (int) (meters * PIXELS_PER_METER);
    }

    /**
     * Converts values in meters to pixels.
     * @param meters A point whose position is in meters.
     * @return This position in pixels.
     */
    public static Point metersToPixels(Point meters) {
        return new Point(metersToPixels(meters.x) + 200, - metersToPixels(meters.y) + 500);
    }

    public Obstacle createRandomObstacle(float y) {
        Obstacle.ObstacleType[] obstacleTypes = new Obstacle.ObstacleType[] {
                Obstacle.ObstacleType.Rotating,
                Obstacle.ObstacleType.Sliding
        };
        return new Obstacle(
                obstacleTypes[(int) Math.random() * obstacleTypes.length],
                y,
                (float) Math.random() + 1
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
    public void keyPressed(KeyEvent event) {
        if (event.getKey() == ' ')
            bumpCube.jump();
    }

    /**
     * Clears the window.
     * Calls the Processing update function.
     * Draws the bumpCube and the obstacles.
     */
    public void draw(PApplet pApplet) {
        pApplet.clear();
        update();
        pApplet.textSize(20);
        pApplet.text(score, 20, 30);
        pApplet.pushMatrix();
        pApplet.translate(0, metersToPixels(cameraHeight));
        bumpCube.draw(this, pApplet);
        for (Obstacle obstacle : obstacles)
            obstacle.draw(this, pApplet);
        pApplet.popMatrix();
    }

    /**
     * Calls the cube's and the obstacles' update methods.
     */
    public void update() {
        Obstacle ground = null;
        for (int i = 0, steps = 15; i < steps; ++i) {
            float delta = 1f / (60 * steps);
            bumpCube.setColliding(false);
            for (Obstacle obstacle : obstacles) {
                obstacle.update(delta);
                if (Utils.detectPolygonCircleIntersection(
                        obstacle.getCollisionShape(),
                        bumpCube.getCollisionShape())) {
                    ground = obstacle;
                    bumpCube.setColliding(true);
                }
            }
            bumpCube.update(delta);
            if (bumpCube.isOnGround() && ground == getNextObstacle()) {
                ++score;
                cycleObstacles();
            }
            if (cameraHeight < getGroundObstacle().getPosition().y)
                cameraHeight += CAMERA_SPEED * delta;
        }
    }

}