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

    public void keyPressed(KeyEvent event) {
        if (event.getKey() == ' ')
            main.setScreen(new Bumple());
    }

    public void draw(PApplet pApplet) {
        pApplet.textSize(24);
        pApplet.textAlign(PConstants.CENTER);
        pApplet.text("Press the spacebar to play",130, 250, 130, 200);
    }

}
