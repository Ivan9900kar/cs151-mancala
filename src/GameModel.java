/**
 * Title: CS 151 Mancala Project
 * Authors: Anthony Ryabov, Darren Vu, Isaiah Mak
 */

import java.util.ArrayList;

/**
 * Model class for the Mancala game, representing the state of the game. It holds all the data of the game as well as the game logic.
 */
public class GameModel {

    // DATA MEMBERS

    /**
     * Number of players in the game (currently needs to stay at 2)
     */
    public final int NUM_PLAYERS = 2;
    /**
     * Matrix to hold pits and mancala data (holding number of stones in each during game)
     */
    private StoneContainer[][] containers;
    /**
     * Number of pits per player (excluding mancala)
     */
    private int numPits;
    /**
     * ArrayList to hold views to update (listeners)
     */
    private ArrayList<GameView> views;
    /**
     * Reference to the current main view being displayed (for game end menu)
     */
    private GameView currentView;
    /**
     * Inner class object for additional data
     */
    private GameState state;
    /**
     * Enum for the type of move completed by the player
     */
    public enum MOVE_TYPE { 
        NEW_TURN,NORMAL, FREE_TURN, CAPTURE, UNDO, 
    }
    /**
     * GameState is an inner class that holds additional data for tracking and preserving the state of the game, apart from the board itself.
     */
    protected class GameState {
        /**
         * Which player has the turn (also used as iterator / checker for row)
         */
        private int turn;
        /**
         * Iterator for column in the container matrix (indicates which pit / mancala)
         */
        private int startCol;
        /**
         * Tracking where iteration ends
         */
        private int endCol;
        /**
         * How many stones are being moved
         */
        private int numStones;
        /**
         * How stones did the respective player's mancala have before the turn
         */
        private int specialSteal;
        /**
         * How many undos the current player has remaining
         */
        private int undosRemaining;
        /**
         * Whether the player has already moved in their turn
         */
        private boolean moved;
        /**
         * What type of move the player has made
         */
        private MOVE_TYPE moveType;
        /**
         * GameState class default constructor.
         */
        private GameState() {
            this.turn = 0;
            this.startCol = 0;
            this.endCol = 0;
            this.numStones = 0;
            this.specialSteal = 0;
            this.undosRemaining = 3;
            this.moved = false;
            this.moveType = MOVE_TYPE.NEW_TURN;
        }
        /**
         * Getter method for the current turn.
         * @return an int representing the current turn (0 for player A, 1 player B, etc.)
         */
        public int getTurn() {
            return this.turn;
        }
        /**
         * Getter method for the number of undos remaining.
         * @return the number of undos remaining
         */
        public int getUndosRemaining() {
            return this.undosRemaining;
        }
        /**
         * Getter method for whether the current player has moved (selected a valid pit)
         * @return true if moved, false if not
         */
        public boolean isMoved() {
            return this.moved;
        }
        /**
         * Getter method for the type of move most recently processed.
         * @return the type of move
         */
        public MOVE_TYPE getMoveType() {
            return this.moveType;
        }
        /**
         * Getter method for the column number of the pit that the last stone was placed in after a move.
         * @return the column of the pit with the last stone
         */
        public int getEndCol() {
            return this.endCol;
        }
    }


    // CONSTRUCTORS

    /**
     * GameModel class constructor, with specification on the number of pits each player will have in the game.
     * @param numPits the number of pits for each player
     */
    public GameModel(int numPits) {
        this.numPits = numPits;
        containers = new StoneContainer[NUM_PLAYERS][numPits + 1];
        initContainers();
        this.views = new ArrayList<>();
        this.state = new GameState();
    }

    // GETTERS

    /**
     * Getter method for the GameState object.
     * @return the GameState object
     */
    public GameState getState() {
        return this.state;
    }
    /**
     * Getter method for the number of pits.
     * @return the number of pits
     */
    public int getNumPits() {
        return this.numPits;
    }
    /**
     * Getter method to access StoneContainer data.
     * @return the matrix of StoneContainer objects (all pits and mancalas)
     */
    public StoneContainer[][] getContainers() {
        return this.containers;
    }
    /**
     * Getter method to access current displayed view.
     * @return the current main GameView object
     */
    public GameView getCurrentView() {
        return this.currentView;
    }


