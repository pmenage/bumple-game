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
     * Binds the mouse click to different actions.
     */
    public void mouseClicked() { }

    /**
     * Draws the screen.
     */
    public void draw() { }

}