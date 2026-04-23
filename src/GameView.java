import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {
    GameModel model;
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
                container.setX(x);
                container.setY(y);
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
        StoneContainer[][] containers = model.getContainers();
        for (int row = 0; row < containers.length; row++) {
            for (int col = 0; col < containers[row].length; col++) {
                StoneContainer container = containers[row][col];
                container.draw(g2);
            }
        }
    }

}
