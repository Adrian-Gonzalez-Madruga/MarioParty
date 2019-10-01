package Minigames;

import DLibX.DConsole;
import jconsole.JConsole;
import party.pkg2.pkg0.Player;

public class StageShow extends Minigame { // game by adrian. Pick one of the four pedestals and something drops on you

    GamePlayer[] gp = new GamePlayer[p.length];
    Stage s;

    public StageShow(Player[] p, DConsole dc, JConsole[] j) {
        super(p, dc, j);
    }

    @Override
    public void run() {
        //dc.setSize(null);// set the size later appropriatly
        s = new Stage();
        for (int i = 0; i < p.length; i++) {
            gp[i] = new GamePlayer(i);
        }
        int timer = 30 * 50;
        while (timer > 0) {
            dc.clear();
            timer--;
            for (int i = 0; i < p.length; i++) {
                s.draw();
                gp[i].moveCursor();
                gp[i].drawCursor();
            }
            dc.redraw();
            dc.pause(20);
        }

    }

    @Override
    public void reset() {

    }

    @Override
    public void reward(int player) {

    }

    private class GamePlayer {

        private int c;
        private int blockLocal;
        private String[] character = {"mario", "luigi", "yoshi", "peach"};
        private String characterLink;

        public GamePlayer(int c) {
            this.c = c;
            this.blockLocal = c;
            characterLink = "assets/animations/" + character[c] + "/down/1.png";
        }

        public void draw() {

        }

        public void moveCursor() {

        }

        public void drawCursor() {

        }

        public void reset() {

        }

    }// gamePlayer class end

    private class Stage {

        String[] s = {"Coins", "Crush", "Nothing", "SpikeBall"};
        int[] randomOrder = {-1, -1, -1, -1};

        public Stage() {
            randomize();
        }

        public void randomize() {
            String[] temp = new String[s.length];
            for (int i = 0; i < s.length; i++) {
                while (true) {
                    int t = (int) (Math.random() * s.length);
                    if (temp[t] == null) {
                        temp[t] = s[i];
                        break;
                    }
                }
            }
            for (int i = 0; i < s.length; i++) {
                s[i] = temp[i];
            }
        }

        public void draw() {
            for (int i = 0; i * 45 < dc.getWidth(); i++) {
                dc.drawImage("assets/Minigames/StageShow/stage.png", i * 45, dc.getHeight() - 100);
            }
        }

        public void drawFall() {
            for (int i = 0; i < 4; i++) {
                java.lang.reflect.Method method = null; // link the method to the right class. start null
                try {
                    method = s.getClass().getMethod(s[i], Integer.class, Integer.class); // of the string chosen get that method i the mystery class
                } catch (Exception e) {
                }
                try {
                    method.invoke(s[i], ((i + 1) * 128) + (i * 32), i); // run that method
                } catch (Exception e) {
                }
            }
        }

        public void Coins(Integer x, Integer block) { // give players 5 coins

        }

        public void Crush(Integer x, Integer block) { // crush the player killing them

        }

        public void Nothing(Integer x, Integer block) { // leave the block alone

        }

        public void SpikeBall(Integer x, Integer block) { //remove 5 coins from player & kill them

        }

        public void reset() {

        }

    } // stage class end

}
