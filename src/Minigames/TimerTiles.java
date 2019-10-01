package Minigames;

import DLibX.DConsole;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import jconsole.JConsole;
import party.pkg2.pkg0.Player;

public class TimerTiles extends Minigame {

    Random random = new Random();

    private MiniPlayer[] mp = new MiniPlayer[p.length];

    //The time before the tiles drop
    int timerCount = 3;
    int[] timers = new int[timerCount * timerCount];

    Color[] colors = new Color[]{
        new Color(196, 1, 1), new Color(196, 66, 1), new Color(249, 219, 44),
        new Color(174, 249, 44), new Color(0, 226, 56), new Color(0, 226, 181),
        new Color(0, 155, 226), new Color(0, 71, 226), new Color(33, 0, 145)
    };

    long startTime;
    long timeSinceLastSecond = 0;

    public TimerTiles(Player[] p, DConsole dc, JConsole[] j) {
        super(p, dc, j);

    }

    @Override
    public void run() {
        for (int t = 0; t < timers.length; t++) {
            timers[t] = random(4, 8);
        }

        for (int i = 0; i < p.length; i++) {
            mp[i] = new MiniPlayer(this.dc, i, this.j[i]);
            mp[i].x = dc.getWidth() / 2;
            mp[i].y = dc.getHeight() / 2;
        }
        startTime = System.currentTimeMillis();

        while (true) {
            dc.setPaint(Color.black);
            dc.fillRect(0, 0, dc.getWidth(), dc.getHeight());
            dc.setPaint(Color.BLACK);

            int curCell = 0;
            for (int y = 0; y < timerCount; y++) {
                for (int x = 0; x < timerCount; x++) {
                    if (timers[curCell] >= 0) {
                        dc.setPaint(colors[timers[curCell]]);
                        dc.fillRect(x * dc.getWidth() / timerCount, y * dc.getHeight() / timerCount, dc.getWidth() / 3, dc.getHeight() / timerCount);
                        dc.setPaint(Color.black);
                        dc.drawRect(x * dc.getWidth() / timerCount, y * dc.getHeight() / timerCount, dc.getWidth() / timerCount, dc.getHeight() / timerCount);
                        dc.setPaint(Color.WHITE);
                        dc.drawString("" + timers[curCell], x * dc.getWidth() / timerCount, y * dc.getHeight() / timerCount);
                    } else {
                        dc.setPaint(Color.black);
                        dc.fillRect(x * dc.getWidth() / timerCount, y * dc.getHeight() / timerCount, dc.getWidth() / timerCount, dc.getHeight() / timerCount);

                        int zeroCount = 0;
                        for (int t = 0; t < timers.length; t++) {
                            if (timers[t] < 0) {
                                zeroCount++;
                            }
                        }
                        if (zeroCount == timers.length) {
                            resetTimers(timerCount + 3);
                        }
                    }
                    curCell++;
                }
            }

            if ((System.currentTimeMillis() - startTime) / 1000 >= timeSinceLastSecond + 1) {
                timeSinceLastSecond = (System.currentTimeMillis() - startTime) / 1000;
                for (int i = 0; i < timers.length; i++) {
                    timers[i]--;
                }
            }

            for (MiniPlayer m : mp) {
                m.move();
                m.draw();
            }
            dc.pause(5);
            dc.redraw();
        }

    }

    @Override
    public void reset() {
    }

    @Override
    public void reward(int player) {
    }

    private int random(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    private void resetTimers(int count) {
        timerCount = count;
        timers = new int[count * count];

        for (int t = 0; t < timers.length; t++) {
            timers[t] = random(4, 8);
        }
    }

    private class MiniPlayer {

        private DConsole dc;
        private JConsole j;
        private double x = 300, y = 420, moveX = 6.5, moveY = 6.5;

        private String[] name = {"mario", "luigi", "yoshi", "peach"};
        private String[] dirName = {"up", "down", "left", "right"};

        private int[] cycleChar = {1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 3, 3, 3, 3};
        private int frame = 0;
        private int sprite;
        private int direction = 3;

        private boolean moving = false;
        boolean alive = true;

        public MiniPlayer(DConsole dc, int s, JConsole j) {
            this.dc = dc;
            this.j = new JConsole(s);
            sprite = s;

            if (sprite == 1) {
                y = 416;
            }
        }

        public void draw() {
            //character animations
            if (moving) {
                this.dc.drawImage("assets/animations/" + this.name[this.sprite] + "/" + this.dirName[this.direction] + "/" + this.cycleChar[this.frame % 16] + ".png", (x), y);
                frame++;
            } else {
                dc.drawImage("assets/animations/" + this.name[this.sprite] + "/" + this.dirName[this.direction] + "/1.png", x, y);
            }
        }

        public void move() {
            moving = false;

            if (dc.isKeyPressed('a')) {
                x -= 2;
            } else if (dc.isKeyPressed('d')) {
                x += 2;
            }
            if (j.analogHorizontal(1) > 70) {
                direction = 3;
                x += moveX;
                moving = true;
            } else if (j.analogHorizontal(1) < 30) {
                direction = 2;
                x -= moveX;
                moving = true;
            }
        }
    }
}
