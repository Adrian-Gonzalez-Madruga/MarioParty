package Minigames;

import DLibX.DConsole;
import java.awt.Color;
import jconsole.JConsole;
import party.pkg2.pkg0.Player;

public class MazedAndConfused extends Minigame {

    private int timer;
    private Tile[][] t = new Tile[11][15];
    private GamePlayer[] gp = new GamePlayer[p.length];

    public MazedAndConfused(Player[] p, DConsole dc, JConsole[] j) { // init 
        super(p, dc, j);
    }

    @Override
    public void run() { // minigame main
        dc.setSize(t[0].length * 48, t.length * 48); // change screen size
        boolean wait = true;
        int winner = 0;
        while (wait) { // info screen pre-game
            dc.clear();
            dc.drawImage("assets/Minigames/MazedAndConfused/introScreen.png", 0, 0);
            for (int i = 0; i < p.length; i++) {
                if (j[i].isButtonPressed(9)) {
                    wait = false;
                }
            }
            dc.redraw();
            dc.pause(20);
        }
        reset();
        change();
        while (playing) { // while no one has won. actual game within this loop
            dc.clear();
            change();
            move();
            draw();
            for (int i = 0; i < p.length; i++) {
                if (gp[i].getX() == 7 && gp[i].getY() == 5) { //finish if a player is on a winTile
                    reward(i);
                    winner = i;
                    playing = false;
                }
            }
            dc.redraw();
            dc.pause(20);
        }
        wait = true;
        while (wait) { //says who the winner is screen
            dc.clear();
            dc.drawImage("assets/Minigames/MazedAndConfused/winnerScreen.png", 0, 0);
            dc.setOrigin(DConsole.ORIGIN_CENTER);
            dc.setPaint(Color.ORANGE);
            dc.drawString(gp[winner].getPlayerName(winner), 329, 129);
            dc.setOrigin(DConsole.ORIGIN_TOP_LEFT);
            for (int i = 0; i < p.length; i++) {
                if (j[i].isButtonPressed(1)) {
                    wait = false;
                }
            }
            dc.redraw();
            dc.pause(20);
        }
    }

    @Override
    public void reset() { // reset all changed variables
        playing = true;
        timer = -1;
        for (int c = 0; c < t.length; c++) { // call tiles
            for (int v = 0; v < t[0].length; v++) {
                t[c][v] = new Tile(c, v, t);
            }
        }
        int localSwap = (int) (Math.random() * p.length);
        for (int i = 0; i < p.length; i++) {
            gp[i] = new GamePlayer(i, t, localSwap);
        }
    }

    @Override
    public void reward(int player) { // reward the player
        p[player].scorePlus(10);
    }

    public void change() { // change all the lasers in all tiles
        timer++;
        if (timer % 60 == 0) { // changing speed
            for (int c = 0; c < t.length; c++) {
                for (int v = 0; v < t[0].length; v++) {
                    t[c][v].change();
                }
            }
            for (int c = 0; c < t.length; c++) {
                for (int v = 0; v < t[0].length; v++) {
                    t[c][v].checkOthers();
                }
            }
        }
    }

    public void draw() {
        dc.setPaint(new Color(1, 220, 255));
        dc.fillRect(0, 0, 1000, 1000);
        for (int c = 0; c < t.length; c++) {
            for (int v = 0; v < t[0].length; v++) { // draw field first
                t[c][v].fieldDraw();
            }
        }
        for (int c = 0; c < t.length; c++) { // draw lasers second
            for (int v = 0; v < t[0].length; v++) {
                t[c][v].laserDraw();
            }
        }
        for (int q = 0; q < gp.length; q++) { // draw players last
            gp[q].draw();
        }
    }

    public void move() {
        for (int q = 0; q < gp.length; q++) {
            gp[q].move();
        }
    }

    private class Tile { // tile class

        private boolean[] line = new boolean[4];
        private boolean[] locked = new boolean[4];
        private boolean winTile;
        private int x;
        private int y;
        private String link = "assets/Minigames/MazedAndConfused/";

        public Tile(int y, int x, Tile[][] t) { // initiate and lock the edges/corners to always be on
            this.x = x;
            this.y = y;
            int[] toLockVariable = {y, x};
            int[] toLockLength = {0, t.length - 1, 0, t[0].length - 1};
            if (x == 7 && y == 5) {
                winTile = true;
            }
            for (int q = 0; q < line.length; q++) {
                if (toLockVariable[q / 2] == toLockLength[q]) {
                    locked[q] = true;
                    line[q] = true;
                }
            }
        }

