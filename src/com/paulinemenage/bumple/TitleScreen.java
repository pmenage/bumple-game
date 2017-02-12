package com.paulinemenage.bumple;

import com.paulinemenage.bumple.game.Bumple;
import processing.core.PConstants;
import processing.core.PImage;
import processing.event.KeyEvent;

public class TitleScreen extends Screen {

    private Applet applet;
    private PImage title;
    private PImage playButton;

    public TitleScreen(Applet applet) {
        this.applet = applet;
        title = applet.loadImage("bumple-title.png");
        playButton = applet.loadImage("play-button.png");
    }

    /**
     * Binds the spacebar to the beginning of a game.
     * @param event An event.
     */
    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKey() == ' ')
            applet.setScreen(new Bumple(applet));
    }

    /**
     * Draws the main menu screen.
     */
    @Override
    public void draw() {
        applet.clear();
        applet.background(255, 255, 255);
        applet.imageMode(PConstants.CENTER);
        applet.image(title, 200, 170, 315, 129);
        applet.image(playButton, 200, 400, 191, 121);
    }

}
