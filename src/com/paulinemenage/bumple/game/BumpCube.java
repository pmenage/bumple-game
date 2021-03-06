package com.paulinemenage.bumple.game;

import com.paulinemenage.bumple.Applet;
import com.paulinemenage.bumple.physics.Circle;
import com.paulinemenage.bumple.physics.Point;
import processing.core.PConstants;
import processing.core.PImage;

import static com.paulinemenage.bumple.game.Bumple.DEBUG;

public class BumpCube {

    private static final float MAX_JUMP_TIME = .4f;
    private Applet applet;
    private float size = 0.4f;
    private Point position = new Point(0, size/2);
    private float vy = 0f;
    private float ay = 0f;
    private float radius = 0.15f;
    private boolean isOnGround = true;
    private boolean isColliding = false;
    private float jumpTimeLeft = 0;
    private PImage sprite;

    public BumpCube(Applet applet) {
        this.applet = applet;
        sprite = applet.loadImage("octopus.png");
    }

    /**
     * Get the cube's shape.
     * @return A circle, which represents the shape of the cube which can collide with another shape.
     */
    public Circle getCollisionShape() {
        return new Circle(position, radius);
    }

    public boolean isOnGround() {
        return isOnGround;
    }

    public void setColliding(boolean colliding) {
        isColliding = colliding;
    }

    /**
     * Makes the cube jump, after checking that it is on the ground.
     */
    public void jump() {
        if (isOnGround)
            jumpTimeLeft = MAX_JUMP_TIME;
    }

    /**
     * Updates the position, the speed, and the acceleration of the cube.
     * @param delta Small time interval to calculate the approximation.
     */
    public void update(float delta) {
        if (jumpTimeLeft > 0)
            ay = 20f;
        if (jumpTimeLeft <= 0)
            ay = 0f;
        if (isColliding && jumpTimeLeft < .9f * MAX_JUMP_TIME) {
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
        applet.imageMode(PConstants.CENTER);
        applet.image(sprite, positionPixels.x, positionPixels.y, sizePixels, sizePixels);
        if (DEBUG) {
            applet.rectMode(PConstants.CENTER);
            applet.rect(positionPixels.x, positionPixels.y, sizePixels, sizePixels);
            int diameter = bumple.metersToPixels(radius * 2);
            applet.ellipse(positionPixels.x, positionPixels.y, diameter, diameter);
        }
    }

}