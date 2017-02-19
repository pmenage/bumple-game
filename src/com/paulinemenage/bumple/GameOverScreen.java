package com.paulinemenage.bumple;

import com.paulinemenage.bumple.game.Bumple;
import processing.core.PConstants;
import processing.core.PImage;
import processing.event.KeyEvent;

public class GameOverScreen extends Screen {

    private Applet applet;
    private PImage gameOver;
    private PImage playAgainButton;
    private PImage playAgainButtonHighlighted;
    private PImage mainMenuButton;
    private PImage mainMenuButtonHighlighted;

    public GameOverScreen(Applet applet) {
        this.applet = applet;
        gameOver = applet.loadImage("game-over.png");
        playAgainButton = applet.loadImage("play-again-button.png");
        playAgainButtonHighlighted = applet.loadImage("play-again-button-highlighted.png");
        mainMenuButton = applet.loadImage("main-menu-button.png");
        mainMenuButtonHighlighted = applet.loadImage("main-menu-button-highlighted.png");
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
     * Binds the mouse click to the beginning of a game, if its position is on the play button, and to the title screen if it's on the main menu button.
     */
    @Override
    public void mouseClicked() {
        if (applet.mouseX > 28 && applet.mouseX < 192 && applet.mouseY > 341 && applet.mouseY < 459)
            applet.setScreen(new Bumple(applet));
        if (applet.mouseX > 213 && applet.mouseX < 367 && applet.mouseY > 341 && applet.mouseY < 459)
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
        applet.image(gameOver, 200, 170, 276, 188);
        if (applet.mouseX > 28 && applet.mouseX < 192 && applet.mouseY > 341 && applet.mouseY < 459)
            applet.image(playAgainButtonHighlighted, 110, 400, 164, 118);
        else
            applet.image(playAgainButton, 110, 400, 164, 118);
        if (applet.mouseX > 213 && applet.mouseX < 367 && applet.mouseY > 341 && applet.mouseY < 459)
            applet.image(mainMenuButtonHighlighted, 290, 400, 155, 119);
        else
            applet.image(mainMenuButton, 290, 400, 155, 119);
    }
}
