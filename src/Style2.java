import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Style 2 for the board using the strategy pattern (Party style).
 * Affects the appearance of the board, pits, mancalas, and stones.
 */
public class Style2 implements BoardStyle {
    final double ICON_SIZE = 100;
    final double ICON_WIDTH = 100;
    final double ICON_HEIGHT = 450;
    final double ICON_SEMI = 100;

    public void drawPit(Graphics2D g2, double x, double y, int row, int col) {
        Rectangle2D pit = new Rectangle2D.Double(x, y, ICON_SIZE, ICON_SIZE);

        // pit color
        RandomColorFactory.drawRandomColor(g2, pit);

        // draw pit
        g2.draw(pit);

        // draw pit label
        char letter = (char) ('A' + row);
        String name = "";
        name += letter;
        name += col + 1;
        g2.drawString(name, (int) x + 33, (int) y - 5);

    }
    // draws the mancala with a random color and label
    public void drawMancala(Graphics2D g2, double x, double y, int row) {
        Rectangle2D mancala = new Rectangle2D.Double(x, y, ICON_WIDTH, ICON_HEIGHT);

        // mancala color
        RandomColorFactory.drawRandomColor(g2, mancala);

        // draw mancala
        g2.draw(mancala);

        // draw mancala label
        char letter = (char) ('A' + row);
        String label = "Mancala " + letter;
        g2.drawString(label, (int) (x - 18), (int) (y - 10));

    }
    // draws the board with a random color
    public void drawBoard(Graphics2D g2, double x, double y) {
        // board font and thickness
        g2.setFont(new Font("SansSerif", Font.BOLD, 24));
        g2.setStroke(new BasicStroke(2));

        Rectangle2D board = new Rectangle2D.Double(x, y, 1250, 550);

        // board color
        RandomColorFactory.drawRandomColor(g2, board);

        // draw board
        g2.draw(board);

    }
    // Draws stones in each container
    public void drawStone(Graphics2D g2, StoneContainer container) {
        int numStones = container.getStones();
        double x = container.getX();
        double y = container.getY();
        if (container instanceof Pit) drawStonesPit(g2, numStones, x, y);
        if (container instanceof Mancala) drawStonesMancala(g2, numStones, x, y);
    }
     //Draws and adjust stones in a pit using preset layouts and coordinates fill in with color 
    public void drawStonesPit(Graphics2D g2, int numStones, double x, double y) {
        if (numStones == 0) return;
        switch (numStones) {
            case 1:
                Rectangle2D one = new Rectangle2D.Double(x + 37.5, y + 37.5, 25, 25);
                RandomColorFactory.drawRandomColor(g2, one);
                break;
            case 2:
                Rectangle2D two = new Rectangle2D.Double(x + 37.5, y + 25, 25, 25);
                RandomColorFactory.drawRandomColor(g2, two);
                two = new Rectangle2D.Double(x + 37.5, y + 50, 25, 25);
                RandomColorFactory.drawRandomColor(g2, two);
                break;
            case 3:
                Rectangle2D three = new Rectangle2D.Double(x + 37.5, y + 25, 25, 25);
                RandomColorFactory.drawRandomColor(g2, three);
                three = new Rectangle2D.Double(x + 25, y + 50, 25, 25);
                RandomColorFactory.drawRandomColor(g2, three);
                three = new Rectangle2D.Double(x + 50, y + 50, 25, 25);
                RandomColorFactory.drawRandomColor(g2, three);
                break;
            case 4:
                Rectangle2D four = new Rectangle2D.Double(x + 25, y + 25, 25, 25);
                RandomColorFactory.drawRandomColor(g2, four);
                four = new Rectangle2D.Double(x + 50, y + 25, 25, 25);
                RandomColorFactory.drawRandomColor(g2, four);
                four = new Rectangle2D.Double(x + 25, y + 50, 25, 25);
                RandomColorFactory.drawRandomColor(g2, four);
                four = new Rectangle2D.Double(x + 50, y + 50, 25, 25);
                RandomColorFactory.drawRandomColor(g2, four);
                break;
            case 5:
                Rectangle2D five = new Rectangle2D.Double(x + 37.5, y + 37.5, 25, 25);
                RandomColorFactory.drawRandomColor(g2, five);
                five = new Rectangle2D.Double(x + 12.5, y + 12.5, 25, 25);
                RandomColorFactory.drawRandomColor(g2, five);
                five = new Rectangle2D.Double(x + 62.5, y + 12.5, 25, 25);
                RandomColorFactory.drawRandomColor(g2, five);
                five = new Rectangle2D.Double(x + 12.5, y + 62.5, 25, 25);
                RandomColorFactory.drawRandomColor(g2, five);
                five = new Rectangle2D.Double(x + 62.5, y + 62.5, 25, 25);
                RandomColorFactory.drawRandomColor(g2, five);
                break;
            case 6:
                Rectangle2D six = new Rectangle2D.Double(x + 12.5, y + 25, 25, 25);
                RandomColorFactory.drawRandomColor(g2, six);
                six = new Rectangle2D.Double(x + 37.5, y + 25, 25, 25);
                RandomColorFactory.drawRandomColor(g2, six);
                six = new Rectangle2D.Double(x + 62.5, y + 25, 25, 25);
                RandomColorFactory.drawRandomColor(g2, six);
                six = new Rectangle2D.Double(x + 12.5, y + 50, 25, 25);
                RandomColorFactory.drawRandomColor(g2, six);
                six = new Rectangle2D.Double(x + 37.5, y + 50, 25, 25);
                RandomColorFactory.drawRandomColor(g2, six);
                six = new Rectangle2D.Double(x + 62.5, y + 50, 25, 25);
                RandomColorFactory.drawRandomColor(g2, six);
                break;
            case 7:
                Rectangle2D seven = new Rectangle2D.Double(x + 37.5, y + 10, 25, 25);
                RandomColorFactory.drawRandomColor(g2, seven);
                seven = new Rectangle2D.Double(x + 12.5, y + 35, 25, 25);
                RandomColorFactory.drawRandomColor(g2, seven);
                seven = new Rectangle2D.Double(x + 37.5, y + 35, 25, 25);
                RandomColorFactory.drawRandomColor(g2, seven);
                seven = new Rectangle2D.Double(x + 62.5, y + 35, 25, 25);
                RandomColorFactory.drawRandomColor(g2, seven);
                seven = new Rectangle2D.Double(x + 12.5, y + 60, 25, 25);
                RandomColorFactory.drawRandomColor(g2, seven);
                seven = new Rectangle2D.Double(x + 37.5, y + 60, 25, 25);
                RandomColorFactory.drawRandomColor(g2, seven);
                seven = new Rectangle2D.Double(x + 62.5, y + 60, 25, 25);
                RandomColorFactory.drawRandomColor(g2, seven);
                break;
            case 8:
                Rectangle2D eight = new Rectangle2D.Double(x + 25, y + 10, 25, 25);
                RandomColorFactory.drawRandomColor(g2, eight);
                eight = new Rectangle2D.Double(x + 50, y + 10, 25, 25);
                RandomColorFactory.drawRandomColor(g2, eight);
                eight = new Rectangle2D.Double(x + 12.5, y + 35, 25, 25);
                RandomColorFactory.drawRandomColor(g2, eight);
                eight = new Rectangle2D.Double(x + 37.5, y + 35, 25, 25);
                RandomColorFactory.drawRandomColor(g2, eight);
                eight = new Rectangle2D.Double(x + 62.5, y + 35, 25, 25);
                RandomColorFactory.drawRandomColor(g2, eight);
                eight = new Rectangle2D.Double(x + 12.5, y + 60, 25, 25);
                RandomColorFactory.drawRandomColor(g2, eight);
                eight = new Rectangle2D.Double(x + 37.5, y + 60, 25, 25);
                RandomColorFactory.drawRandomColor(g2, eight);
                eight = new Rectangle2D.Double(x + 62.5, y + 60, 25, 25);
                RandomColorFactory.drawRandomColor(g2, eight);
                break;
            case 9:
                Rectangle2D nine = new Rectangle2D.Double(x + 12.5, y + 10, 25, 25);
                RandomColorFactory.drawRandomColor(g2, nine);
                nine = new Rectangle2D.Double(x + 37.5, y + 10, 25, 25);
                RandomColorFactory.drawRandomColor(g2, nine);
                nine = new Rectangle2D.Double(x + 62.5, y + 10, 25, 25);
                RandomColorFactory.drawRandomColor(g2, nine);
                nine = new Rectangle2D.Double(x + 12.5, y + 35, 25, 25);
                RandomColorFactory.drawRandomColor(g2, nine);
                nine = new Rectangle2D.Double(x + 37.5, y + 35, 25, 25);
                RandomColorFactory.drawRandomColor(g2, nine);
                nine = new Rectangle2D.Double(x + 62.5, y + 35, 25, 25);
                RandomColorFactory.drawRandomColor(g2, nine);
                nine = new Rectangle2D.Double(x + 12.5, y + 60, 25, 25);
                RandomColorFactory.drawRandomColor(g2, nine);
                nine = new Rectangle2D.Double(x + 37.5, y + 60, 25, 25);
                RandomColorFactory.drawRandomColor(g2, nine);
                nine = new Rectangle2D.Double(x + 62.5, y + 60, 25, 25);
                RandomColorFactory.drawRandomColor(g2, nine);
                break;
            default:
                Rectangle2D multi1 = new Rectangle2D.Double(x + 25, y + 25, 50, 50);
                RandomColorFactory.drawRandomColor(g2, multi1);
                g2.drawString("" + numStones, (int) (x + 37), (int) (y + 58));
                break;
        }
    }
    // Draws stones and color stacked vertically in a mancala
    public void drawStonesMancala(Graphics2D g2, int numStones, double x, double y) {
        Rectangle2D.Double stone;
        double width = (ICON_HEIGHT - 50) / numStones;
        if (width > 50) width = 50;
        for (int i = 0; i < numStones; i++) {
            stone = new Rectangle2D.Double(x + 50 - (width / 2), y + 25 + (i * width), width, width);
            RandomColorFactory.drawRandomColor(g2, stone);
        }
        g2.setColor(Color.BLACK);
    }
}