package party.pkg2.pkg0;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.Font;

public class Board {

    private DConsole dc;
    private Tile[] t;
    private Player[] p;

    private int map[][]
            = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 9, 1, 1, 2, 9, 1, 4, 2, 1, 1, 9, 2, 1, 1, 2, 4, 1, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 4, 0, 0, 0, 4, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 9, 1, 2, 1, 1, 1, 4, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 3, 0, 0, 0, 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 9, 4, 1, 1, 2, 1, 9, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 9, 1, 4, 1, 9, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 0, 0, 0, 0, 0, 9, 1, 2, 4, 9, 1, 1, 2, 4, 1, 1, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 4, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 9, 1, 2, 1, 1, 2, 9, 1, 1, 1, 1, 4, 2, 1, 4, 1, 2, 9, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    private String place[] = {"1st", "2nd", "3rd", "4th"};
    private String characters[] = {"mario", "luigi", "yoshi", "peach"};
    private int characterNums[] = {0, 1, 2, 3};
    private int tileNum = 0, starFrame = 1, coinFrame = 1;
    private int[] coins = new int[4], stars = new int[4];
    private boolean nextIsChosen;
    private int chosenValue;
    
    private int turns = 20; // how many turns there are

    private Mystery m = new Mystery();

    public void start(DConsole dc, Tile[] t, Player[] p) {
        this.dc = dc;
        this.t = t;
        this.p = p;
        for (int c = 0; c < map.length; c++) { // initalize  each of the tiles on the map
            for (int v = 0; v < map[0].length; v++) {
                if (map[c][v] == 1) {
                    t[tileNum] = new BlueTile(v, c);
                    tileNum++;
                } else if (map[c][v] == 2) {
                    t[tileNum] = new RedTile(v, c);
                    tileNum++;
                } else if (map[c][v] == 3) {
                    t[tileNum] = new BankTile(v, c);
                    tileNum++;
                } else if (map[c][v] == 4) {
                    t[tileNum] = new MysteryTile(v, c);
                    tileNum++;
                } else if (map[c][v] == 9) {
                    t[tileNum] = new TurnTile(v, c);
                    tileNum++;
                }
            }
        }
        newStar(1);
    }

    public void draw() { // draw the board and tiles along with score cards displaying the scores
        tileNum = 0;
        for (int c = 0; c < map.length; c++) {
            for (int v = 0; v < map[0].length; v++) { // draw one of the two images
                if (map[c][v] == 20 || map[c][v] == 0) { // go through the tile class
                    dc.drawImage("assets/board/" + map[c][v] + ".png", v * 32, c * 32);
                } else { // go through the tile class
                    t[tileNum].draw(dc);
                    tileNum++;
                }
            }
        }

        dc.setFont(new Font("Cooper Black", Font.BOLD, 15));

        characterNums = getPlaces();

        //draws everything involving the player scores
        for (int i = 0; i < p.length; i++) {
            coins[i] = p[i].getCoin();
            stars[i] = p[i].getStar();

            dc.setPaint(Color.WHITE);
            dc.drawImage("assets/misc/score card.png", 700, 100 + (100 * i));
            dc.drawImage("assets/characters/icons/" + characters[i] + ".png", 716, 112 + (100 * i));
            dc.drawImage("assets/animations/star/" + starFrame + ".png", 781, 153 + (100 * i));
            dc.drawImage("assets/animations/coin/" + coinFrame + ".png", 786, 118 + (100 * i));
            dc.drawString(coins[i], 810, 115 + (100 * i));
            dc.drawString(stars[i], 810, 155 + (100 * i));
        }

        for (int r = 0; r < p.length; r++) {
            dc.drawImage("assets/misc/" + (r + 1) + ".png", 849, 110 + (100 * characterNums[r]));
        }

        starFrame++;
        coinFrame++;

        if (starFrame > 8) { //frames of the stars
            starFrame = 1;
        }
        if (coinFrame > 4) { //frames of the coin
            coinFrame = 1;
        }

        dc.setFont(new Font("Cooper Black", Font.BOLD, 25)); // draw turns left text
        dc.drawString(turns + "Turns Left", 710, 50);
    }

    public void drawEndScreen() {
        //dc.setSize();
        dc.setPaint(Color.BLACK);
        dc.fillRect(0, 0, dc.getWidth(), dc.getHeight());
        this.dc.setFont(new Font("Cooper Black", Font.BOLD, 15));

        characterNums = getPlaces();

        //draws everything involving the player scores
        for (int i = 0; i < p.length; i++) {
            coins[i] = p[i].getCoin();
            stars[i] = p[i].getStar();

            dc.setPaint(Color.WHITE);
            dc.drawImage("assets/misc/score card.png", 700, 100 + (100 * i));
            dc.drawImage("assets/characters/icons/" + characters[characterNums[i]] + ".png", 716, 112 + (100 * i));
            dc.drawImage("assets/animations/star/" + starFrame + ".png", 781, 153 + (100 * i));
            dc.drawImage("assets/animations/coin/" + coinFrame + ".png", 786, 118 + (100 * i));
            dc.drawString(coins[characterNums[i]], 810, 115 + (100 * i));
            dc.drawString(stars[characterNums[i]], 810, 155 + (100 * i));
        }

        for (int r = 0; r < p.length; r++) {
            dc.drawImage("assets/misc/" + (r + 1) + ".png", 849, 110 + (100 * r));
        }

        starFrame++;
        coinFrame++;

        if (starFrame > 8) { //frames of the stars
            starFrame = 1;
        }
        if (coinFrame > 4) { //frames of the coin
            coinFrame = 1;
        }
    }

