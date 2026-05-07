/**
 * Title: CS 151 Mancala Project
 * Authors: Anthony Ryabov, Darren Vu, Isaiah Mak
 */

import java.awt.*;

/**
 * Class representing a pit on the board.
 * Holds the stones that are currently in the pit (from StoneContainer), and its position on the board.
 */
public class Pit extends StoneContainer {
    BoardStyle strategy;
    private int row;
    private int col;

    /**
     * Default constructor of a pit.
     */
    public Pit() {
        this(0);
    }

    /**
     * Constructs a pit that will start with a specified amount of stones inside.
     * @param stones the stones that the pit will start out with
     */
    public Pit(int stones) {
        super(stones);
        this.row = -1;
        this.col = -1;
    }

    /**
     * Gets the row of the pit.
     * @return the row of the pit (0 for player A, 1 for player B)
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Sets the row of the pit.
     * @param row the specified row of the pit (0 for player A, 1 for player B)
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Returns the column of the pit.
     * @return the column of the pit
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Sets the column of the pit.
     * @param col the specified column of the pit
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Sets the strategy for drawing the pit.
     * @param strategy the strategy to draw with
     */
    public void setStrategy(BoardStyle strategy) {
        this.strategy = strategy;
    }

    /**
     * Takes all of the stones out of the pit and returns the taken amount.
     * @return the number of stones that were inside of the pit
     */
    public int takeStones() {
        int ret = getStones();
        setStones(0);
        return ret;
    }

    /**
     * Draws the pit using the strategy pattern.
     * @param g2 the relevant Graphics2D object
     */
    public void draw(Graphics2D g2) {
        // draw pit using strategy pattern
        strategy.drawPit(g2, getX(), getY(), row, col);
    }

    /**
     * Checks if a given point (from a mouse click) is within the bounds of a pit.
     * @param p the point to check
     * @return true if the point is inside the pit, false otherwise
     */
    public boolean contains(Point p) {
        double x = getX();
        double y = getY();
        return p.x >= x && p.x <= x + 100 && p.y >= y && p.y <= y + 100;
    }
}
