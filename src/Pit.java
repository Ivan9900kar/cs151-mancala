import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Pit extends StoneContainer {
    final double ICON_SIZE = 100;
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
        super.draw(g2);
        Ellipse2D pit = new Ellipse2D.Double(getX(), getY(), ICON_SIZE, ICON_SIZE);
        g2.draw(pit);

        g2.setFont(new Font("SansSerif", Font.BOLD, 18));

        char letter = (char) ('A' + row);
        String name = "";
        name += letter;
        name += col + 1;
        g2.drawString(name, (int) getX() + 34, (int) getY());
    }
}
