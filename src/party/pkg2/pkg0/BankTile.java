package party.pkg2.pkg0;

import DLibX.DConsole;

public class BankTile extends Tile {

    public BankTile(int x, int y) {
        super(x, y);
    }
    private int storedCoins = 20;

    @Override
    public void draw(DConsole dc) {
        dc.drawImage("assets/board/bank.png", x * 32, y * 32);
    }

    @Override
    public void reward(Player p) {
        p.setCoin(p.getCoin() + storedCoins);
        storedCoins = 0;
    }

    @Override
    public void isOver(Player p) {
        p.setRoll(p.returnRoll() - 1);
        if (p.getCoin() - 4 <= 0) {
            storedCoins += p.getCoin();
            p.setCoin(0);
            return;
        }
        p.setCoin(p.getCoin() - 4);
        storedCoins += 4;
    }

}
