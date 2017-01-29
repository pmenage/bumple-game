package com.paulinemenage.bumple;

import com.paulinemenage.bumple.game.Bumple;
import processing.core.PApplet;

import processing.event.KeyEvent;

public class Main extends PApplet {

    private Bumple bumple = new Bumple();

    public static void main(String[] args) {
        Main.main(Main.class.getName());
    }

    /**
     * Gives the dimensions of the window.
     * Creates and adds the ground and the first obstacle to the obstacles array.
     */
    @Override
    public void settings() {
        size(Bumple.metersToPixels(2), Bumple.metersToPixels(3));
    }

    @Override
    public void keyPressed(KeyEvent event) {
        bumple.keyPressed(event);
    }

    @Override
    public void draw() {
        bumple.draw(this);
    }

}
