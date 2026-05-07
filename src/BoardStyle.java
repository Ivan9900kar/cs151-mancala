/**
 * Title: CS 151 Mancala Project
 * Authors: Anthony Ryabov, Darren Vu, Isaiah Mak
 */

import java.awt.*;

/**
 * Interface for the strategy pattern to draw board in different styles (board, pits, mancalas, and stones).
 */
public interface BoardStyle {
    void drawPit(Graphics2D g2, double x, double y, int row, int col);
    void drawMancala(Graphics2D g2, double x, double y, int row);
    void drawBoard(Graphics2D g2, double x, double y);
    void drawStone(Graphics2D g2, StoneContainer container);
    void drawStonesPit(Graphics2D g2, int numStones, double x, double y);
    void drawStonesMancala(Graphics2D g2, int numStones, double x, double y);
}
