import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Style 1 for the board using the strategy pattern (Standard style).
 * Affects the appearance of the board, pits, mancalas, and stones.
 */
public class Style1 implements BoardStyle {
    final double ICON_SIZE = 100;
    final double ICON_WIDTH = 100;
    final double ICON_HEIGHT = 450;
    final double ICON_SEMI = 100;

    public void drawPit(Graphics2D g2, double x, double y, int row, int col) {
        Ellipse2D pit = new Ellipse2D.Double(x, y, ICON_SIZE, ICON_SIZE);

        // pit color setup
        g2.setColor(Color.decode("#dcbc97"));
        g2.fill(pit);
        g2.setColor(Color.BLACK);

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
        g2.setColor(Color.decode("#dcbc97"));
        g2.fill(mancala);
        g2.setColor(Color.BLACK);

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
        g2.setColor(Color.decode("#a06545"));
        g2.fill(board);
        g2.setColor(Color.BLACK);

        // draw board
        g2.draw(board);

    }
    public void drawStone(Graphics2D g2, StoneContainer container) {
        int numStones = container.getStones();
        double x = container.getX();
        double y = container.getY();
        if (container instanceof Pit) drawStonesPit(g2, numStones, x, y);
        if (container instanceof Mancala) drawStonesMancala(g2, numStones, x, y);
    }
    public void drawStonesPit(Graphics2D g2, int numStones, double x, double y) {
        if (numStones == 0) return;
        switch (numStones) {
            case 1:
                Ellipse2D one = new Ellipse2D.Double(x + 37.5, y + 37.5, 25, 25);
                RandomColorFactory.drawGray(g2, one);
                break;
            case 2:
                Ellipse2D two = new Ellipse2D.Double(x + 37.5, y + 25, 25, 25);
                RandomColorFactory.drawGray(g2, two);
                two = new Ellipse2D.Double(x + 37.5, y + 50, 25, 25);
                RandomColorFactory.drawGray(g2, two);
                break;
            case 3:
                Ellipse2D three = new Ellipse2D.Double(x + 37.5, y + 25, 25, 25);
                RandomColorFactory.drawGray(g2, three);
                three = new Ellipse2D.Double(x + 25, y + 50, 25, 25);
                RandomColorFactory.drawGray(g2, three);
                three = new Ellipse2D.Double(x + 50, y + 50, 25, 25);
                RandomColorFactory.drawGray(g2, three);
                break;
            case 4:
                Ellipse2D four = new Ellipse2D.Double(x + 25, y + 25, 25, 25);
                RandomColorFactory.drawGray(g2, four);
                four = new Ellipse2D.Double(x + 50, y + 25, 25, 25);
                RandomColorFactory.drawGray(g2, four);
                four = new Ellipse2D.Double(x + 25, y + 50, 25, 25);
                RandomColorFactory.drawGray(g2, four);
                four = new Ellipse2D.Double(x + 50, y + 50, 25, 25);
                RandomColorFactory.drawGray(g2, four);
                break;
            case 5:
                Ellipse2D five = new Ellipse2D.Double(x + 37.5, y + 37.5, 25, 25);
                RandomColorFactory.drawGray(g2, five);
                five = new Ellipse2D.Double(x + 15, y + 15, 25, 25);
                RandomColorFactory.drawGray(g2, five);
                five = new Ellipse2D.Double(x + 60, y + 15, 25, 25);
                RandomColorFactory.drawGray(g2, five);
                five = new Ellipse2D.Double(x + 15, y + 60, 25, 25);
                RandomColorFactory.drawGray(g2, five);
                five = new Ellipse2D.Double(x + 60, y + 60, 25, 25);
                RandomColorFactory.drawGray(g2, five);
                break;
            case 6:
                Ellipse2D six = new Ellipse2D.Double(x + 12.5, y + 25, 25, 25);
                RandomColorFactory.drawGray(g2, six);
                six = new Ellipse2D.Double(x + 37.5, y + 25, 25, 25);
                RandomColorFactory.drawGray(g2, six);
                six = new Ellipse2D.Double(x + 62.5, y + 25, 25, 25);
                RandomColorFactory.drawGray(g2, six);
                six = new Ellipse2D.Double(x + 12.5, y + 50, 25, 25);
                RandomColorFactory.drawGray(g2, six);
                six = new Ellipse2D.Double(x + 37.5, y + 50, 25, 25);
                RandomColorFactory.drawGray(g2, six);
                six = new Ellipse2D.Double(x + 62.5, y + 50, 25, 25);
                RandomColorFactory.drawGray(g2, six);
                break;
            case 7:
                Ellipse2D seven = new Ellipse2D.Double(x + 37.5, y + 10, 25, 25);
                RandomColorFactory.drawGray(g2, seven);
                seven = new Ellipse2D.Double(x + 12.5, y + 35, 25, 25);
                RandomColorFactory.drawGray(g2, seven);
                seven = new Ellipse2D.Double(x + 37.5, y + 35, 25, 25);
                RandomColorFactory.drawGray(g2, seven);
                seven = new Ellipse2D.Double(x + 62.5, y + 35, 25, 25);
                RandomColorFactory.drawGray(g2, seven);
                seven = new Ellipse2D.Double(x + 12.5, y + 60, 25, 25);
                RandomColorFactory.drawGray(g2, seven);
                seven = new Ellipse2D.Double(x + 37.5, y + 60, 25, 25);
                RandomColorFactory.drawGray(g2, seven);
                seven = new Ellipse2D.Double(x + 62.5, y + 60, 25, 25);
                RandomColorFactory.drawGray(g2, seven);
                break;
            case 8:
                Ellipse2D eight = new Ellipse2D.Double(x + 25, y + 10, 25, 25);
                RandomColorFactory.drawGray(g2, eight);
                eight = new Ellipse2D.Double(x + 50, y + 10, 25, 25);
                RandomColorFactory.drawGray(g2, eight);
                eight = new Ellipse2D.Double(x + 12.5, y + 35, 25, 25);
                RandomColorFactory.drawGray(g2, eight);
                eight = new Ellipse2D.Double(x + 37.5, y + 35, 25, 25);
                RandomColorFactory.drawGray(g2, eight);
                eight = new Ellipse2D.Double(x + 62.5, y + 35, 25, 25);
                RandomColorFactory.drawGray(g2, eight);
                eight = new Ellipse2D.Double(x + 12.5, y + 60, 25, 25);
                RandomColorFactory.drawGray(g2, eight);
                eight = new Ellipse2D.Double(x + 37.5, y + 60, 25, 25);
                RandomColorFactory.drawGray(g2, eight);
                eight = new Ellipse2D.Double(x + 62.5, y + 60, 25, 25);
                RandomColorFactory.drawGray(g2, eight);
                break;
            case 9:
                Ellipse2D nine = new Ellipse2D.Double(x + 12.5, y + 10, 25, 25);
                RandomColorFactory.drawGray(g2, nine);
                nine = new Ellipse2D.Double(x + 37.5, y + 10, 25, 25);
                RandomColorFactory.drawGray(g2, nine);
                nine = new Ellipse2D.Double(x + 62.5, y + 10, 25, 25);
                RandomColorFactory.drawGray(g2, nine);
                nine = new Ellipse2D.Double(x + 12.5, y + 35, 25, 25);
                RandomColorFactory.drawGray(g2, nine);
                nine = new Ellipse2D.Double(x + 37.5, y + 35, 25, 25);
                RandomColorFactory.drawGray(g2, nine);
                nine = new Ellipse2D.Double(x + 62.5, y + 35, 25, 25);
                RandomColorFactory.drawGray(g2, nine);
                nine = new Ellipse2D.Double(x + 12.5, y + 60, 25, 25);
                RandomColorFactory.drawGray(g2, nine);
                nine = new Ellipse2D.Double(x + 37.5, y + 60, 25, 25);
                RandomColorFactory.drawGray(g2, nine);
                nine = new Ellipse2D.Double(x + 62.5, y + 60, 25, 25);
                RandomColorFactory.drawGray(g2, nine);
                break;
            default:
                Ellipse2D multi1 = new Ellipse2D.Double(x + 25, y + 25, 50, 50);
                RandomColorFactory.drawGray(g2, multi1);
                g2.drawString("" + numStones, (int) (x + 37), (int) (y + 58));
                break;
        }
    }
    public void drawStonesMancala(Graphics2D g2, int numStones, double x, double y) {
        Ellipse2D.Double stone;
        double width = (ICON_HEIGHT - 50) / numStones;
        if (width > 50) width = 50;
        for (int i = 0; i < numStones; i++) {
            stone = new Ellipse2D.Double(x + 50 - (width / 2), y + 25 + (i * width), width, width);
            RandomColorFactory.drawGray(g2, stone);
        }
        g2.setColor(Color.BLACK);
    }
}
