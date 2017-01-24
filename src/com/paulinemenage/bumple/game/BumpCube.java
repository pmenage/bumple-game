package com.paulinemenage.bumple.game;

import com.paulinemenage.bumple.physics.Circle;
import com.paulinemenage.bumple.physics.Point;
import processing.core.PConstants;

import java.util.Date;

public class BumpCube {

    private float size = 0.3f;
    private Point position = new Point(0, size/2);
    private float vy = 0f;
    private float ay = 0f;
    private float radius = 0.1f;
    private boolean isOnGround = true;
    private boolean isColliding = false;
    private float jumpTimeLeft = 0;

    public Circle getCollisionShape() {
        return new Circle(position, radius);
    }

    public void setColliding(boolean colliding) {
        isColliding = colliding;
    }

    /**
     * Makes the cube jump, after checking that it is on the ground.
     */
    public void jump() {
        if (isOnGround)
            jumpTimeLeft = .4f;
    }

    /**
     * Updates the position, the speed, and the acceleration of the cube.
     * @param delta Small float value to calculate the approximation.
     */
    public void update(float delta) {
        if (jumpTimeLeft > 0)
            ay = 20f;
        if (jumpTimeLeft <= 0)
            ay = 0f;
        if (isColliding && jumpTimeLeft < 0) {
            if (vy < 0)
                isOnGround = true;
            vy = 0;
        } else {
            isOnGround = false;
            vy += (ay - 9.81f) * delta;
            position.y += vy * delta;
        }
        jumpTimeLeft -= delta;
    }

    /**
     * Draws the cube with the Processing library.
     * @param bumple Instance of Bumple, to call methods in the Bumple class.
     */
    public void draw(Bumple bumple) {
        int sizePixels = bumple.metersToPixels(size);
        Point positionPixels = bumple.metersToPixels(position);
        bumple.rectMode(PConstants.CENTER);
        bumple.rect(positionPixels.x, positionPixels.y, sizePixels, sizePixels);
        int diameter = bumple.metersToPixels(radius*2);
        bumple.ellipse(positionPixels.x, positionPixels.y, diameter, diameter);
    }

}