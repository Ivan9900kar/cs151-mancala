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
    protected class GameState {
        private int turn; // which player has the turn (also used as iterator / checker for row)
        private int startCol; // iterator for column in matrix (which pit / mancala)
        private int endCol; // tracking where iteration ends
        private int numStones; // how many stones are being moved
        private int specialSteal; //how stones did the respective player's mancala have before the turn
        private int undosRemaining; // how many undos the current player has remaining
        private boolean moved; // whether the player has already moved in their turn
        /**
         * GameState class default constructor
         */
        private GameState() {
            this.turn = 0;
            this.startCol = 0;
            this.endCol = 0;
            this.numStones = 0;
            this.specialSteal = 0;
            this.undosRemaining = 3;
            this.moved = false;
        }
        public int getTurn() {
            return this.turn;
        }
        public int getUndosRemaining() {
            return this.undosRemaining;
        }
        public boolean isMoved() {
            return this.moved;
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
        updateView();
    }
    /**
     * Initializes the pits and mancalas.
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
    private void defaultView() {
        GameView view = new GameView(this);
        this.currentView = view;
        attach(view);
    }
    /**
     * Getter method for the GameState
     */
    public GameState getState() {
        return this.state;
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

    /**
     * To be implemented
     */
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
    public void move(int row, int col) {
        // if clicking the opponent's Pit, disallow and return (do nothing)
        if (row != state.turn) return;
        // if already moved, return
        if (state.moved) return;

        // check validity of move (is there a stone to take)
        int numStones = 0;

        if (containers[row][col] instanceof Pit p) {
            numStones = p.takeStones();
            state.startCol = col;
            state.numStones = numStones;
        }
        // if the number of stones in pit was 0, return
        if (numStones == 0) return;
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

        state.endCol = col;

        // special case where last stone is placed in empty pit on current player's side
        if (row == state.turn && col != containers[row].length - 1) {
            StoneContainer s = containers[row][col];
            if (s.getStones() == 1 && s instanceof Pit p) {
                Mancala m = (Mancala) containers[row][containers[row].length - 1];

                // take from current player's side and add to their mancala
                m.addStones(p.takeStones());

                // take from opponent's side and add to current player's mancala
                int oppCol = numPits - 1 - col;
                state.specialSteal = ((Pit) containers[(row + 1) % NUM_PLAYERS][oppCol]).takeStones();
                m.addStones(state.specialSteal);
            }
        }
        state.moved = true;

        updateView();
    }
    /**
     * Confirms the move of the current player, ends turn and starts turn of next player
     */
    public void confirm() {
        // if player hasn't moved yet, return
        if (!state.moved) return;
        //reset move status
        state.moved = false;

        // turn over to next player if not gaining another turn
        if (state.endCol != containers[state.turn].length - 1) {
            state.turn = (state.turn + 1) % NUM_PLAYERS;
            state.undosRemaining = 3;
        }

        updateView();
    }
    /**
     * Undos the move done by the current player, setting the board back to how it was before the move.
     */
    public void undo() {
        // if player hasn't moved or instead used up all of their undos, return
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

        state.moved = false;
        state.undosRemaining--;

        updateView();
    }
}
