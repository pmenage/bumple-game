package com.paulinemenage.bumple.game;

import com.paulinemenage.bumple.physics.Point;
import com.paulinemenage.bumple.physics.Polygon;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.util.Arrays;

import static com.paulinemenage.bumple.game.Bumple.DEBUG;

public class Obstacle {

    private float height = 0.15f;
    private float width = 1f;
    private float duration;
    private float angle;
    private Point position = new Point(0f,0f);
    private ObstacleType type;
    private PImage obstacleLeft;
    private PImage obstacleRight;

    public enum ObstacleType {
        Sliding,
        Rotating,
        Ground
    }

    public Point getPosition() {
        return position;
    }

    /**
     * Obstacle constructor.
     * @param type The type of obstacle wanted, chosen from the enum ObstacleType.
     * @param y The height of the obstacle at the beginning.
     * @param duration The time needed for the obstacle to close itself, in seconds.
     * @param pApplet Instance of PApplet, to call methods from the Processing library.
     */
    public Obstacle(ObstacleType type, float y, float duration, PApplet pApplet) {
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
                // width *= 2;
                angle = 0;
                break;
        }
        obstacleLeft = pApplet.loadImage("obstacle-left.png");
        obstacleRight = pApplet.loadImage("obstacle-right.png");
    }

    /**
     * Get the obstacle's shape.
     * @return A polygon which represents the obstacle's shape.
     */
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
     * @param delta Small time interval to calculate the approximation.
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
     * @param pApplet Instance of PApplet, to call methods from the Processing library.
     */
    public void draw(Bumple bumple, PApplet pApplet) {
        pApplet.rectMode(PConstants.CORNER);
        pApplet.imageMode(PConstants.CORNER);
        Point positionPixelsLeft = bumple.metersToPixels(position);
        Point positionPixelsRight = bumple.metersToPixels(new Point(-position.x, position.y));

        // Left part of obstacle
        pApplet.pushMatrix();
        pApplet.translate(positionPixelsLeft.x, positionPixelsLeft.y);
        pApplet.rotate(-angle);
        pApplet.image(obstacleLeft, 0, 0, 202, 40);
        if (DEBUG)
            pApplet.rect(0, 0, bumple.metersToPixels(width), bumple.metersToPixels(height));
        pApplet.popMatrix();

        // Right part of obstacle
        pApplet.pushMatrix();
        pApplet.translate(positionPixelsRight.x, positionPixelsRight.y);
        pApplet.rotate(angle);
        pApplet.translate(-bumple.metersToPixels(width), 0);
        pApplet.image(obstacleRight, 0, 0, 202, 40);
        if (DEBUG)
            pApplet.rect(0, 0, bumple.metersToPixels(width), bumple.metersToPixels(height));
        pApplet.popMatrix();
    }

}
