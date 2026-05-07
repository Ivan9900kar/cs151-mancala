import java.awt.*;

// strategy pattern for board style (board, pits, mancalas, stones)
public interface BoardStyle {
    void drawPit(Graphics2D g2, double x, double y, int row, int col);
    void drawMancala(Graphics2D g2, double x, double y, int row);
    void drawBoard(Graphics2D g2, double x, double y);
    void drawStone(Graphics2D g2, StoneContainer container);
    void drawStonesPit(Graphics2D g2, int numStones, double x, double y);
    void drawStonesMancala(Graphics2D g2, int numStones, double x, double y);
}
