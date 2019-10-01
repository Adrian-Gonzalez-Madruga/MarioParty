package Minigames;

import DLibX.DConsole;
import java.awt.Color;
import party.pkg2.pkg0.Player;
import jconsole.JConsole;

public class MiniGame01 extends Minigame {

    public MiniGame01(Player[] p, DConsole dc, JConsole[] j) {
        super(p, dc, j);
    }

    @Override
    public void run() {
        playing = true;
        int winner = 0;
        while (playing) {
            dc.clear();
            //graphics code
            dc.setPaint(Color.white);
            dc.fillRect(0, 0, dc.getWidth(), dc.getHeight());
            dc.setPaint(Color.black);
            dc.drawString("PRESS A", 100, 100);
            for (int i = 0; i < j.length; i++) {
                if (j[i].isButtonPressed(1) || dc.isKeyPressed('a')) {
                    winner = i;
                    System.out.println("Player " + (i + 1) + " wins");
                    playing = false;
                    break;
                }
            }
            dc.pause(20);
            dc.redraw();
        }
        this.reward(winner);
    }

    @Override
    public void reward(int player) {
        p[player].scorePlus(10);
    }

    @Override
    public void reset() {

    }

}
