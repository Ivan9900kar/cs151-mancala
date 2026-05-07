import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * View class for the Mancala game, responsible for rendering the game board and interface.
 * Also provides some interaction with the game through menus and buttons (other than what is covered in controller).
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
    BoardStyle strategy;

    /**
     * Constructor for GameView, initializes view and its components.
     * @param model the reference to GameModel object to get game data from and to update when needed
     */
    public GameView(GameModel model) {
        this.model = model;
        this.confirmButton = new JButton("Confirm Move");
        this.undoButton = new JButton("Undo Move");
        setLayout(new BorderLayout());
        addStatePanel();
        addButtonPanel();
        setPositions();
    }
    /**
     * Adds the state panel to the view. The state panel holds labels to display the current state of the game (whose turn is it, if player has moved, undos remaining).
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
     * Adds the action buttons to the view. Has the buttons for confirming a move or undoing a move of a player.
     */
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        confirmButton.setPreferredSize(new Dimension(200, 50));
        undoButton.setPreferredSize(new Dimension(200, 50));
        buttonPanel.add(confirmButton);
        buttonPanel.add(undoButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    /**
     * Adds an action listener to the confirm button to allow the controller to listen for when the player confirms their move.
     * @param listener the listener to be added to the confirm button
     */
    public void addConfirmActionListener(ActionListener listener) {
        confirmButton.addActionListener(listener);
    }
    /**
     * Adds an action listener to the undo button to allow the controller to listen for when the player undoes their move.
     * @param listener the listener to be added to the undo button
     */
    public void addUndoActionListener(ActionListener listener) {
        undoButton.addActionListener(listener);
    }
    /**
     * Sets the positions of the pits and mancalas on the board.
     * Precondition: all StoneContainers in the data array are instantiated
     * Postcondition: x-coordinates and y-coordinates of all StoneContainers are set
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
     * Displays a menu for choosing the style of the board to display in the game.
     * Postcondition: the Style strategy is selected and updates display
     */
    public void styleMenu() {
        String[] options = {"Standard", "Party"};
        int choice = JOptionPane.showOptionDialog(this, "Select which style of board to use:", "Board Style Selection", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        switch (choice) {
            case 0:
                strategy = new Style1();
                break;
            case 1:
                strategy = new Style2();
                break;
        }
        model.setStrategy(strategy);
    }
    /**
     * Displays a menu for choosing the number of stones each pit will start with at the beginning of the game.
     * @return Returns the integer number of stones per pit at the start of the game
     */
    public int stonesMenu() {
        String[] options = {"1", "2", "3", "4"};
        int choice = JOptionPane.showOptionDialog(this, "Set the number of stones per pit to start with:",
                "Game Initialization",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        return choice + 1;
    }
    /**
     * Displays a menu with information on the winner and scores at the end of the game, and for choosing whether to restart or exit.
     * Postcondition: Resets game if "Restart" selected; Ends program if "Exit" is selected
     */
    public void endMenu(int playerAScore, int playerBScore) {
        // determine message to display based on player score
        String message;
        if (playerAScore == playerBScore) {
            message = "Game tie!";
        } else {
            char winner = (playerAScore > playerBScore) ? 'A' : 'B';
            message = "Player " + winner + " wins!";
        }

        String[] options = {"Restart", "Exit"};
        int choice = JOptionPane.showOptionDialog(this,
                message + "\nPlayer A Score: " + playerAScore + "\nPlayer B Score: " + playerBScore + "\nWould you like to restart game or exit?",
                "Game End",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (choice == 0) { 
            styleMenu();
            int newStartingStones = stonesMenu();
            model.reset(newStartingStones);
        } else {
            System.exit(0);
        }
    }
    /**
     * Updates the view based on the current game state.
     */
    public void update() {
        // get game state data
        GameModel.GameState gameState = model.getState();
        int turn = gameState.getTurn(); // for turn label
        int undosRemaining = gameState.getUndosRemaining(); // for undos remaining label and undo button
        boolean moved = gameState.isMoved(); // for buttons
        GameModel.MOVE_TYPE moveType = gameState.getMoveType(); // for moved label
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
    /**
     * Draws the board, pits, mancalas, and stones based on the set strategy.
     * @param g2 the relevant Graphics2D object
     */
    public void draw(Graphics2D g2) {
        // draw board using strategy
        strategy.drawBoard(g2, getX() + xOffset, getY() + yOffset);

        // draw containers (pits and mancalas) (call strategy from their own draw() methods)
        StoneContainer[][] containers = model.getContainers();
        for (int row = 0; row < containers.length; row++) {
            for (int col = 0; col < containers[row].length; col++) {
                StoneContainer container = containers[row][col];
                container.draw(g2);
                strategy.drawStone(g2, container);
            }
        }
    }
}
