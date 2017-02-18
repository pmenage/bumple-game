package com.paulinemenage.bumple;

import com.paulinemenage.bumple.game.Bumple;
import processing.core.PConstants;
import processing.core.PImage;
import processing.event.KeyEvent;

public class GameOverScreen extends Screen {

    private Applet applet;
    private PImage gameOver;

    public GameOverScreen(Applet applet) {
        this.applet = applet;
        gameOver = applet.loadImage("game-over.png");
    }

    /**
     * Binds the spacebar to the beginning of a game and the m key to the applet menu.
     * @param event An event.
     */
    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKey() == ' ')
            applet.setScreen(new Bumple(applet));
        if (event.getKey() == 'm')
            applet.setScreen(new TitleScreen(applet));
    }

    /**
     * Draws the game over screen.
     */
    @Override
    public void draw() {
        applet.clear();
        applet.background(255, 255, 255);
        applet.imageMode(PConstants.CENTER);
        applet.image(gameOver, 200, 170, 380, 214);
        applet.textAlign(PConstants.CENTER);
        applet.textSize(20);
        applet.fill(50);
        applet.text("Press m to return to menu", 70, 250, 100, 200);
        applet.text("Press spacebar to play again", 200, 250, 150, 200);
    }
}
