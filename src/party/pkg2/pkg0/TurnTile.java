package party.pkg2.pkg0;

import DLibX.DConsole;

public class TurnTile extends Tile { // make the player turn either forced or chosen depending on amount of directions

    private int slot;

    private int dirnum;
    private String dirPic[] = {"up", "down", "left", "right"};
    private int turnX[] = {1, 5, 11, 18, 11, 18, 5, 11, 1, 5, 7, 11, 18, 1, 7, 18};
    private int turnY[] = {1, 1, 1, 1, 6, 6, 9, 9, 12, 12, 14, 14, 14, 17, 17, 17};
    private boolean dir[][]
            = {{false, false, false, true},
            {false, false, false, true},
            {false, true, false, true},
            {false, true, false, false},
            {false, true, false, true},
            {false, true, false, false},
            {true, true, false, false},
            {false, false, true, false},
            {true, false, false, false},
            {false, false, true, false},
            {false, false, false, true},
            {true, false, false, false},
            {false, true, true, false},
            {true, false, false, false},
            {true, false, true, false},
            {false, false, true, false}};

    public TurnTile(int x, int y) {
        super(x, y);
        for (int t = 0; t < dir.length; t++) { // figure which turning tile this one is
            if (turnX[t] == x && turnY[t] == y) {
                slot = t;
            }
        }
        for (int e = 0; e < 4; e++) { // find how many directions we can pick from
            if (dir[slot][e]) {
                dirnum++;
            }
        }
    }

    @Override
    public void draw(DConsole dc) { // draw the blank then each arrow
        dc.drawImage("assets/board/generic.png", x * 32, y * 32);
        for (int i = 0; i < dir[0].length; i++) {
            if (dir[slot][i]) { // draw the arrows of where u can actually go
                dc.drawImage("assets/board/" + dirPic[i] + ".png", x * 32, y * 32);
            }
        }
    }

    @Override
    public void reward(Player p) { //should never land on this tile
        System.out.println("error. Turn Tile should be passed");
    }

    @Override
    public void isOver(Player p) { // go to player to change the direction for that one player
        p.chooseDirection(slot, dirnum, dir[slot]);
    }

}
