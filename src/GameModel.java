import java.util.ArrayList;

/**
 * GameModel is a class taking the role of the Model in MVC, and represents the state of the game. It holds all of the data of the game as well as the game logic.
 */
public class GameModel {
    public final int NUM_PLAYERS = 2; // number of players in the game (currently needs to stay at 2)
    private StoneContainer[][] containers; // matrix to hold pits and mancala data (holding number of stones in each during game)
    private int numPits; // number of pits per player (excluding mancala)
    private ArrayList<GameView> views; // arraylist to hold views to update (listeners)
    private GameView currentView;
    private GameState state; // inner class object for additional data

    /**
     * GameState is an inner class that holds additional data for tracking and preserving the state of the game, apart from the board itself.
     */
    private class GameState {
        private int turn; // which player has the turn (also used as iterator / checker for row)
        private int col; // iterator for column in matrix (which pit / mancala)
        private int numStones; // how many stones are being moved
        private int undosRemaining; // how many undos the current player has remaining
        private boolean moved; // whether or not the player has already moved in their turn
        /**
         * GameState class default constructor
         */
        private GameState() {
            this.turn = 0;
            this.col = 0;
            this.numStones = 0;
            this.undosRemaining = 3;
            this.moved = false;
        }
    }
    /**
     * GameMode class constructor, with specification on the number of pits each player will have in the game.
     * @param numPits the number of pits for each player
     */
    public GameModel(int numPits) {
        this.numPits = numPits;
        containers = new StoneContainer[NUM_PLAYERS][numPits + 1];
        initContainers();
        this.views = new ArrayList<>();
        this.state = new GameState();
        defaultView();
    }
    /**
     * Initializes the pits and mancalas.
     */
    private void initContainers() {
        // for each player
        for (int i = 0; i < containers.length; i++) {
            // for each pit  
            for (int j = 0; j < numPits; j++) {
                containers[i][j] = new Pit();
            }
            // for the mancala
            containers[i][containers[i].length - 1] = new Mancala();
        }
    }
    private void defaultView() {
        GameView view = new GameView(this);
        this.currentView = view;
        attach(view);
    }

    /**
     * Getter method for the number of pits
     * @return
     */
    public int getNumPits() {
        return this.numPits;
    }
    /**
     * Getter method to access StoneContainer data
     * @return
     */
    public StoneContainer[][] getContainers() {
        return this.containers;
    }
    public void setCurrentView() {

    }
    /**
     * Getter method to access current displayed view
     * @return
     */
    public GameView getCurrentView() {
        return this.currentView;
    }
    /**
     * Attaches a view to to the model to be notified on any changes.
     * @param view the view to be attached as a listener
     * @return {@code true} if the view has been successfully added
     */
    public boolean attach(GameView view) {
        return views.add(view);
    }
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
     * Updates any views (the GUI of the game) upon changes, which call this method.
     */
    public void updateView() {
        for (GameView view : views) {
            view.update();
        }
    }
    /**
     * Moves the stones out of the specified pit counterclockwise on the board, per the core function of the game.
     * @param col the index of the pit chosen by the player to be the source of the move
     */
    public void move(int col) {
        // if already moved, return
        if (state.moved) return;
        // check validity of move (is there a stone to take)
        int row = state.turn;
        int numStones = 0;
        if (containers[row][col] instanceof Pit p) {
            numStones = p.takeStones();
            state.col = col;
            state.numStones = numStones;
        }
        // if a mancala or number of stones in pit was 0, return
        if (numStones == 0) return;
        // move the stones
        while (numStones > 0) {
            // do not put into opponent's mancala
            if (!(row != state.turn && col == containers[row].length - 1)) {
                // add to pit or own mancala
                containers[row][col].addStone();
                numStones--;
            }
            // traversal of the board counterclockwise, looping through the matrix
            col = (col + 1) % (numPits + 1);
            if (col == 0) row = (row + 1) % NUM_PLAYERS;
        }
        // special case where last stone is placed in empty pit on current player's side
        if (row == state.turn) {
            StoneContainer s = containers[row][col];
            if (s.getStones() == 1 && s instanceof Pit p) {
                Mancala m = (Mancala) containers[row][containers[row].length - 1];

                // take from current player's side and add to their mancala
                m.addStones(p.takeStones());

                // take from opponent's side and add to current player's mancala
                int oppCol = numPits - 1 - col;
                m.addStones(((Pit) containers[row][oppCol]).takeStones());
            }
        }
        state.moved = true;
    }
    /**
     * Confirms the move of the current player, ends turn and starts turn of next player
     */
    public void confirm() {
        // if player hasnt moved yet, return
        if (!state.moved) return;
        // turn over to next player
        state.turn = (state.turn + 1) % NUM_PLAYERS;
        // reset 
        state.moved = false;
        state.undosRemaining = 3;
    }
    /**
     * Undos the move done by the current player, setting the board back to how it was before the move.
     */
    public void undo() {
        // if player hasnt moved or instead used up all of their undos, return
        if (!state.moved || state.undosRemaining < 1) return;

        int row = state.turn;
        int col = state.col;
        int numStones = state.numStones;

        // remove stones that were added
        while (numStones > 0) {
            // do not remove from opponent's mancala
            if (!(row != state.turn && col == containers[row].length - 1)) {
                // remove from pit or current player's mancala
                containers[row][col].removeStone();
                numStones--;
            }

            //traversal
            col = (col + 1) % (numPits + 1);
            if (col == 0) row = (row + 1) % NUM_PLAYERS;
        }
        // add back stones to original pit (where the move had began)
        containers[row][col].setStones(state.numStones);

        state.moved = false;
        state.undosRemaining--;

        updateView();
    }
}
