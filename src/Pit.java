import java.awt.*;

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
    public int getRow() {
        return this.row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getCol() {
        return this.col;
    }
    public void setCol(int col) {
        this.col = col;
    }
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
    public void draw(Graphics2D g2) {
        strategy.drawPit(g2, getX(), getY(), row, col);
    }

    public boolean contains(Point p) {
        double x = getX();
        double y = getY();
        return p.x >= x && p.x <= x + 100 && p.y >= y && p.y <= y + 100;
    }
}
