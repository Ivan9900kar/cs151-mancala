import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class GameView extends JPanel {
    private GameModel model;
    private JPanel state;
    private JTextField moved;
    private JTextField turn;
    private JTextField undosRemaining;
    private final double xOffset = 100;
    private final double yOffset = 100;
    public GameView(GameModel model) {
        this.model = model;
        gameState();
        setPositions();
        addMouseListener(new MyMouseListener());
    }
    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            StoneContainer[][] containers = model.getContainers();
            for (StoneContainer[] row : containers) {
                for (StoneContainer col : row) {
                    if (col instanceof Pit pit && pit.contains(event.getPoint())) {
                        model.move(pit.getRow(), pit.getCol());
                    }
                }
            }
        }
    }
    private void gameState() {
        this.state = new JPanel();
        add(state, BorderLayout.NORTH);

        Font font = new Font("SansSerif", Font.PLAIN, 24);

        this.moved = new JTextField("Moved");
        moved.setFont(font);
        moved.setEditable(false);
        state.add(moved);

        this.turn = new JTextField("Turn");
        turn.setFont(font);
        turn.setEditable(false);
        state.add(turn);

        this.undosRemaining = new JTextField("Undos Remaining");
        undosRemaining.setFont(font);
        undosRemaining.setEditable(false);
        state.add(undosRemaining);
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
        GameModel.GameState gameState = model.getState();
        int turn = gameState.getTurn();
        int undosRemaining = gameState.getUndosRemaining();
        boolean moved = gameState.isMoved();

        char player = (char) ('A' + turn);
        this.turn.setText("Player " + player + "'s turn");
        this.undosRemaining.setText(undosRemaining + " undos remaining");
        if (moved) this.moved.setText("Player " + player + " has moved");
        else this.moved.setText("Player " + player + " has not moved yet");

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
        g2.setStroke(new BasicStroke(2));

        RoundRectangle2D board = new RoundRectangle2D.Double(getX() + xOffset, getY() + yOffset, 1250, 550, 150, 150);

        // color
        Color temp = g2.getColor();
        g2.setColor(Color.decode("#a06545"));
        g2.fill(board);
        g2.setColor(temp);

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
