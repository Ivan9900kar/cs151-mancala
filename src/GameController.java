/**
 * Title: CS 151 Mancala Project
 * Authors: Anthony Ryabov, Darren Vu, Isaiah Mak
 */

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Controller class for the Mancala game, responsible for handling user interactions and updating the model accordingly.
 */
public class GameController {
    private final GameModel model;

    /**
     * Constructor for GameController. Adds action listeners to the confirm and undo buttons in the view and a mouse listener for the game board.
     * @param model the reference to the GameModel object
     * @param view the reference to the GameView object
     */
    public GameController(GameModel model, GameView view) {
        this.model = model;
        view.addConfirmActionListener(e -> model.confirm());
        view.addUndoActionListener(e -> model.undo());
        view.addMouseListener(new MyMouseListener());
        view.update();
    }

    /**
     * Inner class for handling mouse clicks on the game board (specifically the pits) using MouseAdapter.
     * Detects which pit was clicked on and calls the move method in the model with the pit's row and column.
     */
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
}
