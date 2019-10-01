package party.pkg2.pkg0;

import DLibX.DConsole;

public class BlueTile extends Tile { // #1 +3 coins
    
    public BlueTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(DConsole dc) { // draw the tile in the in its location
        dc.drawImage("assets/board/1.png", this.x * 32, this.y * 32);
    }

    @Override
    public void reward(Player p) { // +3 coins
        p.scorePlus(6);
    }

    @Override
    public void isOver(Player p) { // -1 on the roll so it proceeds to go down
        p.setRoll(p.returnRoll() - 1);
    }

}
