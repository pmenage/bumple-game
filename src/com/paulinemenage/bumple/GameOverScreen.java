package com.paulinemenage.bumple;

import com.paulinemenage.bumple.game.Bumple;
import processing.core.PConstants;
import processing.event.KeyEvent;

public class GameOverScreen extends Screen {

    private Applet applet;

    public GameOverScreen(Applet applet) {
        this.applet = applet;
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
        applet.textAlign(PConstants.CENTER);
        applet.textSize(30);
        applet.text("Game over", 200, 170);
        applet.textSize(20);
        applet.text("Press m to return to menu", 70, 250, 100, 200);
        applet.text("Press spacebar to play again", 200, 250, 150, 200);
    }
}
