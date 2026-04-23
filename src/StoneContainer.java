import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public abstract class StoneContainer {
    final double STONE_SIZE = 25;
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
    public void removeStone() {
        if (stones > 0) stones--;
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
    public void drawStones(Graphics2D g2) {
        int numStones = getStones();
        for (int i = 0; i < numStones; i++) {
            double x = getX() + 20 + (i * 4);
            double y = getY() + 20 + (i * 4);
            Ellipse2D stone = new Ellipse2D.Double(x, y, STONE_SIZE, STONE_SIZE);
            g2.draw(stone);
        }
    }
}
