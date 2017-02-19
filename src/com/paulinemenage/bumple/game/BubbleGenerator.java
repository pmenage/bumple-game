package com.paulinemenage.bumple.game;

import com.paulinemenage.bumple.physics.Circle;
import com.paulinemenage.bumple.physics.Point;
import processing.core.PApplet;

public class BubbleGenerator {

    private class Bubble {

        public Circle circle = new Circle(new Point(0, 0), 0);
        public float vy;

        /*public Bubble(Point center, float radius, float vy) {
            circle = new Circle(center, radius);
            this.vy = vy;
        }*/

    }

    private Bubble[] bubbles = new Bubble[15];

    public BubbleGenerator() {
        for (int i = 0; i < bubbles.length; i++) {
            bubbles[i] = new Bubble();
            resetBubble(bubbles[i]);
        }
    }

    public void resetBubble(Bubble bubble) {
        bubble.circle.center.x = (float) Math.random() * 2;
        bubble.circle.center.y = 3.1f;
        bubble.circle.radius = (float) Math.random() * .2f;
        bubble.vy = (float) Math.random();
    }

    public void regenerateBubble() {
        for (Bubble bubble : bubbles)
            if (bubble.circle.center.y < -0.1)
                resetBubble(bubble);
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
        regenerateBubble();
    }

}