    public void newStar(int oldLocal) {
        t[oldLocal] = new BlueTile(t[oldLocal].getX(), t[oldLocal].getY()); // make the old star tile a blue tile with same co-ordinates
        int temp = 0;
        while (true) { // if this was a turn tile keep trying until it isnt
            temp = (int) (Math.random() * t.length);
            if (t[temp].getClass().getName().contains("BlueTile") || t[temp].getClass().getName().contains("RedTile")) {
                break;
            }
        }
        t[temp] = new StarTile(t[temp].getX(), t[temp].getY()); // set the new star at same location
    }

    public int[] getPlaces() { // get who is in 1st , 2nd ... as an array
        int[] characterNums = {0, 1, 2, 3};
        int[] places = new int[p.length];
        int temp, cTemp;

        for (int i = 0; i < p.length; i++) {
            places[i] = p[i].getScore(); //grabs all the scores
        }
        for (int r = 0; r < 4; r++) {
            for (int i = 0; i < p.length - 1; i++) {
                if (places[i] < places[i + 1]) { //try to organize the scores
                    temp = places[i + 1];
                    cTemp = characterNums[i + 1];

                    places[i + 1] = places[i];
                    characterNums[i + 1] = characterNums[i];

                    characterNums[i] = cTemp;
                    places[i] = temp;
                }
            }
        }
        return characterNums;
    }

    public int getValue(int x, int y) { //get the value of the specified location
        return map[y][x];
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public void setValue(int x, int y, int value) { // set a value at the specified location
        map[y][x] = value;
    }

    public void selectMystery(Player c) { // select the mystery from the mystery tile
        String[] s = {"Position_Swap", "Coin_Swap", "Halve_Coins", "Double_Coins", "All_To_Zero", "Back_To_Start", "Equal_Coins", "Two_More_Turns", "Tile_Color_Switch", "Two_Rolls"}; // put mystery tile actions in here identical to the method name. note methods can't have parameters
        int chosen = (int) (Math.random() * s.length);
        if(nextIsChosen) {
            chosen = chosenValue;
        }
        dc.redraw();
        c.waitForResponse(s[chosen]);
        java.lang.reflect.Method method = null; // link the method to the right class. start null
        try {
            method = m.getClass().getMethod(s[chosen], Player.class); // of the string chosen get that method i the mystery class
        } catch (Exception e) {
        }
        try {
            method.invoke(m, c); // run that method
        } catch (Exception e) {
        }
    }

    private class Mystery { //class called in board for the mysteryTile. each method is an option

        public void Position_Swap(Player c) { // swap the positions of all players on the map
            int[] xs = new int[p.length];
            int[] ys = new int[p.length];
            int[] ds = new int[p.length];
            int added = (int) (Math.random() * (p.length - 1)) + 1;
            for (int i = added; i < (p.length + added); i++) { //randomize each players x and y with other players x and y
                xs[i % p.length] = p[i - added].getX();
                ys[i % p.length] = p[i - added].getY();
                ds[i % p.length] = p[i - added].getDirection();
            }
            for (int i = 0; i < p.length; i++) { //then set them appropriatly on the map
                p[i].setX(xs[i]);
                p[i].setY(ys[i]);
                p[i].setDirection(ds[i]);
            }
        }

        public void Coin_Swap(Player c) {

            int[] cs = new int[p.length];
            int added = (int) (Math.random() * 3) + 1;
            for (int i = added; i < (p.length + added); i++) { //randomize each players coins with other players coins
                cs[i % p.length] = p[i - added].getCoin();
            }
            for (int i = 0; i < p.length; i++) { //set each players coins appropriatly
                p[i].setCoin(cs[i]);
            }
        }

        public void Halve_Coins(Player c) { //make the player's coin count half of what it is
            c.setCoin(c.getCoin() / 2);
        }

       /* public void Accept_The_Star(Player c) {// make the player go straight to the star and ask to accept //bugged and makes a new star space if accepted. not enough time to de-bug         
            c.acceptStar();
        }*/

        public void Double_Coins(Player c) { // double the player's coins
            c.setCoin(c.getCoin() * 2);
        }

        public void All_To_Zero(Player c) { // put everyone's coins to zero
            for (int i = 0; i < p.length; i++) {
                p[i].setCoin(0);
            }
        }

        public void Back_To_Start(Player c) { // send the player back to the starting position
            c.setX(19);
            c.setY(17);
            c.setDirection(2);
        }

        public void Equal_Coins(Player c) { // make everyone's coins all coins /4
            int temp = 0;
            for (int i = 0; i < p.length; i++) {
                temp += p[i].getCoin();
            }
            temp /= p.length;
            for (int i = 0; i < p.length; i++) {
                p[i].setCoin(temp);
            }
        }

        public void Two_More_Turns(Player c) { // add 2 more turns to the game
            turns += 2;
        }
        
        public void Tile_Color_Switch(Player c) { // switch red and blue tiles around
            for(int i = 0; i < t.length; i++) {
                int x = t[i].getX();
                int y = t[i].getY();
                if(t[i].getClass().getName().contains("BlueTile")) { // if blue make red
                    t[i] = new RedTile(x, y);
                } else if(t[i].getClass().getName().contains("RedTile")) { // if red make blue
                    t[i] = new BlueTile(x, y);
                }           
            }
            nextIsChosen = !nextIsChosen;
            chosenValue = 9;
        }
        
        public void Two_Rolls(Player c) { // add a number between 1-10 t0 next roll
            c.addRollPlus((int)(Math.random() * 10) + 1);
        }

    }//mystery class end

} //board class end
