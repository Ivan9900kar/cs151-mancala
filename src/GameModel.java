import java.util.ArrayList;

public class GameModel {
    private final int NUM_PLAYERS = 2;
    private StoneContainer[][] containers; // matrix to hold pits and mancala data (holding number of stones in each during game)
    private int numPits; // number of pits per player (excluding mancala)
    private ArrayList<GameView> views; // arraylist to hold views to update (listeners)
    private GameState state;

    /**
     * Class that holds data for tracking and preserving the state of the game.
     */
    private class GameState {
        private int turn; //same as row
        private int col;
        private int numStones;
        private int undosRemaining;
        private boolean moved;
        private GameState() {
            this.turn = 0;
            this.col = 0;
            this.numStones = 0;
            this.undosRemaining = 3;
            this.moved = false;
        }
    }
    /**
     * 
     * @param pits
     */
    public GameModel(int numPits) {
        this.numPits = numPits;
        containers = new StoneContainer[NUM_PLAYERS][numPits + 1];
        initContainers();
        this.views = new ArrayList<>();
        this.state = new GameState();
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
    /**
     * Attach a view to to the model to be notified on any changes.
     * @param view the view to be attached as a listener
     * @return {@code true} if the view has been successfully added
     */
    public boolean attach(GameView view) {
        return views.add(view);
    }
    /**
     * Sets each pit to start with the specified number of stones
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
     * 
     */
    public void updateView() {
        for (GameView view : views) {
            //view.update();
        }
    }
    /**
     * 
     * @param col the index of the pit chosen by the player to be the source of the move
     */
    public void move(int col) {
        // check if already moved
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
            // traversal
            col = (col + 1) % (numPits + 1);
            if (col == 0) row = (row + 1) % NUM_PLAYERS;
        }
        // special move
        if (row == state.turn) {
            StoneContainer s = containers[row][col];
            if (s.getStones() == 1 && s instanceof Pit p) {
                Mancala m = (Mancala) containers[row][containers[row].length - 1];

                // take from our side and add to our mancala
                m.addStones(p.takeStones());

                // take from opponent's side and add to our mancala
                int oppCol = numPits - 1 - col;
                m.addStones(((Pit) containers[row][oppCol]).takeStones());
            }
        }
        state.moved = true;
    }
    /**
     * 
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
     * 
     */
    public void undo() {
        // if player hasnt moved or instead used up all of their undos, return
        if (!state.moved || state.undosRemaining < 1) return;

        int row = state.turn;
        int col = state.col;
        int numStones = state.numStones;

        // remove stones that were added
        while (numStones > 0) {
            //do not remove from opponent's mancala
            if (!(row != state.turn && col == containers[row].length - 1)) {
                //remove from pit or own mancala
                containers[row][col].removeStone();
                numStones--;
            }

            //traversal
            col = (col + 1) % (numPits + 1);
            if (col == 0) row = (row + 1) % NUM_PLAYERS;
        }
        // !!! CONFIRM VALIDITY OF THIS !!!
        // add back stones to original pit
        containers[row][col].setStones(state.numStones);

        state.moved = false;
        state.undosRemaining--;
    }
}
