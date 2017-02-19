package com.paulinemenage.bumple.game;

import com.paulinemenage.bumple.Applet;
import com.paulinemenage.bumple.physics.Point;
import com.paulinemenage.bumple.physics.Segment;
import processing.core.PConstants;
import processing.core.PImage;

public class Water {

    private Applet applet;
    private Segment segment;
    private PImage shark;

    public Water(Applet applet, float y) {
        this.applet = applet;
        segment = new Segment(new Point(-1, y), new Point(1, y));
        shark = applet.loadImage("shark.png");
    }

    public Segment getCollisionShape() {
        return segment;
    }

    /**
     * Updates the position of the water.
     * @param delta Small time interval to calculate the approximation.
     */
    public void update(float delta) {
        segment.a.y += 0.5 * delta;
        segment.b.y += 0.5 * delta;
    }

    /**
     * Draws the water with the Processing library.
     * @param bumple Instance of Bumple, to call methods in the Bumple class.
     */
    public void draw(Bumple bumple) {
        applet.imageMode(PConstants.CENTER);
        Point pointPixels = bumple.metersToPixels(segment.a);
        // applet.rect(pointPixels.x, pointPixels.y, bumple.metersToPixels(2f), bumple.metersToPixels(.05f));
        applet.image(shark, 220, pointPixels.y);
    }

}
