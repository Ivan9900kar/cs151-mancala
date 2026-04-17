import java.util.ArrayList;

public class GameModel {
    private final int NUM_PLAYERS = 2;
    private StoneContainer[][] containers;
    private int pits;
    private ArrayList<GameView> views;
    private State state;

    private class State {
        private int turn; //same as row
        private int col;
        private int numStones;
        private boolean undo;
        private int undos;
        private boolean moved;
        private State() {
            this.turn = 0;
            this.col = 0;
            this.numStones = 0;
            this.undo = false;
            this.undos = 0;
            this.moved = false;
        }
    }
    public GameModel(int pits) {
        this.pits = pits;
        containers = new StoneContainer[NUM_PLAYERS][pits + 1];
        initContainers();
        this.views = new ArrayList<>();
        this.state = new State();
    }
    private void initContainers() {
        for (int i = 0; i < containers.length; i++) {
            for (int j = 0; j < pits; j++) {
                containers[i][j] = new Pit();
            }
            containers[i][containers[i].length - 1] = new Mancala();
        }
    }
    public boolean attach(GameView view) {
        return views.add(view);
    }
    public void startingStones(int numStones) {
        for (StoneContainer[] row : containers) {
            for (int i = 0; i < pits; i++) {
                row[i].setStones(numStones);
            }
        }
    }
    public void updateView() {
        for (GameView view : views) {
            view.update();
        }
    }
    public void move(int col) {
        //check if already moved
        if (state.moved) return;

        //check validity of move (is there a stone to take)
        int row = state.turn;
        int numStones = 0;
        if (containers[row][col] instanceof Pit p) {
            numStones = p.takeStones();
            state.col = col;
            state.numStones = numStones;
        }
        if (numStones == 0) return;

        //move the stones
        while (numStones > 0) {
            //do not put into opponent's mancala
            if (!(row != state.turn && col == containers[row].length - 1)) {
                //add to pit or own mancala
                containers[row][col].addStone();
                numStones--;
            }

            //traversal
            col = (col + 1) % (pits + 1);
            if (col == 0) row = (row + 1) % NUM_PLAYERS;
        }

        //special move
        if (row == state.turn) {
            StoneContainer s = containers[row][col];
            if (s.getStones() == 1 && s instanceof Pit p) {
                Mancala m = (Mancala) containers[row][pits + 1];

                //take from our side
                m.addStones(p.takeStones());

                //take from opponent's side
                int oppCol = pits - 1 - col;
                m.addStones(((Pit) containers[row][oppCol]).takeStones());
            }
        }

        state.moved = true;
    }
    public void confirm() {
        if (!state.moved) return;

        state.turn = (state.turn + 1) % NUM_PLAYERS;
        state.undo = false;
        state.moved = false;
        state.undos = 0;
    }
    public void undo() {
        if (state.undo) return;

        int row = state.turn;
        int col = state.col;
        int numStones = state.numStones;

        //move the stones
        while (numStones > 0) {
            //do not remove from opponent's mancala
            if (!(row != state.turn && col == containers[row].length - 1)) {
                //remove from pit or own mancala
                containers[row][col].removeStone();
                numStones--;
            }

            //traversal
            col = (col + 1) % (pits + 1);
            if (col == 0) row = (row + 1) % NUM_PLAYERS;
        }

        state.undo = true;
        state.moved = false;
    }
}
