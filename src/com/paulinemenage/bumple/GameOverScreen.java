package com.paulinemenage.bumple;

import com.paulinemenage.bumple.game.Bumple;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.event.KeyEvent;

public class GameOverScreen extends Screen {

    private Main main;

    public GameOverScreen(Main main) {
        this.main = main;
    }

    /**
     * Binds the spacebar to the beginning of a game and the m key to the main menu.
     * @param event An event.
     */
    public void keyPressed(KeyEvent event) {
        if (event.getKey() == ' ')
            main.setScreen(new Bumple());
        if (event.getKey() == 'm')
            main.setScreen(new TitleScreen(main));
    }

    /**
     * Draws the game over screen.
     * @param pApplet An instance of pApplet to be able to use the Processing library.
     */
    public void draw(PApplet pApplet) {
        pApplet.clear();
        pApplet.textAlign(PConstants.CENTER);
        pApplet.textSize(20);
        pApplet.text("Press m to return to menu", 70, 250, 100, 200);
        pApplet.text("Press spacebar to play again", 200, 250, 150, 200);
    }
}
