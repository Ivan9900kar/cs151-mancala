import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class GameView extends JPanel {
    private GameModel model;
    private JPanel state;
    private JLabel moved;
    private JLabel turn;
    private JLabel undosRemaining;
    private final double xOffset = 100;
    private final double yOffset = 100;

    private final JButton confirm;
    private final JButton undo;

    public GameView(GameModel model) {
        this.model = model;
        this.confirm = new JButton("Confirm Move");
        this.undo = new JButton("Undo Move");
        setLayout(new BorderLayout());
        addGameState();
        addButtons();
        setPositions();
    }

    private void addGameState() {
        this.state = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // fixed column
        gbc.gridy = GridBagConstraints.RELATIVE; // stacks one after another

        add(state, BorderLayout.NORTH);

        Font font = new Font("SansSerif", Font.PLAIN, 24);
        this.turn = new JLabel("Turn");
        turn.setFont(font);
        state.add(turn, gbc);

        this.moved = new JLabel("Moved");
        moved.setFont(font);
        state.add(moved, gbc);

        this.undosRemaining = new JLabel("Undos Remaining");
        undosRemaining.setFont(font);
        state.add(undosRemaining, gbc);
    }

    private void addButtons() {
        JPanel buttonPanel = new JPanel();
        confirm.setPreferredSize(new Dimension(200, 50));
        undo.setPreferredSize(new Dimension(200, 50));
        buttonPanel.add(confirm);
        buttonPanel.add(undo);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void addConfirmActionListener(ActionListener listener) {
        confirm.addActionListener(listener);
    }

    public void addUndoActionListener(ActionListener listener) {
        undo.addActionListener(listener);
    }

    private void setPositions() {
        StoneContainer[][] containers = model.getContainers();
        for (int row = 0; row < containers.length; row++) {
            for (int col = 0; col < containers[row].length; col++) {
                StoneContainer container = containers[row][col];
                double x;
                if (row == 0) x = getX() + 50 + (150 * (col + 1));
                else x = getX() + 50 + (150 * ((model.getNumPits() - 1 - col) + 1));
                double y;
                if (col == containers[row].length - 1) y = getY() + 50;
                else if (row == 0) y = getY() + 300;
                else y = getY() + 150;
                container.setX(x + xOffset);
                container.setY(y + yOffset);
            }
        }
    }

    public void update() {
        GameModel.GameState gameState = model.getState();
        int turn = gameState.getTurn();
        int undosRemaining = gameState.getUndosRemaining();
        char player = (char) ('A' + turn);

        this.turn.setText("Player " + player + "'s turn");
        this.undosRemaining.setText(undosRemaining + " undos remaining");
        GameModel.MOVE_TYPE moveType = gameState.getMoveType();
        switch (moveType) {
            case NEW_TURN:
                this.moved.setText("Player " + player + " has not moved yet.");
                break;
            case FREE_TURN:
                this.moved.setText("Player " + player + " has moved. Free turn!");
                break;
            case CAPTURE:
                this.moved.setText("Player " + player + " has moved. Capture!");
                break;
            case NORMAL:
                this.moved.setText("Player " + player + " has moved.");
                break;
            case UNDO:
                this.moved.setText("Last move undone. Player " + player + " has not moved yet.");
                break;
        }
        boolean moved = gameState.isMoved();
        confirm.setEnabled(moved);
        undo.setEnabled(moved && gameState.getUndosRemaining() > 0);
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
