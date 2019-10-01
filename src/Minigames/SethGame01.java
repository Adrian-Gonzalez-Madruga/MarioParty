package Minigames;

import DLibX.DConsole;
import java.awt.Color;
import jconsole.JConsole;
import party.pkg2.pkg0.Player;

public class SethGame01 extends Minigame {

    public SethGame01(Player[] p, DConsole dc, JConsole[] j) {
        super(p, dc, j);
    }

    @Override
    public void run() {
        playing = true;
        int winner = 0;
        while (playing) {
            dc.clear();
            //graphics code

            dc.pause(20);
            dc.redraw();
        }
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reward(int player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
