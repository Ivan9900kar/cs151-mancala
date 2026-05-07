import java.awt.*;

public class Mancala extends StoneContainer {
    BoardStyle strategy;
    private int row; 

    public Mancala() {
        super();
    }
    public Mancala(int stones) {
        super(stones);
    }

    public void setRow(int row) { 
        this.row = row;
    }
    public void setStrategy(BoardStyle strategy) {
        this.strategy = strategy;
    }
    public void draw(Graphics2D g2) {
        // draw mancala using strategy pattern
        strategy.drawMancala(g2, getX(), getY(), row);
    }
}
