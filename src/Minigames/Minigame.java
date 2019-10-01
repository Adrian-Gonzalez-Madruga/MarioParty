package Minigames;

import DLibX.DConsole;
import jconsole.JConsole;
import party.pkg2.pkg0.Player;

public abstract class Minigame {

    protected DConsole dc;
    protected Player[] p;
    protected JConsole[] j;
    protected boolean playing = true;

    public Minigame(Player[] p, DConsole dc, JConsole[] j) {
        this.j = j;
        this.dc = dc;
        this.p = p;
    }

    public abstract void run(); // main

    public abstract void reset();// reset all variables

    public abstract void reward(int player); // the reward

}
