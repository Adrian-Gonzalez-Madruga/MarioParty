package Minigames;

import DLibX.DConsole;
import java.awt.Color;
import java.util.ArrayList;
import jconsole.JConsole;
import party.pkg2.pkg0.Player;

public class SpikeBall extends Minigame {

    //want to add more balls after a time has passed to speed up game
    private int timer = 5;
    private long counter;
    private boolean instruct = true, countdown = true;
    private ArrayList<SpikyBall> s = new ArrayList<>();
    private MiniPlayer[] mp = new MiniPlayer[p.length];
    private boolean[] isDead = new boolean[p.length];

    public SpikeBall(Player[] p, DConsole dc, JConsole[] j) {
        super(p, dc, j);

        for (int i = 0; i < p.length; i++) {
            mp[i] = new MiniPlayer(this.dc, i, this.j[i]);
        }
    }

    @Override
    public void run() {
        dc.setSize(800, 450);
        playing = true;
        instruct = true;

        dc.clear();
        dc.redraw();
        reset();

        //game is running
        while (playing) {
            while (instruct) { //instructions
                dc.drawImage("assets/minigames/spike ball/instructions.png", 0, 0);
                dc.redraw();
                for (int i = 0; i < j.length; i++) {
                    if (j[i].isButtonPressed(1) || dc.isKeyPressed('a')) { //a player presses 'a' and exits the instructions
                        instruct = false;
                        counter = System.currentTimeMillis(); //start the timer
                    }
                }
            }
            for (int i = 0; i < s.size(); i++) {
                s.get(i).move();
                s.get(i).draw();
            }
            countDown(); //starts the countdown

            for (int i = 0; i < p.length; i++) {
                if (!isDead[i]) {
                    if (mp[i].collision(s) || isDead[i]) {
                        isDead[i] = true;
                    } else {
                        mp[i].move();
                        mp[i].draw();
                    }
                }

                if (playerCount() == 1) {
                    reward(i);
                    playing = false;
                }
            }

            this.dc.redraw();
            this.dc.clear();
            dc.pause(30);
        }
        this.dc.setSize(960, 640);
    }

    public int playerCount() {
        int counter = 0;

        for (int i = 0; i < p.length; i++) {
            if (isDead[i]) {

            } else {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public void reward(int player) {
        p[player].scorePlus(10); // does not work. keeps throw NullPointerException
    }

    @Override
    public void reset() {
        s.clear();
        for (int i = 0; i < p.length; i++) {
            mp[i] = new MiniPlayer(this.dc, i, this.j[i]);
            isDead[i] = false;
            timer = 5;
            playing = true;
            instruct = true;
        }
        SpikyBall temp;
        s.add(temp = new SpikyBall(dc));
    }

    public void countDown() {
        while (timer > 0) {

            dc.drawImage("assets/number blocks/" + timer + ".png", 380, 200);

            //keep track of the time elapsed
            if (counter + 500 - System.currentTimeMillis() < 0) {
                timer--;
                counter = System.currentTimeMillis();
            }

            dc.redraw();
            dc.clear();
        }
    }

    private class SpikyBall {

        private DConsole dc;
        private long counter, lastIncrease;
        private double x = (int) (Math.random() * 600) + 150, y = 0, moveX = -10, moveY = 10, speedX = 10, speedY = 10;

        public SpikyBall(DConsole dc) {
            this.dc = dc;
        }

        public void draw() {
            this.dc.drawImage("assets/minigames/spike ball/spike ball.png", x, y);
        }

        public void move() {
            if (lastIncrease <= 0) {
                lastIncrease = System.currentTimeMillis();
            }

            if (x <= 0) {
                moveX = speedX;
            } else if (x >= 700) {
                moveX = -(speedX);
            }
            if (y <= 0) {
                moveY = speedY;
            } else if (y >= 350) {
                moveY = -(speedY);
            }

            x += moveX;
            y += moveY;

            if (lastIncrease - System.currentTimeMillis() + 5000 <= 0) {
                lastIncrease = System.currentTimeMillis();
                speedX += Math.random();
                speedY += Math.random();
            }

            /*dc.setPaint(Color.BLACK);
             dc.drawString(moveX, 10, 10);
             dc.drawString(moveY, 10, 50);
             dc.drawString(speedX, 10, 90);
             dc.drawString(speedY, 10, 130);
             dc.drawString(lastIncrease - System.currentTimeMillis() - 5000, 10, 170);*/
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public void reset() {
            x = 700;
            y = 0;
            moveX = -10;
            moveY = 10;
            speedX = 10;
            speedY = 10;
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
            
            //testing purpose only
            /*
            if (dc.isKeyPressed('a')) {
                x -= 8;
            } else if (dc.isKeyPressed('d')) {
                x += 8;
            } */

            if (j.analogHorizontal(1) > 70 && (x += moveX) <= dc.getWidth() - 40) {
                direction = 3;
                x += moveX;
                moving = true;
            } else if (j.analogHorizontal(1) < 30 && (x -= moveX) >= 0) {
                direction = 2;
                x -= moveX;
                moving = true;
            }
            
            if (x < 0) {
                x = 0;
            }
            if (x > 780) {
                x = 780;
            }
        }

        public boolean collision(ArrayList<SpikyBall> s) {
            for (int i = 0; i < s.size(); i++) {
                if (s.get(i).getX() + 100 >= x && s.get(i).getX() <= x + 20 && s.get(i).getY() + 100 >= y && s.get(i).getY() <= 450) {
                    SpikyBall temp;
                    s.add(temp = new SpikyBall(dc));
                    return true;
                }
            }
            return false;
        }
    }

}
