package party.pkg2.pkg0;

import DLibX.DConsole;

public class MysteryTile extends Tile {

    public MysteryTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(DConsole dc) {
        dc.drawImage("assets/board/mystery.png", x * 32, y * 32);
    }

    @Override
    public void reward(Player p) {
        p.selectMystery(p);
    }

    @Override
    public void isOver(Player p) {
        p.setRoll(p.returnRoll() - 1);
    }
    
}
