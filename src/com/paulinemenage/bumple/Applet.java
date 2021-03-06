package com.paulinemenage.bumple;

import com.paulinemenage.bumple.game.Bumple;
import processing.core.PApplet;

import processing.event.KeyEvent;

public class Applet extends PApplet {

    /**
     * Defines the screen on which the game is.
     */
    private Screen screen;

    public static void main(String[] args) {
        Applet.main(Applet.class.getName());
    }

    /**
     * Sets the screen to another screen.
     * @param screen The other screen.
     */
    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    /**
     * Gives the dimensions of the window.
     */
    @Override
    public void settings() {
        size(Bumple.metersToPixels(2), Bumple.metersToPixels(3));
        smooth(4);
        screen = new TitleScreen(this);

    }

    @Override
    public void keyPressed(KeyEvent event) {
        screen.keyPressed(event);
    }

    @Override
    public void mouseClicked() {
        screen.mouseClicked();
    }

    @Override
    public void draw() {
        screen.draw();
    }

}
