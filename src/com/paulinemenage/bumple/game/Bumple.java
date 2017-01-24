package com.paulinemenage.bumple.game;

import com.paulinemenage.bumple.physics.Point;
import com.paulinemenage.bumple.physics.Segment;
import com.paulinemenage.bumple.physics.Utils;
import processing.core.PApplet;
import processing.event.KeyEvent;

import java.util.ArrayList;

public class Bumple extends PApplet {

    private static final int PIXELS_PER_METER = 200;
    private BumpCube bumpCube = new BumpCube();
    private ArrayList<Obstacle> obstacles = new ArrayList<>();

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
        obstacles.add(new Obstacle(Obstacle.ObstacleType.Ground, 0f, 0));
        obstacles.add(new Obstacle(Obstacle.ObstacleType.Rotating, 1f, 1.5f));
    }

    @Override
    public void draw() {
        clear();
        update();
        bumpCube.draw(this);
        for (Obstacle obstacle : obstacles)
            obstacle.draw(this);
    }

    public void update() {
        int fps = 60;
        for (int i = 0, steps = 1; i < steps; ++i) {
            bumpCube.setColliding(false);
            for (Obstacle obstacle : obstacles) {
                obstacle.update(); // TODO add delta to Obstacle.update
                if (detectCollision(obstacle, bumpCube))
                    bumpCube.setColliding(true);
            }
            bumpCube.update(1f / (fps * steps));
        }
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
        return Utils.detectSegmentCircleIntersection(new Segment(LeftUp, LeftDown), bumpCube.getCollisionShape()) ||
                Utils.detectSegmentCircleIntersection(new Segment(LeftUp, RightUp), bumpCube.getCollisionShape()) ||
                Utils.detectSegmentCircleIntersection(new Segment(LeftDown, RightDown), bumpCube.getCollisionShape()) ||
                Utils.detectSegmentCircleIntersection(new Segment(RightUp, RightDown), bumpCube.getCollisionShape());
    }

}