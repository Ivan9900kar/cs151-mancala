import java.awt.*;

/**
 * Abstract class representing a container for stones (extends to pit and mancala). Holds a number of stones as well as its position on the board.
 */
public abstract class StoneContainer {
    private int stones;
    private double x;
    private double y;
    private String name;
    public StoneContainer() {
        this(0);
    }
    public StoneContainer(int stones) {
        this.stones = stones;
        this.x = 0;
        this.y = 0;
    }
    public int getStones() {
        return this.stones;
    }
    public void setStones(int stones) {
        this.stones = stones;
    }
    public void addStone() {
        stones++;
    }
    public void addStones(int stones) {
        setStones(getStones() + stones);
    }
    public void removeStone() {
        stones--;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public abstract void draw(Graphics2D g2);
}
