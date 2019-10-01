package party.pkg2.pkg0;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.Font;
import jconsole.JConsole;

public class Player {

    private Board f;
    private JConsole j;
    private DConsole dc;
    private Player[] p;

    private int sprite;
    private long startTime = 9;
    private long elapsedTime;
    private int previousTime = 0;
    private int printRoll = 1;
    private int x = 19;
    private int y = 17;
    private int moveX[] = {0, 0, -1, 1};
    private int moveY[] = {-1, 1, 0, 0};
    private int direction = 2;
    private int coin = 10;
    private int star = 0;
    private int roll = 1;
    private int rollPlus = 0;
    private long baseTime;
    private int number = 2;
    private boolean minus = false;

    private String[] name = {"mario", "luigi", "yoshi", "peach"};
    private String[] dirName = {"up", "down", "left", "right"};
    private int[] cycleChar = {1, 2, 1, 3};
    private int frame = 0, starFrame = 1, coinFrame = 1;

    public void start(Board f, JConsole j, DConsole dc, int sprite, Player[] p) { // init
        this.f = f;
        this.j = j;
        this.dc = dc;
        this.p = p;
        this.sprite = sprite;
        startTimer();
    }

    public void draw() {
        //character animations
        this.dc.drawImage("assets/animations/" + this.name[this.sprite] + "/" + this.dirName[this.direction] + "/" + this.cycleChar[this.frame % 4] + ".png", (this.x * 32) + 7, this.y * 32);
    }

    public long startTimer() {//used for die rolling
        startTime = System.nanoTime();
        baseTime = startTime;
        return startTime;
    }

    public long baseTimer() {//used for die rolling
        return baseTime;
    }

    public long elapsedTimer() {
        elapsedTime = ((System.nanoTime() - baseTimer()) / 100000000) % 10;//seconds passed
        return elapsedTime;
    }

    public void roll() { // when clicking the appropriate button roll the dice

        //while (dc.isKeyPressed('a')); // sub this in when not using controllers
        while (j.isButtonPressed(1)); // this line is to stop from holding button 1. aka must be released
        while (true) {
            dc.clear();
            f.draw();
            for (int r = 0; r < p.length; r++) { // draw all 4 players
                p[r].draw();
            }
            frame();

            if (previousTime == 8) {//previousTime == time value at previous code run
                previousTime = 1;   //if the time at previous run was 8, set time for next run to 1.
            }
            if (elapsedTimer() >= previousTime + 1) {//if one or more seconds have passed
                if (elapsedTime == previousTime + 1) {//if ONLY one second has passed

                    //variable number is 2 values used according to picture files. This represents frame numbers 
                    //alternating reduction and incrementing of an integer variable
                    previousTime += 1;//increase saved time value at previous code run by 1
                    if (minus == true) {//if the code needs to decrease variable number
                        minus = false;//set boolean minus to false so this code doesn't run twice in a row
                        number--;// reduce variable number by 1
                    } else {
                        minus = true;//set boolean minus to true so this code doesn't run twice
                        number++;//increase variable number by 1
                    }

                    this.roll = (int) (Math.random() * 10) + 1;//pick a number between 1 and 10
                    printRoll = this.roll;

                    dc.drawImage("assets/animations/die/DieRoll" + number + ".png", (266), (102));//draw frame number x at specific coordinate
                    dc.redraw();
                }
            }
            if (j.isButtonPressed(1) && roll != 0 || dc.isKeyPressed(' ') && roll != 0 ) {
                roll += rollPlus;
                rollPlus = 0;
                break;
            }
            /*if (dc.isKeyPressed('a') && roll != 0) {
             break;
             }*/
            dc.drawImage("assets/animations/die/DieRoll" + number + ".png", (266), (102));
            dc.redraw();
            dc.pause(100); // roll speed
        }
    }

    public int returnRoll() { // return roll
        return this.roll;
    }

    public void setRoll(int roll) { // set the roll
        this.roll = roll;
    }
    
    public void addRollPlus(int rp) {
        rollPlus = rp;
    }

    public int returnPrintRoll() {
        return printRoll;
    }

    public void drawRoll() {
        dc.drawImage("assets/animations/die/Die" + roll + ".png", (x * 32) + 5, (y * 32) - 41);

    }

    public void move() { // only move the player foreward
        x += moveX[direction];
        y += moveY[direction];
    }

