package com.paulinemenage.bumple;

import processing.core.PApplet;
import processing.event.KeyEvent;

public abstract class Screen {

    /**
     * Binds keys to different actions.
     * @param event An event.
     */
    public void keyPressed(KeyEvent event) { }

    /**
     * Draws the screen.
     * @param pApplet An instance of pApplet, to be able to use the Processing library.
     */
    public void draw(PApplet pApplet) { }

}