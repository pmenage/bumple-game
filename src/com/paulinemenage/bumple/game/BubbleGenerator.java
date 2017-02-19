package com.paulinemenage.bumple.game;

import com.paulinemenage.bumple.physics.Circle;
import com.paulinemenage.bumple.physics.Point;
import processing.core.PApplet;

public class BubbleGenerator {

    private class Bubble {

        public Circle circle;
        public float vy;

        public Bubble(Point center, float radius, float vy) {
            circle = new Circle(center, radius);
            this.vy = vy;
        }

    }

    private static final int STOCK = 15;

    private int numberOfBubbles = STOCK;
    private Bubble[] bubbles = new Bubble[STOCK];

    public BubbleGenerator() {
        for (int i = 0; i < STOCK; i++)
            bubbles[i] = new Bubble(new Point((float) Math.random() * 2, 3.1f), (float) Math.random() * .2f, (float) Math.random());
    }

    public void draw(Bumple bumple, PApplet pApplet) {
        pApplet.fill(176,224,230);
        pApplet.stroke(255, 255, 255);
        for (Bubble bubble : bubbles)
            pApplet.ellipse(
                    bumple.metersToPixels(bubble.circle.center.x),
                    bumple.metersToPixels(bubble.circle.center.y),
                    bumple.metersToPixels(bubble.circle.radius),
                    bumple.metersToPixels(bubble.circle.radius)
            );
    }

    public void update(float delta) {
        for (Bubble bubble : bubbles)
            bubble.circle.center.y -= bubble.vy * delta;
    }

}