    public void chooseDirection(int slot, int dirnum, boolean[] dir) { // called in turning tile and to change the direction
        if (dirnum == 1) { //if you dont have to choose a direction (forced)
            for (int e = 0; e < 4; e++) {
                if (dir[e]) {
                    direction = e;
                }
            }
        } else {
            boolean newdir = false;
            while (j.isButtonPressed(1));
            while (!newdir) { // until we pick a new direction
                dc.clear();
                f.draw(); // redraw everything since this is a loop
                for (int i = 0; i < p.length; i++) {
                    p[i].draw();
                }
                if (j.analogVertical(1) < 30 && dir[0] && j.isButtonPressed(1)) { // controller analogs now used
                    direction = 0;
                    newdir = true;
                } else if (j.analogVertical(1) > 70 && dir[1] && j.isButtonPressed(1)) {
                    direction = 1;
                    newdir = true;
                }
                if (j.analogHorizontal(1) < 30 && dir[2] && j.isButtonPressed(1)) {
                    direction = 2;
                    newdir = true;
                } else if (j.analogHorizontal(1) > 70 && dir[3] && j.isButtonPressed(1)) {
                    direction = 3;
                    newdir = true;
                }
                drawRoll();
                dc.redraw();
                dc.pause(100);
            }
        }
    }

    public int getTile() { // search to which tile the player is on
        int temp = 0;
        for (int c = 0; c < 20; c++) {
            for (int v = 0; v < 20; v++) {
                if (x == v && y == c) {
                    return temp;
                }
                if (f.getValue(v, c) != 20 && f.getValue(v, c) != 0) {
                    temp++;
                }
            }
        }
        return -1;
    }

    public void acceptStar() { // called when isOver a star tile
        boolean choosing = true;
        boolean animation = true;
        boolean flying = false;
        int tempStarX = 394;
        int tempStarY = 248;
        starFrame = 1;
        if (getCoin() < 20) { // less than 20 coins ignore
            return;
        }
        while (j.isButtonPressed(1));//wait till the 'A' button is not pressed incase of roll
        while (choosing) { // redraw everything since this is a loop until we click a button
            dc.clear();
            f.draw();
            for (int i = 0; i < p.length; i++) {
                p[i].draw();
            }
            dc.drawImage("assets/textboxes/frame.png", 7 * 32, 6 * 32); // textbox frame
            dc.setPaint(Color.WHITE);
            dc.setFont(new Font("Cooper Black", Font.BOLD, 15));
            dc.drawString("Exchange 20 Coins For A Star?", (8 * 32) + 20, (6 * 32) + 15);
            dc.drawImage("assets/animations/star/star move/" + starFrame + ".png", tempStarX, tempStarY);
            if (starFrame == 12) { // star animations limiter
                animation = false;
            } else if (starFrame == 3) {
                animation = true;
            }
            if (animation) {
                starFrame++;
            } else {
                starFrame--;
            }
            dc.setFont(new Font("Cooper Black", Font.BOLD, 30)); // yes or no text
            dc.setPaint(Color.GREEN);
            dc.drawString("YES", (7 * 32) + 35, 300);
            dc.setPaint(Color.RED);
            dc.drawString("NO", (15 * 32) + 35, 300);
            if (j.isButtonPressed(1)) { // if 'A' button let the star fly off
                flying = true;
            } else if (j.isButtonPressed(2)) { // if 'B' button end the method
                return;
            }
            if (flying) {
                tempStarY -= 15;
            }
            if (tempStarY <= -30) {
                scorePlus(-20, 1);
                f.newStar(getTile());
                choosing = false;
            }
            dc.redraw();
            dc.pause(100);
        }
    }

    public void selectMystery(Player p) { // stupid wrap around so I don't have to link board to tiles too. ~adrian
        f.selectMystery(p);
    }

    public int getCoin() { //get coins
        return coin;
    }

    public void setCoin(int coin) { //set coin amount
        this.coin = coin;
    }

    public int getStar() { //get stars
        return star;
    }

    public int getScore() { // get the score.
        return (star * 1000) + coin;
    }

    public void scorePlus(int coin) { // add coins
        this.coin += coin;
    }

    public void scorePlus(int coin, int star) { // add coins and star
        this.star += star;
        this.coin += coin;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSprite() {
        return sprite;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public void waitForResponse(String s) { // to mystery tile waiting for the player to press 'A'
        while(j.isButtonPressed(1) || dc.isKeyPressed(' '));
        while (!j.isButtonPressed(1) && !dc.isKeyPressed(' ')) {
            f.draw();
            for (int i = 0; i < p.length; i++) {
                p[i].draw();
            }
            dc.drawImage("assets/textboxes/MysteryFrame.png", 6 * 32, 8 * 32);
            dc.setOrigin(DConsole.ORIGIN_CENTER);
            dc.setFont(new Font("Cooper Black", Font.BOLD, 20));
            dc.setPaint(Color.WHITE);
            dc.drawString(s, (6 * 32) + 128, (8 * 32) + 48);
            dc.setOrigin(DConsole.ORIGIN_TOP_LEFT);
            dc.redraw();
            dc.pause(100);
        }
    }

    public void frame() { // increase the frame signalling a different animation for the characters and scorecards
        frame++;
    }

}
