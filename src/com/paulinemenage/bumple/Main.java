package com.paulinemenage.bumple;

import com.paulinemenage.bumple.game.Bumple;
import processing.core.PApplet;

import processing.event.KeyEvent;

public class Main extends PApplet {

    /**
     * Defines the screen on which the game is.
     */
    private Screen screen = new TitleScreen(this);

    public static void main(String[] args) {
        Main.main(Main.class.getName());
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
    }

    @Override
    public void keyPressed(KeyEvent event) {
        screen.keyPressed(event);
    }

    @Override
    public void draw() {
        screen.draw(this);
    }

}
