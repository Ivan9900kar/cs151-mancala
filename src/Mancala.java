import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class Mancala extends StoneContainer {
    final double ICON_WIDTH = 100;
    final double ICON_HEIGHT = 450;
    final double ICON_SEMI = 100;
    public Mancala() {
        super();
    }
    public Mancala(int stones) {
        super(stones);
    }
    public void addStones(int stones) {
        setStones(getStones() + stones);
    }
    public void draw(Graphics2D g2) {
        RoundRectangle2D mancala = new RoundRectangle2D.Double(getX(), getY(), ICON_WIDTH, ICON_HEIGHT, ICON_SEMI, ICON_SEMI);
        g2.draw(mancala);
        drawStones(g2);
    }
}
