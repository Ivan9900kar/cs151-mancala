import javax.swing.*;
import java.awt.*;

import java.awt.geom.RoundRectangle2D;

public class GameView extends JPanel {
    GameModel model;
    double xOffset = 100;
    double yOffset = 100;
    public GameView(GameModel model) {
        this.model = model;
        setPositions();
    }
    private void setPositions() {
        StoneContainer[][] containers = model.getContainers();
        for (int row = 0; row < containers.length; row++) {
            for (int col = 0; col < containers[row].length; col++) {
                StoneContainer container = containers[row][col];
                double x;
                if (col == 0) x = getX() + 50 + 150;
                else if (row == 0 && col == containers[row].length - 1) x = getX() + 50;
                else x = getX() + 50 + (150 * (col + 1));
                double y;
                if (col == containers[row].length - 1) y = getY() + 50;
                else y = getY() + 150 + (150 * row);
                container.setX(x + xOffset);
                container.setY(y + yOffset);
            }
        }
    }
    public void update() {
        repaint();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
    }

    public void draw(Graphics2D g2) {
        // could be part of a strategy pattern
        g2.setFont(new Font("SansSerif", Font.BOLD, 24));

        RoundRectangle2D board = new RoundRectangle2D.Double(getX() + xOffset, getY() + yOffset, 1250, 550, 100, 100);
        g2.draw(board);

        StoneContainer[][] containers = model.getContainers();
        for (int row = 0; row < containers.length; row++) {
            for (int col = 0; col < containers[row].length; col++) {
                StoneContainer container = containers[row][col];
                container.draw(g2);
            }
        }
    }

}