        public void fieldDraw() { // draw the field, tiles, and poles
            if (!winTile) {
                dc.drawImage(link + "tile.png", x * 48, y * 48);
            } else {
                dc.drawImage(link + "WinTile.png", x * 48, y * 48);
            }
            for (int v = 0; v < 2; v++) {
                for (int h = 0; h < 2; h++) {
                    dc.drawImage(link + "pole.png", ((x + (1 * v)) * 48) - 3, ((y + (1 * h)) * 48) - 3);
                }
            }
        }

        public void laserDraw() { // draw the lasers when theyre true independantly
            for (int q = 0; q < line.length; q++) {
                if (line[q]) {
                    if (q < 2) {
                        dc.drawImage(link + "laserH.png", (x * 48) + 3, ((y + ((q % 2) * 1)) * 48) - 3);
                    } else {
                        dc.drawImage(link + "laserV.png", ((x + ((q % 2) * 1)) * 48) - 3, (y * 48) + 3);
                    }
                }
            }
        }

        public void change() { // change this tiles lasers if not locked
            for (int q = 0; q < line.length; q++) {
                if (!locked[q]) {
                    line[q] = false;
                    if ((int) (Math.random() * 7) < 2) { // rarity
                        line[q] = true;
                    }
                }
            }
        }

        public void checkOthers() { // check the other tiles so if there is a line each tile reconizes it
            int[] adjacentX = {0, 0, -1, 1};
            int[] adjacentY = {-1, 1, 0, 0};
            int[] oppositeSide = {1, 0, 3, 2}; // numbers for opposite side checked
            for (int i = 0; i < 4; i++) {
                if (x + adjacentX[i] >= 0 && x + adjacentX[i] < t[0].length && y + adjacentY[i] >= 0 && y + adjacentY[i] < t.length) { // if not ArrayOutOfBounds
                    if (t[y + adjacentY[i]][x + adjacentX[i]].getLine(oppositeSide[i])) { // check the line opposite
                        line[i] = true; // make ours true if it's true;
                    }
                }
            }
        }

        public boolean getLine(int a) { // see if the line is on or off
            return line[a];
        }

    } // tile class end

    private class GamePlayer { // player class

        private int[] initX = {0, 14, 0, 14};
        private int[] initY = {0, 0, 10, 10};
        private int[] px = new int[p.length];
        private int[] py = new int[p.length];
        private int c;
        private int moveTimer = -1;
        private final String link = "assets/animations/";
        private final String[] character = {"mario", "luigi", "yoshi", "peach"};
        private final int[] animation = {1, 2, 1, 3};
        private final String[] dir = {"/up/", "/down/", "/left/", "/right/"};
        private int dirSelect = 1;
        private double frame = 0;
        private JConsole j;
        private Tile[][] t;

        public GamePlayer(int c, Tile[][] t, int localSwap) { // init
            this.c = c;
            this.j = new JConsole(c);
            this.t = t;
            for (int i = 0; i < p.length; i++) {
                px[i] = initX[(c + localSwap) % 4];
                py[i] = initY[(c + localSwap) % 4];
            }
        }

        public void move() { // move using jumping through tiles
            if (moveTimer < 15) { // reset the timer to move
                moveTimer++;
            }
            if (j.analogVertical(1) < 30 && moveTimer == 15) { // up // if pointed direction && line is off && timer is good
                if (!t[py[c]][px[c]].getLine(0)) {
                    dirSelect = 0;
                    py[c]--;
                }
                moveTimer = 0;
                return;
            } else if (j.analogVertical(1) > 70 && moveTimer == 15) { // down
                if (!t[py[c]][px[c]].getLine(1)) {
                    dirSelect = 1;
                    py[c]++;
                }
                moveTimer = 0;
                return;
            }
            if (j.analogHorizontal(1) < 30 && moveTimer == 15) { // left
                if (!t[py[c]][px[c]].getLine(2)) {
                    dirSelect = 2;
                    px[c]--;
                }
                moveTimer = 0;
                return;
            } else if (j.analogHorizontal(
                    1) > 70 && moveTimer == 15) { // right
                if (!t[py[c]][px[c]].getLine(3)) {
                    dirSelect = 3;
                    px[c]++;
                }
                moveTimer = 0;
                return;
            }
        }

        public void draw() { // draw the character
            frame += 0.2;
            dc.setOrigin(DConsole.ORIGIN_CENTER);
            dc.drawImage(link + character[c] + dir[dirSelect] + animation[(int) frame % 4] + ".png", (px[c] * 48) + 24, (py[c] * 48) + 24);
            dc.setOrigin(DConsole.ORIGIN_TOP_LEFT);
        }

        public int getX() { // return x
            return px[c];
        }

        public int getY() { // return y
            return py[c];
        }

        public String getPlayerName(int c) {
            return character[c];
        }

    } //gamePlayer end

} // game class end
