package com.paulinemenage.bumple.game;

import com.paulinemenage.bumple.physics.Circle;
import com.paulinemenage.bumple.physics.Point;
import processing.core.PConstants;

public class BumpCube {

    private float size = 0.3f;
    private Point position = new Point(0, size/2);
    private float vy = 0f;
    private float ay = 0f;
    private float radius = 0.1f;
    private boolean isOnGround = true;

    public Circle getCollisionShape() {
        return new Circle(position, radius);
    }

    public void jump() {
        if (isOnGround)
            ay += 30f;
    }

    public void update() {
        isOnGround = false;
        ay *= 0.94f;
        vy += (ay - 9.81f) / 60;
        position.y += vy / 60;
        if (position.y < size/2) {
            isOnGround = true;
            position.y = size/2;
            vy = 0;
        }
    }

    public void draw(Bumple bumple) {
        int sizePixels = bumple.metersToPixels(size);
        Point positionPixels = bumple.metersToPixels(position);
        bumple.rectMode(PConstants.CENTER);
        bumple.rect(positionPixels.x, positionPixels.y, sizePixels, sizePixels);
    }

}