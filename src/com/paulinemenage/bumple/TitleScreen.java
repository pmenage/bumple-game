package com.paulinemenage.bumple;

import com.paulinemenage.bumple.game.Bumple;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.event.KeyEvent;

public class TitleScreen extends Screen {

    private Main main;

    public TitleScreen(Main main) {
        this.main = main;
    }

    /**
     * Binds the spacebar to the beginning of a game.
     * @param event An event.
     */
    public void keyPressed(KeyEvent event) {
        if (event.getKey() == ' ')
            main.setScreen(new Bumple(main));
    }

    /**
     * Draws the main menu screen.
     * @param pApplet An instance of pApplet to be able to use the Processing library.
     */
    public void draw(PApplet pApplet) {
        pApplet.clear();
        pApplet.textSize(30);
        pApplet.text("Main Menu", 200, 170);
        pApplet.textSize(20);
        pApplet.textAlign(PConstants.CENTER);
        pApplet.text("Press the spacebar to play",130, 250, 130, 200);
    }

}
