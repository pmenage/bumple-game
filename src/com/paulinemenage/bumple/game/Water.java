package com.paulinemenage.bumple.game;

import com.paulinemenage.bumple.Applet;
import com.paulinemenage.bumple.physics.Point;
import com.paulinemenage.bumple.physics.Segment;

public class Water {

    private Applet applet;
    private Segment segment;

    public Water(Applet applet, float y) {
        this.applet = applet;
        segment = new Segment(new Point(-1, y), new Point(1, y));
    }

    public Segment getCollisionShape() {
        return segment;
    }

    public void update(float delta) {
        segment.a.y += 0.5 * delta;
        segment.b.y += 0.5 * delta;
    }

    public void draw(Bumple bumple) {
        Point pointPixels = bumple.metersToPixels(segment.a);
        applet.rect(pointPixels.x, pointPixels.y, bumple.metersToPixels(2f), bumple.metersToPixels(.05f));
    }

}