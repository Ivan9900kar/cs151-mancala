import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

/**
 * 
 */
public class GameView extends JPanel {
    private GameModel model;
    private JPanel statePanel;
    private JLabel movedLabel;
    private JLabel turnLabel;
    private JLabel undosRemainingLabel;
    private final JButton confirmButton;
    private final JButton undoButton;
    private final double xOffset = 120;
    private final double yOffset = 120;
    /**
     * 
     * @param model
     */
    public GameView(GameModel model) {
        this.model = model;
        this.confirmButton = new JButton("Confirm Move");
        this.undoButton = new JButton("Undo Move");
        setLayout(new BorderLayout());
        addStatePanel();
        addButtons();
        setPositions();
    }
    /**
     * 
     */
    private void addStatePanel() {
        this.statePanel = new JPanel(new GridBagLayout());
        add(statePanel, BorderLayout.NORTH);

        // label font
        Font font = new Font("SansSerif", Font.PLAIN, 24);

        // grid bag constraints for label organization
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // fixed column
        gbc.gridy = GridBagConstraints.RELATIVE; // stacks one after another

        // add turn label
        this.turnLabel = new JLabel("Turn");
        turnLabel.setFont(font);
        statePanel.add(turnLabel, gbc);

        // add moved label
        this.movedLabel = new JLabel("Moved");
        movedLabel.setFont(font);
        statePanel.add(movedLabel, gbc);

        // add undos label
        this.undosRemainingLabel = new JLabel("Undos Remaining");
        undosRemainingLabel.setFont(font);
        statePanel.add(undosRemainingLabel, gbc);
    }
    /**
     * 
     */
    private void addButtons() {
        JPanel buttonPanel = new JPanel();
        confirmButton.setPreferredSize(new Dimension(200, 50));
        undoButton.setPreferredSize(new Dimension(200, 50));
        buttonPanel.add(confirmButton);
        buttonPanel.add(undoButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    /**
     * 
     * @param listener
     */
    public void addConfirmActionListener(ActionListener listener) {
        confirmButton.addActionListener(listener);
    }
    /**
     * 
     * @param listener
     */
    public void addUndoActionListener(ActionListener listener) {
        undoButton.addActionListener(listener);
    }
    /**
     * 
     */
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
    /**
     * 
     */
    public int startMenu() {
        String[] options = {"3", "4"};
        int choice = JOptionPane.showOptionDialog(this, "Set the number of stones per pit to start with:", "Starting Number of Stones", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        return choice + 3;
    }
    /**
     * 
     */
    public void endMenu(int playerAScore, int playerBScore) {
        String message;
        if (playerAScore == playerBScore) {
            message = "Game tie!";
        } else {
            char winner = (playerAScore > playerBScore) ? 'A' : 'B';
            message = "Player " + winner + " wins!";
        }

        String[] options = {"Restart", "Exit"};
        int choice = JOptionPane.showOptionDialog(this, message + "\nPlayer A Score: " + playerAScore + "\nPlayer B Score: " + playerBScore + "\nWould you like to restart game or exit?", "Game End", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (choice == 0) { 
            // WRITE RESTART
            
        } else {
            System.exit(0);
        }
    }
    /**
     * 
     */
    public void update() {
        // get game state data
        GameModel.GameState gameState = model.getState();
        int turn = gameState.getTurn();
        int undosRemaining = gameState.getUndosRemaining();
        boolean moved = gameState.isMoved();
        GameModel.MOVE_TYPE moveType = gameState.getMoveType();
        char player = (char) ('A' + turn);

        // update labels
        this.turnLabel.setText("Player " + player + "'s turn");
        this.undosRemainingLabel.setText(undosRemaining + " undos remaining");
        switch (moveType) {
            case NEW_TURN:
                this.movedLabel.setText("Player " + player + " has not moved yet.");
                break;
            case FREE_TURN:
                this.movedLabel.setText("Player " + player + " has moved. Free turn!");
                break;
            case CAPTURE:
                char letter = (char) ('A' + (gameState.getTurn() + 1) % 2);
                String capturedPitName = "";
                capturedPitName += letter;
                capturedPitName += model.getNumPits() - gameState.getEndCol();
                this.movedLabel.setText("Player " + player + " has moved. Captured " + capturedPitName + "!");
                break;
            case NORMAL:
                this.movedLabel.setText("Player " + player + " has moved.");
                break;
            case UNDO:
                this.movedLabel.setText("Last move undone. Player " + player + " has not moved yet.");
                break;
        }

        // update buttons
        confirmButton.setEnabled(moved);
        undoButton.setEnabled(moved && gameState.getUndosRemaining() > 0);

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
    }
    
    public void draw(Graphics2D g2) {
        // board font and thickness
        g2.setFont(new Font("SansSerif", Font.BOLD, 24));
        g2.setStroke(new BasicStroke(2));

        RoundRectangle2D board = new RoundRectangle2D.Double(getX() + xOffset, getY() + yOffset, 1250, 550, 150, 150);

        // board color setup
        Color temp = g2.getColor();
        g2.setColor(Color.decode("#a06545"));
        g2.fill(board);
        g2.setColor(temp);

        // draw board
        g2.draw(board);

        // draw containers (pits and mancalas)
        StoneContainer[][] containers = model.getContainers();
        for (int row = 0; row < containers.length; row++) {
            for (int col = 0; col < containers[row].length; col++) {
                StoneContainer container = containers[row][col];
                container.draw(g2);
            }
        }
    }
}
