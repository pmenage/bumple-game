package com.paulinemenage.bumple;

import com.paulinemenage.bumple.game.Bumple;
import processing.core.PConstants;
import processing.event.KeyEvent;

public class TitleScreen extends Screen {

    private Applet applet;

    public TitleScreen(Applet applet) {
        this.applet = applet;
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
        applet.textSize(30);
        applet.text("Main Menu", 200, 170);
        applet.textSize(20);
        applet.textAlign(PConstants.CENTER);
        applet.text("Press the spacebar to play",130, 250, 130, 200);
    }

}
