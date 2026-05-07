/**
 * Title: CS 151 Mancala Project
 * Authors: Anthony Ryabov, Darren Vu, Isaiah Mak
 */

import java.awt.*;

/**
 * Abstract class representing a container for stones (extends to pit and mancala). Holds a number of stones as well as its position on the board.
 */
public abstract class StoneContainer {
    private int stones;
    private double x;
    private double y;
    private String name;

    /**
     * StoneContainer default constructor.
     */
    public StoneContainer() {
        this(0);
    }
    /**
     * StoneContainer constructor with specified starting amount of stones.
     * @param stones the number of stones to start out with
     */
    public StoneContainer(int stones) {
        this.stones = stones;
        this.x = 0;
        this.y = 0;
    }
    /**
     * Gets the number of stones in the container.
     * @return the number of stones in the container
     */
    public int getStones() {
        return this.stones;
    }
    /**
     * Sets the number of stones in the container.
     * @param stones the specified number of stones
     */
    public void setStones(int stones) {
        this.stones = stones;
    }
    /**
     * Increments the number of stones in the container by 1.
     */
    public void addStone() {
        stones++;
    }
    /**
     * Adds to the number of stones in the container by a specified amount.
     * @param stones the number of stones to add
     */
    public void addStones(int stones) {
        setStones(getStones() + stones);
    }
    /**
     * Decrements the number of stones in the container by 1.
     */
    public void removeStone() {
        stones--;
    }
    /**
     * Gets the name of the container (such as "A1" or "Mancala A").
     * @return the name of the container
     */
    public String getName() {
        return this.name;
    }
    /**
     * Sets the name of the container.
     * @param name the name to set the container to
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets the x coordinate of the container.
     * @return the x coordinate of the container
     */
    public double getX() {
        return this.x;
    }
    /**
     * Gets the y coordinate of the container.
     * @return the y coordinate of the container
     */
    public double getY() {
        return this.y;
    }
    /**
     * Sets the x coordinate of the container.
     * @param x the x coordinate to set the container to
     */
    public void setX(double x) {
        this.x = x;
    }
    /**
     * Sets the y coordinate of the container.
     * @param y the y coordinate to set the container to
     */
    public void setY(double y) {
        this.y = y;
    }
    /**
     * Abstract method for drawing the container using the strategy pattern.
     * @param g2 the relevant Graphics2D object
     */
    public abstract void draw(Graphics2D g2);
}
