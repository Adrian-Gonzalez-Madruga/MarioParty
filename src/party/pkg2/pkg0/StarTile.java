package party.pkg2.pkg0;

import DLibX.DConsole;

public class StarTile extends Tile { // give stars if they pass with 20 coins

    public StarTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(DConsole dc) { // draw the tile and the star with animation above it
        dc.drawImage("assets/board/star.png", x * 32, y * 32);
    }

    @Override
    public void reward(Player p) { //this tile shouldnt be landed on
        System.out.println("error. Star Tile should be passed");
    }

    @Override
    public void isOver(Player p) { // when over star tile pay 20 coins to exchange for a star
        p.acceptStar();
    }
    
}