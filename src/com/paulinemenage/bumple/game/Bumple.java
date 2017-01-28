package com.paulinemenage.bumple.game;

import com.paulinemenage.bumple.physics.Point;
import com.paulinemenage.bumple.physics.Utils;
import processing.core.PApplet;
import processing.event.KeyEvent;

public class Bumple extends PApplet {

    private static final int PIXELS_PER_METER = 200;
    private BumpCube bumpCube = new BumpCube();
    private Obstacle[] obstacles = new Obstacle[2];
    private float cameraHeight = 0f;
    private static final float CAMERA_SPEED = 1.8f;
    private int score = 0;

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
        textSize(20);
        text(score, 20, 30);
        pushMatrix();
        translate(0, metersToPixels(cameraHeight));
        bumpCube.draw(this);
        for (Obstacle obstacle : obstacles)
            obstacle.draw(this);
        popMatrix();
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