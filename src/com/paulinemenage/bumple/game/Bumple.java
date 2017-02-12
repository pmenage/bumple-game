package com.paulinemenage.bumple.game;

import com.paulinemenage.bumple.Applet;
import com.paulinemenage.bumple.GameOverScreen;
import com.paulinemenage.bumple.Screen;
import com.paulinemenage.bumple.physics.Point;
import com.paulinemenage.bumple.physics.Utils;
import processing.event.KeyEvent;

public class Bumple extends Screen {

    private static final int PIXELS_PER_METER = 200;
    private Obstacle[] obstacles = new Obstacle[2];
    private float cameraHeight = 0f;
    private static final float CAMERA_SPEED = 1.8f;
    private int score = 0;
    private Water water;
    private Applet applet;
    private BumpCube bumpCube;

    public static final boolean DEBUG = false;

    public Bumple(Applet applet) {
        this.applet = applet;
        setNextObstacle(new Obstacle(Obstacle.ObstacleType.Ground, 0f, 0));
        cycleObstacles();
        water = new Water(- .5f);
        bumpCube = new BumpCube(applet);
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

    /**
     * Creates a random rotating or sliding obstacle.
     * @param y The height at which the obstacle is.
     * @return The obstacle which has been created.
     */
    public Obstacle createRandomObstacle(float y) {
        Obstacle.ObstacleType[] obstacleTypes = new Obstacle.ObstacleType[] {
                Obstacle.ObstacleType.Rotating,
                Obstacle.ObstacleType.Sliding
        };
        return new Obstacle(
                obstacleTypes[(int) (Math.random() * obstacleTypes.length)],
                y,
                (float) Math.random() + 1
        );
    }

    /**
     * Sets the next obstacle as the ground.
     * Creates a random obstacle as the next obstacle.
     */
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
     * Clears the window.
     * Calls the Processing update function.
     * Draws the bumpCube and the obstacles.
     */
    @Override
    public void draw() {
        applet.clear();
        update();
        applet.textSize(20);
        applet.text(score, 20, 30);
        applet.pushMatrix();
        applet.translate(0, metersToPixels(cameraHeight));
        bumpCube.draw(this, applet);
        for (Obstacle obstacle : obstacles)
            obstacle.draw(this, applet);
        water.draw(this, applet);
        applet.popMatrix();
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
            water.update(delta);
            if (bumpCube.isOnGround() && ground == getNextObstacle()) {
                ++score;
                cycleObstacles();
            }
            if (cameraHeight < getGroundObstacle().getPosition().y)
                cameraHeight += CAMERA_SPEED * delta;
            if (Utils.detectSegmentCircleIntersection(water.getCollisionShape(), bumpCube.getCollisionShape()))
                applet.setScreen(new GameOverScreen(applet));
        }
    }

}