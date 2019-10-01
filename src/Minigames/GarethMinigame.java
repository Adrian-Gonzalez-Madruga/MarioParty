package Minigames;

import DLibX.DConsole;
import jconsole.JConsole;
import party.pkg2.pkg0.Player;

public class GarethMinigame extends Minigame {

    private GamePlayer[] gp = new GamePlayer[p.length];

    public GarethMinigame(Player[] p, DConsole dc, JConsole[] j) {
        super(p, dc, j);
    }

    @Override
    public void run() {
        reset();
        while (playing) {
            dc.clear();

            dc.redraw();
            dc.pause(20);
        }
    }

    @Override
    public void reset() {
        for (int i = 0; i < p.length; i++) {
            gp[i] = new GamePlayer();
        }
    }

    @Override
    public void reward(int player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class GamePlayer {

        private final String link = "assets/animations/";
        private final String[] character = {"mario", "luigi", "yoshi", "peach"};
        private final int[] animation = {1, 2, 1, 3};
        private final String[] dir = {"/up/", "/down/", "/left/", "/right/"};
        private int dirSelect = 1;
        private double frame = 0;
        
        public void draw() { // draw the character
            frame += 0.2;
           // dc.drawImage(link + character[c] + dir[dirSelect] + animation[(int) frame % 4] + ".png", (px[c] * 48) + 24, (py[c] * 48) + 24);
        }
        
    }

}
