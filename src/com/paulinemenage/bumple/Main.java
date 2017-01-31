package com.paulinemenage.bumple;

import com.paulinemenage.bumple.game.Bumple;
import processing.core.PApplet;

import processing.event.KeyEvent;

public class Main extends PApplet {

    private Screen screen = new GameOverScreen(this);

    public static void main(String[] args) {
        Main.main(Main.class.getName());
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    /**
     * Gives the dimensions of the window.
     */
    @Override
    public void settings() {
        size(Bumple.metersToPixels(2), Bumple.metersToPixels(3));
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
