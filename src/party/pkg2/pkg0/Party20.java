package party.pkg2.pkg0;

import DLibX.DConsole;
import Minigames.*;
import java.awt.Color;
import jconsole.JConsole;

public class Party20 { // Discord: https://discord.gg/rRM7nGC

    public static void main(String[] args) {

        int playerCount = 4;

        DConsole dc = new DConsole(960, 640);
        while (true) { // encompasses the entire game so it can be played again and will reload once done
            JConsole[] j = new JConsole[playerCount]; // comment and remove JConsole if u have no controllers connected. it will die
            Player p[] = new Player[playerCount];
            Tile[] t = new Tile[116]; // enter the number of tiles there are
            Board f = new Board();
            Minigame m[] = new Minigame[1];

            m[0] = new MazedAndConfused(p, dc, j); // enter your minigame to test
            //m[1] = new SpikeBall(p, dc, j);
            //m[2] = new TimerTiles(p,dc,j); // incomplete

            dc.setSize(960, 640);

            f.start(dc, t, p);
            for (int c = 0; c < playerCount; c++) { // init classes
                j[c] = new JConsole(c);
                p[c] = new Player();
                p[c].start(f, j[c], dc, c, p);
            }
           //m[(int) (Math.random() * m.length)].run(); // uncomment when you want to read minigames
            boolean wait = true;
            double frame = 0;
            while (wait) { //says who the winner is screen
                dc.clear();
                dc.drawImage("assets/titleScreen.png", 0, 0);
                dc.drawImage("assets/animations/start button/" + (((int)frame % 19) + 1) + ".png", 412, 340);
                frame += 0.2;
                for (int i = 0; i < p.length; i++) {
                    if (j[i].isButtonPressed(9) || dc.isKeyPressed(' ')) {
                        wait = false;
                    }
                }
                dc.redraw();
                dc.pause(10);
            }
            while (f.getTurns() > 0) {// while we still hacve turns to play in
                for (int c = 0; c < playerCount; c++) {//for each player's turn
                    f.draw();
                    for (int r = 0; r < playerCount; r++) { // draw all 4 players
                        p[r].draw();
                    }
                    p[c].roll();
                    while (p[c].returnRoll() != 0) { // while you can still move do that players turn
                        dc.clear();

                        f.draw();
                        p[c].move();
                        p[c].drawRoll();
                        t[p[c].getTile()].isOver(p[c]);
                        p[c].frame();

                        for (int r = 0; r < playerCount; r++) { // draw all players
                            p[r].draw();
                        }

                        dc.redraw();
                        dc.pause(100);
                    }
                    t[p[c].getTile()].reward(p[c]); //reward when landed on a tile
                    f.getPlaces(); // show who is in 1st ...
                }
                f.setTurns(f.getTurns() - 1); //decrease the turns by one
                m[(int) (Math.random() * m.length)].run(); // uncomment when you want to read minigames
                dc.setSize(960, 640);
            }

            while (!dc.isKeyPressed(' ')) { // until you reset the game
                dc.clear();
                f.drawEndScreen();
                dc.redraw();
                dc.pause(100);
            }
        }// the entire game - DConsole since it is the screen
    }
}
