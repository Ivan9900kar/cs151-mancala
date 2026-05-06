import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

public class Style1 implements BoardStyle {
    final double ICON_SIZE = 100;
    final double ICON_WIDTH = 100;
    final double ICON_HEIGHT = 450;
    final double ICON_SEMI = 100;

    public void drawPit(Graphics2D g2, double x, double y, int row, int col) {
        Ellipse2D pit = new Ellipse2D.Double(x, y, ICON_SIZE, ICON_SIZE);

        // pit color setup
        Color temp = g2.getColor();
        g2.setColor(Color.decode("#dcbc97"));
        g2.fill(pit);
        g2.setColor(temp);

        // draw pit
        g2.draw(pit);

        // draw pit label
        char letter = (char) ('A' + row);
        String name = "";
        name += letter;
        name += col + 1;
        g2.drawString(name, (int) x + 33, (int) y - 5);

    }
    public void drawMancala(Graphics2D g2, double x, double y, int row) {
        RoundRectangle2D mancala = new RoundRectangle2D.Double(x, y, ICON_WIDTH, ICON_HEIGHT, ICON_SEMI, ICON_SEMI);

        // mancala color setup
        Color temp = g2.getColor();
        g2.setColor(Color.decode("#dcbc97"));
        g2.fill(mancala);
        g2.setColor(temp);

        // draw mancala
        g2.draw(mancala);
        
        // draw mancala label
        char letter = (char) ('A' + row);
        String label = "Mancala " + letter;
        g2.drawString(label, (int) (x - 18), (int) (y - 10));

    }
    public void drawBoard(Graphics2D g2, double x, double y) {
        // board font and thickness
        g2.setFont(new Font("SansSerif", Font.BOLD, 24));
        g2.setStroke(new BasicStroke(2));

        RoundRectangle2D board = new RoundRectangle2D.Double(x, y, 1250, 550, 150, 150);

        // board color setup
        Color temp = g2.getColor();
        g2.setColor(Color.decode("#a06545"));
        g2.fill(board);
        g2.setColor(temp);

        // draw board
        g2.draw(board);

    }
    public void drawStone(Graphics2D g2) {

    }
}
