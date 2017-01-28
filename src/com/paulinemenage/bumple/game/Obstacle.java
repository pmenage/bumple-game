package com.paulinemenage.bumple.game;

import com.paulinemenage.bumple.physics.Point;
import com.paulinemenage.bumple.physics.Polygon;
import processing.core.PConstants;

import java.util.ArrayList;
import java.util.Arrays;

public class Obstacle {

    private float height = 0.15f;
    private float width = 1f;
    private float duration;
    private float angle;
    private Point position = new Point(0f,0f);
    private ObstacleType type;

    public enum ObstacleType {
        Sliding,
        Rotating,
        Ground
    }

    public Point getPosition() {
        return position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getAngle() {
        return angle;
    }

    /**
     * Obstacle constructor.
     * @param type The type of obstacle wanted, chosen from the enum ObstacleType.
     * @param y The height of the obstacle at the beginning.
     * @param duration The time needed for the obstacle to close itself, in seconds.
     */
    public Obstacle(ObstacleType type, float y, float duration) {
        this.type = type;
        position.y = y;
        this.duration = duration;
        switch (type) {
            case Sliding:
                position.x = -2 * width;
                angle = 0;
                break;
            case Rotating:
                position.x = -width;
                angle = (float) (- Math.PI / 2);
                break;
            case Ground:
                position.x = -width;
                width *= 2;
                angle = 0;
                break;
        }
    }

    public Polygon getCollisionShape() {
        Point LeftUp = new Point(
                position.x,
                position.y
        );
        Point RightUp = new Point(
                (float) (position.x + Math.cos(angle) * width),
                (float) (position.y + Math.sin(angle) * width)
        );
        Point RightDown = new Point(
                (float) (position.x + Math.cos(angle) * width + Math.sin(angle) * height),
                (float) (position.y + Math.sin(angle) * width + (-Math.cos(angle)) * height)
        );
        Point LeftDown = new Point(
                (float) (position.x + Math.sin(angle) * height),
                (float) (position.y + (-Math.cos(angle)) * height)
        );
        return new Polygon(Arrays.asList(LeftUp, RightUp, RightDown, LeftDown));
    }
    
    /**
     * Updates the position and the angle of the obstacle.
     * @param delta
     */
    public void update(float delta) {
        switch (type) {
            case Sliding:
                if (position.x < -width)
                    position.x += width / duration * delta;
                else
                    position.x = -width;
                break;
            case Rotating:
                if (angle < 0)
                    angle += (Math.PI / 2) / duration * delta;
                else
                    angle = 0;
                break;
            case Ground:
                break;
        }
    }

    /**
     * Draws the obstacle with the Processing library.
     * @param bumple Instance of Bumple, to call methods in the Bumple class.
     */
    public void draw(Bumple bumple) {
        bumple.rectMode(PConstants.CORNER);
        Point positionPixelsLeft = bumple.metersToPixels(position);
        Point positionPixelsRight = bumple.metersToPixels(new Point(-position.x, position.y));

        // Left part of obstacle
        bumple.pushMatrix();
        bumple.translate(positionPixelsLeft.x, positionPixelsLeft.y);
        bumple.rotate(-angle);
        bumple.rect(0, 0, bumple.metersToPixels(width), bumple.metersToPixels(height));
        bumple.popMatrix();

        // Right part of obstacle
        bumple.pushMatrix();
        bumple.translate(positionPixelsRight.x, positionPixelsRight.y);
        bumple.rotate(angle);
        bumple.translate(-bumple.metersToPixels(width), 0);
        bumple.rect(0, 0, bumple.metersToPixels(width), bumple.metersToPixels(height));
        bumple.popMatrix();
    }

}
