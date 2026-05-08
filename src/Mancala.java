/**
 * Title: CS 151 Mancala Project
 * Authors: Anthony Ryabov, Darren Vu, Isaiah Mak
 */

import java.awt.*;

/**
 * Class representing the Mancala for each player.
 * Holds the stones that are currently in the mancala (from StoneContainer) and its position on the board.
 */
public class Mancala extends StoneContainer {
    BoardStyle strategy;
    private int row; 

    /**
     * Mancala default constructor.
     */
    public Mancala() {
        this(0);
        //this.strategy = new Style1();
    }

    /**
     * Mancala constructor with specified starting amount of stones.
     * @param stones the number of stones to start out with
     */
    public Mancala(int stones) {
        super(stones);
        this.row = -1;
    }

    /**
     * Sets the row of the mancala.
     * @param row the row of the mancala (0 for player A, 1 for player B)
     */
    public void setRow(int row) { 
        this.row = row;
    }

    /**
     * Sets the strategy for drawing the mancala.
     * @param strategy the strategy to draw with
     */
    public void setStrategy(BoardStyle strategy) {
        this.strategy = strategy;
    }

    /**
     * Draws the mancala using the strategy pattern.
     * @param g2 the relevant Graphics2D object
     */
    public void draw(Graphics2D g2) {
        // draw mancala using strategy pattern
        strategy.drawMancala(g2, getX(), getY(), row);
    }
}