    // SETTERS

    /**
     * Sets each pit to start with the specified number of stones.
     * @param numStones the number of stones each pit will start out with
     */
    public void setStartingStones(int numStones) {
        for (StoneContainer[] row : containers) {
            for (int i = 0; i < numPits; i++) {
                row[i].setStones(numStones);
            }
        }
    }
    /**
     * Sets the strategy for the board style for all pits and mancalas.
     * @param strategy the strategy object for the board style to be set to
     */
    public void setStrategy(BoardStyle strategy) {
        for (StoneContainer[] row : containers) {
            for (StoneContainer container : row) {
                if (container instanceof Pit p) {
                    p.setStrategy(strategy);
                } else if (container instanceof Mancala m) {
                    m.setStrategy(strategy);
                }
            }
        }
    }
    /**
     * Attaches a view to to the model to be notified on any changes.
     * Sets the current view to the attached view from the most recent call.
     * @param view the view to be attached as a listener
     * @return {@code true} if the view has been successfully added
     */
    public boolean attach(GameView view) {
        boolean added = views.add(view);
        if (added)
        {
            this.currentView = view;
            currentView.setGameModel(this);
            updateView();
        }
        return added;
    }


    // CORE GAME FUNCTIONS

    /**
     * Initializes the pits and mancalas in the containers matrix with their appropriate row and column.
     * Postcondition: the containers matrix is initialized with containers set to their appropriate row and column.
     */
    private void initContainers() {
        // for each player
        for (int i = 0; i < containers.length; i++) {
            // for each pit  
            for (int j = 0; j < numPits; j++) {
                Pit p = new Pit();
                p.setRow(i);
                p.setCol(j);
                containers[i][j] = p;
            }
            // for the mancala
            Mancala m = new Mancala();
            m.setRow(i); 
            containers[i][containers[i].length - 1] = m;
        }
    }
    /**
     * Moves the stones out of the specified pit and places them at each subsequent pit or own mancala counterclockwise on the board.
     * @param row the row of the pit chosen by the player to be the source of the move
     * @param col the column of the pit chosen by the player to be the source of the move
     * Postcondition: The selected move is modified in data and updated in view. If the row selected is not the player's, this does nothing.
     */
    public void move(int row, int col) {
        // if clicking opponent's containers, exit
        if (row != state.turn) return;

        // if already moved, exit
        if (state.moved) return;

        // check validity of move (if it is pit and not mancala, and is there a stone to take)
        int numStones = 0;
        if (containers[row][col] instanceof Pit p) {
            numStones = p.takeStones();
        }
        // if the number of stones in pit was 0, or if selected container was a mancala, exit
        if (numStones == 0) return;

        // save initial state of move for undoing
        state.startCol = col;
        state.numStones = numStones;

        // move the stones
        while (numStones > 0) {
            // traversal of the board counterclockwise, looping through the matrix
            col = (col + 1) % (numPits + 1);
            if (col == 0) row = (row + 1) % NUM_PLAYERS;
            // do not put into opponent's mancala
            if (!(row != state.turn && col == containers[row].length - 1)) {
                // add to pit or own mancala
                containers[row][col].addStone();
                numStones--;
            }
        }

        // update game state to reflect move
        state.moveType = MOVE_TYPE.NORMAL;
        state.moved = true;
        state.endCol = col;

        // special case where last stone is placed in empty pit on current player's side
        if (row == state.turn && containers[row][col] instanceof Pit p && p.getStones() == 1) {
            state.moveType = GameModel.MOVE_TYPE.CAPTURE;

            Mancala m = (Mancala) containers[row][containers[row].length - 1];

            // take from current player's side and add to their mancala
            m.addStones(p.takeStones());

                // take from opponent's side and add to current player's mancala
                int oppCol = numPits - 1 - col;
                state.specialSteal = ((Pit) containers[(row + 1) % NUM_PLAYERS][oppCol]).takeStones();
                m.addStones(state.specialSteal);
        }

        // special case where last stone is placed in current player's mancala
        if (col == containers[state.turn].length - 1) {
            state.moveType = MOVE_TYPE.FREE_TURN;
        }

        // update view
        updateView();
    }
    /**
     * Confirms the move of the current player. The next turn or end game is calculated.
     * Precondition: a move has been selected
     * Postcondition: the next move can be selected
     */
    public void confirm() {
        // if player hasn't moved yet, exit
        if (!state.moved) return;

        // check if game end (when all of a player's pits are empty)
        for (int i = 0; i < NUM_PLAYERS; i++) {
            int sum = 0;
            for (int j = 0; j < numPits; j++) {
                sum += containers[i][j].getStones();
            }
            if (sum == 0) {
                gameEnd();
                return;
            }
        }
        // reset move state
        state.moved = false;
        state.moveType = MOVE_TYPE.NEW_TURN;
        state.undosRemaining = 3;

        // turn over to next player if not using free turn
        if (state.endCol != containers[state.turn].length - 1) {
            state.turn = (state.turn + 1) % NUM_PLAYERS;
        }

        // update view
        updateView();
    }
    /**
     * Undoes the move done by the current player, setting the board back to how it was before the move.
     * Previous board is re-calculated rather than stored.
     * Precondition: a move has been selected
     * Postcondition: the move is reverted and a new move can be selected
     */
    public void undo() {
        // if player hasn't moved or instead used up all of their undos, exit
        if (!state.moved || state.undosRemaining < 1) return;

        int row = state.turn;
        int col = state.startCol;
        int numStones = state.numStones;

        // add back stones to original pit (where the move had began)
        containers[row][col].setStones(state.numStones);

        // remove stones that were added
        while (numStones > 0) {
            //traversal
            col = (col + 1) % (numPits + 1);
            if (col == 0) row = (row + 1) % NUM_PLAYERS;

            // do not remove from opponent's mancala
            if (!(row != state.turn && col == containers[row].length - 1)) {
                StoneContainer container = containers[row][col];
                // remove from pit or current player's mancala

                // if removing from a pit with nothing on the player's side, indicates it was the special move case
                if (container.getStones() == 0 && row == state.turn) {
                    Mancala m = (Mancala) containers[state.turn][containers[row].length - 1];
                    container.addStone();
                    m.removeStone();
                    containers[(state.turn + 1) % NUM_PLAYERS][numPits - 1 - col].addStones(state.specialSteal);
                    m.addStones(-state.specialSteal);
                }
                container.removeStone(); //on special case, removing from an empty pit does nothing (deferral)
                numStones--;
            }
        }
        // set move state to reflect undo
        state.moved = false;
        state.moveType = MOVE_TYPE.UNDO;
        state.undosRemaining--;

        // update view
        updateView();
    }
    /**
     * Called on game end, gathers all stones from players' pits and adds them to their respective mancalas, then calls game end menu.
     * Precondition: all pits on one player's row are empty
     * Postcondition: remaining stones in pits are added to that player's mancala, then triggers end game menu
     */
    public void gameEnd() {
        // gather remaining stones
        for (int i = 0; i < NUM_PLAYERS; i++) {
            Mancala m = (Mancala) containers[i][numPits];
            for (int j = 0; j < numPits; j++) {
                m.addStones(((Pit) containers[i][j]).takeStones());
            }
        }
        // update view to reflect gathered stones in mancalas
        updateView();

        // call game end menu with final scores
        currentView.endMenu(containers[0][numPits].getStones(), containers[1][numPits].getStones());
    }
    /**
     * Resets the game to the initial state and updates the view. Resets the game state.
     * @param numPits the new number of pits each player will have
     * @param numStones the new number of stones each pit will start out with
     */
    public void reset(int numStones) {
        // reset number of pits and arrays
        //this.numPits = numPits;
        //containers = new StoneContainer[NUM_PLAYERS][numPits + 1];
        //initContainers();
        // reset number of stones in all containers
        for (StoneContainer[] row : containers) {
            for (StoneContainer container : row) {
                container.setStones(0);
            }
        }
        // new number of starting stones
        setStartingStones(numStones);
        // reset game state
        state = new GameState();
        // update view
        updateView();
    }
    /**
     * Updates any views (the GUI of the game) upon changes, which call this method.
     */
    public void updateView() {
        for (GameView view : views) {
            view.update();
        }
    }
}