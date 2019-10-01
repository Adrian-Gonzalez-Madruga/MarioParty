package party.pkg2.pkg0;

import DLibX.DConsole;

/*
whenever you want to make a new tile after making the class and extension go to board.
there you will want to add it to the board wherever you wish then when it inits the tiles add your's similarly to the other tiles
remember to select a number for that tile that the board will reference. as long as it is not 0 or 20
*/

public abstract class Tile { //tile on the board
    
    int x, y; 
   
    
   public Tile(int x, int y){
       this.x = x; 
       this.y = y; 
   } 
   
   public abstract void draw(DConsole dc);
   
   public abstract void reward(Player p);
   
    public abstract void isOver(Player p);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
   
    
}
