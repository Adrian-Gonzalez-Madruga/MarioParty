package party.pkg2.pkg0;

import DLibX.DConsole;

public class RedTile extends Tile {//#2 -3 coins

    public RedTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(DConsole dc) {
        dc.drawImage("assets/board/2.png", this.x * 32, this.y * 32);
    }

    @Override
    public void reward(Player p) { // -3 coins unless it would reach <= 0. if so make 0
        if ((p.getCoin() - 3) <= 0) {
            p.setCoin(0);
        } else {
            p.scorePlus(-3);
        }
    }

    @Override
    public void isOver(Player p) { // -1 on the roll so it proceeds to go down
        p.setRoll(p.returnRoll() - 1);
    }

}
